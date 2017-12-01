package com.digiwin.ecims.platforms.suning.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public final class SuningCommonTool {

    /**
     * 蘇寧API接口URL
     */
    public static final String SERVER_URL = "http://open.suning.com/api/http/sopRequest";
    private static SuningCommonTool sct;


    public enum ApiInterface {
        DIGIWIN_ITEM_LISTING("digiwin.item.listing"),
        DIGIWIN_ITEM_DELISTING("digiwin.item.delisting"),
        DIGIWIN_ORDER_DETAIL_GET("digiwin.order.detail.get"),
        DIGIWIN_REFUND_GET("digiwin.refund.get"),
        DIGIWIN_SHIPPING_SEND("digiwin.shipping.send"),
        DIGIWIN_INVENTORY_UPDATE("digiwin.inventory.update"),
        DIGIWIN_ITEM_DETAIL_GET("digiwin.item.detail.get"),
        DIGIWIN_SYSTEM("digiwin.system");

        private String configKey;

        ApiInterface(String configKey) {
            this.configKey = configKey;
        }

        @Override public String toString() {
            return this.configKey;
        }
    }


    /**
     * 订单状态 10待发货，20已发货，21部分发货，30交易成功 ，40交易关闭
     */
    public static final String[] ORDER_STATUS_CREATE_TIME = {"10", "20", "21", "30", "40"};
    /**
     * 订单行项目状态 10待发货，20已发货，30交易成功
     */
    public static final String[] ORDER_STATUS_MODIFY_TIME = {"10", "20", "30"};


    /**
     * 基本參數設定
     *
     * @author Xavier
     */
    public enum SyncBasicParm {
        ORDER(90, 1), // 訂單
        GOODS(90, 30), // 商品
        REFUNDS_AND_RETURNS(90, 30); // 退換貨

        private int maxProcessDays; // 每次排程調用, 最大可處理的天數
        private int getDataDays; // 每次從api 取資料的天數

        SyncBasicParm(int maxProcessDays, int getDataDays) {
            this.maxProcessDays = maxProcessDays;
            this.getDataDays = getDataDays;
        }

        public int getMaxProcessDays() {
            return maxProcessDays;
        }

        public int getGetDataDays() {
            return getDataDays;
        }
    }


    public enum OrderUseTime {
        CREATE_TIME, UPDATE_TIME
    }


    public enum OrderOperateType {
        IS_MANUALLY("Manually"), IS_SCHEDULE("Schedule"), IS_CHECK_SCHEDULE("CheckSchedule");

        private String name;

        OrderOperateType(String name) {
            this.name = name;
        }

        public String tosString() {
            return this.name;
        }
    }


    public static final String STORE_TYPE = "4";
    public static final String STORE_NAME = "Suning";
    public static final String STORE_CHN_NAME_IN_DB = "苏宁";

    public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 30;

    public static final String API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;

    public static final String API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;

    public static final String API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

    public static final String ORD_POST_SCHEDULE_TYPE =
        STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
            + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

    public static final String REF_POST_SCHEDULE_TYPE =
        STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
            + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

    public static final String ITE_POST_SCHEDULE_TYPE =
        STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
            + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

    public static final int MIN_PAGE_NO = 1;
    public static final int MIN_PAGE_SIZE = 1;


    /**
     * 記錄錯誤訊息
     */
    private final Map<ApiInterface, Map<String, String>> errorDescMap;

    private SuningCommonTool() {
        this.errorDescMap = new HashMap<>();

        // 商品上架(suning.custom.shelves.move)
        Map<String, String> itemListing = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_ITEM_LISTING, itemListing);
        itemListing.put("biz.custom.shelves.invalid-biz:productCode",
            "1． 商品编码+商户编码查询商品，不存在 2． 商品类型是否为子码商品 3． 销售状态为上架状态 4． 商品的库存是否为0 5.不能上架强制下架的商品");
        itemListing.put("biz.shelves.missing-parameter:productCode", "苏宁商品编码不能为空");

        //// 商品下架 (與上架共用)(suning.custom.shelves.add )
        this.errorDescMap.put(ApiInterface.DIGIWIN_ITEM_DELISTING, itemListing);

        // 電商系統的基本錯誤訊息
        Map<String, String> system = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_SYSTEM, system);
        system.put("sys.check.header-param:forbidden", "調用參數出錯, 請檢查授權碼！");


        // 单笔订单查询(suning.custom.order.get)
        Map<String, String> orderDetailGet = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_ORDER_DETAIL_GET, orderDetailGet);
        orderDetailGet.put("biz.orderget.ordercode-format:error", "订单号格式错误");
        orderDetailGet.put("biz.handler.data-get:no-result", "订单号不存在");

        // 以下為批量使用(suning.custom.order.query)
        orderDetailGet.put("biz.orderquery.startTime-format:error",
            "交易开始时间格式错误.使用正确的时间格式，如 2012-06-20 00:00:00");
        orderDetailGet.put("biz.orderquery.endTime-format:error",
            "交易结束时间格式错误.使用正确的时间格式，如 2012-06-20 00:00:00");
        orderDetailGet.put("sys.check.page-param:error", "分页参数错误.请输入正确的分页参数");
        orderDetailGet.put("sys.check.query-time:outofrange", "时间范围超过配置.请输入正确的时间范围");
        orderDetailGet.put("sys.check.query-time:error", "查询时间错误.请输入正确的日期");
        orderDetailGet.put("biz.orderquery.time-value:outofrange", "查询日期超过接口范围.请检查日期范围");
        orderDetailGet.put("sys.check.request-apprequesttime:error", "请求时间戳误差太大.请检查系统时间");

        // 单笔查询退货信息( suning.custom.singlerejected.get)
        Map<String, String> refundGet = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_REFUND_GET, refundGet);
        refundGet.put("biz.handler.data-get:no-result", "订单号不存在");
        refundGet.put("biz.rejected.ordercode-format:error", "请输入正确的订单号");
        refundGet.put("biz.queryReturnByNoServer.orderCode-length:overlong", "B2C订单号长度过长");
        refundGet.put("biz.singleGetRejected.missing-parameter:orderCode", "订单号输入为空");

        // 以下為批量使用(suning.custom.batchrejected.query )
        refundGet.put("sys.check.query-time:error", "时间错误. 传入正确的时间");
        refundGet.put("sys.check.query-time:outofrange", "时间超过设置范围. 开始时间与结束时间间隔不超过30天");
        refundGet.put("biz.orderquery.startTime-format:error",
            "查询开始时间格式不对. 使用正确的时间格式，如 2012-06-20 00:00:00");
        refundGet.put("biz.queryBatchReturnServer.endTime-format error",
            "查询结束时间格式不对. 使用正确的时间格式，如 2012-06-20 00:00:00");
        refundGet.put("biz.orderquery.time-value:outofrange", "查询日期超过接口范围, 请检查输入的日期，需在三个月内");
        refundGet.put("sys.check.starttime-value:null", "交易开始时间为空. 时间必填");
        refundGet.put("sys.check.endtime-value:null", "交易结束时间为空. 时间必填");
        refundGet.put("biz.handler.data-get:no-result", "业务数据结果为空");

        // 订单发货(suning.custom.orderdelivery.add)
        Map<String, String> shippingSend = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_SHIPPING_SEND, shippingSend);
        shippingSend.put("biz.orderDelivery.order-status:error", "订单发货订单的状态非买家已付款");
        shippingSend.put("biz.shutdown.order.cannot.delivery", "交易已关闭，不能发货");
        shippingSend.put("biz.orderDelivery.missing-parameter:orderCode", "订单号输入为空");
        shippingSend.put("biz.orderDelivery.ordercode-value:inexistence", "订单发货订单号不存在");
        shippingSend.put("biz.orderDelivery.expressno-value:existence", "订单发货运单号存在");
        shippingSend.put("biz.orderDelivery.ordercode-length:overlong", "订单发货订单长度过长");
        shippingSend.put("biz.orderDelivery.expressno-format:error", "订单发货运单号格式错误");
        shippingSend.put("biz.orderDelivery.expresscompanycode-format:error", "订单发货物流公司编码格式错误");
        shippingSend.put("biz.orderDelivery.expresscompanycode-value:inexistence", "订单发货物流公司编码不存在");
        shippingSend.put("biz.orderDelivery.missing-parameter:expressCompanyCode", "物流公司编码为空");
        shippingSend.put("biz.orderDelivery.missing-parameter:productcode", "商品编码不能为空");
        shippingSend.put("biz.orderDelivery.productcode-length:overlong", "订单发货商品编码长度过长");
        shippingSend.put("biz.orderDelivery.productcode-value:inexistence", "订单发货商品编码不存在");
        shippingSend.put("biz.orderDelivery.default-sendaddress:inexistence", "默认发货地址不存在");
        shippingSend.put("biz.orderDelivery.default-returnaddress:inexistence", "默认退货地址不存在");
        shippingSend.put("biz.orderDelivery.productcode-value:repeat", "商品编码重复");
        shippingSend.put("biz.custom.orderDelivery.invalid-biz:deliveryTime", "发货时间格式有误");
        shippingSend.put("biz.custom.orderDelivery.invalid-biz:orderLineNumber",
            "1.订单行项目号不存在 2.苏宁商品编码及订单行项目号都为空");

        // 库存修改(单个)(suning.custom.inventory.modify)
        Map<String, String> inventoryUpdate = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_INVENTORY_UPDATE, inventoryUpdate);
        inventoryUpdate.put("biz.custom.inventory.invalid-biz:productCode", "苏宁商品编码不存在");
        inventoryUpdate.put("biz.custom.inventory.invalid-biz:itemCode", "苏宁商品编码和商家商品编码必须有一个字段有值");
        inventoryUpdate.put("biz.custom.inventory.productCode-format:error", "苏宁商品编码格式错误");
        inventoryUpdate.put("biz.custom.inventory.productCode-length:overlong", "苏宁商品编码长度过长");
        inventoryUpdate
            .put("biz.custom.inventory.invalid-biz:productcode cannot be master productcode",
                "库存更新商品编码不能为主商品编码");
        inventoryUpdate.put("biz.custom.inventory.invAddrid-length:overlong", "仓库地址ID长度过长");
        inventoryUpdate.put("biz.custom.inventory.invAddrid-format:error", "仓库地址ID格式错误");
        inventoryUpdate.put("biz.inventory.missing-parameter:destInvNum", "目标库存数量为空");
        inventoryUpdate.put("biz.custom.inventory.destInvNum-format:error", "目标库存数量格式错误");
        inventoryUpdate.put("biz.custom.inventory.destInvNum-length:overlong", "目标库存数量长度过长");
        inventoryUpdate
            .put("biz.custom.inventory.invalid-biz:modify failed", "修改过程中产生的销售量大于您维护的目标库存值，修改失败");
        inventoryUpdate
            .put("biz.custom.inventory.invalid-biz:operate failed,retry later", "库存暂时无法修改，请稍后重试");

        // 單筆鋪貨詳情(suning.custom.itemdetail.query)
        Map<String, String> itemDetailGet = new HashMap<>();
        this.errorDescMap.put(ApiInterface.DIGIWIN_ITEM_DETAIL_GET, itemDetailGet);
        itemDetailGet.put("biz.itemDetail.missing-parameter:productCode", "苏宁商品编码必填");
        itemDetailGet.put("biz.handler.data-get:no-result", "商品编码不存在");
        itemDetailGet.put("biz.itemDetail.invalid-biz:productCode", "此商品为规格商品编码");

        // 以下為批量使用(suning.custom.item.query)
        itemDetailGet.put("biz.custom.item.invalid-biz:*", "*代表对应字段，含有中文	检查对应字段是否有中文");
        itemDetailGet.put("biz.custom.item.invalid-biz:startTime", "开始时间格式有误.请检查输入");
        itemDetailGet
            .put("biz.custom.item.invalid-biz:endTime", "1、结束时间格式有误2、开始时间和结束时间间隔超过30天.请检查输入");
        itemDetailGet.put("biz.custom.item.invalid-biz:time", "开始时间和结束时间必须同时填写.请检查输入");
        itemDetailGet.put("biz.custom.item.invalid-biz:status", "处理状态值非法.检查状态值");
        itemDetailGet.put("biz.handler.data-get:no-result", "查询无结果.检查输入值, 可能错误如下：1.查询时间超过90天");
        itemDetailGet.put("sys.check.request-params:error", "请求参数格式有误.检查请求参数格式");

    }

    public static SuningCommonTool getInstance() {
        if (sct == null) {
            sct = new SuningCommonTool();
        }
        return sct;
    }

    public String getBeanValue(Object bean, String propertyName)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String value = BeanUtils.getProperty(bean, propertyName);
        if (StringUtils.isBlank(value)) {
            if ("transportFee".equals(propertyName)) {
                return "0";
            } else if ("saleNum".equals(propertyName)) {
                return "0";
            } else if ("payAmount".equals(propertyName)) {
                return "0";
            } else if ("vouchertotalMoney".equals(propertyName)) {
                return "0";
            } else if ("coupontotalMoney".equals(propertyName)) {
                return "0";
            } else if ("payamount".equals(propertyName)) {
                return "0";
            }
        }
        return value;
    }


    public List<?> getBeanListValue(Object bean, String propertyName)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return (List<?>) PropertyUtils.getProperty(bean, propertyName);
    }


    /**
     * 檢查是不是有 error
     *
     * @param json
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     * @throws Exception
     */
    public ResponseError hasError(ApiInterface apiFace, String json)
        throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // 提取 sn_responseContent
        JsonNode sn_responseContent = root.path("sn_responseContent");

        // 提取 sn_error
        JsonNode sn_error = sn_responseContent.path("sn_error");

        if (sn_error != null) {
            if (sn_error.get("error_code") != null) {
                // 取得error code
                String error_code = sn_error.get("error_code").textValue();

                // 取得對應api 的錯誤訊息.
                String systemErrorMsg =
                    this.errorDescMap.get(ApiInterface.DIGIWIN_SYSTEM).get(error_code);
                if (StringUtils.isNotBlank(systemErrorMsg)) {
                    return new ResponseError(error_code, systemErrorMsg);
                } else {
                    // biz.handler.data-get:no-result
                    Map<String, String> error = this.errorDescMap.get(apiFace);
                    // System.out.println(error_code);
                    return new ResponseError(error_code, error.get(error_code));
                }
            }
        }
        return null;
    }

    /**
     * 取得 總頁數相關資訊
     *
     * @param json
     * @return
     * @throws Exception
     */
    public SuningPageBean getMaxPage(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // 提取 sn_responseContent
        JsonNode sn_responseContent = root.path("sn_responseContent");

        // 提取 sn_error
        JsonNode sn_head = sn_responseContent.path("sn_head");

        int pageNo = 0;
        int pageTotal = 0;
        int returnTotalRows = 0;
        String returnMsg = "";
        if (sn_head.get("pageNo") != null) {
            pageNo = StringUtils.isNotBlank(sn_head.get("pageNo").asText()) ?
                Integer.parseInt(sn_head.get("pageNo").asText()) :
                0;
        }
        if (sn_head.get("pageTotal") != null) {
            pageTotal = StringUtils.isNotBlank(sn_head.get("pageTotal").asText()) ?
                Integer.parseInt(sn_head.get("pageTotal").asText()) :
                0;
        }
        if (sn_head.get("totalSize") != null) {
            returnTotalRows = StringUtils.isNotBlank(sn_head.get("totalSize").asText()) ?
                Integer.parseInt(sn_head.get("totalSize").asText()) :
                0;
        }
        if (sn_head.get("returnMessage") != null) {
            returnMsg = sn_head.get("returnMessage").asText();
        }
        return new SuningPageBean(pageNo, pageTotal, returnTotalRows, returnMsg);
    }

    /**
     * 批量會用到的, 存放 總頁數的 bean.
     *
     * @author Xavier
     */
    public static class SuningPageBean {
        private int pageNo;
        private int pageTotal;
        private int returnTotalRows;
        private String returnMessage;

        public SuningPageBean(int pageNo, int pageTotal, int returnTotalRows,
            String returnMessage) {
            this.pageNo = pageNo;
            this.pageTotal = pageTotal;
            this.returnMessage = returnMessage;
            this.returnTotalRows = returnTotalRows;
        }

        /**
         * 當前頁
         *
         * @return
         */
        public int getPageNo() {
            return pageNo;
        }

        /**
         * 取得總頁數
         *
         * @return
         */
        public int getPageTotal() {
            return pageTotal;
        }

        /**
         * 回傳訊息
         *
         * @return
         */
        public String getReturnMessage() {
            return returnMessage;
        }

        /**
         * 取得總筆數
         *
         * @return
         */
        public int getReturnTotalRows() {
            return returnTotalRows;
        }

    }
}
