package com.digiwin.ecims.platforms.feiniu.enums;

/**
 * Created by zaregoto on 2017/2/6.
 */
public enum RefundDateType {
    CREATE_TIME("1", "退单时间"),
    UPDATE_TIME("2", "最新更新时间");

    private String code;
    private String desc;

    RefundDateType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
