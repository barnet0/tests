package com.digiwin.ecims.platforms.feiniu.enums;

/**
 * Created by zaregoto on 2017/2/4.
 */
public enum OrderStatus {
//    1：待付款
//    2：待发货
//    3：打包完成
//    4：交易成功
//    5：已取消
//    6：已退款
//    7：退货中/未发货用户申请退款
//    8：退货中/退货中
//    9：交易关闭/已取消（逾期未付款）
//    14：出库/待收货
//    15：客户签收
//    [跨境 10:申请报关,11:报关通过, 12:申请通关,13:通关通过]
//    [虚拟充值 101：待充值  102：充值成功 103：充值失败]
    WAIT_BUYER_PAY("1", "待付款"),
    WAIT_SELLER_SEND_GOODS("2", "待发货"),
    PACKAGED("3", "打包完成"),
    TRADE_FINISHED("4", "交易成功"),
    TRADE_CANCELLED("5", "已取消"),
    TRADE_REFUNDED("6", "已退款"),
    GOODS_UNSENT_REFUND("7", "退货中/未发货用户申请退款"),
    GOODS_RETURNING("8", "退货中/退货中"),
    TRADE_CLOSED("9", "交易关闭/已取消（逾期未付款）"),
    WAIT_BUYER_CONFIRM_GOODS("14", "出库/待收货"),
    BUYER_RECEIVED("15", "客户签收");

    private String code;
    private String desc;
    private OrderStatus(String code, String desc) {
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
