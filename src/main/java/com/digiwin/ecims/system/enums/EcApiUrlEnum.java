package com.digiwin.ecims.system.enums;

/**
 * @author 维杰
 * @since 2015.09.28
 */
public enum EcApiUrlEnum {
    /**
     * 淘宝API调用URL
     */
    TOP_API("TaobaoUrl"),

    /**
     * 淘宝聚石塔获取数据URL
     */
    JST_RDS("TaobaoVMUrl"),

    /**
     * 淘宝OAuth2.0授权获取/刷新access_token服务地址
     */
    TOP_TOKEN("TaobaoTokenUrl"),

    /**
     * 京东OAuth2.0授权获取/刷新access_token服务地址
     */
    JINGDONG_TOKEN("JingdongTokenUrl"),

    /**
     * 京东云API调用URL
     */
    JINGDONG_API("JingdongUrl"),

    /**
     * 一号店API调用URL
     */
    YHD_API("YhdUrl"),

    /**
     * 工商银行API调用URL
     */
    ICBC_API("IcbcUrl"),

    /**
     * 苏宁API调用URL
     */
    SUNING_API("SuningUrl"),

    /**
     * 当当API调用URL
     */
    DANGDANG_API("DangdangUrl"),


    /**
     * 唯品会API调用URL
     */
    VIP_API("VipUrl"),

    /**
     * 百度商城API调用URL
     */
    BAIDU_API("BaiduUrl"),

    /**
     * 阿里巴巴速卖通API调用URL
     */
    ALIEXPRESS_API("AliexpressUrl"),

    /**
     * 敦煌网API调用URL
     */
    DHGATE_API("DhgateUrl"),

    /**
     * 优购网API调用URL
     */
    YOUGOU_API("YougouUrl"),


    /**
     * 建设银行API调用URL
     */
    CCB_API("CcbUrl"),


    /**
     * 拼多多API调用URL
     */
    PDD_API("PddUrl"),


    /**
     * 贝贝网API调用URL
     */
    BEIBEI_API("BeibeiUrl"),

    /**
     * 飞牛网API调用URL
     */
    FEINIU_API("FeiniuUrl"),
    
    /**
     * 拼多多新版本API调用URL
     *  add by cjp 20170510
     */
    PDD2_API("Pdd2Url"),
    
    /**
     * 网易考拉API
     * add by cjp 2017/5/18
     */    
	Kaola_API("KaolaUrl"),
	
    /**
     * 云集API
     * add by xhb 2017/6/27
     */   	
	Yunji_API("YunjiUrl");
	
    private String paraKey;

    EcApiUrlEnum(String paraKey) {
        this.paraKey = paraKey;
    }

    public String toString() {
        return this.paraKey;
    }
}
