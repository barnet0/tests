package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

import java.util.List;

/**
 * 产品备货信息
 * @author 维杰
 *
 */
public class ItemInventoryUpdate {

//	必须	产品备货地	示例值：CN
	private String inventoryLocation;
//	ItemInventoryLocationUpdate[]	可选	产品备货地址信息列表	产品备货地址信息列表
	private List<ItemInventoryLocationUpdate> inventoryLocationList;
//	可选	产品原备货量	示例值：100；设置该值时，与产品备货数量一致
	private Integer inventoryOriQty;
//	必须	产品备货数量	备货状态为有备货时必填，设置该值时，与产品中可销售的SKU之和一致；示例值：100
	private Integer inventoryQty;
//	可选	是否有备货：0否，1是	0：没有备货，1：有备货（有备货的产品备货期小于等于2天，没有备货的产品备货期为1—60天）；示例值：1
	private String inventoryStatus;
	
	public String getInventoryLocation() {
		return inventoryLocation;
	}
	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	public List<ItemInventoryLocationUpdate> getInventoryLocationList() {
		return inventoryLocationList;
	}
	public void setInventoryLocationList(List<ItemInventoryLocationUpdate> inventoryLocationList) {
		this.inventoryLocationList = inventoryLocationList;
	}
	public Integer getInventoryOriQty() {
		return inventoryOriQty;
	}
	public void setInventoryOriQty(Integer inventoryOriQty) {
		this.inventoryOriQty = inventoryOriQty;
	}
	public Integer getInventoryQty() {
		return inventoryQty;
	}
	public void setInventoryQty(Integer inventoryQty) {
		this.inventoryQty = inventoryQty;
	}
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
	
	public ItemInventoryUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
