package com.digiwin.ecims.platforms.feiniu.bean.request.refund;

import com.digiwin.ecims.platforms.feiniu.bean.reponse.refund.MyFeiniuRefundGetDetailInfoResponse;
import com.feiniu.open.api.sdk.internal.util.JsonUtil;
import com.feiniu.open.api.sdk.request.AbstractRequest;
import com.feiniu.open.api.sdk.request.FnRequest;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by zaregoto on 2017/2/7.
 */
public class MyFeiniuRefundGetDetailInfoRequest extends AbstractRequest
    implements FnRequest<MyFeiniuRefundGetDetailInfoResponse> {
    private String rssSeq;

    public MyFeiniuRefundGetDetailInfoRequest() {
    }

    public String getRssSeq() {
        return this.rssSeq;
    }

    public void setRssSeq(String rssSeq) {
        this.rssSeq = rssSeq;
    }

    public String getApiMethod() {
        return "fn.refund.getDetailInfo";
    }

    public String getJsonParams() throws IOException {
        TreeMap pmap = new TreeMap();
        pmap.put("rssSeq", this.rssSeq);
        return JsonUtil.toJson(pmap);
    }

    public Class<MyFeiniuRefundGetDetailInfoResponse> getResponseClass() {
        return MyFeiniuRefundGetDetailInfoResponse.class;
    }
}
