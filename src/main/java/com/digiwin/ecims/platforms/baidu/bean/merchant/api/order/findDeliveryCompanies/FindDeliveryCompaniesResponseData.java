package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies;

import java.util.List;

public class FindDeliveryCompaniesResponseData {

    private int totalCount;

    private int pageNo;

    private List<DeliveryCompany> deliveryCompanyList;

    private int pageSize;

    private int code;

    private String msg;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<DeliveryCompany> getDeliveryCompanyList() {
        return deliveryCompanyList;
    }

    public void setDeliveryCompanyList(List<DeliveryCompany> deliveryCompanyList) {
        this.deliveryCompanyList = deliveryCompanyList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
