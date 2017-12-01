package com.digiwin.ecims.platforms.kaola.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_edit_list;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Raw_item_edit;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Raw_sku;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Sku_list;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSearchResponse;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.ItemStatus;

public class AomsitemTTranslator {

  private Object object;
  KaolaSkuColorSizeAttribute KaolaSkuColorSize=new KaolaSkuColorSizeAttribute();
  
  public AomsitemTTranslator(Object itemList) {
    super();
    this.object = itemList;
  }
  
  public List<AomsitemT> doTranslate(String storeId, ItemStatus itemStatus) {
    if (object instanceof ItemSearchResponse) {
      return parseKaolaItemToAomsitemTList(storeId, itemStatus);
    } else if (object instanceof ItemGetResponse) {
        return parseKaolaItemToAomsitemT(storeId, itemStatus);
      }else {
      return new ArrayList<AomsitemT>();
    }
  }
  
  private List<AomsitemT> parseKaolaItemToAomsitemTList(String storeId, ItemStatus itemstatus) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    ItemSearchResponse itemSearchResponse = (ItemSearchResponse)this.object;
    
    for (Item_edit_list item : itemSearchResponse.getItem_edit_list()) {
    	Raw_item_edit rawItem=item.getRaw_item_edit();
      for (Sku_list itemSku : item.getSku_list()) {
        AomsitemT aomsitemT = new AomsitemT();
        Raw_sku rawSku=itemSku.getRaw_sku();
              
       // aomsitemT.setId(CommonUtil.checkNullOrNot(rawItem.getId()));
        aomsitemT.setId(CommonUtil.checkNullOrNot(item.getKey()));
        aomsitemT.setAoms002(CommonUtil.checkNullOrNot(rawItem.getItem_NO()));
        aomsitemT.setAoms003(CommonUtil.checkNullOrNot(rawItem.getName()));
        
       
       // aomsitemT.setAoms004(CommonUtil.checkNullOrNot(rawSku.getId()));
        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getKey()));
       // aomsitemT.setAoms005(CommonUtil.checkNullOrNot(rawSku.getMaterial_num()));
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(rawSku.getBar_code()));
       
        aomsitemT.setAoms007(itemstatus == ItemStatus.ONSALE ? "onsale" : (itemstatus == null ? rawItem.getItem_status() : "instock"));
        //aomsitemT.setAoms007(rawItem.getItem_status());
        KaolaSkuColorSize.setSku_property_list(itemSku.getSku_property_list());
        
        aomsitemT.setAoms006(KaolaSkuColorSize.getKaolaSkuProperty());
         aomsitemT.setAoms008(KaolaSkuColorSize.getKaolaSkuColor());
         aomsitemT.setAoms009(KaolaSkuColorSize.getKaolaSkuSize());
            
        aomsitemT.setModified(CommonUtil.checkNullOrNot(DateTimeTool.stampToDate(String.valueOf(rawItem.getUpdate_time()))));
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(item.getItem_image_list().get(0).getImage_url()));
        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(rawSku.getStock_can_sale()));
        
        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(rawSku.getSale_price()));
        
        aomsitemT.setStoreId(storeId);
        aomsitemT.setStoreType(KaolaCommonTool.STORE_TYPE);
        
        Date now = new Date();
        aomsitemT.setAomsstatus("0");
        aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
        
        aomsitemTs.add(aomsitemT);
      }
    }
    
    return aomsitemTs;
  }
  
  private List<AomsitemT> parseKaolaItemToAomsitemT(String storeId, ItemStatus itemstatus) {
	    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
	    ItemGetResponse ItemGetResponse = (ItemGetResponse)this.object;
	    String itemstat=null;
	 //   for (Item_edit_list item : itemSearchResponse.getItem_edit_list()) {
	    ItemGetResponse item=ItemGetResponse;
	    	Raw_item_edit rawItem=item.getRaw_item_edit();
	      for (Sku_list itemSku : item.getSku_list()) {
	        AomsitemT aomsitemT = new AomsitemT();
	        Raw_sku rawSku=itemSku.getRaw_sku();
	              
	       // aomsitemT.setId(CommonUtil.checkNullOrNot(rawItem.getId()));
	        aomsitemT.setId(CommonUtil.checkNullOrNot(item.getKey()));
	        aomsitemT.setAoms002(CommonUtil.checkNullOrNot(rawItem.getItem_NO()));
	        aomsitemT.setAoms003(CommonUtil.checkNullOrNot(rawItem.getName()));
	        
	       
	       // aomsitemT.setAoms004(CommonUtil.checkNullOrNot(rawSku.getId()));
	        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getKey()));
	       // aomsitemT.setAoms005(CommonUtil.checkNullOrNot(rawSku.getMaterial_num()));
	        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(rawSku.getBar_code()));
	        itemstat=rawItem.getItem_status();	     
	        aomsitemT.setAoms007(itemstat.equals("ON_SALE") ? "onsale" : (itemstat.equals("UN_SALE") ? "instock":rawItem.getItem_status() ));
	       // aomsitemT.setAoms007(rawItem.getItem_status());
	        KaolaSkuColorSize.setSku_property_list(itemSku.getSku_property_list());
	        
	        aomsitemT.setAoms006(KaolaSkuColorSize.getKaolaSkuProperty());
	         aomsitemT.setAoms008(KaolaSkuColorSize.getKaolaSkuColor());
	         aomsitemT.setAoms009(KaolaSkuColorSize.getKaolaSkuSize());
	            
	        aomsitemT.setModified(CommonUtil.checkNullOrNot(DateTimeTool.stampToDate(String.valueOf(rawItem.getUpdate_time()))));
	        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(item.getItem_image_list().get(0).getImage_url()));
	        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(rawSku.getStock_can_sale()));
	        
	        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(rawSku.getSale_price()));
	        
	        aomsitemT.setStoreId(storeId);
	        aomsitemT.setStoreType(KaolaCommonTool.STORE_TYPE);
	        
	        Date now = new Date();
	        aomsitemT.setAomsstatus("0");
	        aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
	        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
	        
	        aomsitemTs.add(aomsitemT);
	      }
	  //  }
	    
	    return aomsitemTs;
	  }
  
  /**
   * 根据SKU属性的值，从中获取颜色和尺寸
   * @param skuSpec sku属性字符串
   * @return
   */
 /* private kaolaSkuColorSizeAttribute getSkuColorSizeAttribute(String skuSpec) {
    String color = "";
    String size = "";
    String[] splittedSkuSpec = skuSpec.split(KaolaCommonTool.SKU_SPEC_DELIMITER);
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
    
    return new kaolaSkuColorSizeAttribute(color, size);
  }*/
  
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
