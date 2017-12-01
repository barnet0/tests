package com.digiwin.ecims.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the aomsitem_t database table.
 * 
 */
@Entity
@Table(name = "aomsitem_t")
@IdClass(AomsitemTPK.class)
@NamedQuery(name = "AomsitemT.findAll", query = "SELECT a FROM AomsitemT a")
public class AomsitemT implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String BIZ_NAME = "AomsitemT";
	
	@Id
	@Column(name = "aomsitem001")
	@JsonProperty("aomsitem001")
	private String id;// 商品在电商平台的编码

	@Id
	@Column(name = "aomsitem004")
	@JsonProperty("aomsitem004")
	private String aoms004;// 商品在电商平台的规格编码(sku_id)

	@Id
	@Column(name = "aomsitem011")
	@JsonProperty("aomsitem011")
	private String storeType;// 商品所在店铺类型

	@Column(name = "aomsitem002")
	@JsonProperty("aomsitem002")
	private String aoms002 = "";// 商品的外部编码

	@Column(name = "aomsitem003")
	@JsonProperty("aomsitem003")
	private String aoms003 = "";// 商品在电商平台的名称

	@Column(name = "aomsitem005")
	@JsonProperty("aomsitem005")
	private String aoms005 = "";// 商品的外部规格编码(outersku_id)

	@Column(name = "aomsitem006")
	@JsonProperty("aomsitem006")
	private String aoms006 = "";// 商品在电商平台的规格名称

	@Column(name = "aomsitem007")
	@JsonProperty("aomsitem007")
	private String aoms007 = "";// 商品电商平台状态

	@Column(name = "aomsitem008")
	@JsonProperty("aomsitem008")
	private String aoms008 = "";// 商品在电商平台SKU的颜色

	@Column(name = "aomsitem009")
	@JsonProperty("aomsitem009")
	private String aoms009 = "";// 商品在电商平台SKU的尺寸

	@Column(name = "aomsitem010")
	@JsonProperty("aomsitem010")
	private String storeId = "";// 商品所在店铺id

	@Column(name = "aomsitem012")
	@JsonProperty("aomsitem012")
	private String modified = "";// 更新時間

	@Column(name = "aomsitem013")
	@JsonProperty("aomsitem013")
	private String aoms013 = "";// 商品URL

	@Column(name = "aomsitem014")
	@JsonProperty("aomsitem014")
	private String aoms014 = "";// 图片URL

	@Column(name = "aomsitem015")
	@JsonProperty("aomsitem015")
	private String aoms015 = "";// 商品发布时间

	@Column(name = "aomsitem016")
	@JsonProperty("aomsitem016")
	private String aoms016 = "";// 商品上架时间

	@Column(name = "aomsitem017")
	@JsonProperty("aomsitem017")
	private String aoms017 = "";// 商品下架时间

	@Column(name = "aomsitem018")
	@JsonProperty("aomsitem018")
	private String aoms018 = "";// 库存数量

	@Column(name = "aomsitem019")
	@JsonProperty("aomsitem019")
	private String aoms019 = "";// 单身sku修改时间

	@Column(name = "aomsitem020")
	@JsonProperty("aomsitem020")
	private String aoms020 = "";// 备用字段（OMS已使用）

	@Column(name = "aomsitem021")
	@JsonProperty("aomsitem021")
	private String aoms021 = "";// 一号店系列产品的ProductCode

	@Column(name = "aomsitem022")
	@JsonProperty("aomsitem022")
	private String aoms022 = "";// 一号店系列子产品的ProductCode

	@Column(name = "aomsitem023")
	@JsonProperty("aomsitem023")
	private String jdpModified = "";// 淘寶聚石塔鋪貨推送更新時間

	@Column(name = "aomsitem024")
	@JsonProperty("aomsitem024")
	private String aoms024 = "";// 商品价格	add by mowj 20150818

	@Column(name = "aomsitem025")
	@JsonProperty("aomsitem025")
	private String aoms025 = "";// 备用字段

	@Column(name = "aomsitem026")
	@JsonProperty("aomsitem026")
	private String aoms026 = "";// 备用字段

	@Column(name = "aomsitem027")
	@JsonProperty("aomsitem027")
	private String aoms027 = "";// 备用字段

	@Column(name = "aomsitemcrtdt", updatable = false)
//	@JsonProperty("aomsitemcrtdt")
	@JsonIgnore
	private String aomscrtdt = "";// 资料创建时间

	@Column(name = "aomsitemmoddt")
