package com.digiwin.ecims.platforms.feiniu.enums;

/**
 * Created by zaregoto on 2017/2/6.
 */
public enum ReturnDateType {
    CREATE_TIME("1", "申请时间"),
    FINISH_TIME("2", "完成时间");

    private String code;
    private String desc;

    ReturnDateType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
}
