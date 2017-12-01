package com.digiwin.ecims.platforms.dhgate.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class DhgateCommonTool {

  public static final String STORE_TYPE = "F";
  public static final String STORE_NAME = "Dhgate";
  public static final String STORE_CHN_NAME_IN_DB = "敦煌";

  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

  public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

  public static final int UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 30;

  public static final int MIN_PAGE_NO = 1;
  public static final int MIN_PAGE_SIZE = 1;
  public static final int DEFAULT_PAGE_SIZE = 50;
  public static final int MAX_PAGE_SIZE = 100;

  public static final String BIZ_SUCCESS_CODE = "00000000";

  public static final String[] ITEM_STATES = {"100000", // 未定义
      "100100", // 上架产品
      "100200", // 待审核产品
      "100300", // 审核未通过产品
      "100400", // 下架产品
      "100500", // 品牌商投诉产品
      "100600" // 疑似侵权产品
  };

  public static final int[] ITEM_COLOR_ATTR_CODE = {971900, 1071882};
  public static final int[] ITEM_SIZE_ATTR_CODE = {11, 476901, 778703};
  
  public static final boolean isCodeExist(int code, int[] codeArray) {
    for (int i : codeArray) {
      if (code == i) {
        return true;
      }
    }
    return false;
  }
  
  public enum DhgateApiEnum {

    DH_ORDER_LIST_GET("dh.order.list.get", "2.0"), 
    DH_ORDER_GET("dh.order.get", "2.0"), 
    DH_ORDER_PRODUCT_GET("dh.order.product.get", "2.0"), 
    DH_ORDER_DISPUTEOPEN_LIST("dh.order.disputeopen.list", "2.0"), 
    DH_ORDER_DISPUTECLOSE_LIST("dh.order.disputeclose.list", "2.0"), 
    DH_ORDER_DELIVERY_SAVE("dh.order.delivery.save", "2.0"), 
    DH_ITEM_LIST("dh.item.list", "2.0"), 
    DH_ITEM_GET("dh.item.get", "2.0"), 
    DH_ITEM_UPSHELF_LIST("dh.item.upshelf.list", "2.0"), 
    DH_ITEM_DOWNSHELF_LIST("dh.item.downshelf.list", "2.0"), 
    DH_ITEM_UPDATE("dh.item.update", "2.0"),
    DH_ITEM_SKU_LIST("dh.item.sku.list", "2.0");

    private String apiName;
    private String version;

    DhgateApiEnum(String dhgateApiName, String version) {
      this.apiName = dhgateApiName;
      this.version = version;
    }

    public String getApiName() {
      return apiName;
    }

    public String getVersion() {
      return version;
    }

  }

  public enum OrderUseTime {

    CREATE_TIME("1"), PAY_TIME("2");

    private String timeType;

    private OrderUseTime(String timeType) {
      // TODO Auto-generated constructor stub
      this.timeType = timeType;
    }

    public String getTimeType() {
      return timeType;
    }

    public void setTimeType(String timeType) {
      this.timeType = timeType;
    }

  }

  public enum ItemOrderByColumnEnum {
    OPERATE_DATE_START("operateDateStart"), EXPIRE_DATE_START("expireDateStart"), ITEM_CODES(
        "itemCodes"), DOWN_DATE_START("downDateStart");

    private String columnName;

    private ItemOrderByColumnEnum(String columnName) {
      this.columnName = columnName;
    }

    public String getColumnName() {
      return columnName;
    }

    public void setColumnName(String columnName) {
      this.columnName = columnName;
    }

  }

  public enum ItemOrderByAscEnum {
    ASCEND("1"), DESCEND("0");

    private String value;

    private ItemOrderByAscEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }


  }
}
