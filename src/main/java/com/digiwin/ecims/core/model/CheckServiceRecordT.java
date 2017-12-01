package com.digiwin.ecims.core.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the check_service_record_t database table.
 * 
 */
@Entity
@Table(name="check_service_record_t")
@NamedQuery(name="CheckServiceRecordT.findAll", query="SELECT c FROM CheckServiceRecordT c")
public class CheckServiceRecordT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	@Column(name = "service")
	private String service;
	
	@Column(name = "check_start_date")
	private String startDate;
	
	@Column(name = "check_end_date")
	private String endDate;
	
	@Column(name = "results")
	private Long results;
	
	@Column(name = "store_type")
	private String storeType;
	
	@Column(name = "store_id")
	private String storeId;

	@Column(name="schedule_type")
	private String scheduleType;
	
	@Column(name = "mapping_code")
	private String mappingCode;

	public CheckServiceRecordT() {
	}

	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the results
	 */
	public Long getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(Long results) {
		this.results = results;
	}

	/**
	 * @return the storeType
	 */
	public String getStoreType() {
		return storeType;
	}

	/**
	 * @param storeType the storeType to set
	 */
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the scheduleType
	 */
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * @param scheduleType the scheduleType to set
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	/**
	 * @return the mappingCode
	 */
	public String getMappingCode() {
		return mappingCode;
	}

	/**
	 * @param mappingCode the mappingCode to set
	 */
	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}

	

}