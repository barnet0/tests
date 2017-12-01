package com.digiwin.ecims.platforms.jingdong.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jd.open.api.sdk.domain.refundapply.RefundApplySoaService.RefundApplyVo;
import com.jd.open.api.sdk.response.afsservice.AfsServiceMessage;
import com.jd.open.api.sdk.response.afsservice.AfsServiceOut;
import com.jd.open.api.sdk.response.afsservice.AfsserviceServiceinfoGetResponse;
import com.jd.open.api.sdk.response.refundapply.PopAfsSoaRefundapplyQueryByIdResponse;
import com.jd.open.api.sdk.response.refundapply.PopAfsSoaRefundapplyQueryPageListResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.jingdong.bean.response.afsservice.MyAfsserviceServicedetailListResponse;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;

public class AomsrefundTTranslator {
  private Object obj;
  private String storeId;

  /**
   * do translator param setting
   * 
   * @param obj refund object
   * @param storeId 商店Id
   */
  public AomsrefundTTranslator(Object obj, String storeId) {
    super();
    this.obj = obj;
    this.storeId = storeId;
  }

  /**
   * 僅退款用(但分為單筆跟多筆)
   * 
   * @return 處理完後的 list 清單
   */
  public List<AomsrefundT> doTranslate() {
    List<RefundApplyVo> refundApplyVos = null;

    if (obj instanceof PopAfsSoaRefundapplyQueryByIdResponse) {

      refundApplyVos = ((PopAfsSoaRefundapplyQueryByIdResponse) obj).getQueryResult().getResult();

    } else if (obj instanceof PopAfsSoaRefundapplyQueryPageListResponse) {

      refundApplyVos =
          ((PopAfsSoaRefundapplyQueryPageListResponse) obj).getQueryResult().getResult();

    } else {
      return null;
    }

    return refundApplyVosToAomsRefunds(refundApplyVos);
  }

  /**
   * 僅退款用(單筆多筆都跑)
   * 
   * @param refundApplyVos 要處理的物件
   * @return translate 後的 list 清單
   */
  private List<AomsrefundT> refundApplyVosToAomsRefunds(List<RefundApplyVo> refundApplyVos) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    for (RefundApplyVo refundApplyVo : refundApplyVos) {
      AomsrefundT aomsrefundT = new AomsrefundT();

      aomsrefundT.setId(CommonUtil.checkNullOrNot(refundApplyVo.getId()));
      aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(null));

