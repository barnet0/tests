package com.digiwin.ecims.core.api;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.util.ErrorMessageBox;

public abstract class EcImsApiServiceDispatcher implements EcImsApiService {

  @Override
  public CmdRes execute(CmdReq cmdReq) throws Exception {
//    String api = cmdReq.getApi();
    CmdRes cmdRes = null;
//
//    switch (EcImsApiEnum.valueOf(api)) {
//      case DIGIWIN_ORDER_DETAIL_GET: 
//        cmdRes = digiwinOrderDetailGet(cmdReq);
//        break;
//      case DIGIWIN_JD_EXPRESSNO_GET:
//        cmdRes = digiwinJdExpressNoGet(cmdReq);
//        break;
//      case DIGIWIN_LOGISTICS_AREAS_GET:
//        cmdRes = digiwinLogisticsAreasGet(cmdReq);
//        break;
//      case DIGIWIN_SHIPPING_SEND:
//        cmdRes = digiwinShippingSend(cmdReq);
//        break;
//      case DIGIWIN_JD_WAYBILL_SEND:
//        cmdRes = digiwinJdWaybillSend(cmdReq);
//        break;
//      case DIGIWIN_JD_PACKAGE_UPDATE:
//        cmdRes = digiwinJdPackageUpdate(cmdReq);
//        break;
//      case DIGIWIN_DD_SHIPPING_DDSEND:
//        cmdRes = digiwinDdShippingDdsend(cmdReq);
//        break;
//      case DIGIWIN_DD_RECEIPT_DETAILS_LIST:
//        cmdRes = digiwinDdReceiptDetailsList(cmdReq);
//        break;
//      case DIGIWIN_DD_PICKGOODS_UPDATE:
//        cmdRes = digiwinDdPickgoodsUpdate(cmdReq);
//        break;
//      case DIGIWIN_VIP_JIT_SHIPPING_SEND:
//        cmdRes = digiwinVipJitShippingSend(cmdReq);
//        break;
//      case DIGIWIN_LOGISTICS_TRACE_SEARCH:
//        cmdRes = digiwinLogisticsTraceSearch(cmdReq);
//        break;
//      case DIGIWIN_INVENTORY_UPDATE:
//        cmdRes = digiwinInventoryUpdate(cmdReq);
//        break;
//      case DIGIWIN_INVENTORY_BATCH_UPDATE:
//        cmdRes = digiwinInventoryBatchUpdate(cmdReq);
//        break;
//      case DIGIWIN_REFUND_GET:
//        cmdRes = digiwinRefundGet(cmdReq);
//        break;
//      case DIGIWIN_ITEM_LISTING:
//        cmdRes = digiwinItemListing(cmdReq);
//        break;
//      case DIGIWIN_ITEM_DELISTING:
//        cmdRes = digiwinItemDelisting(cmdReq);
//        break;
//      case DIGIWIN_ITEM_DETAIL_GET:
//        cmdRes = digiwinItemDetailGet(cmdReq);
//        break;
//      case DIGIWIN_ITEM_UPDATE:
//        cmdRes = digiwinItemUpdate(cmdReq);
//        break;
//      case DIGIWIN_PICTURE_EXTERNAL_UPLOAD:
//        cmdRes = digiwinPictureExternalUpload(cmdReq);
//        break;
//      default:
//        cmdRes = new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
//        break;
//    }

    return cmdRes;
  }

//  public abstract CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinJdExpressNoGet(CmdReq cmdReq) throws Exception;
//  
//  
//  public abstract CmdRes digiwinLogisticsAreasGet(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinJdWaybillSend(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinJdPackageUpdate(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinDdShippingDdsend(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinDdReceiptDetailsList(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinDdPickgoodsUpdate(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinVipJitShippingSend(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinLogisticsTraceSearch(CmdReq cmdReq) throws Exception;
//  
//  /**
//   * 获取菜鸟物流面单号 http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.jEiZyn&apiId=23869
//   * @param cmdReq
//   * @return
//   * @throws ApiException
//   */
//  public abstract CmdRes digiwinWlbWaybillIGet(CmdReq cmdReq) throws Exception;
//  
//  /**
//   * 更新菜鸟物流面单信息
//   * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.g6mYMu&apiId=23871
//   * @param cmdReq
//   * @return
//   * @throws ApiException
//   */
//  public abstract CmdRes digiwinWlbWaybillIUpdate(CmdReq cmdReq) throws Exception;
//  
//  /**
//   * 取消已经获取的菜鸟物流面单号
//   * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.JCvrHY&apiId=23874
//   * @param cmdReq
//   * @return
//   * @throws ApiException
//   */
//  public abstract CmdRes digiwinWlbWaybillICancel(CmdReq cmdReq) throws Exception;
//  
//  
//  /**
//   * 打印菜鸟面单之前确认打印接口
//   * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.sxnUhk&apiId=23872
//   * @param cmdReq
//   * @return
//   * @throws ApiException
//   */
//  public abstract CmdRes digiwinWlbWaybillIPrint(CmdReq cmdReq) throws Exception;
//  
//  
//  /**
//   * 在线订单发货
//   * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.4muPv1&apiId=10687
//   * @param cmdReq
//   * @return
//   * @throws ApiException
//   */
//  public abstract CmdRes digiwinOnlineShippingSend(CmdReq cmdReq) throws Exception;
//
//  
//  public abstract CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception;
//  
//  
//  public abstract CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception;
//  
//
//  public abstract CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception;
//  
//  public abstract CmdRes digiwinItemDelisting(CmdReq cmdreq) throws Exception;
//  
//  public abstract CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception;
//
//  public abstract CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception;
//
//
//  public abstract CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception;
}
