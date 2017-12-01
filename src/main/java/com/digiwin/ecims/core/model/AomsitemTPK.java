package com.digiwin.ecims.core.model;

import java.io.Serializable;

/**
 * The primary key class for the aomsitem_t database table.
 * 
 */
//@Embeddable
public class AomsitemTPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name = "aomsitem001")
	private String id;

//	@Column(name = "aomsitem004")
	private String aoms004;

//	@Column(name = "aomsitem011")
	private String storeType;

	public AomsitemTPK() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAoms004() {
		return aoms004;
	}

	public void setAoms004(String aoms004) {
		this.aoms004 = aoms004;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aoms004 == null) ? 0 : aoms004.hashCode());
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
		AomsitemTPK other = (AomsitemTPK) obj;
		if (aoms004 == null) {
			if (other.aoms004 != null)
				return false;
		} else if (!aoms004.equals(other.aoms004))
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