package com.digiwin.ecims.platforms.pdd2.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.OrderSearchResult;

public class CheckOrdersAfterSaleResponse extends Pdd2BaseResponse {

  private List<OrderSearchResult> order_sns_exists_refund;

/**
 * @return the order_sns_exists_refund
 */
public List<OrderSearchResult> getOrder_sns_exists_refund() {
	return order_sns_exists_refund;
}

/**
 * @param order_sns_exists_refund the order_sns_exists_refund to set
 */
public void setOrder_sns_exists_refund(List<OrderSearchResult> order_sns_exists_refund) {
	this.order_sns_exists_refund = order_sns_exists_refund;
}



}
