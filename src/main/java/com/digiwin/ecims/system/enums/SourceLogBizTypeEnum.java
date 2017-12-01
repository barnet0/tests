package com.digiwin.ecims.system.enums;

/**
 * 平台原始日志记录类型
 *
 * @author zaregoto
 */
public enum SourceLogBizTypeEnum {
    /**
     * 订单
     */
    AOMSORDT("AomsordT"),

    /**
     * 退单
     */
    AOMSREFUNDT("AomsrefundT"),

    /**
     * 铺货（亦叫商品）
     */
    AOMSITEMT("AomsitemT"),

    /**
     * 评价
     */
    AOMSRATET("AomsrateT");

    private final String valueString;

    SourceLogBizTypeEnum(String sourceLogBizType) {
        this.valueString = sourceLogBizType;
    }

    /**
     * @return the valueString
     */
    public String getValueString() {
        return valueString;
    }

}
