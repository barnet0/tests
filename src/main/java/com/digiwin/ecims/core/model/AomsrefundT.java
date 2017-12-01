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
 * The persistent class for the aomsrefund_t database table.
 * 
 */
@Entity
@Table(name = "aomsrefund_t")
@IdClass(AomsrefundTPK.class)
@NamedQuery(name = "AomsrefundT.findAll", query = "SELECT a FROM AomsrefundT a")
public class AomsrefundT implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String BIZ_NAME = "AomsrefundT";
	
	@Id
	@JsonProperty("aomsrefund001")
	@Column(name = "aomsrefund001")
	/**
	 * 淘宝：退款单号
	 * 京东：退款/服务单号
	 * 一号店：退款单号
	 * 当当：没有退款单号，无法确认唯一，赋值规则为：订单号+#+年月日时分秒，如：32432432432#20150728125324
	 * 工商：申请单号
	 * 苏宁：没有退款单号，无法确认唯一，赋值规则为：订单号+#+年月日时分秒，如：32432432432#20150728125324
	 * 
	 */
	private String id;// 退款\货\服务单号

	@Id
	@JsonProperty("aomsrefund044")
	@Column(name = "aomsrefund044")
	private String storeType;// 资料来源

	@Id
	@Column(name = "aomsrefund023")
	@JsonProperty("aomsrefund023")
	/**
	 * 联合主键，申请退款的商品数字编号,如果为空，赋值为订单号    modified by Ken 2015/07/15    
	 */
	private String aoms023;

	@Column(name = "aomsrefund002")
	@JsonProperty("aomsrefund002")
	private String aoms002 = "";// 单据类型

	@Column(name = "aomsrefund003")
	@JsonProperty("aomsrefund003")
	private String aoms003 = "";// 分销商nick

	@Column(name = "aomsrefund004")
	@JsonProperty("aomsrefund004")
	private String aoms004 = "";// 支付给供应商的金额

	@Column(name = "aomsrefund005")
	@JsonProperty("aomsrefund005")
	private String aoms005 = "";// 主采购单id

	@Column(name = "aomsrefund006")
	@JsonProperty("aomsrefund006")
	private String aoms006 = "";// 退款的金额

	@Column(name = "aomsrefund007")
	@JsonProperty("aomsrefund007")
	private String aoms007 = "";// 退款流程类型： 4：发货前退款； 1：发货后退款不退货； 2：发货后退款退货

	@Column(name = "aomsrefund008")
	@JsonProperty("aomsrefund008")
	private String aoms008 = "";// 退款状态 1：买家已经申请退款，等待卖家同意 2：卖家已经同意退款，等待买家退货
							// 3：买家已经退货，等待卖家确认收货 4：退款关闭 5：退款成功 6：卖家拒绝退款
							// 12：同意退款，待打款 9：没有申请退款 10：卖家拒绝确认收货

	@Column(name = "aomsrefund009")
	@JsonProperty("aomsrefund009")
	private String aoms009 = "";// 是否退货

	@Column(name = "aomsrefund010")
	@JsonProperty("aomsrefund010")
	private String aoms010 = "";// 子单id

	@Column(name = "aomsrefund011")
	@JsonProperty("aomsrefund011")
	private String aoms011 = "";// 供应商nick

	@Column(name = "aomsrefund012")
	@JsonProperty("aomsrefund012")
	private String modified = "";// 退款修改时间// 統一表達形式，排程程式寫作

	@Column(name = "aomsrefund013")
	@JsonProperty("aomsrefund013")
	private String aoms013 = "";// 卖家收货地址

	@Column(name = "aomsrefund014")
	@JsonProperty("aomsrefund014")
	private String aoms014 = "";// 退款先行垫付默认的未申请状态 0;退款先行垫付申请中 1;退款先行垫付，垫付完成
							// 2;退款先行垫付，卖家拒绝收货 3;退款先行垫付，垫付关闭
							// 4;退款先行垫付，垫付分账成功 5;

	@Column(name = "aomsrefund015")
	@JsonProperty("aomsrefund015")
	private String aoms015 = "";// 支付宝交易号

	@Column(name = "aomsrefund016")
	@JsonProperty("aomsrefund016")
	private String aoms016 = "";// 退款扩展属性          

	@Column(name = "aomsrefund017")
	@JsonProperty("aomsrefund017")
	private String aoms017 = "";// 买家昵称

	@Column(name = "aomsrefund018")
	@JsonProperty("aomsrefund018")
	private String aoms018 = "";// 物流公司名称         

	@Column(name = "aomsrefund019")
	@JsonProperty("aomsrefund019")
	private String aoms019 = "";// 不需客服介入1;需要客服介入2;客服已经介入3;客服初审完成
							// 4;客服主管复审失败5;客服处理完成6;

	@Column(name = "aomsrefund020")
	@JsonProperty("aomsrefund020")
	private String aoms020 = "";// 退货时间           

	@Column(name = "aomsrefund021")
	@JsonProperty("aomsrefund021")
	private String aoms021 = "";// 货物状态。可选值BUYER_NOT_RECEIVED (买家未收到货)
							// BUYER_RECEIVED (买家已收到货)
							// BUYER_RETURNED_GOODS (买家已退货) 

	@Column(name = "aomsrefund022")
	@JsonProperty("aomsrefund022")
	private String aoms022 = "";// 商品购买数量       

	@Column(name = "aomsrefund024")
	@JsonProperty("aomsrefund024")
	private String aoms024 = "";// 子订单号。如果是单笔交易oid会等于tid           

	@Column(name = "aomsrefund025")
	@JsonProperty("aomsrefund025")
	private String aoms025 = "";// 退款约束，可选值：cannot_refuse（不允许操作），refund_onweb（需要到网页版操作）

	@Column(name = "aomsrefund026")
	@JsonProperty("aomsrefund026")
	private String aoms026 = "";// 退款对应的订单交易状态          

	@Column(name = "aomsrefund027")
	@JsonProperty("aomsrefund027")
	private String aoms027 = "";// 商品外部商家编码        "outer_id": "AY519204",

	@Column(name = "aomsrefund028")
	@JsonProperty("aomsrefund028")
	private String aoms028 = "";// 支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分         

	@Column(name = "aomsrefund029")
	@JsonProperty("aomsrefund029")
	private String aoms029 = "";// 商品价格           

	@Column(name = "aomsrefund030")
	@JsonProperty("aomsrefund030")
	private String aoms030 = "";// 退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分           

	@Column(name = "aomsrefund031")
	@JsonProperty("aomsrefund031")
	private String aoms031 = "";// 退款阶段，可选值：onsale/aftersale          

	@Column(name = "aomsrefund032")
	@JsonProperty("aomsrefund032")
	private String aoms032 = "";// 退款版本号（时间戳）            

	@Column(name = "aomsrefund033")
	@JsonProperty("aomsrefund033")
	private String aoms033 = "";// 卖家昵称         

	@Column(name = "aomsrefund034")
	@JsonProperty("aomsrefund034")
	private String aoms034 = "";// 物流方式.可选值:free(卖家包邮),post(平邮),express(快递),ems(EMS).          

	@Column(name = "aomsrefund035")
	@JsonProperty("aomsrefund035")
	private String aoms035 = "";// 退货运单号           

	@Column(name = "aomsrefund036")
	@JsonProperty("aomsrefund036")
	private String aoms036 = "";// 商品SKU信息          

	@Column(name = "aomsrefund037")
	@JsonProperty("aomsrefund037")
	private String aoms037 = "";// 退款状态          

	@Column(name = "aomsrefund038")
	@JsonProperty("aomsrefund038")
	private String aoms038 = "";// 淘宝交易单号           

	@Column(name = "aomsrefund039")
	@JsonProperty("aomsrefund039")
	private String aoms039 = "";// 商品标题          

	@Column(name = "aomsrefund040")
	@JsonProperty("aomsrefund040")
	private String aoms040 = "";// 交易总金额           

	@Column(name = "aomsrefund041")
	@JsonProperty("aomsrefund041")
	private String aoms041 = "";// 退款创建时间

	@Column(name = "aomsrefund042")
	@JsonProperty("aomsrefund042")
	private String aoms042 = "";// 退款说明

	@Column(name = "aomsrefund043")
	@JsonProperty("aomsrefund043")
	private String aoms043 = "";// 退款原因

	@Column(name = "aomsrefund045")
	@JsonProperty("aomsrefund045")
	private String storeId = "";// 店鋪ID

	@Column(name = "aomsrefund046")
	@JsonProperty("aomsrefund046")
	private String aoms046 = "";// 备用字段

	@Column(name = "aomsrefund047")
	@JsonProperty("aomsrefund047")
	private String aoms047 = "";// 备用字段

	@Column(name = "aomsrefund048")
	@JsonProperty("aomsrefund048")
	private String aoms048 = "";// 备用字段

	@Column(name = "aomsrefund049")
	@JsonProperty("aomsrefund049")
	private String aoms049 = "";// 备用字段

	@Column(name = "aomsrefund050")
	@JsonProperty("aomsrefund050")
	private String aoms050 = "";// 备用字段

	@Column(name = "aomsrefund051")
	@JsonProperty("aomsrefund051")
	private String jdpModified = "";// 备用字段

	@Column(name = "aomsrefund052")
	@JsonProperty("aomsrefund052")
	private String aoms052 = "";// 备用字段
	
	@Column(name = "aomsrefund053")
	@JsonProperty("aomsrefund053")
	private String aoms053 = "";// 备用字段
	
	@Column(name = "aomsrefund054")
	@JsonProperty("aomsrefund054")
	private String aoms054 = "";// 备用字段
	
	@Column(name = "aomsrefund055")
	@JsonProperty("aomsrefund055")
	private String aoms055 = "";// 备用字段
	
	@Column(name = "aomsrefund056")
	@JsonProperty("aomsrefund056")
	private String aoms056 = "";// 备用字段
	
	@Column(name = "aomsrefund057")
	@JsonProperty("aomsrefund057")
	private String aoms057 = "";// 备用字段
	
	@Column(name = "aomsrefund058")
	@JsonProperty("aomsrefund058")
	private String aoms058 = "";// 备用字段
	
	@Column(name = "aomsrefund059")
	@JsonProperty("aomsrefund059")
	private String aoms059 = "";// 备用字段
	
	@Column(name = "aomsrefund060")
	@JsonProperty("aomsrefund060")
	private String aoms060 = "";// 备用字段
	
	@Column(name = "aomsrefundcrtdt", updatable = false)
