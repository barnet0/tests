package com.digiwin.ecims.core.bean.area;


/**获取标准省市区接口的返回实体
 * @author 维杰
 * @since 2015.7.4
 */
public class AreaResponse {
	private String country;
	private String province;
	private String city;
	private String district;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public AreaResponse() {}
	
	public AreaResponse(String country, String province, String city,
			String district) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
		this.district = district;
	}
	
	
}
