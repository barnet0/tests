package com.digiwin.ecims.platforms.pdd.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.SkuColorSizeAttribute;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.pdd.bean.domain.item.Item;
import com.digiwin.ecims.platforms.pdd.bean.domain.item.ItemSku;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool.ItemStatus;

public class AomsitemTTranslator {

  private Object object;
  
  public AomsitemTTranslator(Object itemList) {
    super();
    this.object = itemList;
  }
  
  public List<AomsitemT> doTranslate(String storeId, ItemStatus itemStatus) {
    if (object instanceof GetGoodsResponse) {
      return parsePddItemToAomsitemT(storeId, itemStatus);
    } else {
      return new ArrayList<AomsitemT>();
    }
  }
  
  private List<AomsitemT> parsePddItemToAomsitemT(String storeId, ItemStatus itemstatus) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    GetGoodsResponse getGoodsResponse = (GetGoodsResponse)this.object;
    for (Item item : getGoodsResponse.getGoodList()) {
      for (ItemSku itemSku : item.getSkuList()) {
        AomsitemT aomsitemT = new AomsitemT();
        
        aomsitemT.setId(CommonUtil.checkNullOrNot(item.getGoodsID()));
        aomsitemT.setAoms003(CommonUtil.checkNullOrNot(item.getGoodsName()));
        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getSkuID()));
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(itemSku.getOuterID()));
        aomsitemT.setAoms007(itemstatus == ItemStatus.ONSALE ? "onsale" : (itemstatus == null ? "" : "instock"));
        
//        System.out.println(item.getGoodsID() + " " + itemSku.getSpec());
        if (StringTool.isNotEmpty(itemSku.getSpec())) {
          SkuColorSizeAttribute skuColorSize = getSkuColorSizeAttribute(itemSku.getSpec().trim());
          
          aomsitemT.setAoms008(skuColorSize.getColor());
          aomsitemT.setAoms009(skuColorSize.getSize());
        }
        
        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSku.getNum()));
        
        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(item.getGoodsPrice()));
        
        aomsitemT.setStoreId(storeId);
        aomsitemT.setStoreType(PddCommonTool.STORE_TYPE);
        
        Date now = new Date();
        aomsitemT.setAomsstatus("0");
        aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
        
        aomsitemTs.add(aomsitemT);
      }
    }
    
    return aomsitemTs;
  }
  
  /**
   * 根据SKU属性的值，从中获取颜色和尺寸
   * @param skuSpec sku属性字符串
   * @return
   */
  private PddSkuColorSizeAttribute getSkuColorSizeAttribute(String skuSpec) {
    String color = "";
    String size = "";
    String[] splittedSkuSpec = skuSpec.split(PddCommonTool.SKU_SPEC_DELIMITER);
    for (String skuSpecSplitted : splittedSkuSpec) {
      if (StringTool.isEmpty(skuSpecSplitted)) {
        continue;
      }
      // 数字开头的属性字符串为尺寸，否则为颜色
      if (skuSpecSplitted.charAt(0) >= '0' && skuSpecSplitted.charAt(0) <= '9') {
        size = skuSpecSplitted;
      } else {
        color = skuSpecSplitted;
      }
    }
    
    return new PddSkuColorSizeAttribute(color, size);
  }
  
}

//class SkuColorSizeAttribute {
//
//  private String color;
//
//  private String size;
//
//  public String getColor() {
//    return color;
//  }
//
//  public void setColor(String color) {
//    this.color = color;
//  }
//
//  public String getSize() {
//    return size;
//  }
//
//  public void setSize(String size) {
//    this.size = size;
//  }
//
//  public SkuColorSizeAttribute() {}
//
//  public SkuColorSizeAttribute(String color, String size) {
//    this.color = color;
//    this.size = size;
//  }
//
//  @Override
//  public String toString() {
//    if (color == null || color.length() == 0) {
//      return size;
//    }
//    if (size == null || size.length() == 0) {
//      return color;
//    }
//    return color + ";" + size;
//  }
//
//}
