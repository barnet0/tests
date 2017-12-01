package com.digiwin.ecims.platforms.dhgate.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemAttrUpdate;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemAttrVal;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemImgUpdate;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemSkuAttrvalUpdate;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemSkuUpdate;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;

public class AomsitemTTranslator {

  private Object detail;

  public AomsitemTTranslator(Object detail) {
    super();
    this.detail = detail;
  }

  public List<AomsitemT> doTranslate(String storeId) {
    if (this.detail instanceof ItemGetResponse) {
      return parseDhItemToAomsitemT(storeId);
    } else {
      return new ArrayList<AomsitemT>();
    }
  }

  private List<AomsitemT> parseDhItemToAomsitemT(String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    ItemGetResponse detail = (ItemGetResponse) this.detail;

    for (ItemSkuUpdate itemSku : detail.getItemSkuList()) {
      AomsitemT aomsitemT = new AomsitemT();

      aomsitemT.setId(CommonUtil.checkNullOrNot(detail.getItemCode()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(detail.getItemBase().getItemName()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getSkuId()));
      String outerSkuCode = itemSku.getSkuCode();
      if (itemSku.getItemSkuInventoryList() != null) {
        String innerSkuCode = itemSku.getItemSkuInventoryList().get(0).getSkuCode();
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(innerSkuCode));
      } else {
//        System.out.println(detail.getItemCode());
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(outerSkuCode));
      }
      
      
      if (itemSku.getItemSkuAttrValueList() != null) {
        DhgateSkuColorSizeAttribute sColorSizeAttribute = 
            getSkuColorSizeAttribute(itemSku, detail.getItemAttrList());
        aomsitemT.setAoms006(CommonUtil.checkNullOrNot(sColorSizeAttribute.toString()));
        aomsitemT.setAoms008(CommonUtil.checkNullOrNot(sColorSizeAttribute.getColor()));
        aomsitemT.setAoms009(CommonUtil.checkNullOrNot(sColorSizeAttribute.getSize()));
      }
      
      int itemStatus = itemSku.getSaleStatus();
      aomsitemT.setAoms007(CommonUtil.checkNullOrNot(itemStatus == 1 ? "onsale" : "instock"));

      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(DhgateCommonTool.STORE_TYPE);

      aomsitemT.setAoms013(CommonUtil.checkNullOrNot(detail.getItemBase().getHtmlContent()));

      if (detail.getItemImgList().size() > 0) {
        ItemImgUpdate itemImg = detail.getItemImgList().get(0);
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(itemImg.getImgUrl()));
      }

      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSku.getInventory()));
      aomsitemT.setAoms019(CommonUtil.checkNullOrNot(detail.getOperateDate()));

      Date now = new Date();
      aomsitemT.setAomsstatus("0");
      aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsitemTs.add(aomsitemT);
    }

    return aomsitemTs;
  }

  /**
   * 根据SKU属性的值，从商品属性列表中获取颜色和尺寸
   * @param itemSku
   * @param itemAttrList
   * @return
   */
  private DhgateSkuColorSizeAttribute getSkuColorSizeAttribute(ItemSkuUpdate itemSku, 
      List<ItemAttrUpdate> itemAttrList) {
    String color = "";
    String size = "";
    int attrId = 0;
    int attrValId = 0;
    for (ItemSkuAttrvalUpdate itemSkuAttrVal : itemSku.getItemSkuAttrValueList()) {
      attrId = itemSkuAttrVal.getAttrId();
      attrValId = itemSkuAttrVal.getAttrValId();
      if (DhgateCommonTool.isCodeExist(attrId, DhgateCommonTool.ITEM_COLOR_ATTR_CODE)) {
        color = getAttrValNameByAttrIdAndAttrValId(itemSkuAttrVal.getAttrId(), attrValId, itemAttrList);
      } else if (DhgateCommonTool.isCodeExist(attrId, DhgateCommonTool.ITEM_SIZE_ATTR_CODE)) {
        size = getAttrValNameByAttrIdAndAttrValId(itemSkuAttrVal.getAttrId(), attrValId, itemAttrList);
      }
      if (color.length() > 0 && size.length() > 0) {
        break;
      }
    }
    return new DhgateSkuColorSizeAttribute(color, size);
  }
  
  /**
   * 从商品属性列表中获取对应属性ID和属性值ID对应的中文名称或英文名称
   * @param attrId
   * @param attrValId
   * @param itemAttrList
   * @return 属性中文名称
   */
  private String getAttrValNameByAttrIdAndAttrValId(long attrId, long attrValId, List<ItemAttrUpdate> itemAttrList) {
    for (ItemAttrUpdate itemAttrUpdate : itemAttrList) {
      if (itemAttrUpdate.getAttrId() == attrId) {
        for (ItemAttrVal itemAttrVal : itemAttrUpdate.getItemAttrValList()) {
          if (itemAttrVal.getAttrValId() == attrValId) {
            return itemAttrVal.getLineAttrvalNameCn().trim().length() != 0 ?
                itemAttrVal.getLineAttrvalNameCn() : itemAttrVal.getLineAttrvalName();
          }
        }
      }
    }
    return "";
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
//
//}
