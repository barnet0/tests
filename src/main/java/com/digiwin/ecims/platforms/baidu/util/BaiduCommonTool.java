package com.digiwin.ecims.platforms.baidu.util;

import java.util.ArrayList;
import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.OrderStatus;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class BaiduCommonTool {

  public static final String STORE_TYPE = "E";
  public static final String STORE_NAME = "Baidu";
  public static final String STORE_CHN_NAME_IN_DB = "百度";


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

  public static final Integer RESPONSE_SUCCESS_CODE = 0;
  
  /**
   * 当请求数量无法正常返回时，返回-1
   */
  public static final Long NO_COUNT_RETURNED = -1L;

  public static List<Integer> getOrderStatus() {
    final List<Integer> ORDER_STATUS_LIST = new ArrayList<Integer>();
    ORDER_STATUS_LIST.add(OrderStatus.TRADE_CANCELED.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.TRADE_CANCELING_WAIT_MERCHANT_AGREE.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.TRADE_CLOSED.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.TRADE_CREATED.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.TRADE_CREATED_FAILED.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.TRADE_FINISHED.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.WAIT_BUYER_CONFIRM_GOODS.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.WAIT_BUYER_PAY.getValue());
    ORDER_STATUS_LIST.add(OrderStatus.WAIT_SELLER_SEND_GOODS.getValue());

    return ORDER_STATUS_LIST;
  }
  
  public static final int ITEM_COLOR_ATTR_CODE = 28;
  public static final int[] ITEM_SIZE_ATTR_CODE = {41998, 50062, 50067, 50059};
}
