package com.digiwin.ecims.platforms.taobao.service.ontime.update.trade.jst;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncTbOrdersService;
import com.digiwin.ecims.platforms.taobao.service.jst.impl.TaobaoJstBaseUpdateServiceImpl;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 淘宝普通订单更新
 * 
 * @author KenTu
 */
@Service("taobaoTbTradeUpdateServiceImpl")
public class TaobaoTbTradeUpdateServiceImpl extends TaobaoJstBaseUpdateServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private TaobaoJstSyncTbOrdersService taobaoJstSyncTbOrdersService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
  
    timeOutExecute(
        taobaoJstSyncTbOrdersService,
        TaobaoCommonTool.TB_JST_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
        AomsordT.class, 
        TaobaoJstSyncTbOrdersService.GET_LIST_API, 
        TaobaoJstSyncTbOrdersService.GET_LIST_BY_ID_API, 
        TaobaoJstSyncTbOrdersService.GET_COUNT_API, 
        TaobaoJstSyncTbOrdersService.GET_COUNT_BY_ID_API, 
        TaobaoCommonTool.STORE_TYPE);
    
    return false;
  }
    
    
//  @Autowired
//  private TaobaoJstSyncApiService taobaoJstSyncApiService;
//  
//  @Autowired
//  private TaskService taskService;
//  
//  @Autowired
//  private LoginfoOperateService loginfoOperateService;
//
//  private static final Logger logger =
//      LoggerFactory.getLogger(TaobaoTbTradeUpdateServiceImpl.class);
//
//  @Override
//  public boolean timeOutExecute() throws Exception {
//    TaskScheduleConfig taskScheduleConfig = null;
//    String scheduleType = "TaobaoTbTradeUpdate";
//
//    try {
//
//      logger.info("定时任务开始--进入--taobaoTbTradeUpdateServiceImpl--淘宝普通订单更新--方法");
//      Map<String, String> params = null;
//      AomsordT lastAomsordT = null;
//
//      taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
//      // 如果没有设置更新时间，默认从很久以前开始同步 add by mowj 20160526
//      if (taskScheduleConfig.getLastUpdateTime() == null
//          || "".equals(taskScheduleConfig.getLastUpdateTime())) {
//        taskScheduleConfig.setLastUpdateTime(getInitDateTime());
//
//        taskService.saveTaskTaskScheduleConfig(taskScheduleConfig);
//      }
//
//      taskService.newTransaction4SaveLastRunTime(scheduleType, null); // 記錄執行時間, add by xavier on
//                                                                      // 20150830
//
//      Long count;
//
//      params = new HashMap<String, String>();
//      // 設定起始時間
//      params.put("startTime", taskService.getLastUpdateTime(scheduleType));
//      // 設定結束時間
//      params.put("endTime", DateTimeTool.format(new Date()));
//      // 設定升冪
//      params.put("sequence", "ASC");
//      // 新增schedultType参数-------add by lizhi 20150722
//      params.put("scheduleType", scheduleType);
//      // 設定最多取得筆數
//      params.put("limit", String.valueOf(taskScheduleConfig.getMaxReadRow()));
//      // 設定比較的時間欄位
//      params.put("comparisonCol", "jdp_modified");
//
//      // 設定是哪個排程執行的
//      params.put("schedule_type", scheduleType);
//
//      /*
//       * TODO for 创元2016.06.10上线测试，其他客户使用时需要注释掉。 add by mowj 20160526
//       */
////      params.put("storeNick", "玛丽黛佳玛莎专卖店"); 
//
//      // 本次區間是否有資料
//      count = taobaoJstSyncApiService.getCountByApi(params, TaobaoApi.tb.trade.getCount);
//      // 如果區間內有資料
//      while (count > 0L) {
//        // 如果區間內資料大於4000
//        if (count > taskScheduleConfig.getMaxReadRow()) {
//          // 搜尋區間內資料
//          String lastDataModified =
//              taobaoJstSyncApiService.getLastDataUpdateTimeByApi(params, TaobaoApi.tb.trade.getList);
//          // 如果在同一秒筆數大於上限4000
//          if (params.get("startTime").equals(lastDataModified)) {
//            // 重置lastAomsordT
//            lastAomsordT = null;
//            while (count > 0) {
//              // 設定id
//              params.put("id", (lastAomsordT != null) ? lastAomsordT.getId() : "0");
//              // 資料儲存，並回傳最後一筆資料
//              lastAomsordT = (AomsordT) taobaoJstSyncApiService.newTransaction4SaveByApi(params,
//                  TaobaoApi.tb.trade.getListById);
//              // 更新一秒內已更新的Id
//              params.put("id", lastAomsordT.getId());
//              // 這一秒內是否還有資料
//              count = taobaoJstSyncApiService.getCountByApi(params, TaobaoApi.tb.trade.getCountById);
//            }
//            // 一秒之內的資料都更新完後，更新lastUpdateTime
//            params.put("startTime", timeAddOneSecond(params.get("startTime")));
//            taskService.updateLastUpdateTime(scheduleType, params.get("startTime"));
//          } else {
//            // 本次搜尋資料未在同一秒發生直接儲存，並回傳最後一筆資料
//            lastAomsordT = (AomsordT) taobaoJstSyncApiService.newTransaction4SaveByApi(params,
//                TaobaoApi.tb.trade.getList);
//            // 更新lastUpdateTime
//            params.put("startTime", lastAomsordT.getJdpModified());
//            taskService.updateLastUpdateTime(scheduleType, params.get("startTime"));
//          }
//        } else {
//          // 本次搜尋資料未大於4000直接儲存，並回傳最後一筆資料
//          lastAomsordT = (AomsordT) taobaoJstSyncApiService.newTransaction4SaveByApi(params,
//              TaobaoApi.tb.trade.getList);
//          // 更新最後時間
//          taskService.updateLastUpdateTime(scheduleType, lastAomsordT.getJdpModified());
//          break;
//        }
//        // 搜尋區間內還有沒有資料
//        count = taobaoJstSyncApiService.getCountByApi(params, TaobaoApi.tb.trade.getCount);
//      }
//    } catch (ParseException e) {
//      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//          "taobaoTbTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
//          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "json数据转换异常", e.getMessage(),
//          scheduleType, "");
//      e.printStackTrace();
//      logger.error("ParseException = {}", e.getMessage());
//      throw e;
//    } catch (ApiException e) {
//      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//          "taobaoTbTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
//          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "TaobaoApi異常", e.getMessage(),
//          scheduleType, "");
//      e.printStackTrace();
//      logger.error("ApiException = {}", e.getMessage());
//      throw e;
//    } catch (SocketTimeoutException e) {
//      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//          "taobaoTbTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
//          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "讀取資料超時", e.getMessage(),
//          taskScheduleConfig.getScheduleType(), "");
//      e.printStackTrace();
//      logger.error("SocketTimeoutException = {}", e.getMessage());
//      throw e;
//    } catch (DataException e) {
//      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//          "taobaoTbTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
//          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "保存数据异常",
//          e.getSQLException().getMessage(), scheduleType, "");
//      e.printStackTrace();
//      logger.error("DataException = {}", e.getSQLException().getMessage());
//      throw e;
//    } catch (Exception e) {
//      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//          "taobaoTbTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
//          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "获取数据异常", e.getMessage(),
//          taskScheduleConfig.getScheduleType(), "");
//      e.printStackTrace();
//      logger.error("Exception = {}", e.getMessage());
//      throw e;
//    }
//    logger.info("定时任务結束--離開--taobaoTbTradeUpdateServiceImpl--淘寶非分銷訂單更新--方法");
//
//    return false;
//  }

  public static void main(String[] args) {
    System.out.println("It is " + DateTimeTool.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
  }

}
