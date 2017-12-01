package com.digiwin.ecims.platforms.beibei.util.translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.item.ItemWarehouseGetDto;
import com.digiwin.ecims.platforms.beibei.bean.domain.item.base.AbstractItemDto;
import com.digiwin.ecims.platforms.beibei.bean.domain.item.base.ItemSkuDto;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemWarehouseGetResponse;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.SkuColorSizeAttribute;
import com.digiwin.ecims.core.util.StringTool;

public class AomsitemTTranslator {

  private Object object;

  public AomsitemTTranslator(Object object) {
    this.object = object;
  }
  
  public List<AomsitemT> doTranslate(String storeId) {
    if (object instanceof OuterItemGetResponse) {
      OuterItemGetResponse itemGetResponse = (OuterItemGetResponse) this.object;
      return parseBeibeiItemToAomsitemT(itemGetResponse.getData(), storeId);
    } else if (object instanceof OuterItemWarehouseGetResponse) {
      OuterItemWarehouseGetResponse itemWarehouseGetResponse = (OuterItemWarehouseGetResponse) this.object;
      List<AomsitemT> aomsitemTs = new ArrayList<>();
      for (ItemWarehouseGetDto itemWarehouseGetDto : itemWarehouseGetResponse.getData()) {
        aomsitemTs.addAll(parseBeibeiItemToAomsitemT(itemWarehouseGetDto, storeId));
      }
      return aomsitemTs;
    } else {
      return Collections.emptyList();
    }
  }

  private List<AomsitemT> parseBeibeiItemToAomsitemT(AbstractItemDto itemDto, String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<>();
    
    for (ItemSkuDto itemSkuDto : itemDto.getSkus()) {
      AomsitemT aomsitemT = new AomsitemT();
      aomsitemT.setId(itemDto.getIid());
      aomsitemT.setAoms002(CommonUtil.checkNullOrNot(itemDto.getGoodsNum()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(itemDto.getTitle()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSkuDto.getId()));
      aomsitemT.setAoms005(CommonUtil.checkNullOrNot(itemSkuDto.getOuterId()));
      aomsitemT.setAoms006(CommonUtil.checkNullOrNot(itemSkuDto.getSkuProperties()));
      aomsitemT.setAoms007(CommonUtil.checkNullOrNot("onsale"));
      
      SkuColorSizeAttribute skuColorSizeAttribute = getSkuColorAndSize(itemSkuDto.getSkuProperties());
      aomsitemT.setAoms008(CommonUtil.checkNullOrNot(skuColorSizeAttribute.getColor()));
      aomsitemT.setAoms009(CommonUtil.checkNullOrNot(skuColorSizeAttribute.getSize()));
      
      aomsitemT.setAoms014(CommonUtil.checkNullOrNot(itemDto.getImg()));
      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSkuDto.getNum()));
      aomsitemT.setAoms024(CommonUtil.checkNullOrNot(itemSkuDto.getPrice()));
      
      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(BeibeiCommonTool.STORE_TYPE);
      
      Date now = new Date();
      aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsitemTs.add(aomsitemT);
    }
    
    
    return aomsitemTs;
  }
  
  private SkuColorSizeAttribute getSkuColorAndSize(String skuProperties) {
    if (StringTool.isEmpty(skuProperties)) {
      return new SkuColorSizeAttribute();
    }
    String color = "";
    String size = "";
    int skuPropsDelimiterIdx = skuProperties.indexOf(BeibeiCommonTool.SKU_PROPERTIES_DELIMITER);
    if (skuPropsDelimiterIdx > -1) {
      String[] skuProps = skuProperties.split(BeibeiCommonTool.SKU_PROPERTIES_DELIMITER);
      for (String skuProp : skuProps) {
        if (skuProp.contains(BeibeiCommonTool.SKU_COLOR_PROPERTY_NAME)) {
          color = getSkuValue(skuProp);
        } else if (skuProp.contains(BeibeiCommonTool.SKU_SIZE_PROPERTY_NAME)){
          size = getSkuValue(skuProp);
        } else {
          
        }
      }
    } else {
      if (skuProperties.contains(BeibeiCommonTool.SKU_COLOR_PROPERTY_NAME)) {
        color = getSkuValue(skuProperties);
      } else if (skuProperties.contains(BeibeiCommonTool.SKU_SIZE_PROPERTY_NAME)){
        size = getSkuValue(skuProperties);
      } else {
        
      }
    }
    return new SkuColorSizeAttribute(color, size);
  }
  
  private String getSkuValue(String skuProperty) {
    String value = StringTool.EMPTY;
    if (StringTool.isNotEmpty(skuProperty)) {
      int skuPropDelimiterIdx = skuProperty.indexOf(BeibeiCommonTool.SKU_PROPERTY_DELIMITER);
      if (skuPropDelimiterIdx > -1) {
        value = skuProperty.split(BeibeiCommonTool.SKU_PROPERTY_DELIMITER)[1];
      } else {
      
      }
    }
    return value;
  }
  
}
