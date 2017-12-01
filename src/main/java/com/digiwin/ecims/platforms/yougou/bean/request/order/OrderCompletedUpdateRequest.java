package com.digiwin.ecims.platforms.yougou.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderCompletedUpdateResponse;

/**
 * @author 维杰
 *
 */
public class OrderCompletedUpdateRequest extends YougouBaseRequest<OrderCompletedUpdateResponse> {

  /**
   * 子订单号列表,多个子订单时,需要用逗号 , 间隔开(如：订单1,订单2)
   */
  private String orderSubNos;


  /**
   * 子订单号绑定的快递单号列表 快递单号与其绑定的子订单号位置必须一致，如：‘快递单123‘绑定的子订单号在‘order_sub_nos‘参数第二位，则‘快递单123’也必须位于第二位
   * 多个快递单时,需要用逗号 , 间隔开(如：快递单1,快递单2)
   * 
   */
  private String expressCodes;

  /**
   * 子订单号绑定的发货时间列表.格式：yyyy-MM-dd HH:mm:ss
   * 发货时间与其绑定的子订单号位置必须一致，如：‘发货时间123‘绑定的子订单号在‘order_sub_nos‘参数第二位，则‘发货时间123’也必须位于第二位 多个发货时间时,需要用逗号 ,
   * 间隔开(如：发货时间1,发货时间2)
   */
  private String shipTimes;

  /**
   * 子订单号绑定的物流公司代码列表
   * 物流公司代码与其绑定的子订单号位置必须一致，如：‘物流公司代码123‘绑定的子订单号在‘order_sub_nos‘参数第二位，则‘物流公司代码123’也必须位于第二位
   * 多个物流公司代码时,需要用逗号 , 间隔开(如：物流公司代码1,物流公司代码2)
   */
  private String logisticsCompanyCodes;

  public String getOrderSubNos() {
    return orderSubNos;
  }

  public void setOrderSubNos(String orderSubNos) {
    this.orderSubNos = orderSubNos;
  }

  public String getExpressCodes() {
    return expressCodes;
  }

  public void setExpressCodes(String expressCodes) {
    this.expressCodes = expressCodes;
  }

  public String getShipTimes() {
    return shipTimes;
  }

  public void setShipTimes(String shipTimes) {
    this.shipTimes = shipTimes;
  }

  public String getLogisticsCompanyCodes() {
    return logisticsCompanyCodes;
  }

  public void setLogisticsCompanyCodes(String logisticsCompanyCodes) {
    this.logisticsCompanyCodes = logisticsCompanyCodes;
  }

  public OrderCompletedUpdateRequest() {
    super();
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getOrderSubNos() != null) {
      apiParams.put("order_sub_nos", getOrderSubNos());
    }
    if (getExpressCodes() != null) {
      apiParams.put("express_codes", getExpressCodes());
    }
    if (getShipTimes() != null) {
      apiParams.put("ship_times", getShipTimes());
    }
    if (getLogisticsCompanyCodes() != null) {
      apiParams.put("logistics_company_codes", getLogisticsCompanyCodes());
    }

    return apiParams;
  }

  @Override
  public Class<OrderCompletedUpdateResponse> getResponseClass() {
    return OrderCompletedUpdateResponse.class;
  }

  @Override
  public String getApiMethodName() {
    return "yougou.order.completed.update";
  }

}
