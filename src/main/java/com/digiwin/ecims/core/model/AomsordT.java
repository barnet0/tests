package com.digiwin.ecims.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the aomsord_t database table.
 * 
 */
@Entity
@Table(name = "aomsord_t")
@IdClass(AomsordTPK.class)
@NamedQuery(name = "AomsordT.findAll", query = "SELECT a FROM AomsordT a")
/*
 * 新增@DynamicUpdate(true)注解标记，如果没有发生变化的字段，在更新时，就不会进行update， 如果不加，就更新所有字段
 */
@DynamicUpdate(true)
public class AomsordT implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String BIZ_NAME = "AomsordT";
	
	@Id
	@JsonProperty("aomsord001")
	@Column(name = "aomsord001")
	private String id;

	@Id
	@JsonProperty("aomsord057")
	@Column(name = "aomsord057")
	private String storeType;

	@Id
	@JsonProperty("aomsord060")
	@Column(name = "aomsord060")
	private String aoms060;

	@Column(name = "aomsord002")
	@JsonProperty("aomsord002")
	private String aoms002 = "";// 交易类型

	@Column(name = "aomsord003")
	@JsonProperty("aomsord003")
	private String aoms003 = "";// 交易状态

	@Column(name = "aomsord004")
	@JsonProperty("aomsord004")
	private String aoms004 = "";// 交易内部来源

	@Column(name = "aomsord005")
	@JsonProperty("aomsord005")
	private String aoms005 = "";// 交易商品价格

	@Column(name = "aomsord006")
	@JsonProperty("aomsord006")
	private String aoms006 = "";// 交易创建时间

	@Column(name = "aomsord007")
	@JsonProperty("aomsord007")
	private String modified = "";// 交易修改时间

	@Column(name = "aomsord008")
	@JsonProperty("aomsord008")
	private String aoms008 = "";// 交易结束时间

	@Column(name = "aomsord009")
	@JsonProperty("aomsord009")
	private String aoms009 = "";// 交易留言

	@Column(name = "aomsord010")
	@JsonProperty("aomsord010")
	private String aoms010 = "";// 买家留言

	@Column(name = "aomsord011")
	@JsonProperty("aomsord011")
	private String aoms011 = "";// 买家备注

	@Column(name = "aomsord012")
	@JsonProperty("aomsord012")
	private String aoms012 = "";// 卖家备注

	@Column(name = "aomsord013")
	@JsonProperty("aomsord013")
	private String aoms013 = "";// 买家标记

	@Column(name = "aomsord014")
	@JsonProperty("aomsord014")
	private String aoms014 = "";// 卖家标记

	@Column(name = "aomsord015")
	@JsonProperty("aomsord015")
	private String aoms015 = "";// 交易标题(以店铺名为值）

	@Column(name = "aomsord016")
	@JsonProperty("aomsord016")
	private String aoms016 = "";// 卖家昵称

	@Column(name = "aomsord017")
	@JsonProperty("aomsord017")
	private String aoms017 = "";// 商品数量

	@Column(name = "aomsord018")
	@JsonProperty("aomsord018")
	private String aoms018 = "";// 图片地址

	@Column(name = "aomsord019")
	@JsonProperty("aomsord019")
	private String aoms019 = "";// 促销单价

	@Column(name = "aomsord020")
	@JsonProperty("aomsord020")
	private String aoms020 = "";// 折扣金额

	@Column(name = "aomsord021")
	@JsonProperty("aomsord021")
	private String aoms021 = "";// 支付宝交易号

	@Column(name = "aomsord022")
	@JsonProperty("aomsord022")
	private String aoms022 = "";// 支付金额

	@Column(name = "aomsord023")
	@JsonProperty("aomsord023")
	private String aoms023 = ""; // 支付方式

	@Column(name = "aomsord024")
	@JsonProperty("aomsord024")
	private String aoms024 = "";// 支付时间

	@Column(name = "aomsord025")
	@JsonProperty("aomsord025")
	private String aoms025 = "";// 买家昵称

	@Column(name = "aomsord026")
	@JsonProperty("aomsord026")
	private String aoms026 = "";// 买家邮箱

	@Column(name = "aomsord027")
	@JsonProperty("aomsord027")
	private String aoms027 = "";// 买家是否已评价

	@Column(name = "aomsord028")
	@JsonProperty("aomsord028")
	private String aoms028 = "";// 买家支付宝帐号

	@Column(name = "aomsord029")
	@JsonProperty("aomsord029")
	private String aoms029 = "";// 买家获得积分

	@Column(name = "aomsord030")
	@JsonProperty("aomsord030")
	private String aoms030 = "";// 积分

	@Column(name = "aomsord031")
	@JsonProperty("aomsord031")
	private String aoms031 = "";// 实际积分

	@Column(name = "aomsord032")
	@JsonProperty("aomsord032")
	private String aoms032 = "";// 全部积分

	@Column(name = "aomsord033")
	@JsonProperty("aomsord033")
	private String aoms033 = "";// 发货类型

	@Column(name = "aomsord034")
	@JsonProperty("aomsord034")
	private String aoms034 = "";// 物流名称

	@Column(name = "aomsord035")
	@JsonProperty("aomsord035")
	private String aoms035 = "";// 物流费用

	@Column(name = "aomsord036")
	@JsonProperty("aomsord036")
	private String aoms036 = "";// 收货人姓名

	@Column(name = "aomsord037")
	@JsonProperty("aomsord037")
	private String aoms037 = "";// 收获人省份

	@Column(name = "aomsord038")
	@JsonProperty("aomsord038")
	private String aoms038 = ""; // 收货人市

	@Column(name = "aomsord039")
	@JsonProperty("aomsord039")
	private String aoms039 = "";// 收货人区

	@Column(name = "aomsord040")
	@JsonProperty("aomsord040")
	private String aoms040 = "";// 收货地址

	@Column(name = "aomsord041")
	@JsonProperty("aomsord041")
	private String aoms041 = "";// 收货地址邮编

	@Column(name = "aomsord042")
	@JsonProperty("aomsord042")
	private String aoms042 = "";// 收货人手机

	@Column(name = "aomsord043")
	@JsonProperty("aomsord043")
	private String aoms043 = "";// 收货人电话

	@Column(name = "aomsord044")
	@JsonProperty("aomsord044")
	private String aoms044 = "";// 卖家发货时间

	@Column(name = "aomsord045")
	@JsonProperty("aomsord045")
	private String aoms045 = ""; // 手续费

	@Column(name = "aomsord046")
	@JsonProperty("aomsord046")
	private String aoms046 = "";// 卖家支付宝帐号

	@Column(name = "aomsord047")
	@JsonProperty("aomsord047")
	private String aoms047 = "";// 卖家手机号

	@Column(name = "aomsord048")
	@JsonProperty("aomsord048")
	private String aoms048 = "";// 卖家电话

	@Column(name = "aomsord049")
	@JsonProperty("aomsord049")
	private String aoms049 = "";// 卖家姓名

	@Column(name = "aomsord050")
	@JsonProperty("aomsord050")
	private String aoms050 = "";// 卖家邮箱

	@Column(name = "aomsord051")
	@JsonProperty("aomsord051")
	private String aoms051 = ""; // 卖家是否已评价

	@Column(name = "aomsord052")
	@JsonProperty("aomsord052")
	private String aoms052 = ""; // 可确认费用

	@Column(name = "aomsord053")
	@JsonProperty("aomsord053")
	private String aoms053 = "";// 发票抬头

	@Column(name = "aomsord054")
	@JsonProperty("aomsord054")
	private String aoms054 = "";// 发票内容

	@Column(name = "aomsord055")
	@JsonProperty("aomsord055")
	private String aoms055 = ""; // 发票类型

	@Column(name = "aomsord056")
	@JsonProperty("aomsord056")
	private String storeId = "";// 店鋪ID

	@Column(name = "aomsord058")
	@JsonProperty("aomsord058")
	private String aoms058 = ""; // 订单id

	@Column(name = "aomsord059")
	@JsonProperty("aomsord059")
	private String aoms059 = "";// 规格id(SKU ID)

	@Column(name = "aomsord061")
	@JsonProperty("aomsord061")
	private String aoms061 = "";// 规格名称(SKU属性名称)

	@Column(name = "aomsord062")
	@JsonProperty("aomsord062")
	private String aoms062 = "";// 数量

	@Column(name = "aomsord063")
	@JsonProperty("aomsord063")
	private String aoms063 = "";// 商品标题

	@Column(name = "aomsord064")
	@JsonProperty("aomsord064")
	private String aoms064 = "";// 商品标价

	@Column(name = "aomsord065")
	@JsonProperty("aomsord065")
	private String aoms065 = "";// 商品图片路径

	@Column(name = "aomsord066")
	@JsonProperty("aomsord066")
	private String aoms066 = "";// 外部商品id

	@Column(name = "aomsord067")
	@JsonProperty("aomsord067")
	private String aoms067 = "";// 外部规格id(外部SKU id)

	@Column(name = "aomsord068")
	@JsonProperty("aomsord068")
	private String aoms068 = "";// 应付金额

	@Column(name = "aomsord069")
	@JsonProperty("aomsord069")
	private String aoms069 = "";// 子订单级订单优惠金额

	@Column(name = "aomsord070")
	@JsonProperty("aomsord070")
	private String aoms070 = "";// 手工调整金额

	@Column(name = "aomsord071")
	@JsonProperty("aomsord071")
	private String aoms071 = "";// 支付金额

	@Column(name = "aomsord072")
	@JsonProperty("aomsord072")
	private String aoms072 = "";// 订单状态

	@Column(name = "aomsord073")
	@JsonProperty("aomsord073")
	private String aoms073 = "";// 退款状态

	@Column(name = "aomsord074")
	@JsonProperty("aomsord074")
	private String aoms074 = "";// 退款id

	@Column(name = "aomsord075")
	@JsonProperty("aomsord075")
	private String aoms075 = "";// 发货类型

	@Column(name = "aomsord076")
	@JsonProperty("aomsord076")
	private String aoms076 = ""; // 备用字段-凤顺已经占用

	@Column(name = "aomsord077")
	@JsonProperty("aomsord077")
	private String aoms077 = "";// 备用字段-凤顺已经占用

	@Column(name = "aomsord078")
	@JsonProperty("aomsord078")
	private String aoms078 = "";// 备用字段-凤顺已经占用

	@Column(name = "aomsord079")
	@JsonProperty("aomsord079")
	private String aoms079 = "";// 采购单号

	@Column(name = "aomsord080")
	@JsonProperty("aomsord080")
	private String aoms080 = "";// 采购单运单号

	@Column(name = "aomsord081")
	@JsonProperty("aomsord081")
	private String aoms081 = "";// 子采购单退款金额

	@Column(name = "aomsord082")
	@JsonProperty("aomsord082")
	private String aoms082 = "";// 子采购单号

	@Column(name = "aomsord083")
	@JsonProperty("aomsord083")
	private String aoms083 = "";// 子代销采购单对应下游200订单状态

	@Column(name = "aomsord084")
	@JsonProperty("aomsord084")
	private String aoms084 = "";// 子采购单优惠活动类型

	@Column(name = "aomsord085")
	@JsonProperty("aomsord085")
	private String aoms085 = "";// 子采购单优惠活动的折扣金额

	@Column(name = "aomsord086")
	@JsonProperty("aomsord086")
	private String aoms086 = "";// 前台商品SKU ID

	@Column(name = "aomsord087")
	@JsonProperty("aomsord087")
	private String aoms087 = "";// 前台分销商品的宝贝ID

	@Column(name = "aomsord088")
	@JsonProperty("aomsord088")
	private String aoms088 = "";// 收件人街道

	@Column(name = "aomsord089")
	@JsonProperty("aomsord089")
	private String aoms089 = "";// 是否含运费险

	@Column(name = "aomsord090")
	@JsonProperty("aomsord090")
	private String aoms090 = "";// 分摊之后的实付金额

	@Column(name = "aomsord091")
	@JsonProperty("aomsord091")
	private String aoms091 = "";// 优惠分摊

	@Column(name = "aomsord092")
	@JsonProperty("aomsord092")
	private String aoms092 = "";// 一号店商品运费分摊金额

	@Column(name = "aomsordcrtdt", updatable = false)
	// @JsonProperty("aomsordcrtdt")
	@JsonIgnore
	private String aomscrtdt = "";// 资料创建时间

	@Column(name = "aomsordmoddt")
	// @JsonProperty("aomsordmoddt")
	@JsonIgnore
	private String aomsmoddt = "";// 资料修改时间

	@Column(name = "aomsordstatus")
	// @JsonProperty("aomsordstatus")
	@JsonIgnore
	private String aomsstatus = "0";// 资料状态

	@Column(name = "aomsord093")
	@JsonProperty("aomsord093")
	private String jdpModified = "";// 淘宝聚石塔订单推送记录的更新时间
	
	@Column(name = "aomsord094")
	@JsonProperty("aomsord094")
	private String aoms094 = "";// 一号店单身平台优惠分摊
	
	
	@Column(name = "aomsord095")
	@JsonProperty("aomsord095")
	private String aoms095 = "";// 淘宝菜鸟发货
	
	@Column(name = "aomsord096")
	@JsonProperty("aomsord096")
	private String aoms096 = "";// 淘宝菜鸟发货

	
	
	@Column(name = "aomsordundefined001")
	@JsonProperty("aomsordundefined001")
	private String aomsundefined001= ""; // 备用字段。作为京东余额支付金额:balanced_
	
	@Column(name = "aomsordundefined002")
	@JsonProperty("aomsordundefined002")
	private String aomsundefined002= ""; // 备用字段
	
	@Column(name = "aomsordundefined003")
	@JsonProperty("aomsordundefined003")
	private String aomsundefined003= ""; // 备用字段
	
	@Column(name = "aomsordundefined004")
	@JsonProperty("aomsordundefined004")
	private String aomsundefined004= ""; // 备用字段
	
	@Column(name = "aomsordundefined005")
	@JsonProperty("aomsordundefined005")
	private String aomsundefined005= ""; // 备用字段
	
	@Column(name = "aomsordundefined006")
	@JsonProperty("aomsordundefined006")
	private String aomsundefined006= ""; // 备用字段
	
	@Column(name = "aomsordundefined007")
	@JsonProperty("aomsordundefined007")
	private String aomsundefined007= ""; // 备用字段
	
	@Column(name = "aomsordundefined008")
	@JsonProperty("aomsordundefined008")
	private String aomsundefined008= ""; // 备用字段
	
	@Column(name = "aomsordundefined009")
	@JsonProperty("aomsordundefined009")
	private String aomsundefined009= ""; // 备用字段
	
	@Column(name = "aomsordundefined010")
	@JsonProperty("aomsordundefined010")
	private String aomsundefined010= ""; // 备用字段
	
	
	//yang add  20171010 start
	
	
	@Column(name ="aomsordundefined011")
	@JsonProperty("aomsordundefined011")
	private String aomsordundefined011= ""; //退货无忧-京东
	
	
	@Column(name = "aomsordundefined012")
	@JsonProperty("aomsordundefined012")
	private String aomsordundefined012= ""; //纳税人识别号
	
	
	@Column(name = "aomsordundefined013")
	@JsonProperty("aomsordundefined013")
	private String aomsordundefined013= ""; //开户行
	
	@Column(name = "aomsordundefined014")
	@JsonProperty("aomsordundefined014")
	private String aomsordundefined014= ""; //银行账户 
	
	

	public String getAomsordundefined011() {
		return aomsordundefined011;
	}

	public void setAomsordundefined011(String aomsordundefined011) {
		this.aomsordundefined011 = aomsordundefined011;
	}

	public String getAomsordundefined012() {
		return aomsordundefined012;
	}

	public void setAomsordundefined012(String aomsordundefined012) {
		this.aomsordundefined012 = aomsordundefined012;
	}

	public String getAomsordundefined013() {
		return aomsordundefined013;
	}

	public void setAomsordundefined013(String aomsordundefined013) {
		this.aomsordundefined013 = aomsordundefined013;
	}

	public String getAomsordundefined014() {
		return aomsordundefined014;
	}

	public void setAomsordundefined014(String aomsordundefined014) {
		this.aomsordundefined014 = aomsordundefined014;
	}

	
	// yang  add end  171010
	

	
	
	/**
	 * 淘宝聚石塔订单推送记录的更新时间
	 * 
	 * @return
	 */
	public String getJdpModified() {
		return jdpModified;
	}

	public void setJdpModified(String jdpModified) {
		this.jdpModified = jdpModified;
	}

	public AomsordT() {

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

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
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

	public String getAoms012() {
		return aoms012;
	}

	public void setAoms012(String aoms012) {
		this.aoms012 = aoms012;
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

	public String getAoms044() {
		return aoms044;
	}

	public void setAoms044(String aoms044) {
		this.aoms044 = aoms044;
	}

	public String getAoms045() {
		return aoms045;
	}

	public void setAoms045(String aoms045) {
		this.aoms045 = aoms045;
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

	public String getAoms051() {
		return aoms051;
	}

	public void setAoms051(String aoms051) {
		this.aoms051 = aoms051;
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

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
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

	public String getAoms061() {
		return aoms061;
	}

	public void setAoms061(String aoms061) {
		this.aoms061 = aoms061;
	}

	public String getAoms062() {
		return aoms062;
	}

	public void setAoms062(String aoms062) {
		this.aoms062 = aoms062;
	}

	public String getAoms063() {
		return aoms063;
	}

	public void setAoms063(String aoms063) {
		this.aoms063 = aoms063;
	}

	public String getAoms064() {
		return aoms064;
	}

	public void setAoms064(String aoms064) {
		this.aoms064 = aoms064;
	}

	public String getAoms065() {
		return aoms065;
	}

	public void setAoms065(String aoms065) {
		this.aoms065 = aoms065;
	}

	public String getAoms066() {
		return aoms066;
	}

	public void setAoms066(String aoms066) {
		this.aoms066 = aoms066;
	}

	public String getAoms067() {
		return aoms067;
	}

	public void setAoms067(String aoms067) {
		this.aoms067 = aoms067;
	}

	public String getAoms068() {
		return aoms068;
	}

	public void setAoms068(String aoms068) {
		this.aoms068 = aoms068;
	}

	public String getAoms069() {
		return aoms069;
	}

	public void setAoms069(String aoms069) {
		this.aoms069 = aoms069;
	}

	public String getAoms070() {
		return aoms070;
	}

	public void setAoms070(String aoms070) {
		this.aoms070 = aoms070;
	}

	public String getAoms071() {
		return aoms071;
	}

	public void setAoms071(String aoms071) {
		this.aoms071 = aoms071;
	}

	public String getAoms072() {
		return aoms072;
	}

	public void setAoms072(String aoms072) {
		this.aoms072 = aoms072;
	}

	public String getAoms073() {
		return aoms073;
	}

	public void setAoms073(String aoms073) {
		this.aoms073 = aoms073;
	}

	public String getAoms074() {
		return aoms074;
	}

	public void setAoms074(String aoms074) {
		this.aoms074 = aoms074;
	}

	public String getAoms075() {
		return aoms075;
	}

	public void setAoms075(String aoms075) {
		this.aoms075 = aoms075;
	}

	public String getAoms076() {
		return aoms076;
	}

	public void setAoms076(String aoms076) {
		this.aoms076 = aoms076;
	}

	public String getAoms077() {
		return aoms077;
	}

	public void setAoms077(String aoms077) {
		this.aoms077 = aoms077;
	}

	public String getAoms078() {
		return aoms078;
	}

	public void setAoms078(String aoms078) {
		this.aoms078 = aoms078;
	}

	public String getAoms079() {
		return aoms079;
	}

	public void setAoms079(String aoms079) {
		this.aoms079 = aoms079;
	}

	public String getAoms080() {
		return aoms080;
	}

	public void setAoms080(String aoms080) {
		this.aoms080 = aoms080;
	}

	public String getAoms081() {
		return aoms081;
	}

	public void setAoms081(String aoms081) {
		this.aoms081 = aoms081;
	}

	public String getAoms082() {
		return aoms082;
	}

	public void setAoms082(String aoms082) {
		this.aoms082 = aoms082;
	}

	public String getAoms083() {
		return aoms083;
	}

	public void setAoms083(String aoms083) {
		this.aoms083 = aoms083;
	}

	public String getAoms084() {
		return aoms084;
	}

	public void setAoms084(String aoms084) {
		this.aoms084 = aoms084;
	}

	public String getAoms085() {
		return aoms085;
	}

	public void setAoms085(String aoms085) {
		this.aoms085 = aoms085;
	}

	public String getAoms086() {
		return aoms086;
	}

	public void setAoms086(String aoms086) {
		this.aoms086 = aoms086;
	}

	public String getAoms087() {
		return aoms087;
	}

	public void setAoms087(String aoms087) {
		this.aoms087 = aoms087;
	}

	public String getAoms088() {
		return aoms088;
	}

	public void setAoms088(String aoms088) {
		this.aoms088 = aoms088;
	}

	public String getAoms089() {
		return aoms089;
	}

	public void setAoms089(String aoms089) {
		this.aoms089 = aoms089;
	}

	public String getAoms090() {
		return aoms090;
	}

	public void setAoms090(String aoms090) {
		this.aoms090 = aoms090;
	}

	public String getAoms091() {
		return aoms091;
	}

	public void setAoms091(String aoms091) {
		this.aoms091 = aoms091;
	}

	public String getAoms092() {
		return aoms092;
	}

	public void setAoms092(String aoms092) {
		this.aoms092 = aoms092;
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

	public String getAoms094() {
		return aoms094;
	}

	public void setAoms094(String aoms094) {
		this.aoms094 = aoms094;
	}

	public String getAoms095() {
		return aoms095;
	}

	public void setAoms095(String aoms095) {
		this.aoms095 = aoms095;
	}

	public String getAoms096() {
		return aoms096;
	}

	public void setAoms096(String aoms096) {
		this.aoms096 = aoms096;
	}

	/**
	 * @return the aomsundefined001
	 */
	public String getAomsundefined001() {
		return aomsundefined001;
	}

	/**
	 * @param aomsundefined001 the aomsundefined001 to set
	 */
	public void setAomsundefined001(String aomsundefined001) {
		this.aomsundefined001 = aomsundefined001;
	}

	/**
	 * @return the aomsundefined002
	 */
	public String getAomsundefined002() {
		return aomsundefined002;
	}

	/**
	 * @param aomsundefined002 the aomsundefined002 to set
	 */
	public void setAomsundefined002(String aomsundefined002) {
		this.aomsundefined002 = aomsundefined002;
	}

	/**
	 * @return the aomsundefined003
	 */
	public String getAomsundefined003() {
		return aomsundefined003;
	}

	/**
	 * @param aomsundefined003 the aomsundefined003 to set
	 */
	public void setAomsundefined003(String aomsundefined003) {
		this.aomsundefined003 = aomsundefined003;
	}

	/**
	 * @return the aomsundefined004
	 */
	public String getAomsundefined004() {
		return aomsundefined004;
	}

	/**
	 * @param aomsundefined004 the aomsundefined004 to set
	 */
	public void setAomsundefined004(String aomsundefined004) {
		this.aomsundefined004 = aomsundefined004;
	}

	/**
	 * @return the aomsundefined005
	 */
	public String getAomsundefined005() {
		return aomsundefined005;
	}

	/**
	 * @param aomsundefined005 the aomsundefined005 to set
	 */
	public void setAomsundefined005(String aomsundefined005) {
		this.aomsundefined005 = aomsundefined005;
	}

	/**
	 * @return the aomsundefined006
	 */
	public String getAomsundefined006() {
		return aomsundefined006;
	}

	/**
	 * @param aomsundefined006 the aomsundefined006 to set
	 */
	public void setAomsundefined006(String aomsundefined006) {
		this.aomsundefined006 = aomsundefined006;
	}

	/**
	 * @return the aomsundefined007
	 */
	public String getAomsundefined007() {
		return aomsundefined007;
	}

	/**
	 * @param aomsundefined007 the aomsundefined007 to set
	 */
	public void setAomsundefined007(String aomsundefined007) {
		this.aomsundefined007 = aomsundefined007;
	}

	/**
	 * @return the aomsundefined008
	 */
	public String getAomsundefined008() {
		return aomsundefined008;
	}

	/**
	 * @param aomsundefined008 the aomsundefined008 to set
	 */
	public void setAomsundefined008(String aomsundefined008) {
		this.aomsundefined008 = aomsundefined008;
	}

	/**
	 * @return the aomsundefined009
	 */
	public String getAomsundefined009() {
		return aomsundefined009;
	}

	/**
	 * @param aomsundefined009 the aomsundefined009 to set
	 */
	public void setAomsundefined009(String aomsundefined009) {
		this.aomsundefined009 = aomsundefined009;
	}

	/**
	 * @return the aomsundefined010
	 */
	public String getAomsundefined010() {
		return aomsundefined010;
	}

	/**
	 * @param aomsundefined010 the aomsundefined010 to set
	 */
	public void setAomsundefined010(String aomsundefined010) {
		this.aomsundefined010 = aomsundefined010;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aoms060 == null) ? 0 : aoms060.hashCode());
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
		if (!(obj instanceof AomsordT)) {
			return false;
		}
		AomsordT other = (AomsordT) obj;
		if (aoms060 == null) {
			if (other.aoms060 != null) {
				return false;
			}
		} else if (!aoms060.equals(other.aoms060)) {
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