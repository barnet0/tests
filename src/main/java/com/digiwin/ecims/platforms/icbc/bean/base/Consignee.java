package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Consignee {

	@XmlElement(name = "consignee_name")
	private String consigneeName;

	@XmlElement(name = "consignee_province")
	private String consigneeProvince;

	@XmlElement(name = "consignee_province_id")
	private String consigneeProvinceId;

	@XmlElement(name = "consignee_city")
	private String consigneeCity;

	@XmlElement(name = "consignee_city_id")
	private String consigneeCityId;

	@XmlElement(name = "consignee_district")
	private String consigneeDistrict;

	@XmlElement(name = "consignee_district_id")
	private String consigneeDistrictId;

	@XmlElement(name = "consignee_address")
	private String consigneeAddress;

	@XmlElement(name = "consignee_zipcode")
	private String consigneeZipcode;

	@XmlElement(name = "consignee_mobile")
	private String consigneeMobile;

	@XmlElement(name = "consignee_phone")
	private String consigneePhone;

	public Consignee() {

	}

	// icbc.order.detail用到
	public Consignee(String consigneeName, String consigneeProvince,
			String consigneeProvinceId, String consigneeCity,
			String consigneeCityId, String consigneeDistrict,
			String consigneeDistrictId, String consigneeAddress,
			String consigneeZipcode, String consigneeMobile,
			String consigneePhone) {
		super();
		this.consigneeName = consigneeName;
		this.consigneeProvince = consigneeProvince;
		this.consigneeProvinceId = consigneeProvinceId;
		this.consigneeCity = consigneeCity;
		this.consigneeCityId = consigneeCityId;
		this.consigneeDistrict = consigneeDistrict;
		this.consigneeDistrictId = consigneeDistrictId;
		this.consigneeAddress = consigneeAddress;
		this.consigneeZipcode = consigneeZipcode;
		this.consigneeMobile = consigneeMobile;
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public String getConsigneeProvinceId() {
		return consigneeProvinceId;
	}

	public void setConsigneeProvinceId(String consigneeProvinceId) {
		this.consigneeProvinceId = consigneeProvinceId;
	}

	public String getConsigneeCity() {
		return consigneeCity;
	}

	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}

	public String getConsigneeCityId() {
		return consigneeCityId;
	}

	public void setConsigneeCityId(String consigneeCityId) {
		this.consigneeCityId = consigneeCityId;
	}

	public String getConsigneeDistrict() {
		return consigneeDistrict;
	}

	public void setConsigneeDistrict(String consigneeDistrict) {
		this.consigneeDistrict = consigneeDistrict;
	}

	public String getConsigneeDistrictId() {
		return consigneeDistrictId;
	}

	public void setConsigneeDistrictId(String consigneeDistrictId) {
		this.consigneeDistrictId = consigneeDistrictId;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeZipcode() {
		return consigneeZipcode;
	}

	public void setConsigneeZipcode(String consigneeZipcode) {
		this.consigneeZipcode = consigneeZipcode;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

}
