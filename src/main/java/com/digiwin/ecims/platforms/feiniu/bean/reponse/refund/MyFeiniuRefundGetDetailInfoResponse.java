package com.digiwin.ecims.platforms.feiniu.bean.reponse.refund;

import com.feiniu.open.api.sdk.response.AbstractResponse;

/**
 * Created by zaregoto on 2017/2/7.
 */
public class MyFeiniuRefundGetDetailInfoResponse extends AbstractResponse {
    private MyFeiniuRefundGetDetailInfoVo data;

    public MyFeiniuRefundGetDetailInfoResponse() {
    }

    public MyFeiniuRefundGetDetailInfoVo getData() {
        return this.data;
    }

    public void setData(MyFeiniuRefundGetDetailInfoVo data) {
        this.data = data;
    }
}
