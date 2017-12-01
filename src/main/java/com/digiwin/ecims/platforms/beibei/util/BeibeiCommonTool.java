package com.digiwin.ecims.platforms.beibei.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class BeibeiCommonTool {

  public static final String STORE_TYPE = "K";
  public static final String STORE_NAME = "Beibei";
  public static final String STORE_CHN_NAME_IN_DB = "贝贝";
  
  public static final Long MIN_PAGE_NO = 1L;
  public static final Long MIN_PAGE_SIZE = 1L;
  public static final Long DEFAULT_PAGE_SIZE = 40L;
  public static final Long MAX_PAGE_SIZE = 300L;
  
  public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;;
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 25;
  
  /**
   * SKU不同维度间的分隔符
   */
  public static final String SKU_PROPERTIES_DELIMITER = ";";
  /**
   * SKU属性与值的分隔符
   */
  public static final String SKU_PROPERTY_DELIMITER = ":";
  
  public static final String SKU_COLOR_PROPERTY_NAME = "颜色";
  public static final String SKU_SIZE_PROPERTY_NAME = "尺寸";
  
  /**
   * 当请求数量无法正常返回时，返回-1
   */
  public static final Long NO_COUNT_RETURNED = -1L;
  
  public enum OrderTimeRange {
    
    UPDATE_TIME("modified_time"),
    
    PAY_TIME("pay_time");
    
    private String value;

    private OrderTimeRange(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
  
  public enum RefundTimeRange {
    
    UPDATE_TIME("modified_time"),
    
    CREATE_TIME("create_time");
    
    private String value;

    private RefundTimeRange(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
