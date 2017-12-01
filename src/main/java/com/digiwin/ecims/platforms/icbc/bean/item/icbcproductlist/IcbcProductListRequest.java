package com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcProductListRequest implements BasicRequest {

	@XmlTransient
	private final String method = "icbcb2c.product.list";

	@XmlElement(name = "create_start_time")
	private String createStartTime;

	@XmlElement(name = "create_end_time")
	private String createEndTime;

	@XmlElement(name = "modify_time_from")
	private String modifyTimeFrom;

	@XmlElement(name = "modify_time_to")
	private String modifyTimeTo;

	@XmlElement(name = "put_time_from")
	private String putTimeFrom;

	@XmlElement(name = "put_time_to")
	private String putTimeTo;

	@XmlElement(name = "product_status")
	private Integer productStatus;

	public IcbcProductListRequest() {

	}

	public IcbcProductListRequest(String createStartTime, String createEndTime,
			String modifyTimeFrom, String modifyTimeTo, String putTimeFrom,
			String putTimeTo, Integer productStatus) {
		this.createStartTime = createStartTime;
		this.createEndTime = createEndTime;
		this.modifyTimeFrom = modifyTimeFrom;
		this.modifyTimeTo = modifyTimeTo;
		this.putTimeFrom = putTimeFrom;
		this.putTimeTo = putTimeTo;
		this.productStatus = productStatus;
	}

	public IcbcProductListRequest(String modify_time_from, String modify_time_to) {
		this.modifyTimeFrom = modify_time_from;
		this.modifyTimeTo = modify_time_to;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getModifyTimeFrom() {
		return modifyTimeFrom;
	}

	public void setModifyTimeFrom(String modifyTimeFrom) {
		this.modifyTimeFrom = modifyTimeFrom;
	}

	public String getModifyTimeTo() {
		return modifyTimeTo;
	}

	public void setModifyTimeTo(String modifyTimeTo) {
		this.modifyTimeTo = modifyTimeTo;
	}

	public String getPutTimeFrom() {
		return putTimeFrom;
	}

	public void setPutTimeFrom(String putTimeFrom) {
		this.putTimeFrom = putTimeFrom;
	}

	public String getPutTimeTo() {
		return putTimeTo;
	}

	public void setPutTimeTo(String putTimeTo) {
		this.putTimeTo = putTimeTo;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
