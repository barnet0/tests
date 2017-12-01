package com.digiwin.ecims.platforms.feiniu.bean.request.returngoods;

import com.digiwin.ecims.platforms.feiniu.bean.reponse.returngoods.MyFeiniuOrderReverseDetailGetResponse;
import com.feiniu.open.api.sdk.internal.util.JsonUtil;
import com.feiniu.open.api.sdk.request.AbstractRequest;
import com.feiniu.open.api.sdk.request.FnRequest;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by zaregoto on 2017/2/7.
 */
public class MyFeiniuOrderReverseDetailGetRequest extends AbstractRequest implements
    FnRequest<MyFeiniuOrderReverseDetailGetResponse> {
    private String rssSeq;

    public MyFeiniuOrderReverseDetailGetRequest() {
    }

    public String getRssSeq() {
        return this.rssSeq;
    }

    public void setRssSeq(String rssSeq) {
        this.rssSeq = rssSeq;
    }

    public String getApiMethod() {
        return "fn.order.reverse.detail.get";
    }

    public String getJsonParams() throws IOException {
        TreeMap pmap = new TreeMap();
        pmap.put("rssSeq", this.rssSeq);
        return JsonUtil.toJson(pmap);
    }

    public Class<MyFeiniuOrderReverseDetailGetResponse> getResponseClass() {
        return MyFeiniuOrderReverseDetailGetResponse.class;
    }
}
