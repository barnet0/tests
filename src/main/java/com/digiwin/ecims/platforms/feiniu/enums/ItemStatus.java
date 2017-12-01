package com.digiwin.ecims.platforms.feiniu.enums;

/**
 * Created by zaregoto on 2017/2/6.
 */
public enum ItemStatus {
    INSTOCK("2", "仓库中"),
    ONSALE("3", "销售中");

    private String code;
    private String desc;

    ItemStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
}
