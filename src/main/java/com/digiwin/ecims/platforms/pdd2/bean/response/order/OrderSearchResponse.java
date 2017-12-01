package com.digiwin.ecims.platforms.pdd2.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.OrderSearchResult;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.order_sn_list;

/**
 * 
 * @author cjp 2017/6/19
 *
 */
public class OrderSearchResponse extends Pdd2BaseResponse {

	private long total_count;

	private List<order_sn_list> order_sn_list;

	/**
	 * @return the total_count
	 */
	public long getTotal_count() {
		return total_count;
	}

	/**
	 * @param total_count the total_count to set
	 */
	public void setTotal_count(long total_count) {
		this.total_count = total_count;
	}

	/**
	 * @return the order_sn_list
	 */
	public List<order_sn_list> getOrder_sn_list() {
		return order_sn_list;
	}

	/**
	 * @param order_sn_list the order_sn_list to set
	 */
	public void setOrder_sn_list(List<order_sn_list> order_sn_list) {
		this.order_sn_list = order_sn_list;
	}
	
	


}
