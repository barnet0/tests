package com.digiwin.ecims.platforms.dangdang.service.manual.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.exception.SyncResponseErrorException;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.platforms.dangdang.bean.AomsordTTranslator;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.OrderDetailsGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get.OrdersListGetResponse;
import com.digiwin.ecims.platforms.dangdang.service.DangdangApiService;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangClient;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;

/**
 * 当当手动拉取对外接口服务类
 * 
 * @author hopelee
 *
 */
@Service("syncDangDangOrderDataByHandServiceImpl")
public class SyncDangDangOrderDataByHandServiceImpl implements SyncOrderDataByHandService {

  private static final Logger logger =
      LoggerFactory.getLogger(SyncDangDangOrderDataByHandServiceImpl.class);

  @Autowired
  private DangdangApiService dangdangAPIService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private AomsShopService aomsShopService;

  // @Autowired
  // private LoginfoOperateService loginfoOperateService;

  @Autowired
  private ParamSystemService paramSystemService; // add by mowj 20150928

  private XmlUtils xu = XmlUtils.getInstance();

  private final static String CREATE_DATE_SCHEDULE_TYPE =
      "Manually_DangDang_syncOrderDataByCreateDate";

  private final static String MODIFY_DATE_SCHEDULE_TYPE =
      "Manually_DangDang_syncOrderDataByModifyDate";


