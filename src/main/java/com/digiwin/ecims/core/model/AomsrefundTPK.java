package com.digiwin.ecims.core.model;

import java.io.Serializable;

/**
 * The primary key class for the aomsrefund_t database table.
 * 
 */
//@Embeddable
public class AomsrefundTPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name = "aomsrefund001")
	private String id;

//	@Column(name = "aomsrefund044")
	private String storeType;
	

	private String aoms023;// 申请退款的商品数字编号   add by Ken 2015/07/15  

	public AomsrefundTPK() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getAoms023() {
		return aoms023;
	}

	public void setAoms023(String aoms023) {
		this.aoms023 = aoms023;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aoms023 == null) ? 0 : aoms023.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((storeType == null) ? 0 : storeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AomsrefundTPK other = (AomsrefundTPK) obj;
		if (aoms023 == null) {
			if (other.aoms023 != null)
				return false;
		} else if (!aoms023.equals(other.aoms023))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (storeType == null) {
			if (other.storeType != null)
				return false;
		} else if (!storeType.equals(other.storeType))
			return false;
		return true;
	}

	

	
}