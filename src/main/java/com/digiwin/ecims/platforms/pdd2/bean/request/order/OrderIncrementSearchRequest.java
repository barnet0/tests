package com.digiwin.ecims.platforms.pdd2.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderIncrementSearchResponse;

public class OrderIncrementSearchRequest extends Pdd2BaseRequest<OrderIncrementSearchResponse> {

	private Integer is_lucky_flag;
	private Integer order_status;
	private Integer refund_status;
	private String start_updated_at;
	private String end_updated_at;
	private Integer page_size;
	private Integer page;

	/**
	 * @return the is_lucky_flag
	 */
	public Integer getIs_lucky_flag() {
		return is_lucky_flag;
	}

	/**
	 * @param is_lucky_flag
	 *            the is_lucky_flag to set
	 */
	public void setIs_lucky_flag(Integer is_lucky_flag) {
		this.is_lucky_flag = is_lucky_flag;
	}

	/**
	 * @return the order_status
	 */
	public Integer getOrder_status() {
		return order_status;
	}

	/**
	 * @param order_status
	 *            the order_status to set
	 */
	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	/**
	 * @return the refund_status
	 */
	public Integer getRefund_status() {
		return refund_status;
	}

	/**
	 * @param refund_status
	 *            the refund_status to set
	 */
	public void setRefund_status(Integer refund_status) {
		this.refund_status = refund_status;
	}

	/**
	 * @return the start_updated_at
	 */
	public String getStart_updated_at() {
		return start_updated_at;
	}

	/**
	 * @param start_updated_at
	 *            the start_updated_at to set
	 */
	public void setStart_updated_at(String start_updated_at) {
		this.start_updated_at = start_updated_at;
	}

	/**
	 * @return the end_updated_at
	 */
	public String getEnd_updated_at() {
		return end_updated_at;
	}

	/**
	 * @param end_updated_at
	 *            the end_updated_at to set
	 */
	public void setEnd_updated_at(String end_updated_at) {
		this.end_updated_at = end_updated_at;
	}

	/**
	 * @return the page_size
	 */
	public Integer getPage_size() {
		return page_size;
	}

	/**
	 * @param page_size
	 *            the page_size to set
	 */
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();
		apiParams.put("is_lucky_flag", getIs_lucky_flag().toString());
		apiParams.put("order_status", getOrder_status().toString());
		apiParams.put("refund_status", getRefund_status().toString());
		apiParams.put("start_updated_at", getStart_updated_at());
		apiParams.put("end_updated_at", getEnd_updated_at());
		apiParams.put("page_size", getPage_size().toString());
		apiParams.put("page", getPage().toString());
		return apiParams;
	}

	@Override
	public String getMType() {
		return "pdd.order.number.list.increment.get";
	}

	@Override
	public Class<OrderIncrementSearchResponse> getResponseClass() {

		return OrderIncrementSearchResponse.class;
	}

}