  @Override
  public String syncOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    try {
      AomsshopT aomsshopT = null;
      List<AomsshopT> aomsshopTs = new ArrayList<AomsshopT>();
      if (storeId != null && storeId.length() > 0) {
        aomsshopT = aomsShopService.getStoreByStoreId(storeId);
        aomsshopTs.add(aomsshopT);
      } else {
        aomsshopTs = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
      }

      int totalRows = 0;

      for (AomsshopT tempShopT : aomsshopTs) {
        // 取得config设定檔
        TaskScheduleConfig taskScheduleConfig =
            taskService.getTaskScheduleConfigInfo("DangdangTradeUpdate#"
                + tempShopT.getAomsshop001() + "#" + DangdangCommonTool.STORE_TYPE);

        // 參數設定
        String sDate = startDate;
        String eDate = endDate;
        int pageSize = taskScheduleConfig.getMaxPageSize();
        // String state = "9999";
        // String dateType = "1";// 默认0按修改时间查询，1为按创建时间查询
        int initPageNo = 1;
        // 取得時間區間內總資料筆數
        int totalSize = 0;
        try {
          totalSize = Integer.parseInt(dangdangAPIService
              .dangdangOrdersListGet(tempShopT, sDate, eDate, DangdangCommonTool.FULL_ORDER_STATUS,
                  initPageNo, DangdangCommonTool.MIN_PAGE_SIZE, UseTimeEnum.CREATE_TIME,
                  CREATE_DATE_SCHEDULE_TYPE)
              .getTotalInfo().getOrderCount());
          if (totalSize > 400) {
            return JsonUtil.format(
                new SyncResOrderHandBean("由于当当服务器连接数量限制，数据笔数大于400笔连线会失败，请重新输入区间条件！"));
          }
        } catch (Exception e) {
          e.printStackTrace();
          return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
        }

        // 區間內沒有資料
        if (totalSize == 0) {
          return JsonUtil
              .format(new SyncResOrderHandBean(Boolean.TRUE, "0", null, "此查询区间内没有资料!"));
        }
        totalRows += totalSize;
        // 區間內有資料， 計算頁數
        int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
        for (int i = pageNum; i > 0; i--) {
          // 區間內資料可以一次儲存完畢，儲存完後離開迴圈
          List<Object> saveAomsordTs = new ArrayList<Object>();
          OrdersListGetResponse olgResponse = null;
          try {
            olgResponse = dangdangAPIService.dangdangOrdersListGet(tempShopT, sDate, eDate,
                DangdangCommonTool.FULL_ORDER_STATUS, i, pageSize, UseTimeEnum.CREATE_TIME,
                CREATE_DATE_SCHEDULE_TYPE);
          } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
          }
          if (null == olgResponse.getOrdersList()) {
            return JsonUtil.format(new SyncResOrderHandBean(
                "syncOrdersData第" + i + "頁" + "， 總共有" + pageNum + "頁" + "OrderInfo為空"));
          }
          for (com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get.OrderInfo orderInfo : olgResponse
              .getOrdersList().getOrderInfos()) {
            OrderDetailsGetResponse odgResponse = null;
            // 当当抓取资料时，连线可能会timeout，经jet决议，作法为无限reTry
            int reTryNumber = 10000;
            for (int j = 0; j < reTryNumber;) {
              try {
                odgResponse = dangdangAPIService.dangdangOrderDetailsGet(tempShopT,
                    orderInfo.getOrderID(), CREATE_DATE_SCHEDULE_TYPE);
                break;
              } catch (Exception ex) {
                logger.error("当当抓取资料时，连线timeout，正在处理第" + (pageNum - i) + "頁" + "， 總共有" + pageNum
                    + "頁" + "，订单编号为" + orderInfo.getOrderID() + "已经reTry" + reTryNumber + "次");
                // 连线错误时 reTry
                j++;
                continue;
              }
            }

            if (null != odgResponse.getError()) {
              // Response返回错误信息
              int returnTotalRows = (pageNum - i) * pageSize;// 已处理笔数
              return JsonUtil.format(new SyncResponseErrorException(
                  new ResponseError(odgResponse.getError().getOperCode(),
                      odgResponse.getError().getOperation()),
                  returnTotalRows));
            }

            List<AomsordT> list = new AomsordTTranslator().doTransToAomsordTBean(odgResponse,
                tempShopT.getAomsshop001(), orderInfo.getOrderTimeStart());
            saveAomsordTs.addAll(list);
          }

          // 每頁儲存資料一次
          taskService.newTransaction4Save(saveAomsordTs);
        }
      }
      return JsonUtil
          .format(new SyncResOrderHandBean(Boolean.TRUE, String.valueOf(totalRows)));

    } catch (Exception e) {
      e.printStackTrace();
      return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
    }
  }

  @Override
  public String syncOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    try {
      AomsshopT aomsshopT = null;
      List<AomsshopT> aomsshopTs = new ArrayList<AomsshopT>();
      if (storeId != null && storeId.length() > 0) {
        aomsshopT = aomsShopService.getStoreByStoreId(storeId);
        aomsshopTs.add(aomsshopT);
      } else {
        aomsshopTs = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
      }

      int totalRows = 0;

      for (AomsshopT tempShopT : aomsshopTs) {
        // 取得config设定檔
        TaskScheduleConfig taskScheduleConfig =
            taskService.getTaskScheduleConfigInfo("DangdangTradeUpdate#"
                + tempShopT.getAomsshop001() + "#" + DangdangCommonTool.STORE_TYPE);

        // 參數設定
        String sDate = startDate;
        String eDate = endDate;
        int pageSize = taskScheduleConfig.getMaxPageSize();
        // String state = "9999";
        // String dateType = "1";// 默认0按修改时间查询，1为按创建时间查询
        int initPageNo = 1;
        // 取得時間區間內總資料筆數
        int totalSize = 0;
        try {
          totalSize = Integer.parseInt(dangdangAPIService
              .dangdangOrdersListGet(tempShopT, sDate, eDate, DangdangCommonTool.FULL_ORDER_STATUS,
                  initPageNo, DangdangCommonTool.MIN_PAGE_SIZE, UseTimeEnum.UPDATE_TIME,
                  MODIFY_DATE_SCHEDULE_TYPE)
              .getTotalInfo().getOrderCount());
          if (totalSize > 400) {
            return JsonUtil.format(
                new SyncResOrderHandBean("由于当当服务器连接数量限制，数据笔数大于400笔连线会失败，请重新输入区间条件！"));
          }
        } catch (Exception e) {
          e.printStackTrace();
          return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
        }

        // 區間內沒有資料
        if (totalSize == 0) {
          return JsonUtil
              .format(new SyncResOrderHandBean(Boolean.TRUE, "0", null, "此查询区间内没有资料!"));
        }
        totalRows += totalSize;
        // 區間內有資料， 計算頁數
        int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
        for (int i = pageNum; i > 0; i--) {
          // 區間內資料可以一次儲存完畢，儲存完後離開迴圈
          List<Object> saveAomsordTs = new ArrayList<Object>();
          OrdersListGetResponse olgResponse = null;
          try {
            olgResponse = dangdangAPIService.dangdangOrdersListGet(tempShopT, sDate, eDate,
                DangdangCommonTool.FULL_ORDER_STATUS, i, pageSize, UseTimeEnum.UPDATE_TIME,
                MODIFY_DATE_SCHEDULE_TYPE);
          } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
          }
          if (null == olgResponse.getOrdersList()) {
            return JsonUtil.format(new SyncResOrderHandBean(
                "syncOrdersData第" + i + "頁" + "， 總共有" + pageNum + "頁" + "OrderInfo為空"));
          }
          for (com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get.OrderInfo orderInfo : olgResponse
              .getOrdersList().getOrderInfos()) {
            OrderDetailsGetResponse odgResponse = null;
            // 当当抓取资料时，连线可能会timeout，经jet决议，作法为无限reTry
            int reTryNumber = 10000;
            for (int j = 0; j < reTryNumber;) {
              try {
                odgResponse = dangdangAPIService.dangdangOrderDetailsGet(tempShopT,
                    orderInfo.getOrderID(), CREATE_DATE_SCHEDULE_TYPE);
                break;
              } catch (Exception ex) {
                logger.error("当当抓取资料时，连线timeout，正在处理第" + (pageNum - i) + "頁" + "， 總共有" + pageNum
                    + "頁" + "，订单编号为" + orderInfo.getOrderID() + "已经reTry" + reTryNumber + "次");
                // 连线错误时 reTry
                j++;
                continue;
              }
            }

            if (null != odgResponse.getError()) {
              // Response返回错误信息
              int returnTotalRows = (pageNum - i) * pageSize;// 已处理笔数
              return JsonUtil.format(new SyncResponseErrorException(
                  new ResponseError(odgResponse.getError().getOperCode(),
                      odgResponse.getError().getOperation()),
                  returnTotalRows));
            }

            List<AomsordT> list = new AomsordTTranslator().doTransToAomsordTBean(odgResponse,
                tempShopT.getAomsshop001(), orderInfo.getOrderTimeStart());
            saveAomsordTs.addAll(list);
          }

          // 每頁儲存資料一次
          taskService.newTransaction4Save(saveAomsordTs);
        }
      }
      return JsonUtil
          .format(new SyncResOrderHandBean(Boolean.TRUE, String.valueOf(totalRows)));

    } catch (Exception e) {
      e.printStackTrace();
      return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
    }
  }

  @Override
  public String syncOrderDataByOrderId(String storeId, String orderId) {
    try {
      // 取得授权
      AomsshopT aomsshopT = aomsShopService.getStoreByStoreId(storeId);

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("o", orderId);// 订单编号，一次只能查询一张订单详细信息
      String resultClient = client.execute("dangdang.order.details.get", formMap);
      OrderDetailsGetResponse odgResponse =
          (OrderDetailsGetResponse) xu.xml2JavaBean(resultClient, OrderDetailsGetResponse.class);

      boolean isSuccess = (null == odgResponse.getError());
      if (isSuccess) {
        List<AomsordT> aomsordT = new AomsordTTranslator().doTrans(odgResponse, storeId);
        taskService.newTransaction4Save(aomsordT);
        return JsonUtil.format(
            new SyncResOrderHandBean(Boolean.TRUE, String.valueOf(aomsordT.size())));
      } else {
        return JsonUtil.format(new SyncResOrderHandBean(Boolean.FALSE, "0",
            odgResponse.getError().getOperCode(), odgResponse.getError().getOperation()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
    }
  }

  @Override
  public String syncOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    String result = null;
    try {
      // 此接口暂时可以不实现
    } catch (Exception e) {
      e.printStackTrace();
      return JsonUtil.format(new SyncResOrderHandBean(e.getMessage()));
    }
    return result;
  }

  @Override
  public Long findOrderCountFromEcByCreateDate(String scheduleUpdateCheckType, String storeId,
      String startDate, String endDate) {
    // mark by mowj 20151118 start
    // Long result = null;
    // try {
    // // 取得授权
    // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId, "5");
    //
    // // 调用API
    // DangdangClient client = new
    // DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
    // aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
    // Map<String, String> formMap = new HashMap<String, String>();
    // formMap.put("osd", startDate);// 下单开始日期
    // formMap.put("oed", endDate);// 下单结束日期
    // formMap.put("os", "9999");// 订单状态 9999 全部
    // formMap.put("p", "1");// 页数，预设值为1
    // formMap.put("pageSize", "20");// 每页结果数量，只能选择如下数值：5、10、15、20 可以不填，默认是20
    // String response = client.execute("dangdang.orders.list.get", formMap);
    // OrdersListGetResponse olgResponse = (OrdersListGetResponse) xu.xml2JavaBean(response,
    // OrdersListGetResponse.class);
    //
    // // 资料捞回来后，先存DB
    // loginfoOperateService.newTransaction4SaveSource(startDate, endDate, "5",
    // "dangdang.orders.list.get 查询订单列表信息", response, SourceLogBizTypeEnum.AOMSORDT.getValueString()
    // , aomsshopT.getAomsshop001()
    // , scheduleUpdateCheckType
    // );
    //
    // // 取得時間區間內總資料筆數
    // int totalSize = Integer.parseInt(olgResponse.getTotalInfo().getOrderCount());
    //
    // result = Long.parseLong("" + totalSize);
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return result;
    // mark by mowj 20151118 end

    return findOrderCountFromEc(UseTimeEnum.CREATE_TIME, scheduleUpdateCheckType, storeId,
        startDate, endDate); // add by mowj 20151118
  }

  @Override
  public Long findOrderCountFromEcByCreateDate(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) {
    // mark by mowj 20151118 start
    // Long result = null;
    // try {
    // String startDate = taskScheduleConfig.getLastUpdateTime();
    // String endDate = taskScheduleConfig.getEndDate();
    // String dateType = "1";
    //
    // // 取得時間區間內總資料筆數
    // int totalSize = Integer.parseInt(dangdangAPIService.dangdangOrdersListGet(
    // aomsshopT, startDate, endDate, "9999", 1, 5, dateType,
    // "Manually_DangDang_findOrderDataFromECByCreateDate"
    // ).getTotalInfo().getOrderCount());
    // result = Long.parseLong("" + totalSize);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return result;
    // mark by mowj 20151118 end
    return 0l;
  }

  @Override
  public Long findOrderCountFromEcByModifyDate(String scheduleUpdateCheckType, String storeId,
      String startDate, String endDate) {
    return findOrderCountFromEc(UseTimeEnum.UPDATE_TIME, scheduleUpdateCheckType, storeId,
        startDate, endDate); // add by mowj 20151118
  }

  @Override
  public Long findOrderCountFromEcByModifyDate(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) {
    // TODO Auto-generated method stub
    return null;
  }

  private Long findOrderCountFromEc(UseTimeEnum orderUseTimeEnum,
      String scheduleUpdateCheckType, String storeId, String startDate, String endDate) {
    // 取得授权
    AomsshopT aomsshopT =
        aomsShopService.getStoreByStoreId(storeId);
    int pageNo = 1;
    int pageSize = DangdangCommonTool.MIN_PAGE_SIZE;
    try {
      OrdersListGetResponse response = dangdangAPIService.dangdangOrdersListGet(aomsshopT,
          startDate, endDate, DangdangCommonTool.FULL_ORDER_STATUS, pageNo, pageSize,
          orderUseTimeEnum, scheduleUpdateCheckType);
      return Long.parseLong(response.getTotalInfo().getOrderCount());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0l;
  }

}
