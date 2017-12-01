package com.digiwin.ecims.platforms.taobao.service.translator.item.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.FenxiaoProduct;
import com.taobao.api.domain.FenxiaoSku;
import com.taobao.api.response.FenxiaoProductsGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.taobao.service.translator.item.TaobaoJdpFxItemService;
import com.digiwin.ecims.platforms.taobao.util.SkuPropertiesSplitter;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJdpFxItemServiceImpl implements TaobaoJdpFxItemService {

  public List<AomsitemT> parseResponseToAomsitemT(
      FenxiaoProductsGetResponse fenxiaoProductsGetResponse, String storeId) 
          throws NullPointerException, ApiException {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    for (FenxiaoProduct fenxiaoProduct : fenxiaoProductsGetResponse.getProducts()) {
      aomsitemTs.addAll(parseFenxiaoProductToAomsitemT(fenxiaoProduct, storeId));
    }
    return aomsitemTs;
  }
  
  public List<AomsitemT> parseFenxiaoProductToAomsitemT(FenxiaoProduct fenxiaoProduct,
      String storeId) throws ApiException, NullPointerException {
    if (fenxiaoProduct == null) {
      return Collections.emptyList();
    }

    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    // 无SKU
    if (fenxiaoProduct.getSkus() == null) {
      AomsitemT aomsitemT = new AomsitemT();
      aomsitemT.setId(String.valueOf(fenxiaoProduct.getPid()));
      aomsitemT.setAoms002(CommonUtil.checkNullOrNot(fenxiaoProduct.getOuterId()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(fenxiaoProduct.getName()));
      aomsitemT.setAoms004(String.valueOf(fenxiaoProduct.getPid()));
      aomsitemT.setAoms007(
          CommonUtil.checkNullOrNot(fenxiaoProduct.getStatus()).toLowerCase().equals("up")
              ? "onsale" : "instock");

      aomsitemT.setStoreId(CommonUtil.checkNullOrNot(storeId));
      aomsitemT.setStoreType(TaobaoCommonTool.STORE_TYPE_FX);

      aomsitemT.setModified(CommonUtil.checkNullOrNot(fenxiaoProduct.getModified()));
      aomsitemT.setAoms013(CommonUtil.checkNullOrNot(fenxiaoProduct.getDescPath()));
      aomsitemT.setAoms014(CommonUtil.checkNullOrNot(fenxiaoProduct.getPictures()));
      aomsitemT.setAoms015(CommonUtil.checkNullOrNot(fenxiaoProduct.getCreated()));
      aomsitemT.setAoms016(CommonUtil.checkNullOrNot(fenxiaoProduct.getUpshelfTime()));
      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(fenxiaoProduct.getQuantity())); 
      aomsitemT.setAoms019(CommonUtil.checkNullOrNot(fenxiaoProduct.getModified()));
      // modi by mowj 20150819 商品代销价格（采购价格）
      aomsitemT.setAoms024(CommonUtil.checkNullOrNot(fenxiaoProduct.getCostPrice()));

      Date now = new Date();
      aomsitemT.setAomscrtdt(DateTimeTool.format(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsitemTs.add(aomsitemT);
    } else {
      for (FenxiaoSku sku : fenxiaoProduct.getSkus()) {
        AomsitemT aomsitemT = new AomsitemT();
        aomsitemT.setId(String.valueOf(fenxiaoProduct.getPid()));
        aomsitemT.setAoms002(CommonUtil.checkNullOrNot(fenxiaoProduct.getOuterId()));
        aomsitemT.setAoms003(CommonUtil.checkNullOrNot(fenxiaoProduct.getName()));
        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(sku.getId()));
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(sku.getOuterId()));
        aomsitemT.setAoms006(CommonUtil.checkNullOrNot(sku.getName()));
        aomsitemT.setAoms007(
            CommonUtil.checkNullOrNot(fenxiaoProduct.getStatus()).toLowerCase().equals("up")
                ? "onsale" : "instock");

        // add by mowj 2015-07-17 切分淘宝商品的颜色与尺寸
        Map<String, String> skuMap = SkuPropertiesSplitter.getFxItemColorAndSizeMap(sku.getName());
        if (skuMap == null) {
          aomsitemT.setAoms008(CommonUtil.checkNullOrNot(sku.getName()));
          aomsitemT.setAoms009(CommonUtil.checkNullOrNot(sku.getName()));
        } else {
          aomsitemT.setAoms008(CommonUtil.checkNullOrNot(skuMap.get(SkuPropertiesSplitter.COLOR)));
          aomsitemT.setAoms009(CommonUtil.checkNullOrNot(skuMap.get(SkuPropertiesSplitter.SIZE)));
        }

        aomsitemT.setStoreId(CommonUtil.checkNullOrNot(storeId));
        aomsitemT.setStoreType(TaobaoCommonTool.STORE_TYPE_FX);

        aomsitemT.setModified(CommonUtil.checkNullOrNot(fenxiaoProduct.getModified()));
        aomsitemT.setAoms013(CommonUtil.checkNullOrNot(fenxiaoProduct.getDescPath()));
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(fenxiaoProduct.getPictures()));
        aomsitemT.setAoms015(CommonUtil.checkNullOrNot(fenxiaoProduct.getCreated()));
        aomsitemT.setAoms016(CommonUtil.checkNullOrNot(fenxiaoProduct.getUpshelfTime()));
        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(sku.getQuantity()));
        aomsitemT.setAoms019(CommonUtil.checkNullOrNot(fenxiaoProduct.getModified()));
     // modi by mowj 20150819 商品代销价格（采购价格）
        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(sku.getCostPrice())); 

        Date now = new Date();
        aomsitemT.setAomscrtdt(DateTimeTool.format(now));
        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
        
        aomsitemTs.add(aomsitemT);
      }
    }

    return aomsitemTs;
  }

}
