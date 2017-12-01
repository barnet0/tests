package com.digiwin.ecims.platforms.feiniu.enums;

/**
 * Created by zaregoto on 2017/2/4.
 */
public enum OrderDateType {
//    1-订单生成时间
//    2-支付时间
//    3-发货时间
//    4-收货时间
//    5-取消时间
//    6-交易成功时间
//    7-最新更新时间
    CREATE_TIME("1", "订单生成时间"),
    PAY_TIME("2", "支付时间"),
    SEND_TIME("3", "发货时间"),
    RECEIVE_TIME("4", "收货时间"),
    CANCEL_TIME("5", "取消时间"),
    FINISH_TIME("6", "交易成功时间"),
    UPDATE_TIME("7", "最新更新时间");

    private String code;
    private String desc;

    OrderDateType(String code, String desc) {
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