      // aomsrefund009
      // 是否退貨(這邊是僅退款)
      aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.FALSE));

      // aomsrefund017
      // result.buyerId（买家账号）
      aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(refundApplyVo.getBuyerId()));

      aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundApplyVo.getOrderId()));

      // aomsrefund024
      // 子订单号。如果是单笔交易oid会等于tid           
      // refundApplyVo.orderId 
      aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refundApplyVo.getOrderId()));

      aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(null));

      // aomsrefund030
      // refundApplyVo.applyRefundSum
      // 除以100
      // refundApplyVo.applyRefundSum 除以100，转化为"元"
      // 退款金額
      aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(refundApplyVo.getApplyRefundSum() / 100));
      aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(null));

      // refundApplyVo.status
      // 退款状态  
      aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refundApplyVo.getStatus()));

      aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundApplyVo.getOrderId()));

      // aomsrefund041
      // result.checkTime（申请时间）
      // 退款創建時間
      aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundApplyVo.getCheckTime()));
      aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setStoreType(JingdongCommonTool.STORE_TYPE);// 京東-1
      aomsrefundT.setStoreId(storeId);
      aomsrefundT.setAoms048(CommonUtil.checkNullOrNot(null));

      // aomsrefund049
      // result.buyerName(买家姓名）
      // 買家姓名
      aomsrefundT.setAoms049(CommonUtil.checkNullOrNot(refundApplyVo.getBuyerName()));

      aomsrefundT.setModified(CommonUtil.checkNullOrNot(refundApplyVo.getCheckTime()));

      String date = CommonUtil.checkNullOrNot(new Date());
      aomsrefundT.setAomsstatus("0");
      aomsrefundT.setAomscrtdt(date);
      aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

      aomsrefundTs.add(aomsrefundT);
    }
    return aomsrefundTs;
  }


  /**
   * 退款退貨用 mark param AfsserviceRefundinfoGetResponse refundInfo,
   * AfsserviceFreightmessageGetResponse freightMessage,
   * 
   * @param serviceDetailList
   * @return
   */
  public AomsrefundT doTranslate(MyAfsserviceServicedetailListResponse serviceDetailList) {
    AomsrefundT aomsrefundT = new AomsrefundT();

    if (obj instanceof AfsServiceMessage) {
      AfsServiceMessage messgage = (AfsServiceMessage) obj;

      // mark by sen.shen
      // AfsFinanceOut financeOut =
      // refundInfo.getPublicResultObject().getAfsRefundInfoOut().getAfsFinanceOut();
      // AfsRefundOut refundOut =
      // refundInfo.getPublicResultObject().getAfsRefundInfoOut().getAfsRefundOut();
      // AfsFreightOut freightOut = freightMessage.getPublicResultObject().getResult();

      // 退款\货\服务单号
      // API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.afsServiceId
      aomsrefundT.setId(CommonUtil.checkNullOrNot(messgage.getAfsServiceId()));
      aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(null));// financeOut == null ? null :
                                                              // CommonUtil.checkNullOrNot(financeOut.getPrice()));
      // aomsrefundT.setAoms008(aomsrefund008);

      // 是否退货aomsrefund009
      aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));


      aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(null));

      // 物流公司名称         
      // "API:jingdong.afsservice.freightmessage.get （获取发运信息）字段：result.expressCompany
      // 关联条件：afsServiceId=aomsrefund001"
      aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(freightOut
                                                              // == null ? null :
                                                              // freightOut.getExpressCompany()));

      // 商品购买数量aomsrefund022           
      aomsrefundT.setAoms022(CommonUtil.checkNullOrNot("1"));


      aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(
          serviceDetailList.getPublicResultList().getModelList().get(0).getWareId()));


      // 子订单号。如果是单笔交易oid会等于tid           
      // "API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.orderId
      aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(messgage.getOrderId()));


      aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(null));
      aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(null));

      // 退款金额 
      // "API：jingdong.afsservice.refundinfo.get（ 获取退款信息）字段：afsFinanceOut.price
      // 关联条件：afsServiceId=aomsrefund001
      aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(financeOut
                                                              // == null ? null :
                                                              // financeOut.getPrice()));

      // 退货运单号           
      // "API：jingdong.afsservice.freightmessage.get （获取发运信息）字段：result.expressCode
      // 关联条件：afsServiceId=aomsrefund001
      aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(freightOut
                                                              // == null ? null :
                                                              // freightOut.getExpressCode()));

      aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(null));

      // 退款状态          
      /*
       * 使用 API：jingdong.afsservice.receivetask.get 能查到资料时，给值WAIT_BUYER_RETURN_GOODS 使用
       * API：jingdong.afsservice.freightmessage.get 能查到资料时，给值 WAIT_SELLER_CONFIRM_GOODS 使用
       * API：jingdong.afsservice.refundinfo.get 能查到资料时，给值SUCCESS"
       */

      // if(freightOut.getAfsFreightId()!=null){
      // aomsrefundT.setAoms037("WAIT_SELLER_CONFIRM_GOODS");
      // }else if(financeOut!=null){
      // aomsrefundT.setAoms037("SUCCESS");
      // }else{
      aomsrefundT.setAoms037("WAIT_BUYER_RETURN_GOODS");
      // }

      aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(messgage.getOrderId()));

      // 退款创建时间
      // API：jingdong.afsservice.refundinfo.get（ 获取退款信息）字段：afsFinanceOut.opTime
      // "API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段： result.approvedDate
      aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(messgage.getApprovedDate()));// CommonUtil.checkNullOrNot(financeOut
                                                                                    // == null ?
                                                                                    // null :
                                                                                    // financeOut.getOpTime()));

      // 退款说明
      // API：jingdong.afsservice.refundinfo.get （ 获取退款信息）字段：afsRefundOut.mark

      aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(refundOut
                                                              // == null ? null :
                                                              // refundOut.getMark()));

      // 退款原因
      // API：jingdong.afsservice.refundinfo.get （ 获取退款信息）字段：afsFinanceOut.reason

      aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(financeOut
                                                              // == null ? null :
                                                              // financeOut.getReason()));
      aomsrefundT.setStoreType(JingdongCommonTool.STORE_TYPE);
      aomsrefundT.setStoreId(storeId);
      aomsrefundT.setAoms048(CommonUtil.checkNullOrNot(null));

      // 买家姓名
      // API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.customerName
      aomsrefundT.setAoms049(CommonUtil.checkNullOrNot(messgage.getCustomerName()));

      // sku_id（电商）
      // API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.wareId
      aomsrefundT.setAoms050(CommonUtil.checkNullOrNot(messgage.getWareId()));


      aomsrefundT.setModified(CommonUtil.checkNullOrNot(messgage.getApprovedDate() == null
          ? messgage.getAfsApplyTime() : messgage.getApprovedDate()));

      String date = CommonUtil.checkNullOrNot(new Date());
      aomsrefundT.setAomsstatus("0");
      aomsrefundT.setAomscrtdt(date);
      aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

    }
    return aomsrefundT;
  }

  public List<AomsrefundT> doTranslate(AfsserviceServiceinfoGetResponse serviceinfoGetResponse) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    AomsrefundT aomsrefundT = new AomsrefundT();
    AfsServiceOut afsServiceOut = null;
    if (serviceinfoGetResponse != null && serviceinfoGetResponse.getPublicResultObject() != null
        && serviceinfoGetResponse.getPublicResultObject().getAfsServiceOut() != null) {
      afsServiceOut = serviceinfoGetResponse.getPublicResultObject().getAfsServiceOut();
    } else {
      return aomsrefundTs;
    }

    // 退款\货\服务单号
    // API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.afsServiceId
    aomsrefundT.setId(CommonUtil.checkNullOrNot(afsServiceOut.getAfsApplyId()));
    aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(null));
    aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(null));// financeOut == null ? null :
                                                            // CommonUtil.checkNullOrNot(financeOut.getPrice()));
    // aomsrefundT.setAoms008(aomsrefund008);

    // 是否退货aomsrefund009
    aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));


    aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(null));

    // 物流公司名称         
    // "API:jingdong.afsservice.freightmessage.get （获取发运信息）字段：result.expressCompany
    // 关联条件：afsServiceId=aomsrefund001"
    aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(freightOut
                                                            // == null ? null :
                                                            // freightOut.getExpressCompany()));

    // 商品购买数量aomsrefund022           
    aomsrefundT.setAoms022(CommonUtil.checkNullOrNot("1"));


    aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(afsServiceOut.getWareId()));


    // 子订单号。如果是单笔交易oid会等于tid           
    // "API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.orderId
    aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(afsServiceOut.getOrderId()));


    aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(null));
    aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(null));

    // 退款金额 
    // "API：jingdong.afsservice.refundinfo.get（ 获取退款信息）字段：afsFinanceOut.price
    // 关联条件：afsServiceId=aomsrefund001
    aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(financeOut
                                                            // == null ? null :
                                                            // financeOut.getPrice()));

    // 退货运单号           
    // "API：jingdong.afsservice.freightmessage.get （获取发运信息）字段：result.expressCode
    // 关联条件：afsServiceId=aomsrefund001
    aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(freightOut
                                                            // == null ? null :
                                                            // freightOut.getExpressCode()));

    aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(null));

    // 退款状态          
    /*
     * 使用 API：jingdong.afsservice.receivetask.get 能查到资料时，给值WAIT_BUYER_RETURN_GOODS 使用
     * API：jingdong.afsservice.freightmessage.get 能查到资料时，给值 WAIT_SELLER_CONFIRM_GOODS 使用
     * API：jingdong.afsservice.refundinfo.get 能查到资料时，给值SUCCESS"
     */

    // if(freightOut.getAfsFreightId()!=null){
    // aomsrefundT.setAoms037("WAIT_SELLER_CONFIRM_GOODS");
    // }else if(financeOut!=null){
    // aomsrefundT.setAoms037("SUCCESS");
    // }else{
    aomsrefundT.setAoms037("WAIT_BUYER_RETURN_GOODS");
    // }

    aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(afsServiceOut.getOrderId()));

    // 退款创建时间
    // API：jingdong.afsservice.refundinfo.get（ 获取退款信息）字段：afsFinanceOut.opTime
    // "API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段： result.approvedDate
    aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(afsServiceOut.getApprovedDate()));// CommonUtil.checkNullOrNot(financeOut
                                                                                       // == null ?
                                                                                       // null :
                                                                                       // financeOut.getOpTime()));

    // 退款说明
    // API：jingdong.afsservice.refundinfo.get （ 获取退款信息）字段：afsRefundOut.mark

    aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(refundOut
                                                            // == null ? null :
                                                            // refundOut.getMark()));

    // 退款原因
    // API：jingdong.afsservice.refundinfo.get （ 获取退款信息）字段：afsFinanceOut.reason

    aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(null));// CommonUtil.checkNullOrNot(financeOut
                                                            // == null ? null :
                                                            // financeOut.getReason()));
    aomsrefundT.setStoreType(JingdongCommonTool.STORE_TYPE);
    aomsrefundT.setStoreId(storeId);
    aomsrefundT.setAoms048(CommonUtil.checkNullOrNot(null));

    // 买家姓名
    // API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.customerName
    aomsrefundT.setAoms049(CommonUtil.checkNullOrNot(afsServiceOut.getCustomerName()));

    // sku_id（电商）
    // API：jingdong.afsservice.receivetask.get（获取待收货服务单信息）字段：result.wareId
    aomsrefundT.setAoms050(CommonUtil.checkNullOrNot(afsServiceOut.getWareId()));


    aomsrefundT.setModified(CommonUtil.checkNullOrNot(afsServiceOut.getApprovedDate() == null
        ? afsServiceOut.getAfsApplyTime() : afsServiceOut.getApprovedDate()));

    String date = CommonUtil.checkNullOrNot(new Date());
    aomsrefundT.setAomsstatus("0");
    aomsrefundT.setAomscrtdt(date);
    aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

    aomsrefundTs.add(aomsrefundT);
    return aomsrefundTs;
  }
}
