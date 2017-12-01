package com.digiwin.ecims.platforms.aliexpress.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.Money;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenAddressDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenChildOrderDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenLogisticInfoDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenPersonDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressDateTimeTool;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;

public class AomsordTTranslator {

  // private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);

  private Object detail;

  public void setDetail(Object detail) {
    this.detail = detail;
  }

  public AomsordTTranslator() {}

  public AomsordTTranslator(Object detail) {
    super();
    this.detail = detail;
  }

  public List<AomsordT> doTranslate(String storeId) {
    if (this.detail instanceof FindOrderByIdResponse) {
      return parseAeOrderToAomsordT(storeId);
    } else {
      return new ArrayList<AomsordT>();
    }
  }

  private List<AomsordT> parseAeOrderToAomsordT(String storeId) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    FindOrderByIdResponse orderDetail = (FindOrderByIdResponse) this.detail;

    for (TpOpenChildOrderDTO childOrder : orderDetail.getChildOrderList()) {
      AomsordT aomsordT = new AomsordT();

      aomsordT.setId(CommonUtil.checkNullOrNot(orderDetail.getId()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderDetail.getOrderStatus()));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(
          AliexpressDateTimeTool.turnAeResDateStringToDateString(orderDetail.getGmtCreate())));
      aomsordT.setModified(CommonUtil.checkNullOrNot(
          AliexpressDateTimeTool.turnAeResDateStringToDateString(orderDetail.getGmtModified())));
      // List<TpOpenOrderMsgDTO> orderMsgList = orderDetail.getOrderMsgList();
      // // TODO 这里只取了列表的第一项
      // if (orderMsgList != null && orderMsgList.size() > 0) {
      // aomsordT.setAoms009(CommonUtil.checkNullOrNot(orderMsgList.get(0).getContent()));
      // }
      TpOpenAddressDTO receiptAddress = orderDetail.getReceiptAddress();
      // add by mowj 20160428 与敦煌网相同，添加为固定格式地址到备注
      aomsordT.setAoms009(generateOverseaAddress(receiptAddress));


