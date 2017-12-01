package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

import java.math.BigDecimal;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class ItemPostage {
	private long id;
	private long item_id;
	private long business_id;
	private int is_postage_fee;
	private BigDecimal post_fee;
	private BigDecimal expess_fee;
	private BigDecimal ems_fee;
	private String postage_template_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public long getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(long business_id) {
		this.business_id = business_id;
	}

	public int getIs_postage_fee() {
		return is_postage_fee;
	}

	public void setIs_postage_fee(int is_postage_fee) {
		this.is_postage_fee = is_postage_fee;
	}

	public BigDecimal getPost_fee() {
		return post_fee;
	}

	public void setPost_fee(BigDecimal post_fee) {
		this.post_fee = post_fee;
	}

	public BigDecimal getExpess_fee() {
		return expess_fee;
	}

	public void setExpess_fee(BigDecimal expess_fee) {
		this.expess_fee = expess_fee;
	}

	public BigDecimal getEms_fee() {
		return ems_fee;
	}

	public void setEms_fee(BigDecimal ems_fee) {
		this.ems_fee = ems_fee;
	}

	public String getPostage_template_id() {
		return postage_template_id;
	}

	public void setPostage_template_id(String postage_template_id) {
		this.postage_template_id = postage_template_id;
	}

}
