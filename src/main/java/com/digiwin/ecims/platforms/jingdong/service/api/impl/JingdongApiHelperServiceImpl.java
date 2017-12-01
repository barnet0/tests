package com.digiwin.ecims.platforms.jingdong.service.api.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.afsservice.AfsServiceProcessFacade.AfsFinanceOut;
import com.jd.open.api.sdk.domain.afsservice.AfsServiceProcessFacade.AfsRefundOut;
import com.jd.open.api.sdk.response.afsservice.AfsFreightOut;
import com.jd.open.api.sdk.response.afsservice.AfsserviceFreightmessageGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceRefundinfoGetResponse;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiHelperService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.service.ontime.update.JingdongRefundUpdateStatusServiceImpl;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class JingdongApiHelperServiceImpl implements JingdongApiHelperService {

  @Autowired
  private BaseDAO baseDao;

  @Autowired
  private JingdongApiService jingdongApiService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Override
  public List<Object[]> getStatusReturnGoods(String status) {
    List<Object[]> dataList = null;

    StringBuffer sbf = new StringBuffer();
    sbf.append(
        " SELECT refund.id,shop.aomsshop001,shop.aomsshop004,shop.aomsshop005,shop.aomsshop006 ");
    sbf.append(" FROM AomsrefundT refund,AomsshopT shop ");
    sbf.append(" WHERE refund.storeType='").append(JingdongCommonTool.STORE_TYPE).append("' ")
      .append(" AND refund.aoms037='").append(status).append("' AND refund.storeId=shop.aomsshop001");

    dataList = baseDao.selectByHqlLimit(sbf.toString(), -1);

    return dataList;
  }

  @Override
  public int updateStatusFromBuyerReturnGoods2SellerConfirmGoods(List<Object[]> dataList)
      throws NumberFormatException, JdException, IOException {
    int count = 0;
    String storeId = null;
    String appKey = null;
    String appSecret = null;
    String accessToken = null;
    for (int j = 0; j < dataList.size(); j++) {
      AomsshopT shop = getAomshopTObj(dataList.get(j));
      storeId = shop.getAomsshop001();
      appKey = shop.getAomsshop004();
      appSecret = shop.getAomsshop005();
      accessToken = shop.getAomsshop006();
      
      Integer afsserviceId = Integer.valueOf(dataList.get(j)[0].toString());
      AfsserviceFreightmessageGetResponse freightMessage = jingdongApiService
          .jindongAfsserviceFreightMessageGet(
              appKey, appSecret, accessToken, afsserviceId);

      // add by mowj 20150916 添加对京东返回值的判断，避免出现不必要的java.lang.NullPointerException
      if (freightMessage != null && freightMessage.getPublicResultObject() != null) {
        // add by mowj 20151008
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", 
            JingdongCommonTool.STORE_TYPE,
            "jingdong.afsservice.freightmessage.get 获取发运信息",
            JsonUtil.formatByMilliSecond(freightMessage),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            JingdongRefundUpdateStatusServiceImpl.SCHEDULETYPE);

        // add by mowj 20150828 添加对京东返回的resultCode的判断
        int resultCode = freightMessage.getPublicResultObject().getResultCode();
        if (resultCode == 100) {
          AfsFreightOut freightOut = freightMessage.getPublicResultObject().getResult();

          if (freightOut != null) {
            // 更新aoms018
            String expressCompany = CommonUtil.checkNullOrNot(freightOut.getExpressCompany());
            // 更新aoms035
            String expressCode = CommonUtil.checkNullOrNot(freightOut.getExpressCode());

            String hql = "UPDATE AomsrefundT refund SET " 
                + " refund.aoms018='" + expressCompany + "'" 
                + ",refund.aoms035='" + expressCode + "'"
                + ",refund.aoms037='WAIT_SELLER_CONFIRM_GOODS'" 
                // add by mowj 20150914 修复状态改变后不再推送服务单的问题
                + ",refund.aomsmoddt='" + DateTimeTool.getTodayByMilliSecond() + "'"
                + " WHERE refund.id='" + afsserviceId + "'"
                + " AND refund.storeId='" + storeId + "'";

            baseDao.update(hql);
            count += 1;// 計算有需要update的個數
          }
        } else {
          loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
              "JingdongRefundUpdateStatusServiceImpl",
              LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), new Date(),
              "resultCode is " + resultCode, 
              resultCode == 300 ? "异常" : "传参异常" + freightMessage.getZhDesc(),
              "jingdongUpdateStatus", "");
        }
      }

    }
    baseDao.flush();
    return count;
  }

  @Override
  public int updateStatusFromSellerConfirmGoods2Success(List<Object[]> dataList)
      throws NumberFormatException, JdException, IOException {
    int count = 0;
    String storeId = null;
    String appKey = null;
    String appSecret = null;
    String accessToken = null;
    for (int j = 0; j < dataList.size(); j++) {
      AomsshopT shop = getAomshopTObj(dataList.get(j));
      storeId = shop.getAomsshop001();
      appKey = shop.getAomsshop004();
      appSecret = shop.getAomsshop005();
      accessToken = shop.getAomsshop006();
      
      Integer afsserviceId = Integer.valueOf(dataList.get(j)[0].toString());
      AfsserviceRefundinfoGetResponse refundInfo = 
          jingdongApiService.jingdongAfsserviceRefundInfoGet(
              appKey, appSecret, accessToken, afsserviceId);

      // add by mowj 20150916 添加对京东返回值的判断，避免出现不必要的java.lang.NullPointerException
      if (refundInfo != null && refundInfo.getPublicResultObject() != null) {
        // add by mowj 20151008
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", 
            JingdongCommonTool.STORE_TYPE,
            "jingdong.afsservice.refundinfo.get	获取退款信息",
            JsonUtil.formatByMilliSecond(refundInfo),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            JingdongRefundUpdateStatusServiceImpl.SCHEDULETYPE);

        // add by mowj 20150828 添加对京东返回的resultCode的判断
        int resultCode = refundInfo.getPublicResultObject().getResultCode();
        if (resultCode == 100) {
          AfsRefundOut refundOut =
              refundInfo.getPublicResultObject().getAfsRefundInfoOut().getAfsRefundOut();
          AfsFinanceOut financeOut =
              refundInfo.getPublicResultObject().getAfsRefundInfoOut().getAfsFinanceOut();

          if (refundOut != null && financeOut != null) {
            // 更新aoms030
            String price = CommonUtil.checkNullOrNot(financeOut.getPrice());
            // 更新aoms041
            String opTime = CommonUtil.checkNullOrNot(financeOut.getOpTime());
            // 更新aoms042
            String mark = CommonUtil.checkNullOrNot(refundOut.getMark());
            // 更新aoms043
            String reason = CommonUtil.checkNullOrNot(refundOut.getReason());

            String hql = "UPDATE AomsrefundT refund SET " 
                + "refund.aoms030='" + price + "'"
                + ",refund.modified='" + opTime + "'" 
                + ",refund.aoms037='SUCCESS'"
                + ",refund.aoms042='" + mark + "'" 
                + ",refund.aoms043='" + reason + "'"
                // add by mowj 20150914 修复状态改变后不再推送服务单的问题
                + ",refund.aomsmoddt='" + DateTimeTool.getTodayByMilliSecond() + "'"
                + " WHERE refund.id='" + afsserviceId + "'"
                + " AND refund.storeId='" + storeId + "'";

            baseDao.update(hql);
            count += 1; // 計算有需要update的個數
          }
        } else {
          String resultErrorMsg = refundInfo.getPublicResultObject().getResultErrorMsg();
          loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
              "JingdongRefundUpdateStatusServiceImpl",
              LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), new Date(), resultErrorMsg,
              resultCode == 300 ? "异常" : "传参异常" + refundInfo.getZhDesc(), 
                  "jingdongUpdateStatus", "");
        }
      }
    }
    baseDao.flush();
    return count;
  }

  /**
   * 取得對應到的 AomsshopT 物件
   * 
   * @param objArr list 內的單筆物件
   * @return AomsshopT 物件
   */
  private AomsshopT getAomshopTObj(Object[] objArr) {

    AomsshopT shop = new AomsshopT();
    shop.setAomsshop001(objArr[1].toString());// shop001
    shop.setAomsshop004(objArr[2].toString());// shop004
    shop.setAomsshop005(objArr[3].toString());// shop005
    shop.setAomsshop006(objArr[4].toString());// shop006
    return shop;
  }

}
