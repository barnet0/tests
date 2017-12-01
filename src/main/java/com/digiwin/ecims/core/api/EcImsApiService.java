package com.digiwin.ecims.core.api;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;

/**
 * 單筆拉取 service 用的
 * 
 * @author Xavier
 *
 */
public interface EcImsApiService {

   public enum EcImsApiEnum {
    DIGIWIN_ORDER_DETAIL_GET("digiwin.order.detail.get"),
    DIGIWIN_JD_EXPRESSNO_GET("digiwin.jd.expressno.get"),
    
    DIGIWIN_LOGISTICS_AREAS_GET("digiwin.logistics.areas.get"),
    DIGIWIN_SHIPPING_SEND("digiwin.shipping.send"),
    DIGIWIN_JD_WAYBILL_SEND("digiwin.jd.waybill.send"),
    DIGIWIN_JD_PACKAGE_UPDATE("digiwin.jd.package.update"),
    DIGIWIN_DD_SHIPPING_DDSEND("digiwin.dd.shipping.ddsend"),
    DIGIWIN_DD_RECEIPT_DETAILS_LIST("digiwin.dd.receipt.details.get"),
    DIGIWIN_DD_PICKGOODS_UPDATE("digiwin.dd.pickgoods.update"),
    DIGIWIN_VIP_JIT_SHIPPING_SEND("digiwin.vip.jit.shipping.send"),
    DIGIWIN_LOGISTICS_TRACE_SEARCH("digiwin.logistics.trace.search"),
    
    DIGIWIN_WLB_WAYBILL_I_GET("digiwin.wlb.waybill.i.get"),
    DIGIWIN_WLB_WAYBILL_I_UPDATE("digiwin.wlb.waybill.i.update"),
    DIGIWIN_WLB_WAYBILL_I_CANCEL("digiwin.wlb.waybill.i.cancel"),
    DIGIWIN_WLB_WAYBILL_I_PRINT("digiwin.wlb.waybill.i.print"),
    DIGIWIN_ONLINE_SHIPPING_SEND("digiwin.online.shipping.send"),
    
    DIGIWIN_INVENTORY_UPDATE("digiwin.inventory.update"),
    DIGIWIN_INVENTORY_BATCH_UPDATE("digiwin.inventory.batch.update"),
    
    DIGIWIN_REFUND_GET("digiwin.refund.get"),
    
    DIGIWIN_ITEM_LISTING("digiwin.item.listing"),
    DIGIWIN_ITEM_DELISTING("digiwin.item.delisting"),
    DIGIWIN_ITEM_DETAIL_GET("digiwin.item.detail.get"),
    DIGIWIN_ITEM_UPDATE("digiwin.item.update"),
    
    DIGIWIN_PICTURE_EXTERNAL_UPLOAD("digiwin.picture.external.upload"),
    
    DIGIWIN_RATE_ADD("digiwin.rate.add"),
    DIGIWIN_RATE_LIST_ADD("digiwin.rate.list.add"),
    
    DIGIWIN_KAOLA_EXPRESSINF_GET("digiwin.kaola.expressinf.get"), //2017.06.08 by cjp
    
    DIGIWIN_PDD_SKUSTOCK_GET("digiwin.pdd.skustock.get"), //2017.06.21 by cjp
    
    DIGIWIN_PDD_REFUNDSTATUS_CHECK("digiwin.pdd.refund.status.check"), //2017.06.22 by cjp
    
    NO_VALUE("");
    
    private String apiName;
    EcImsApiEnum(String apiName) {
        this.apiName = apiName;
    }
    
    public String getApiName() {
      return apiName;
    }

    public String toString() {
      return apiName;
    }
    
    public static EcImsApiEnum parse(String apiName) {
      for (EcImsApiEnum apiEnum : EcImsApiEnum.values()) {
        if (apiEnum.getApiName().equals(apiName)) {
          return apiEnum;
        }
      }
      return NO_VALUE;
    }
    
   }

  public static final String CMD_INVENTORY_UPDATE_DELIMITER = ",";

  public static final String REFUND_TYPE_NORMAL = "0";
  public static final String REFUND_TYPE_TBFX = "1";
  public static final String REFUND_TYPE_JDAFSERVICE = "2";
  
  /**
   * service執行起始位置
   * 
   * @param cmdReq command request bean
   * @return command response bean
   * @throws IOException
   * @throws ClientProtocolException
   * @throws JdException
   */
  CmdRes execute(CmdReq cmdReq) throws Exception;

  CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception;

  CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception;

  CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception;

  CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception;


  CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception;


  CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception;

  CmdRes digiwinItemDelisting(CmdReq cmdreq) throws Exception;

  CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception;

  CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception;


  CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception;
}
