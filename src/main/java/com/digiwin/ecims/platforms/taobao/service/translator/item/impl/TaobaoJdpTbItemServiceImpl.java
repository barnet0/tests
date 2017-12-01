package com.digiwin.ecims.platforms.taobao.service.translator.item.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemSellerGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.translator.item.TaobaoJdpTbItemService;
import com.digiwin.ecims.platforms.taobao.util.SkuPropertiesSplitter;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJdpTbItemServiceImpl implements TaobaoJdpTbItemService {

  @Override
  public List<AomsitemT> parseTaobaoResToAomsitemT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException {
    return parseItemToAomsitemT(taobaoRes.getResponse(), taobaoRes.getComparisonCol(), storeId);
  }

  /**
   * 将ItemSellerGetResponse类型转换为中台商品类型aomsitemt
   * @param itemSellerGetResponse
   * @return
   * @throws ApiException
   */
  @Override
  public List<AomsitemT> parseItemGetResponseToAomsitemT(
      ItemSellerGetResponse itemSellerGetResponse, String storeId) {
    return parseItemToAomsitemT(itemSellerGetResponse.getItem(), StringTool.EMPTY, storeId);
  }
  
  /**
   * 将Item类型转换为中台商品类型aomsitemt
   * @param item
   * @return
   * @throws ApiException
   */
  public List<AomsitemT> parseItemToAomsitemT(Item item, String storeId) {
    return parseItemToAomsitemT(item, StringTool.EMPTY, storeId);
  }
  
  /**
   * 将单笔商品的JSON字符串转换为中台的商品类型aomsitemt
   * (总入口)
   * @param json 单笔商品的JSON
   * @param jdpModified 可选。聚石塔上的jdp_modified字段的值
   * @return 中台商品List
   * @throws ApiException
   */
  private List<AomsitemT> parseItemToAomsitemT(String json, String jdpModified, String storeId) 
      throws ApiException {
    // 将字符串转换为对象
    ItemGetResponse itemGetResponse =
        TaobaoUtils.parseResponse(json, ItemGetResponse.class);
    Item item = itemGetResponse.getItem();

    return parseItemToAomsitemT(item, jdpModified, storeId);
  }

  /**
   * 转化淘宝Item实例为中台商品结构aomsitemt
   * (实际转换方法)
   * @param item
   * @param jdpModified
   * @return
   * @throws ApiException
   */
  private List<AomsitemT> parseItemToAomsitemT(Item item, String jdpModified, String storeId) {

    List<AomsitemT> list = new ArrayList<AomsitemT>();

    // 單頭
    if (item.getSkus() == null) {
      AomsitemT aomsitemT = new AomsitemT();
      aomsitemT.setId(CommonUtil.checkNullOrNot(item.getNumIid()));

      // 沒有skuId用numiid 
      aomsitemT.setAoms002(CommonUtil.checkNullOrNot(item.getOuterId()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(item.getTitle()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(item.getNumIid()));
      aomsitemT.setAoms007(CommonUtil.checkNullOrNot(item.getApproveStatus()));

      aomsitemT.setStoreType(TaobaoCommonTool.STORE_TYPE);
      aomsitemT.setStoreId(CommonUtil.checkNullOrNot(storeId));

      // 備用欄位
      aomsitemT.setModified(CommonUtil.checkNullOrNot(item.getModified()));
      aomsitemT.setAoms013(CommonUtil.checkNullOrNot(item.getDetailUrl()));
      aomsitemT.setAoms014(CommonUtil.checkNullOrNot(item.getPicUrl()));
      aomsitemT.setAoms015(CommonUtil.checkNullOrNot(item.getCreated()));
      aomsitemT.setAoms016(CommonUtil.checkNullOrNot(item.getListTime()));
      aomsitemT.setAoms017(CommonUtil.checkNullOrNot(item.getDelistTime()));
      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(item.getNum()));
      aomsitemT.setAoms019(CommonUtil.checkNullOrNot(item.getModified()));

      aomsitemT.setAoms024(CommonUtil.checkNullOrNot(item.getPrice()));
      aomsitemT.setAoms025(CommonUtil.checkNullOrNot(item.getBarcode()));
      aomsitemT.setJdpModified(jdpModified);
      
      Date now = new Date();
      aomsitemT.setAomscrtdt(DateTimeTool.format(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      list.add(aomsitemT);
    } else {
      for (Sku sku : item.getSkus()) {
        AomsitemT aomsitemT = new AomsitemT();

        aomsitemT.setId(CommonUtil.checkNullOrNot(item.getNumIid()));
        aomsitemT.setAoms002(CommonUtil.checkNullOrNot(item.getOuterId()));
        aomsitemT.setAoms003(CommonUtil.checkNullOrNot(item.getTitle()));
        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(sku.getSkuId()));
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(sku.getOuterId()));
        aomsitemT.setAoms007(CommonUtil.checkNullOrNot(item.getApproveStatus()));

        // add by mowj 2015-07-17 切分淘宝商品的颜色与尺寸
        Map<String, String> skuMap = SkuPropertiesSplitter.getTbItemColorAndSizeMap(item, sku);
        if (skuMap == null) {
          aomsitemT.setAoms006(CommonUtil.checkNullOrNot(sku.getPropertiesName()));
          aomsitemT.setAoms008(CommonUtil.checkNullOrNot(sku.getPropertiesName()));
          aomsitemT.setAoms009(CommonUtil.checkNullOrNot(sku.getPropertiesName()));
        } else {
          aomsitemT.setAoms006(
              CommonUtil.checkNullOrNot(skuMap.get(SkuPropertiesSplitter.PROPERTIES_NAME)));
          aomsitemT.setAoms008(CommonUtil.checkNullOrNot(skuMap.get(SkuPropertiesSplitter.COLOR)));
          aomsitemT.setAoms009(CommonUtil.checkNullOrNot(skuMap.get(SkuPropertiesSplitter.SIZE)));
        }

        aomsitemT.setStoreType(TaobaoCommonTool.STORE_TYPE);
        aomsitemT.setStoreId(CommonUtil.checkNullOrNot(storeId));

        // 備用欄位
        aomsitemT.setModified(CommonUtil.checkNullOrNot(item.getModified()));
        aomsitemT.setAoms013(CommonUtil.checkNullOrNot(item.getDetailUrl()));
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(item.getPicUrl()));
        aomsitemT.setAoms015(CommonUtil.checkNullOrNot(item.getCreated()));
        aomsitemT.setAoms016(CommonUtil.checkNullOrNot(item.getListTime()));
        aomsitemT.setAoms017(CommonUtil.checkNullOrNot(item.getDelistTime()));
        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(sku.getQuantity()));
        aomsitemT.setAoms019(CommonUtil.checkNullOrNot(sku.getModified()));

        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(sku.getPrice()));
        aomsitemT.setAoms025(CommonUtil.checkNullOrNot(sku.getBarcode()));

        aomsitemT.setJdpModified(jdpModified);

        Date now = new Date();
        aomsitemT.setAomscrtdt(DateTimeTool.format(now));
        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

        list.add(aomsitemT);
      }
    }
    return list;
  }
}
