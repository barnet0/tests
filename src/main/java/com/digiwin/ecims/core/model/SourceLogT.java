package com.digiwin.ecims.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.digiwin.ecims.core.util.DateTimeTool;

/**
 * The persistent class for the aomsitem_t database table.
 * 
 */
@Entity
@Table(name = "source_log_t")
@NamedQuery(name = "SourceLogT.findAll", query = "SELECT a FROM SourceLogT a")
/*
 * 新增@DynamicUpdate(true)注解标记，如果没有发生变化的字段，在更新时，就不会进行update， 如果不加，就更新所有字段
 */
@DynamicUpdate(true)
public class SourceLogT implements Serializable, Cloneable {
	public SourceLogT() {
		this.createDate = DateTimeTool.formatToMillisecond(new Date());

	}

	public SourceLogT(BigInteger logId, String startDate, String endDate, String storeType, String method,
			String source) {
		this.logId = logId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.storeType = storeType;
		this.method = method;
		this.source = source;
		this.createDate = DateTimeTool.formatToMillisecond(new Date());
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "log_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger logId;

	@Column(name = "start_date")
	private String startDate;

	@Column(name = "end_date")
	private String endDate;

	@Column(name = "store_type")
	private String storeType;

	@Column(name = "method")
	private String method;

	@Column(name = "source")
	private String source;

	@Column(name = "create_date")
	private String createDate;

	@Column(name = "business_type")
	private String businessType; // add by mowj 20150825
									// 给值：AomsordT,AomsitemT,AomsrefundT

	// add by xavier on 20150828, 用來記, 是哪一個電商的資料
	@Column(name = "shop_id")
	private String shopId;

	// add by xavier on 20150831, 用來記, 是哪一個排程, 所產生的資料
	@Column(name = "schedule_type")
	private String scheduleType;
	// add by xavier on 20150831, 用來記, 是哪一個排程, 所產生的資料
	@Column(name = "Mapping_Code")
	private String mappingCode;

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public BigInteger getLogId() {
		return logId;
	}

	public void setLogId(BigInteger logId) {
		this.logId = logId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getMappingCode() {
		return mappingCode;
	}

	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}

}