package com.digiwin.ecims.platforms.feiniu.util.translator;
import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.refund.MyFeiniuRefundGetDetailInfoResponse;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.refund.MyFeiniuRefundGetDetailInfoVo;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.returngoods.MyFeiniuOrderReverseDetailGetResponse;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.returngoods.MyFeiniuOrderReverseDetailGetVo;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.feiniu.open.api.sdk.bean.refund.RefundsItemVo;
import com.feiniu.open.api.sdk.response.returngoods.OrderReverseDetailGetResponse;
import com.feiniu.open.api.sdk.response.returngoods.OrderReverseGetResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * Created by zaregoto on 2017/1/25.
 */
public class AomsrefundTTranslator {
    private Object listObject;
    private Object detailObject;
    public AomsrefundTTranslator(Object listObject, Object detailObject) {
        this.listObject = listObject;
        this.detailObject = detailObject;
    }
    public List<AomsrefundT> doTranslate(String storeId) {
        if (detailObject instanceof MyFeiniuRefundGetDetailInfoResponse) {
            return parseFeiniuRefundToAomsrefundT(storeId);
        } else if (listObject == null && detailObject instanceof MyFeiniuOrderReverseDetailGetResponse) {
            return parseFeiniuReturnToAomsorefundT(storeId);
        } else {
            return Collections.emptyList();
        }
    }
    private List<AomsrefundT> parseFeiniuRefundToAomsrefundT(String storeId) {
        List<AomsrefundT> aomsrefundTs = new ArrayList<>();
        RefundsItemVo listData = (RefundsItemVo) this.listObject;
        MyFeiniuRefundGetDetailInfoResponse detailResponse =
            (MyFeiniuRefundGetDetailInfoResponse) this.detailObject;
        MyFeiniuRefundGetDetailInfoVo detailData = detailResponse.getData();
        AomsrefundT aomsrefundT = new AomsrefundT();
        aomsrefundT.setId(CommonUtil.checkNullOrNot(detailData.getRssSeq()));
        aomsrefundT.setAoms002(CommonUtil.checkNullOrNot(EcImsApiService.REFUND_TYPE_NORMAL));
        aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(
            detailData.getReturnQty() > 0 ? Boolean.TRUE : Boolean.FALSE));
        aomsrefundT.setModified(CommonUtil.checkNullOrNot(detailData.getUpdateDt()));
        aomsrefundT.setAoms013(CommonUtil.checkNullOrNot(detailData.getDefaultReturnAddress()));
        aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(listData.getBuyerName()));
        aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(detailData.getExpressName()));
        aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(detailData.getGoodsReturnDt()));
        aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(detailData.getReturnQty()));
        //aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(detailData.getGoodsId()));   modbycs 20170308
        aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(listData.getGoodsId()));
        aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(detailData.getOgsSeq()));
        aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(detailData.getProductPrice()));
        aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(detailData.getPrice()));
        aomsrefundT.setAoms033(CommonUtil.checkNullOrNot(detailData.getMerchantName()));
        aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(detailData.getExpressNo()));
        aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(detailData.getSkuId()));
        aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(detailData.getStatus()));
        aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(detailData.getOgNo()));
        aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(detailData.getProductName()));
        aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(listData.getSubmitDate()));
        aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(detailData.getProblemDesc()));
        aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(detailData.getReason()));
        aomsrefundT.setStoreId(storeId);
        aomsrefundT.setStoreType(FeiniuCommonTool.STORE_TYPE);
        Date now = new Date();
        aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
        aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
        aomsrefundTs.add(aomsrefundT);
        return aomsrefundTs;
    }
    private List<AomsrefundT> parseFeiniuReturnToAomsorefundT(String storeId) {
        List<AomsrefundT> aomsrefundTs = new ArrayList<>();
        
        MyFeiniuOrderReverseDetailGetResponse detailGetResponse =
            (MyFeiniuOrderReverseDetailGetResponse) this.detailObject;
        MyFeiniuOrderReverseDetailGetVo detailData = detailGetResponse.getData();
        AomsrefundT aomsrefundT = new AomsrefundT();
        aomsrefundT.setId(CommonUtil.checkNullOrNot(detailData.getRssSeq()));
        aomsrefundT.setAoms002(CommonUtil.checkNullOrNot(EcImsApiService.REFUND_TYPE_NORMAL));
        aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));
        aomsrefundT.setModified(CommonUtil.checkNullOrNot(detailData.getUpdateDt()));
        aomsrefundT.setAoms013(CommonUtil.checkNullOrNot(detailData.getDefaultReturnAddress()));
        aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(detailData.getExpressName()));
        aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(detailData.getGoodsReturnDt()));
        aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(detailData.getReturnQty()));
        aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(detailData.getGoodsId()));
        aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(detailData.getOgsSeq()));
        aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(detailData.getProductPrice()));
        aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(detailData.getPrice()));
        aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(detailData.getExpressNo()));
        aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(detailData.getSkuId()));
        aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(detailData.getStatus()));
        aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(detailData.getOgNo()));
        aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(detailData.getProductName()));
        aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(detailData.getOrsInsDt()));
        aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(detailData.getProblemDesc()));
        aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(detailData.getReason()));
        aomsrefundT.setStoreId(storeId);
        aomsrefundT.setStoreType(FeiniuCommonTool.STORE_TYPE);
        Date now = new Date();
        aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
        aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
        aomsrefundTs.add(aomsrefundT);
        return aomsrefundTs;
    }
}