//	@JsonProperty("aomsitemmoddt")
	@JsonIgnore
	private String aomsmoddt = "";// 资料修改时间

	@Column(name = "aomsitemstatus")
//	@JsonProperty("aomsitemstatus")
	@JsonIgnore
	private String aomsstatus = "";// 资料状态

	public AomsitemT() {

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

	public String getAoms002() {
		return aoms002;
	}

	public void setAoms002(String aoms002) {
		this.aoms002 = aoms002;
	}

	public String getAoms003() {
		return aoms003;
	}

	public void setAoms003(String aoms003) {
		this.aoms003 = aoms003;
	}

	public String getAoms005() {
		return aoms005;
	}

	public void setAoms005(String aoms005) {
		this.aoms005 = aoms005;
	}

	public String getAoms006() {
		return aoms006;
	}

	public void setAoms006(String aoms006) {
		this.aoms006 = aoms006;
	}

	public String getAoms007() {
		return aoms007;
	}

	public void setAoms007(String aoms007) {
		this.aoms007 = aoms007;
	}

	public String getAoms008() {
		return aoms008;
	}

	public void setAoms008(String aoms008) {
		this.aoms008 = aoms008;
	}

	public String getAoms009() {
		return aoms009;
	}

	public void setAoms009(String aoms009) {
		this.aoms009 = aoms009;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getAoms013() {
		return aoms013;
	}

	public void setAoms013(String aoms013) {
		this.aoms013 = aoms013;
	}

	public String getAoms014() {
		return aoms014;
	}

	public void setAoms014(String aoms014) {
		this.aoms014 = aoms014;
	}

	public String getAoms015() {
		return aoms015;
	}

	public void setAoms015(String aoms015) {
		this.aoms015 = aoms015;
	}

	public String getAoms016() {
		return aoms016;
	}

	public void setAoms016(String aoms016) {
		this.aoms016 = aoms016;
	}

	public String getAoms017() {
		return aoms017;
	}

	public void setAoms017(String aoms017) {
		this.aoms017 = aoms017;
	}

	public String getAoms018() {
		return aoms018;
	}

	public void setAoms018(String aoms018) {
		this.aoms018 = aoms018;
	}

	public String getAoms019() {
		return aoms019;
	}

	public void setAoms019(String aoms019) {
		this.aoms019 = aoms019;
	}

	public String getAoms020() {
		return aoms020;
	}

	public void setAoms020(String aoms020) {
		this.aoms020 = aoms020;
	}

	public String getAoms021() {
		return aoms021;
	}

	public void setAoms021(String aoms021) {
		this.aoms021 = aoms021;
	}

	public String getAoms022() {
		return aoms022;
	}

	public void setAoms022(String aoms022) {
		this.aoms022 = aoms022;
	}

	public String getJdpModified() {
		return jdpModified;
	}

	public void setJdpModified(String jdpModified) {
		this.jdpModified = jdpModified;
	}

	public String getAoms024() {
		return aoms024;
	}

	public void setAoms024(String aoms024) {
		this.aoms024 = aoms024;
	}

	public String getAoms025() {
		return aoms025;
	}

	public void setAoms025(String aoms025) {
		this.aoms025 = aoms025;
	}

	public String getAoms026() {
		return aoms026;
	}

	public void setAoms026(String aoms026) {
		this.aoms026 = aoms026;
	}

	public String getAoms027() {
		return aoms027;
	}

	public void setAoms027(String aoms027) {
		this.aoms027 = aoms027;
	}

	public String getAomscrtdt() {
		return aomscrtdt;
	}

	public void setAomscrtdt(String aomscrtdt) {
		this.aomscrtdt = aomscrtdt;
	}

	public String getAomsmoddt() {
		return aomsmoddt;
	}

	public void setAomsmoddt(String aomsmoddt) {
		this.aomsmoddt = aomsmoddt;
	}

	public String getAomsstatus() {
		return aomsstatus;
	}

	public void setAomsstatus(String aomsstatus) {
		this.aomsstatus = aomsstatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aoms004 == null) ? 0 : aoms004.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((storeType == null) ? 0 : storeType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AomsitemT)) {
			return false;
		}
		AomsitemT other = (AomsitemT) obj;
		if (aoms004 == null) {
			if (other.aoms004 != null) {
				return false;
			}
		} else if (!aoms004.equals(other.aoms004)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (storeType == null) {
			if (other.storeType != null) {
				return false;
			}
		} else if (!storeType.equals(other.storeType)) {
			return false;
		}
		return true;
	}

	
	
}