package com.digiwin.ecims.platforms.jingdong.service.ontime.update;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiHelperService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

@Service("jingdongRefundUpdateStatusServiceImpl")
public class JingdongRefundUpdateStatusServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  @Autowired
  BaseDAO baseDao;

  @Autowired
  JingdongApiService jingdongApiService;

  @Autowired
  JingdongApiHelperService jingdongApiHelperService;

  @Autowired
  LoginfoOperateService loginfoOperateService;

  private static final Logger logger =
      LoggerFactory.getLogger(JingdongRefundUpdateStatusServiceImpl.class);

  public static final String SCHEDULETYPE = "jingdongRefundUpdateStatus";

  /**
   * 定時更新 Jingdong 退款退貨狀態更新 WAIT_BUYER_RETURN_GOODS ==> WAIT_SELLER_CONFIRM_GOODS ==> SUCCESS 區分
   * BUYER or SELLER BUYER 有資料更新成SELLER SELLER 有資料更新成 SUCCESS
   */
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    // //取得自己這個 service的interface 實例化
    // JingdongApiHelperService jingdongRefundUpdateStatusService
    // = (JingdongApiHelperService) MySpringContext.getContext()
    // .getBean("jingdongRefundUpdateStatusServiceImpl");

    // 要搜尋的狀態
    String[] statusArr = {"WAIT_BUYER_RETURN_GOODS", "WAIT_SELLER_CONFIRM_GOODS"};

    // add by mowj 20150828 添加 try-catch
    try {
      for (int i = 0; i < statusArr.length; i++) {
        List<Object[]> dataList = jingdongApiHelperService.getStatusReturnGoods(statusArr[i]);
        if (dataList != null && dataList.size() > 0) { // add by mowj 20150828 添加对size的判断

          // WAIT_BUYER_RETURN_GOODS ==> WAIT_SELLER_CONFIRM_GOODS 流程
          if (statusArr[i].equals("WAIT_BUYER_RETURN_GOODS")) {

            logger.info("更新京东服务单状态(等待买家发货 ==> 等待卖家收货)开始 ! 共 {}笔资料待处理", dataList.size());
            int count = 0;
            count = jingdongApiHelperService
                .updateStatusFromBuyerReturnGoods2SellerConfirmGoods(dataList);
            logger.info("更新京东服务单状态(等待买家发货 ==> 等待卖家收货)已完成 ! 共 {}笔资料", count);

          }

          // WAIT_SELLER_CONFIRM_GOODS ==> SUCCESS 流程
          else {

            logger.info("更新京东服务单状态(等待卖家收货 ==> 退款成功)开始 ! 共 {}笔资料待处理", dataList.size());
            int count = 0;
            count = jingdongApiHelperService.updateStatusFromSellerConfirmGoods2Success(dataList);
            logger.info("更新京东服务单状态 (等待卖家收货 ==> 退款成功)状态已完成 ! 共 {}笔资料", count);

          }
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "JingdongRefundUpdateStatusServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          // mark by mowj 20150916 直接使用new Date().toString()会报错
          // java.text.ParseException: Unparseable date: "Mon Sep 14 19:44:41 CST 2015"
          // DateTimeTool.parse(new Date().toString(),
          // "yyyy-MM-dd HH:mm:ss"),
          new Date(), // add by mowj 20150916
          "获取数据异常", "Exception ：" + e.getMessage(), "jingdongUpdateStatus", "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      throw e;
    }

    return false;
  }

  // /**
  // * 取得所需要的資料
  // * @param status (分 "WAIT_BUYER_RETURN_GOODS" || "WAIT_SELLER_CONFIRM_GOODS")
  // * @return 取得的資料清單
  // */
  //
  // @Override
  // public List<Object[]> getStatusReturnGoods(String status){
  //
  // List<Object[]> dataList = null;
  //
  // StringBuffer sbf = new StringBuffer();
  // sbf.append(" select
  // refund.id,shop.aomsshop001,shop.aomsshop004,shop.aomsshop005,shop.aomsshop006 ");
  // sbf.append(" from AomsrefundT refund ,AomsshopT shop ");
  // sbf.append(" where refund.storeType = '1' and refund.aoms037 = '" + status + "' and
  // refund.storeId = shop.aomsshop001");
  //
  // dataList = baseDao.selectByHqlLimit(sbf.toString(),-1);
  //
  // return dataList;
  // }

  // /**
  // * 取得對應到的 AomsshopT 物件
  // * @param objArr list 內的單筆物件
  // * @return AomsshopT 物件
  // */
  // private AomsshopT getAomshopTObj(Object[] objArr){
  //
  // AomsshopT shop = new AomsshopT();
  // shop.setAomsshop001(objArr[1].toString());//shop001
  // shop.setAomsshop004(objArr[2].toString());//shop004
  // shop.setAomsshop005(objArr[3].toString());//shop005
  // shop.setAomsshop006(objArr[4].toString());//shop006
  // return shop;
  // }

  // /**
  // * 等待买家发货 => 等待卖家收货 (全數更新)。调用京东接口：jingdong.afsservice.freightmessage.get 获取发运信息
  // * @param shop 單筆的商店對應物件 AomsshopT 物件
  // * @param objArr list 內的單筆物件
  // * @return if 成功 count += 1 else 失敗 count = 0
  // * @throws NumberFormatException
  // * @throws JdException
  // */
  // @Override
  // public int updateStatusFromBuyerReturnGoods2SellerConfirmGoods(List<Object[]> dataList) throws
  // NumberFormatException, JdException{
  // int count = 0;
  // for(int j = 0 ; j < dataList.size() ; j ++){
  //
  // AomsshopT shop = getAomshopTObj(dataList.get(j));
  // AfsserviceFreightmessageGetResponse freightMessage =
  // jingdongApiService.jindongAfsserviceFreightMessageGet(shop,
  // Integer.valueOf(dataList.get(j)[0].toString()));
  //
  // // add by mowj 20150916 添加对京东返回值的判断，避免出现不必要的java.lang.NullPointerException
  // if (freightMessage != null && freightMessage.getPublicResultObject() != null) {
  // // add by mowj 20151008
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", "1",
  // "jingdong.afsservice.freightmessage.get 获取发运信息", CommonUtil.objectToJsonString(freightMessage,
  // false),
  // SourceLogBizTypeEnum.AOMSREFUNDT.getValueString()
  // , shop.getAomsshop001()
  // , SCHEDULETYPE
  // );
  //
  // // add by mowj 20150828 添加对京东返回的resultCode的判断
  // int resultCode = freightMessage.getPublicResultObject().getResultCode();
  // if (resultCode == 100) {
  // AfsFreightOut freightOut = freightMessage.getPublicResultObject().getResult();
  //
  // if(freightOut != null){
  //
  // String expressCompany = CommonUtil.checkNullOrNot(freightOut.getExpressCompany());//更新aoms018
  // String expressCode = CommonUtil.checkNullOrNot(freightOut.getExpressCode());//更新aoms035
  //
  // String hql = "UPDATE AomsrefundT refund SET "
  // + " refund.aoms018 = '" + expressCompany + "' "
  // + ",refund.aoms035 = '" + expressCode + "' "
  // + ",refund.aoms037 = 'WAIT_SELLER_CONFIRM_GOODS' "
  // + ",refund.aomsmoddt = '" + DateTimeTool.formatToMillisecond(new Date()) + "' " // add by mowj
  // 20150914 修复状态改变后不再推送服务单的问题
  // + " WHERE refund.id = '" + Integer.valueOf(dataList.get(j)[0].toString())
  // + "' AND refund.storeId = '" + shop.getAomsshop001() + "'";
  //
  // baseDao.update(hql);
  // count += 1;//計算有需要update的個數
  // }
  // } else {
  // loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
  // "JingdongRefundUpdateStatusServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
  // new Date(), "resultCode is " + resultCode, resultCode == 300 ? "异常" : "传参异常",
  // "jingdongUpdateStatus", "");
  // }
  // }
  //
  // }
  // baseDao.flush();
  // return count;
  // }


  // /**
  // * 等待卖家收货 ==> 退款成功 (全數更新)。调用京东接口：jingdong.afsservice.refundinfo.get 获取退款信息
  // * @param shop 單筆的商店對應物件 AomsshopT 物件
  // * @param objArr list 內的單筆物件
  // * @return if 成功 count += 1 else 失敗 count = 0
  // * @throws NumberFormatException
  // * @throws JdException
  // */
  // @Override
  // public int updateStatusFromSellerConfirmGoods2Success(List<Object[]> dataList) throws
  // NumberFormatException, JdException{
  // int count = 0;
  // for(int j = 0 ; j < dataList.size() ; j ++){
  //
  // AomsshopT shop = getAomshopTObj(dataList.get(j));
  // AfsserviceRefundinfoGetResponse refundInfo =
  // jingdongApiService.jingdongAfsserviceRefundInfoGet(shop,Integer.valueOf(dataList.get(j)[0].toString()));
  //
  // // add by mowj 20150916 添加对京东返回值的判断，避免出现不必要的java.lang.NullPointerException
  // if (refundInfo != null && refundInfo.getPublicResultObject() != null) {
  // // add by mowj 20151008
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", "1",
  // "jingdong.afsservice.refundinfo.get 获取退款信息", CommonUtil.objectToJsonString(refundInfo, false),
  // SourceLogBizTypeEnum.AOMSREFUNDT.getValueString()
  // , shop.getAomsshop001()
  // , SCHEDULETYPE
  // );
  //
  // // add by mowj 20150828 添加对京东返回的resultCode的判断
  // int resultCode = refundInfo.getPublicResultObject().getResultCode();
  // if (resultCode == 100) {
  // AfsRefundOut refundOut =
  // refundInfo.getPublicResultObject().getAfsRefundInfoOut().getAfsRefundOut();
  // AfsFinanceOut financeOut =
  // refundInfo.getPublicResultObject().getAfsRefundInfoOut().getAfsFinanceOut();
  //
  // if(refundOut != null && financeOut != null){
  //
  // String price = CommonUtil.checkNullOrNot(financeOut.getPrice());//更新aoms030
  // String opTime = CommonUtil.checkNullOrNot(financeOut.getOpTime());//更新aoms041
  // String mark = CommonUtil.checkNullOrNot(refundOut.getMark());//更新aoms042
  // String reason = CommonUtil.checkNullOrNot(refundOut.getReason());//更新aoms043
  //
  // String hql = "UPDATE AomsrefundT refund SET "
  // + "refund.aoms030 = '" + price + "' "
  // + ",refund.modified = '" + opTime + "' "
  // + ",refund.aoms037 = 'SUCCESS' "
  // + ",refund.aoms042 = '" + mark + "' "
  // + ",refund.aoms043 = '" + reason + "' "
  // + ",refund.aomsmoddt = '" + DateTimeTool.formatToMillisecond(new Date()) + "' " // add by mowj
  // 20150914 修复状态改变后不再推送服务单的问题
  // + " WHERE refund.id = '" + Integer.valueOf(dataList.get(j)[0].toString())
  // + "' AND refund.storeId = '" + shop.getAomsshop001() + "'";
  //
  // baseDao.update(hql);
  // count+= 1; //計算有需要update的個數
  // }
  // } else {
  // String resultErrorMsg = refundInfo.getPublicResultObject().getResultErrorMsg();
  // loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
  // "JingdongRefundUpdateStatusServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
  // new Date(), resultErrorMsg, resultCode == 300 ? "异常" : "传参异常",
  // "jingdongUpdateStatus", "");
  // }
  // }
  //
  // }
  // baseDao.flush();
  // return count;
  // }

}
