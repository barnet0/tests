package com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcOrderListRequest implements BasicRequest{

	@XmlTransient
	private final String method = "icbcb2c.order.list";
	
	@XmlElement(name = "create_start_time")
	private String createStartTime;

	@XmlElement(name = "create_end_time")
	private String createEndTime;

	@XmlElement(name = "modify_time_from")
	private String modifyTimeFrom;

	@XmlElement(name = "modify_time_to")
	private String modifyTimeTo;

	@XmlElement(name = "order_status")
	private Integer orderStatus;

	public IcbcOrderListRequest(String createStartTime, String createEndTime,
			String modifyTimeFrom, String modifyTimeTo, Integer orderStatus) {
		this.createStartTime = createStartTime;
		this.createEndTime = createEndTime;
		this.modifyTimeFrom = modifyTimeFrom;
		this.modifyTimeTo = modifyTimeTo;
		this.orderStatus = orderStatus;
	}

	public IcbcOrderListRequest() {

	}

	public IcbcOrderListRequest(String startDate, String endDate, int dateType) {
		if(dateType == 1){
			this.createStartTime = startDate;
			this.createEndTime = endDate;
		}else if(dateType == 2){
			this.modifyTimeFrom = startDate;
			this.modifyTimeTo = endDate;
		}else{
			
		}
	}

	public IcbcOrderListRequest(String startDate, String endDate, UseTimeEnum orderUseTime) {
		if (orderUseTime == UseTimeEnum.CREATE_TIME) {
			this.createStartTime = startDate;
			this.createEndTime = endDate;
		} else if (orderUseTime == UseTimeEnum.UPDATE_TIME){
			this.modifyTimeFrom = startDate;
			this.modifyTimeTo = endDate;
		} else {
			
		}
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

	public String getModifyTimeTol() {
		return modifyTimeTo;
	}

	public void setModifyTimeTo(String modifyTimeTo) {
		this.modifyTimeTo = modifyTimeTo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