      aomsordT.setAoms008(CommonUtil.checkNullOrNot(
          AliexpressDateTimeTool.turnAeResDateStringToDateString(orderDetail.getGmtTradeEnd())));
      aomsordT.setAoms016(CommonUtil.checkNullOrNot(orderDetail.getSellerSignerFullname()));
      Money orderAmount = orderDetail.getOrderAmount();
      // 022的金额使用美金，ERP来处理汇率问题
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderAmount.getAmount()));
      aomsordT.setAoms023(CommonUtil.checkNullOrNot(orderDetail.getPaymentType()));
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(
          AliexpressDateTimeTool.turnAeResDateStringToDateString(orderDetail.getGmtPaySuccess())));

      TpOpenPersonDTO buyerInfo = orderDetail.getBuyerInfo();
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderDetail.getBuyerSignerFullname()));
      aomsordT.setAoms026(CommonUtil.checkNullOrNot(buyerInfo.getEmail()));

      List<TpOpenLogisticInfoDTO> logisticInfos = orderDetail.getLogisticInfoList();
      if (logisticInfos != null && logisticInfos.size() > 0) {
        // TODO 需要测试后验证。这里只取了列表的第一项
        aomsordT
            .setAoms034(CommonUtil.checkNullOrNot(logisticInfos.get(0).getLogisticsServiceName()));
      }

      // 035的金额使用美金，ERP来处理汇率问题
      Money logisticsAmount = orderDetail.getLogisticsAmount();
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(logisticsAmount.getAmount()));

      aomsordT.setAoms036(CommonUtil.checkNullOrNot(receiptAddress.getContactPerson()));

      // 省市区不用标准化，使用国际快递国内转发点发货。由ERP处理这部分信息
      // if (receiptAddress.getProvince().length() > 25) {
      // logger.info("id:{}, province:{}", orderDetail.getId(), receiptAddress.getProvince());
      // }
      aomsordT.setAoms037(CommonUtil.checkNullOrNot(receiptAddress.getProvince()));
      aomsordT.setAoms038(CommonUtil.checkNullOrNot(receiptAddress.getCity()));
      aomsordT.setAoms039(CommonUtil.checkNullOrNot(null));

      aomsordT.setAoms040(CommonUtil.checkNullOrNot(receiptAddress.getDetailAddress()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(receiptAddress.getZip()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(receiptAddress.getMobileNo()));
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(receiptAddress.getPhoneNumber()));
      aomsordT.setAoms045(CommonUtil.checkNullOrNot(orderDetail.getEscrowFee().getAmount()));

      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(AliexpressCommonTool.STORE_TYPE);

      aomsordT.setAoms058(CommonUtil.checkNullOrNot(childOrder.getId()));
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(childOrder.getProductId()));

      // modify by mowj 20160613 如果没有平台sku编码，则使用平台商品编码
      if (childOrder.getSkuCode() == null || childOrder.getSkuCode().length() == 0) { 
        aomsordT.setAoms060(CommonUtil.checkNullOrNot(childOrder.getProductId()));
      } else {
        aomsordT.setAoms060(CommonUtil.checkNullOrNot(childOrder.getSkuCode()));
      }
      
      aomsordT.setAoms061(
          CommonUtil.checkNullOrNot(getColorAndSizeAttributes(childOrder.getProductAttributes())));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(childOrder.getProductCount()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(childOrder.getProductPrice().getAmount()));

      aomsordT.setAoms071(CommonUtil.checkNullOrNot(childOrder.getInitOrderAmt().getAmount()));

      Date now = new Date();
      aomsordT.setAomsstatus("0");
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsordTs.add(aomsordT);
    }

    return aomsordTs;
  }

  private String getColorAndSizeAttributes(String productAttributesJson) {
    JsonNode rootNode = JsonUtil.getJsonNodeByFieldName(productAttributesJson, "sku");
    String color = "";
    String size = "";
    if (rootNode.isArray()) {
      Iterator<JsonNode> childrenNodesIterator = rootNode.elements();
      while (childrenNodesIterator.hasNext()) {
        JsonNode childNode = childrenNodesIterator.next();
        String selfDefineValue = "";
        int pidValue = childNode.get("pId").asInt();
        if (pidValue == AliexpressCommonTool.COLOR_PID) {
          color = childNode.get("pValue").asText();
          selfDefineValue = childNode.get("selfDefineValue").asText();
          if (selfDefineValue.length() > 0) {
            color = selfDefineValue;
          }
        }
        if (pidValue == AliexpressCommonTool.SIZE_PID) {
          size = childNode.get("pValue").asText();
          selfDefineValue = childNode.get("selfDefineValue").asText();
          if (selfDefineValue.length() > 0) {
            size = selfDefineValue;
          }
        }
        if (color.length() > 0 && size.length() > 0) {
          break;
        }
      }
    }

    return "COLOR:" + color + ";" + "SIZE:" + size;
  }

  /**
   * 拼接海外联系人地址。格式如下：
   * <p>
   * Contact Name: Amy Sapkota Address Line 1: 7002 Southwark Terrace Address Line 2: City:
   * Hyattsville State: Maryland Country: United States Postal Code: 20782 Phone Number:
   * 1-3016997583
   * </p>
   * 
   * @param orderContact
   * @return
   */
  private String generateOverseaAddress(TpOpenAddressDTO receiptAddress) {
    StringBuilder sbBuilder = new StringBuilder(200);

    sbBuilder.append("Contact Name: ")
        .append(CommonUtil.checkNullOrNot(receiptAddress.getContactPerson())).append(" ")
        .append("Address Line 1: ").append(CommonUtil.checkNullOrNot(receiptAddress.getAddress()))
        .append(" ").append("Address Line 2: ")
        .append(CommonUtil.checkNullOrNot(receiptAddress.getAddress2())).append(" ")
        .append("City: ").append(CommonUtil.checkNullOrNot(receiptAddress.getCity())).append(" ")
        .append("State: ").append(CommonUtil.checkNullOrNot(receiptAddress.getProvince()))
        .append(" ").append("Country: ")
        .append(CommonUtil.checkNullOrNot(receiptAddress.getCountry())).append(" ")
        .append("Postal Code: ").append(CommonUtil.checkNullOrNot(receiptAddress.getZip()))
        .append(" ").append("Phone Number: ")
        .append(CommonUtil.checkNullOrNot(receiptAddress.getMobileNo()));

    return sbBuilder.toString();
  }

  public static void main(String args[]) {
    // String result =
    // getColorAndSizeAttributes("{\"sku\":[{\"order\":1,\"pId\":14,\"pName\":\"Color\",\"pValue\":\"Multi\",\"pValueId\":200003699,\"selfDefineValue\":\"\",\"showType\":\"none\",\"skuImg\":\"\"},{\"order\":2,\"pId\":5,\"pName\":\"Size\",\"pValue\":\"M\",\"pValueId\":361386,\"selfDefineValue\":\"50X28cm\",\"showType\":\"none\",\"skuImg\":\"\"}]}");
    // System.out.println(result);
    System.out.println("a".contains(""));
  }
}
