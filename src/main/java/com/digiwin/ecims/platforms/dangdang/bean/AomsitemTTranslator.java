package com.digiwin.ecims.platforms.dangdang.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.dangdang.bean.response.item.get.ItemGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.item.get.SpecilaItemInfo;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;

public class AomsitemTTranslator {

  public AomsitemTTranslator() {

  }

  /**
   * 单笔鋪貨查询
   * 
   * @param
   * @param
   * @return
   */
  public List<AomsitemT> doTrans(ItemGetResponse itemGetResponse, String storeId) {
    return doTransToAomsitemTBean(itemGetResponse, storeId, null);
  }

  /**
   * 批次鋪貨查询
   * 
   * @param
   * @param
   * @return
   */
  public List<AomsitemT> doTrans(ItemGetResponse itemGetResponse, String storeId, String eDate) {
    return doTransToAomsitemTBean(itemGetResponse, storeId, eDate);
  }

  public List<AomsitemT> doTransToAomsitemTBean(ItemGetResponse itemGetResponse, String storeId,
      String eDate) {
    List<AomsitemT> result = new ArrayList<AomsitemT>();

    if (null != itemGetResponse.getItemDetail().getSpecilaItemInfos()) {
      for (SpecilaItemInfo specilaItemInfo : itemGetResponse.getItemDetail()
          .getSpecilaItemInfos()) {
        AomsitemT aomsitemT = new AomsitemT();
        // 分色分码商品
        aomsitemT.setId(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getItemID()));
        aomsitemT.setAoms002(
            CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getOuterItemID()));
        aomsitemT
            .setAoms003(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getItemName()));
        aomsitemT.setAoms004(CommonUtil.checkNullOrNot(specilaItemInfo.getSubItemID()));
        aomsitemT.setAoms005(CommonUtil.checkNullOrNot(specilaItemInfo.getOuterItemID()));
        aomsitemT.setAoms006(CommonUtil.checkNullOrNot(specilaItemInfo.getSpecialAttribute()));
        // 返回值为上架时，onsale、 返回值为下架时，instock
        if (DangdangCommonTool.ITEM_LISTING
            .equals(itemGetResponse.getItemDetail().getItemState())) {
          aomsitemT.setAoms007(CommonUtil.checkNullOrNot("onsale"));
        } else {
          aomsitemT.setAoms007(CommonUtil.checkNullOrNot("instock"));
        }

        // <specialAttribute>颜色>>好看的军绿;鞋码>>38偏大</specialAttribute>
        // 此栏位取值“好看的军绿”
        if (!"".equals(specilaItemInfo.getSpecialAttribute())) {
          String[] specialAttributeArray = specilaItemInfo.getSpecialAttribute().split(";");
          if (specialAttributeArray.length == 2) {
            aomsitemT
                .setAoms008(CommonUtil.checkNullOrNot(specialAttributeArray[0].split(">>")[1]));
            aomsitemT
                .setAoms009(CommonUtil.checkNullOrNot(specialAttributeArray[1].split(">>")[1]));
          }
          if (specialAttributeArray.length == 1) {
            if ("颜色".equals(specialAttributeArray[0].split(">>")[0])) {
              aomsitemT
                  .setAoms008(CommonUtil.checkNullOrNot(specialAttributeArray[0].split(">>")[1]));
            } else {
              aomsitemT
                  .setAoms009(CommonUtil.checkNullOrNot(specialAttributeArray[0].split(">>")[1]));
            }
          }
        }
        aomsitemT.setStoreId(storeId);
        aomsitemT.setStoreType(DangdangCommonTool.STORE_TYPE);
        aomsitemT.setModified(eDate);
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getPic1())); // add
                                                                                                    // by
                                                                                                    // mowj
                                                                                                    // 20151027
        aomsitemT.setAoms015(CommonUtil.checkNullOrNot(null));
        aomsitemT.setAoms018(CommonUtil.checkNullOrNot(specilaItemInfo.getStockCount())); // add by
                                                                                          // mowj
                                                                                          // 20150804
        aomsitemT.setAoms019(CommonUtil.checkNullOrNot(null));
        aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null));
        aomsitemT.setAoms024(CommonUtil.checkNullOrNot(specilaItemInfo.getUnitPrice())); // add by
                                                                                         // mowj
                                                                                         // 20150819
                                                                                         // 获取商品价格

        String date = CommonUtil.checkNullOrNot(new Date());
        aomsitemT.setAomsstatus("0");
        aomsitemT.setAomscrtdt(date);
        aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

        result.add(aomsitemT);
      }
    } else {
      // 普通商品
      AomsitemT aomsitemT = new AomsitemT();

      aomsitemT.setId(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getItemID()));
      aomsitemT
          .setAoms002(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getOuterItemID()));
      aomsitemT
          .setAoms003(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getItemName()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getItemID()));
      aomsitemT
          .setAoms005(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getOuterItemID()));
      aomsitemT.setAoms006(CommonUtil.checkNullOrNot(null));
      // 返回值为上架时，onsale、 返回值为下架时，instock
      if (DangdangCommonTool.ITEM_LISTING.equals(itemGetResponse.getItemDetail().getItemState())) {
        aomsitemT.setAoms007(CommonUtil.checkNullOrNot("onsale"));
      } else {
        aomsitemT.setAoms007(CommonUtil.checkNullOrNot("instock"));
      }
      aomsitemT.setAoms008(CommonUtil.checkNullOrNot(null));
      aomsitemT.setAoms009(CommonUtil.checkNullOrNot(null));

      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(DangdangCommonTool.STORE_TYPE);
      aomsitemT.setAoms014(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getPic1())); // add
                                                                                                  // by
                                                                                                  // mowj
                                                                                                  // 20151027
      aomsitemT.setAoms015(CommonUtil.checkNullOrNot(null));
      aomsitemT.setModified(eDate);
      aomsitemT
          .setAoms018(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getStockCount())); // add
                                                                                                   // by
                                                                                                   // mowj
                                                                                                   // 20150804
      aomsitemT.setAoms019(CommonUtil.checkNullOrNot(null));
      aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null));
      aomsitemT
          .setAoms020(CommonUtil.checkNullOrNot(itemGetResponse.getItemDetail().getUnitPrice())); // add
                                                                                                  // by
                                                                                                  // mowj
                                                                                                  // 20150819
                                                                                                  // 获取商品价格

      String date = CommonUtil.checkNullOrNot(new Date());
      aomsitemT.setAomsstatus("0");
      aomsitemT.setAomscrtdt(date);
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

      result.add(aomsitemT);
    }
    return result;
  }
}
