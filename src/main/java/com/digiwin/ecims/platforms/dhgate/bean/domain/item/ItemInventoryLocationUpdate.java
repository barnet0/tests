package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 获取产品多备货的信息列表
 * @author 维杰
 *
 */
public class ItemInventoryLocationUpdate {

//	可选	产品备货地址编码	示例值：CN
	private String nventoryLocation;
//	必须	备货期	以天为单位，有备货的产品备货期小于等于2天，没有备货的产品为1—60天；示例值：30
	private Integer leadingTime;
//	可选	产品备货地址排序	第几个备货地址，示例值：0 第0个
	private Integer sortVal;
	
	public String getNventoryLocation() {
		return nventoryLocation;
	}
	public void setNventoryLocation(String nventoryLocation) {
		this.nventoryLocation = nventoryLocation;
	}
	public Integer getLeadingTime() {
		return leadingTime;
	}
	public void setLeadingTime(Integer leadingTime) {
		this.leadingTime = leadingTime;
	}
	public Integer getSortVal() {
		return sortVal;
	}
	public void setSortVal(Integer sortVal) {
		this.sortVal = sortVal;
	}
	
	public ItemInventoryLocationUpdate() {
		super();
	}
	
	
}
