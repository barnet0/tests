package com.digiwin.ecims.platforms.feiniu.bean.reponse.returngoods;

import com.feiniu.open.api.sdk.response.AbstractResponse;

/**
 * Created by zaregoto on 2017/2/7.
 */
public class MyFeiniuOrderReverseDetailGetResponse extends AbstractResponse {
    private MyFeiniuOrderReverseDetailGetVo data;

    public MyFeiniuOrderReverseDetailGetResponse() {
    }

    public MyFeiniuOrderReverseDetailGetVo getData() {
        return this.data;
    }

    public void setData(MyFeiniuOrderReverseDetailGetVo data) {
        this.data = data;
    }
}
