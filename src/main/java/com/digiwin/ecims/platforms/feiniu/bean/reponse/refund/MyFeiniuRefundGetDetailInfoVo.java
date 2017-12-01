package com.digiwin.ecims.platforms.feiniu.bean.reponse.refund;

import com.feiniu.open.api.sdk.bean.refund.OrderReturnSubLogListVo;
import com.google.fngson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by zaregoto on 2017/2/7.
 */
public class MyFeiniuRefundGetDetailInfoVo {

    @SerializedName("ogSeq")
    private String ogSeq;
    @SerializedName("ogNo")
    private String ogNo;
    @SerializedName("ogsSeq")
    private String ogsSeq;
    @SerializedName("olsSeq")
    private String olsSeq;
    @SerializedName("qty")
    private Integer qty;
    @SerializedName("rsSeq")
    private String rsSeq;
    @SerializedName("rssSeq")
    private String rssSeq;
    @SerializedName("status")
    private Integer status;
    @SerializedName("refundStatus")
    private Integer refundStatus;
    @SerializedName("refundRejectReason")
    private String refundRejectReason;
    @SerializedName("refundRejectDesc")
    private String refundRejectDesc;
    @SerializedName("detailType")
    private Integer detailType;
    @SerializedName("pieceNum")
    private Integer pieceNum;
    @SerializedName("price")
    private Float price;
    @SerializedName("memGuid")
    private String memGuid;
    @SerializedName("memberName")
    private String memberName;
    @SerializedName("memberCellphone")
    private String memberCellphone;
    @SerializedName("memberTel")
    private String memberTel;
    @SerializedName("memberProvince")
    private String memberProvince;
    @SerializedName("memberCity")
    private String memberCity;
    @SerializedName("memberPostArea")
    private String memberPostArea;
    @SerializedName("memberAdd")
    private String memberAdd;
    @SerializedName("returnFreightStatus")
    private Integer returnFreightStatus;
    @SerializedName("freight")
    private Float freight;
    @SerializedName("skuId")
    private String skuId;
    @SerializedName("productName")
    private String productName;
    @SerializedName("productPrice")
    private Float productPrice;
    @SerializedName("aprnPromote")
    private Float aprnPromote;
    @SerializedName("aprnVoucher")
    private Float aprnVoucher;
    @SerializedName("productQty")
    private Integer productQty;
    @SerializedName("combineName")
    private String combineName;
    @SerializedName("returnQty")
    private Integer returnQty;
    @SerializedName("realReturnQty")
    private Integer realReturnQty;
    @SerializedName("returnMoney")
    private Float returnMoney;
    @SerializedName("realRefundState")
    private Integer realRefundState;
    @SerializedName("merchantName")
    private String merchantName;
    @SerializedName("merchantId")
    private String merchantId;
    @SerializedName("orsInsDt")
    private Date orsInsDt;
    @SerializedName("agentName")
    private String agentName;
    @SerializedName("agentMobile")
    private String agentMobile;
    @SerializedName("defaultReturnAddress")
    private String defaultReturnAddress;
    @SerializedName("defaultReturnPostcode")
    private String defaultReturnPostcode;
    @SerializedName("orderReturnSubLogList")
    private List<OrderReturnSubLogListVo> orderReturnSubLogList;
    @SerializedName("goodsReturnDt")
    private Date goodsReturnDt;
    @SerializedName("origPrice")
    private Float origPrice;
    @SerializedName("refundablePrice")
    private Float refundablePrice;
    @SerializedName("updateDt")
    private Date updateDt;
    @SerializedName("goodsId")
    private String goodsId;
    @SerializedName("merchantType")
    private String merchantType;
    @SerializedName("oversea")
    private Integer oversea;
    @SerializedName("expressName")
    private String expressName;
    @SerializedName("expressNo")
    private String expressNo;
    @SerializedName("problemDesc")
    private String problemDesc;
    @SerializedName("reason")
    private String reason;

    public MyFeiniuRefundGetDetailInfoVo() {
    }

    public String getOgSeq() {
        return ogSeq;
    }

    public void setOgSeq(String ogSeq) {
        this.ogSeq = ogSeq;
    }

    public String getOgNo() {
        return ogNo;
    }

    public void setOgNo(String ogNo) {
        this.ogNo = ogNo;
    }

    public String getOgsSeq() {
        return ogsSeq;
    }

    public void setOgsSeq(String ogsSeq) {
        this.ogsSeq = ogsSeq;
    }

    public String getOlsSeq() {
        return olsSeq;
    }

