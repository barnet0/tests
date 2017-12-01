package com.digiwin.ecims.platforms.pdd2.bean.response.refund;

import java.util.List;

import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;
import com.digiwin.ecims.platforms.pdd2.bean.domain.refund.RefundList;

/**
 * 
 * @author cjp
 *
 */
public class RefundGetResponse extends Pdd2BaseResponse{
	
	private long total_count;

    private List<RefundList> refund_list;
	public long getTotal_count() {
		return total_count;
	}

	public void setTotal_count(long total_count) {
		this.total_count = total_count;
	}

	public List<RefundList> getRefund_list() {
		return refund_list;
	}

	public void setRefund_list(List<RefundList> refund_list) {
		this.refund_list = refund_list;
	}

	
	    
	    
}
