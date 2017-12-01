package com.digiwin.ecims.platforms.aliexpress.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.category.AeAttrValueDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.category.AeopAttributeDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.item.AeopAeProductSKUs;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.item.AeopSKUProperty;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindAeProductByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AomsitemTTranslator {

//  private Logger logger = LoggerFactory.getLogger(AomsitemTTranslator.class);
  
  private Object detail;
  
  private List<AeopAttributeDTO> attributeDTOs;

  public AomsitemTTranslator(Object detail, List<AeopAttributeDTO> attributeDTOs) {
    super();
    this.detail = detail;
    this.attributeDTOs = attributeDTOs;
  }
  
  public List<AomsitemT> doTranslate(String storeId) {
    if (this.detail instanceof FindAeProductByIdResponse) {
      return parseAeProductToAomsitemT(storeId);
    } else {
      return new ArrayList<AomsitemT>();
    }
  }

  private List<AomsitemT> parseAeProductToAomsitemT(String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    FindAeProductByIdResponse itemDetail = (FindAeProductByIdResponse) this.detail;
    
//    logger.info("productId:{}, categoryId:{}", itemDetail.getProductId(), itemDetail.getCategoryId());
    
    for (AeopAeProductSKUs itemSku : itemDetail.getAeopAeProductSKUs()) {
      AomsitemT aomsitemT = new AomsitemT();
      
      aomsitemT.setId(CommonUtil.checkNullOrNot(itemDetail.getProductId()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(itemDetail.getSubject()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getId()));
      aomsitemT.setAoms005(CommonUtil.checkNullOrNot(itemSku.getSkuCode()));
      aomsitemT.setAoms007(CommonUtil.checkNullOrNot(itemDetail.getProductStatusType().equals("onSelling") ? "onsale" : "instock"));
      
      AliexpressSkuColorSizeAttribute skuColorSizeAttribute = getSkuColorSizeAttribute(itemSku.getAeopSKUProperty());
      aomsitemT.setAoms006(CommonUtil.checkNullOrNot(skuColorSizeAttribute.toString()));
      
      aomsitemT.setAoms008(CommonUtil.checkNullOrNot(skuColorSizeAttribute.getColor()));
      aomsitemT.setAoms009(CommonUtil.checkNullOrNot(skuColorSizeAttribute.getSize()));
      
      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(AliexpressCommonTool.STORE_TYPE);
      
      aomsitemT.setAoms014(CommonUtil.checkNullOrNot(getProductMainPic(itemDetail.getImageURLs())));
      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSku.getIpmSkuStock()));
      aomsitemT.setAoms024(CommonUtil.checkNullOrNot(itemSku.getSkuPrice()));
      
      Date now = new Date();
      aomsitemT.setAomsstatus("0");
      aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsitemTs.add(aomsitemT);
    }
    
    return aomsitemTs;
  }
  
  private String getProductMainPic(String imageUrls) {
    String[] images = imageUrls.split(AliexpressCommonTool.VALUE_DELIMITER);
    return images.length > 0 ? images[0] : imageUrls;
  }
  
  /**
   * 根据速卖通的skuid
   * @param aeSkuId
   * @return
   */
  private AliexpressSkuColorSizeAttribute getSkuColorSizeAttribute(List<AeopSKUProperty> aeopSKUProperties) {
    AliexpressSkuColorSizeAttribute attribute = new AliexpressSkuColorSizeAttribute();
    for (AeopSKUProperty aeopSKUProperty : aeopSKUProperties) {
      if (attribute.getColor() != null && attribute.getColor().length() > 0
          && attribute.getSize() != null && attribute.getSize().length() > 0) {
        break;
      }
      int valueId = aeopSKUProperty.getPropertyValueId();
      switch (aeopSKUProperty.getSkuPropertyId()) {
        case AliexpressCommonTool.COLOR_PID:
          attribute.setColor(getSkuPropertyValueCnName(AliexpressCommonTool.COLOR_PID, valueId));
          break;
        case AliexpressCommonTool.SIZE_PID:
          attribute.setSize(getSkuPropertyValueCnName(AliexpressCommonTool.SIZE_PID, valueId));
          break;
        default:
          break;
      }
    }
    return attribute;
  }
  
  private String getSkuPropertyValueCnName(int pid, int valueId) {
    for (AeopAttributeDTO aeopAttributeDTO : attributeDTOs) {
      if (aeopAttributeDTO.getId() == pid) {
        for (AeAttrValueDTO aeAttrValueDTO : aeopAttributeDTO.getValues()) {
          if (aeAttrValueDTO.getId() == valueId) {
            return aeAttrValueDTO.getNames().get("zh");
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
//}
