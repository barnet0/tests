package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseResponse;

public class FindDeliveryCompaniesResponse
        extends BaiduBaseResponse<FindDeliveryCompaniesResponseData> {

    private List<FindDeliveryCompaniesResponseData> data;

    public List<FindDeliveryCompaniesResponseData> getData() {
        return data;
    }

    public void setData(List<FindDeliveryCompaniesResponseData> data) {
        this.data = data;
    }


}
