package com.digiwin.ecims.platforms.aliexpress.bean.request.delivery;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.delivery.SellerShipmentResponse;

public class SellerShipmentRequest extends AliExpressBaseRequest<SellerShipmentResponse> {

  // 否 用户选择的实际发货物流服务（物流服务key：该接口根据api.listLogisticsService列出平台所支持的物流服务
  // 进行获取目前所支持的物流。平台支持物流服务详细一览表详见论坛链接http://bbs.seller.aliexpress.com/bbs/read.php?tid=266120&page=1&toread=1#tpc）
  // AUSPOST, ROYAL_MAIL, CORREOS, DEUTSCHE_POST, LAPOSTE, POSTEITALIANE, RUSSIAN_POST, USPS,
  // UPS_US, UPS, JNE, ACOMMERCE, UPSE, DHL_UK, DHL_ES, DHL_IT, DHL_DE, ENVIALIA, DHL_FR, DHL,
  // FEDEX, FEDEX_IE, TNT, SF, EMS, ROYAL_MAIL_PY, EMS_ZX_ZX_US, E_EMS, EMS_SH_ZX_US, SINOTRANS_AM,
  // ITELLA_PY, ITELLA, CPAM, SINOTRANS_PY, YANWEN_JYT, CPAP, TOLL, HKPAM, HKPAP, SGP, CHP, SEP,
  // ARAMEX, ECONOMIC139, SPSR_RU, YANWEN_AM, CPAM_HRB, CTR_LAND_PICKUP, SPSR_CN, POST_NL, POST_MY,
  // OTHER_ES, OTHER_IT, OTHER_FR, OTHER_US, OTHER_UK, OTHER_RU, OTHER_DE, OTHER_AU, Other
  private String serviceName;

  // 否 物流追踪号
  private String logisticsNo;

  // 否 备注(只能输入英文，且长度限制在512个字符。）
  private String description;

  // 否 状态包括：全部发货(all)、部分发货(part)
  private String sendType;

  // 否 用户需要发货的订单id
  private String outRef;

  // 否 当serviceName=Other的情况时，需要填写对应的追踪网址
  private String trackingWebsite;

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getLogisticsNo() {
    return logisticsNo;
  }

  public void setLogisticsNo(String logisticsNo) {
    this.logisticsNo = logisticsNo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSendType() {
    return sendType;
  }

  public void setSendType(String sendType) {
    this.sendType = sendType;
  }

  public String getOutRef() {
    return outRef;
  }

  public void setOutRef(String outRef) {
    this.outRef = outRef;
  }

  public String getTrackingWebsite() {
    return trackingWebsite;
  }

  public void setTrackingWebsite(String trackingWebsite) {
    this.trackingWebsite = trackingWebsite;
  }

  @Override
  protected String getApiName() {
    return "api.sellerShipment";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getServiceName() != null) {
      apiParams.put("serviceName", getServiceName());
    }
    if (getLogisticsNo() != null) {
      apiParams.put("logisticsNo", getLogisticsNo() + "");
    }
    if (getDescription() != null) {
      apiParams.put("description", getDescription());
    }
    if (getSendType() != null) {
      apiParams.put("sendType", getSendType());
    }
    if (getOutRef() != null) {
      apiParams.put("outRef", getOutRef());
    }
    if (getTrackingWebsite() != null) {
      apiParams.put("trackingWebsite", getTrackingWebsite());
    }
    return apiParams;
  }

  @Override
  public Class<SellerShipmentResponse> getResponseClass() {
    return SellerShipmentResponse.class;
  }

}