    public void setOlsSeq(String olsSeq) {
        this.olsSeq = olsSeq;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getRsSeq() {
        return rsSeq;
    }

    public void setRsSeq(String rsSeq) {
        this.rsSeq = rsSeq;
    }

    public String getRssSeq() {
        return rssSeq;
    }

    public void setRssSeq(String rssSeq) {
        this.rssSeq = rssSeq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundRejectReason() {
        return refundRejectReason;
    }

    public void setRefundRejectReason(String refundRejectReason) {
        this.refundRejectReason = refundRejectReason;
    }

    public String getRefundRejectDesc() {
        return refundRejectDesc;
    }

    public void setRefundRejectDesc(String refundRejectDesc) {
        this.refundRejectDesc = refundRejectDesc;
    }

    public Integer getDetailType() {
        return detailType;
    }

    public void setDetailType(Integer detailType) {
        this.detailType = detailType;
    }

    public Integer getPieceNum() {
        return pieceNum;
    }

    public void setPieceNum(Integer pieceNum) {
        this.pieceNum = pieceNum;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getMemGuid() {
        return memGuid;
    }

    public void setMemGuid(String memGuid) {
        this.memGuid = memGuid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberCellphone() {
        return memberCellphone;
    }

    public void setMemberCellphone(String memberCellphone) {
        this.memberCellphone = memberCellphone;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemberProvince() {
        return memberProvince;
    }

    public void setMemberProvince(String memberProvince) {
        this.memberProvince = memberProvince;
    }

    public String getMemberCity() {
        return memberCity;
    }

    public void setMemberCity(String memberCity) {
        this.memberCity = memberCity;
    }

    public String getMemberPostArea() {
        return memberPostArea;
    }

    public void setMemberPostArea(String memberPostArea) {
        this.memberPostArea = memberPostArea;
    }

    public String getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(String memberAdd) {
        this.memberAdd = memberAdd;
    }

    public Integer getReturnFreightStatus() {
        return returnFreightStatus;
    }

    public void setReturnFreightStatus(Integer returnFreightStatus) {
        this.returnFreightStatus = returnFreightStatus;
    }

    public Float getFreight() {
        return freight;
    }

    public void setFreight(Float freight) {
        this.freight = freight;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Float getAprnPromote() {
        return aprnPromote;
    }

    public void setAprnPromote(Float aprnPromote) {
        this.aprnPromote = aprnPromote;
    }

    public Float getAprnVoucher() {
        return aprnVoucher;
    }

    public void setAprnVoucher(Float aprnVoucher) {
        this.aprnVoucher = aprnVoucher;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public String getCombineName() {
        return combineName;
    }

    public void setCombineName(String combineName) {
        this.combineName = combineName;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public Integer getRealReturnQty() {
        return realReturnQty;
    }

    public void setRealReturnQty(Integer realReturnQty) {
        this.realReturnQty = realReturnQty;
    }

    public Float getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Float returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getRealRefundState() {
        return realRefundState;
    }

    public void setRealRefundState(Integer realRefundState) {
        this.realRefundState = realRefundState;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Date getOrsInsDt() {
        return orsInsDt;
    }

    public void setOrsInsDt(Date orsInsDt) {
        this.orsInsDt = orsInsDt;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile;
    }

    public String getDefaultReturnAddress() {
        return defaultReturnAddress;
    }

    public void setDefaultReturnAddress(String defaultReturnAddress) {
        this.defaultReturnAddress = defaultReturnAddress;
    }

    public String getDefaultReturnPostcode() {
        return defaultReturnPostcode;
    }

    public void setDefaultReturnPostcode(String defaultReturnPostcode) {
        this.defaultReturnPostcode = defaultReturnPostcode;
    }

    public List<OrderReturnSubLogListVo> getOrderReturnSubLogList() {
        return orderReturnSubLogList;
    }

    public void setOrderReturnSubLogList(List<OrderReturnSubLogListVo> orderReturnSubLogList) {
        this.orderReturnSubLogList = orderReturnSubLogList;
    }

    public Date getGoodsReturnDt() {
        return goodsReturnDt;
    }

    public void setGoodsReturnDt(Date goodsReturnDt) {
        this.goodsReturnDt = goodsReturnDt;
    }

    public Float getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(Float origPrice) {
        this.origPrice = origPrice;
    }

    public Float getRefundablePrice() {
        return refundablePrice;
    }

    public void setRefundablePrice(Float refundablePrice) {
        this.refundablePrice = refundablePrice;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public Integer getOversea() {
        return oversea;
    }

    public void setOversea(Integer oversea) {
        this.oversea = oversea;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
