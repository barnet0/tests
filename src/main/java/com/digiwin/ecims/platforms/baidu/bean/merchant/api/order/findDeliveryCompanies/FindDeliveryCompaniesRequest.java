package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class FindDeliveryCompaniesRequest extends BaiduBaseRequest {

    private int pageNo;

    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getUrlPath() {
        return "OrderService/findDeliveryCompanies";
    }

}
