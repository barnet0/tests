package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

/**
 * 收货人基本信息
 * @author 维杰
 *
 */
public class OrderContact {

//	必须	地址1(发货信息)	示例值：yousheng building, wudaokou street, haidian district,表示发货地址是海淀区五道口优盛大厦
	private String addressLine1;
//	必须	地址2(发货信息)	示例值：yousheng building, wudaokou street, haidian district,表示发货地址是海淀区五道口优盛大厦
	private String addressLine2;
//	必须	买家ID	示例值：ff808081416839d5014168e43ab30033
	private String buyerId;
//	必须	买家别名	买家昵称；示例值：zhangsan
	private String buyerNickName;
//	必须	城市(发货信息)	示例值：beijing,表示发货城市是北京
	private String city;
//	必须	发货国家	示例值：china,表示发货国家是中国
	private String country;
//	必须	收货人的邮件	收货人的邮件
	private String email;
//	必须	收货人姓名	示例值：michael
	private String firstName;
//	必须	收货人姓名	示例值：jackson
	private String lastName;
//	必须	邮编(发货信息)	示例值：100190
	private String postalcode;
//	必须	省(发货信息)	示例值：beijing,表示发货省份是北京
	private String state;
//	必须	电话(发货信息)	示例值：1380000000
	private String telephone;
//	必须	税号	示例值：01041247842
	private String vatNumber;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerNickName() {
		return buyerNickName;
	}
	public void setBuyerNickName(String buyerNickName) {
		this.buyerNickName = buyerNickName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	
}
