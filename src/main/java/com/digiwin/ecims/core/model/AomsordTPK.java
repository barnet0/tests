package com.digiwin.ecims.core.model;

import java.io.Serializable;

/**
 * The primary key class for the aomsord_t database table.
 * 
 */
//@Embeddable
public class AomsordTPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name = "aomsord001")
//	@JsonProperty("aomsord001")
	private String id;

//	@Column(name = "aomsord057")
//	@JsonProperty("aomsord057")
	private String storeType;// 店铺类型

//	@Column(name = "aomsord060")
//	@JsonProperty("aomsord060")
	private String aoms060;// SKUID

	public AomsordTPK() {
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

	public String getAoms060() {
		return aoms060;
	}

	public void setAoms060(String aoms060) {
		this.aoms060 = aoms060;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aoms060 == null) ? 0 : aoms060.hashCode());
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
		AomsordTPK other = (AomsordTPK) obj;
		if (aoms060 == null) {
			if (other.aoms060 != null)
				return false;
		} else if (!aoms060.equals(other.aoms060))
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