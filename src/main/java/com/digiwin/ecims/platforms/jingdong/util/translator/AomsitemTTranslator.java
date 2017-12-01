package com.digiwin.ecims.platforms.jingdong.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;

public class AomsitemTTranslator {
  private Object obj;
  private List<Sku> skus;
  private List<HashMap<String, String>> priceList;  //addbycs at 20170412传入价格

  /**
   * do translator param setting
   * 
   * @param obj item object
   * @param skus sku object
   */
  public AomsitemTTranslator(Object obj, List<Sku> skus, List<HashMap<String, String>> priceList) {
    super();
    this.obj = obj;
    this.skus = skus;
    this.priceList = priceList;
  }

  /**
   * do translator 區分有 sku obj 或無 sku obj
   * 
   * @param storeId 商店Id
   * @return 整批的 item 清單
   */
  public List<AomsitemT> doTranslator(String storeId) {
    List<AomsitemT> aomsitemTs = null;
    if (obj instanceof Ware) {
      Ware ware = (Ware) obj;
      if (skus != null) {
        aomsitemTs = new ArrayList<AomsitemT>();
        for (Sku sku : skus) {
        	    	
         String price = "0.0";
         String marketPrice = "0.0";
         //根据skuid找到priceList数组中的对应价格,addbycs at 20170418
         for(HashMap<String, String> par : priceList){
        		
        	 if(par.get("skuId").equals(sku.getSkuId().toString())){
        		 price = par.get("price");
        		 marketPrice = par.get("marketPrice");
        		 break;
        	 }
         }
        	
          AomsitemT aomsitemT = new AomsitemT();
          aomsitemT.setId(CommonUtil.checkNullOrNot(ware.getWareId()));
          aomsitemT.setAoms002(CommonUtil.checkNullOrNot(ware.getItemNum()));
          aomsitemT.setAoms003(CommonUtil.checkNullOrNot(ware.getTitle()));
          aomsitemT.setAoms004(CommonUtil.checkNullOrNot(sku.getSkuId()));
          aomsitemT.setAoms005(CommonUtil.checkNullOrNot(sku.getOuterId()));
          aomsitemT.setAoms006(CommonUtil.checkNullOrNot(null));
          // aomsitemT.setAoms007(CommonUtil.checkNullOrNot(sku.getStatus())); // mark by mowj
          // 20150730
          aomsitemT.setAoms007(CommonUtil.checkNullOrNot(
              sku.getStatus().toUpperCase().equals("VALID") ? "onsale" : "instock")); // add by mowj
                                                                                      // 20150730
          aomsitemT.setAoms008(CommonUtil.checkNullOrNot(sku.getColorValue()));
          aomsitemT.setAoms009(CommonUtil.checkNullOrNot(sku.getSizeValue()));
          aomsitemT.setStoreId(storeId);
          aomsitemT.setStoreType(JingdongCommonTool.STORE_TYPE);
          aomsitemT.setAoms014(CommonUtil.checkNullOrNot(ware.getLogo()));
          aomsitemT.setAoms015(CommonUtil.checkNullOrNot(ware.getCreated()));
          aomsitemT.setAoms018(CommonUtil.checkNullOrNot(sku.getStockNum())); // modi by mowj
                                                                              // 20150805
          aomsitemT.setAoms019(CommonUtil.checkNullOrNot(sku.getModified()));
          aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null)); // modi by mowj 20150819
          aomsitemT.setAoms024(price); // add by mowj 20150819   modifybycs at 20170412 价格单独从接口获取
                                                                             // 获取商品价格
          aomsitemT.setModified(CommonUtil.checkNullOrNot(ware.getModified()));
          String date = CommonUtil.checkNullOrNot(new Date());
          aomsitemT.setAomsstatus("0");
          aomsitemT.setAomscrtdt(date);
          aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

          aomsitemTs.add(aomsitemT);
        }
      } else {
        aomsitemTs = new ArrayList<AomsitemT>();
        AomsitemT aomsitemT = new AomsitemT();
        aomsitemT.setId(CommonUtil.checkNullOrNot(ware.getWareId()));
        aomsitemT.setAoms002(CommonUtil.checkNullOrNot(ware.getItemNum()));
        aomsitemT.setAoms003(CommonUtil.checkNullOrNot(ware.getTitle()));
        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(ware.getWareId()));
        aomsitemT.setAoms006(CommonUtil.checkNullOrNot(null));

        aomsitemT.setStoreId(storeId);
        aomsitemT.setStoreType(JingdongCommonTool.STORE_TYPE);
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(ware.getLogo()));
        aomsitemT.setAoms015(CommonUtil.checkNullOrNot(ware.getCreated()));
        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(ware.getStockNum())); // modi by mowj
                                                                             // 20150804
        aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null)); // modi by mowj 20150819
        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(ware.getJdPrice())); // add by mowj 20150819 modifybycs 价格字段调整为jdprice
                                                                               
        aomsitemT.setModified(CommonUtil.checkNullOrNot(ware.getModified()));
        String date = CommonUtil.checkNullOrNot(new Date());
        aomsitemT.setAomsstatus("0");
        aomsitemT.setAomscrtdt(date);
        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

        aomsitemTs.add(aomsitemT);
      }
    }

    return aomsitemTs;
  }

  private String getMaxPrice(Object obj) {
    String[] priceStrings = new String[3];
    double[] priceDoubles = new double[3];

    if (obj instanceof Ware) {
      Ware ware = (Ware) obj;
      priceStrings[0] = ware.getCostPrice() == null ? "0.0" : ware.getCostPrice();
      priceStrings[1] = ware.getMarketPrice() == null ? "0.0" : ware.getMarketPrice();
      priceStrings[2] = ware.getJdPrice() == null ? "0.0" : ware.getJdPrice();

      priceDoubles[0] = Double.parseDouble(priceStrings[0]);
      priceDoubles[1] = Double.parseDouble(priceStrings[1]);
      priceDoubles[2] = Double.parseDouble(priceStrings[2]);

    } else if (obj instanceof Sku) {
      Sku sku = (Sku) obj;
      priceStrings[0] = sku.getCostPrice() == null ? "0.0" : sku.getCostPrice();
      priceStrings[1] = sku.getMarketPrice() == null ? "0.0" : sku.getMarketPrice();
      priceStrings[2] = sku.getJdPrice() == null ? "0.0" : sku.getJdPrice();

      priceDoubles[0] = Double.parseDouble(priceStrings[0]);
      priceDoubles[1] = Double.parseDouble(priceStrings[1]);
      priceDoubles[2] = Double.parseDouble(priceStrings[2]);
    } else {
      return "";
    }
    int maxPricePos = 0;
    for (int i = 1; i < priceDoubles.length; i++) {
      if (priceDoubles[i] > priceDoubles[maxPricePos]) {
        maxPricePos = i;
      }
    }
    return priceStrings[maxPricePos];
  }

}