//	@JsonProperty("aomsrefundcrtdt")
	@JsonIgnore
	private String aomscrtdt = "";// 资料创建时间

	@Column(name = "aomsrefundmoddt")
//	@JsonProperty("aomsrefundmoddt")
	@JsonIgnore
	private String aomsmoddt = "";// 资料修改时间

	@Column(name = "aomsrefundstatus")
//	@JsonProperty("aomsrefundstatus")
	@JsonIgnore
	private String aomsstatus = "";// 资料状态

	public AomsrefundT() {

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

	public String getAoms004() {
		return aoms004;
	}

	public void setAoms004(String aoms004) {
		this.aoms004 = aoms004;
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

	public String getAoms010() {
		return aoms010;
	}

	public void setAoms010(String aoms010) {
		this.aoms010 = aoms010;
	}

	public String getAoms011() {
		return aoms011;
	}

	public void setAoms011(String aoms011) {
		this.aoms011 = aoms011;
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

	public String getAoms023() {
		return aoms023;
	}

	public void setAoms023(String aoms023) {
		this.aoms023 = aoms023;
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

	public String getAoms028() {
		return aoms028;
	}

	public void setAoms028(String aoms028) {
		this.aoms028 = aoms028;
	}

	public String getAoms029() {
		return aoms029;
	}

	public void setAoms029(String aoms029) {
		this.aoms029 = aoms029;
	}

	public String getAoms030() {
		return aoms030;
	}

	public void setAoms030(String aoms030) {
		this.aoms030 = aoms030;
	}

	public String getAoms031() {
		return aoms031;
	}

	public void setAoms031(String aoms031) {
		this.aoms031 = aoms031;
	}

	public String getAoms032() {
		return aoms032;
	}

	public void setAoms032(String aoms032) {
		this.aoms032 = aoms032;
	}

	public String getAoms033() {
		return aoms033;
	}

	public void setAoms033(String aoms033) {
		this.aoms033 = aoms033;
	}

	public String getAoms034() {
		return aoms034;
	}

	public void setAoms034(String aoms034) {
		this.aoms034 = aoms034;
	}

	public String getAoms035() {
		return aoms035;
	}

	public void setAoms035(String aoms035) {
		this.aoms035 = aoms035;
	}

	public String getAoms036() {
		return aoms036;
	}

	public void setAoms036(String aoms036) {
		this.aoms036 = aoms036;
	}

	public String getAoms037() {
		return aoms037;
	}

	public void setAoms037(String aoms037) {
		this.aoms037 = aoms037;
	}

	public String getAoms038() {
		return aoms038;
	}

	public void setAoms038(String aoms038) {
		this.aoms038 = aoms038;
	}

	public String getAoms039() {
		return aoms039;
	}

	public void setAoms039(String aoms039) {
		this.aoms039 = aoms039;
	}

	public String getAoms040() {
		return aoms040;
	}

	public void setAoms040(String aoms040) {
		this.aoms040 = aoms040;
	}

	public String getAoms041() {
		return aoms041;
	}

	public void setAoms041(String aoms041) {
		this.aoms041 = aoms041;
	}

	public String getAoms042() {
		return aoms042;
	}

	public void setAoms042(String aoms042) {
		this.aoms042 = aoms042;
	}

	public String getAoms043() {
		return aoms043;
	}

	public void setAoms043(String aoms043) {
		this.aoms043 = aoms043;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getAoms046() {
		return aoms046;
	}

	public void setAoms046(String aoms046) {
		this.aoms046 = aoms046;
	}

	public String getAoms047() {
		return aoms047;
	}

	public void setAoms047(String aoms047) {
		this.aoms047 = aoms047;
	}

	public String getAoms048() {
		return aoms048;
	}

	public void setAoms048(String aoms048) {
		this.aoms048 = aoms048;
	}

	public String getAoms049() {
		return aoms049;
	}

	public void setAoms049(String aoms049) {
		this.aoms049 = aoms049;
	}

	public String getAoms050() {
		return aoms050;
	}

	public void setAoms050(String aoms050) {
		this.aoms050 = aoms050;
	}

	public String getJdpModified() {
		return jdpModified;
	}

	public void setJdpModified(String jdpModified) {
		this.jdpModified = jdpModified;
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

	public String getAoms052() {
		return aoms052;
	}

	public void setAoms052(String aoms052) {
		this.aoms052 = aoms052;
	}

	public String getAoms053() {
		return aoms053;
	}

	public void setAoms053(String aoms053) {
		this.aoms053 = aoms053;
	}

	public String getAoms054() {
		return aoms054;
	}

	public void setAoms054(String aoms054) {
		this.aoms054 = aoms054;
	}

	public String getAoms055() {
		return aoms055;
	}

	public void setAoms055(String aoms055) {
		this.aoms055 = aoms055;
	}

	public String getAoms056() {
		return aoms056;
	}

	public void setAoms056(String aoms056) {
		this.aoms056 = aoms056;
	}

	public String getAoms057() {
		return aoms057;
	}

	public void setAoms057(String aoms057) {
		this.aoms057 = aoms057;
	}

	public String getAoms058() {
		return aoms058;
	}

	public void setAoms058(String aoms058) {
		this.aoms058 = aoms058;
	}

	public String getAoms059() {
		return aoms059;
	}

	public void setAoms059(String aoms059) {
		this.aoms059 = aoms059;
	}

	public String getAoms060() {
		return aoms060;
	}

	public void setAoms060(String aoms060) {
		this.aoms060 = aoms060;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aoms023 == null) ? 0 : aoms023.hashCode());
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
		if (!(obj instanceof AomsrefundT)) {
			return false;
		}
		AomsrefundT other = (AomsrefundT) obj;
		if (aoms023 == null) {
			if (other.aoms023 != null) {
				return false;
			}
		} else if (!aoms023.equals(other.aoms023)) {
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