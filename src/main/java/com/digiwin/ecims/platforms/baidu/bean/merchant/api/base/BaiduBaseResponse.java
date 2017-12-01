package com.digiwin.ecims.platforms.baidu.bean.merchant.api.base;

import java.util.List;

public abstract class BaiduBaseResponse<E> {

    private List<E> data;

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public BaiduBaseResponse() {
        super();
    }
    
}
