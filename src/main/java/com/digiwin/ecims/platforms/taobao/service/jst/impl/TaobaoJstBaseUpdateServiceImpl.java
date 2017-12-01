package com.digiwin.ecims.platforms.taobao.service.jst.impl;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.enumvar.TaskScheduleConfigRunTypeEnum;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.platforms.taobao.bean.jst.JstParam;
import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoJstApi;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstBaseUpdateService;

@Service
public class TaobaoJstBaseUpdateServiceImpl extends UpdateTask implements TaobaoJstBaseUpdateService {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoJstBaseUpdateServiceImpl.class);
  
  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  @Override
  public <T> boolean timeOutExecute(
      TaobaoJstSyncService taobaoJstSyncService, 
      String scheduleTypeNamePrefix, Class<T> clazz, 
      AbstractTaobaoJstApi getListApi, 
      AbstractTaobaoJstApi getListByIdApi, 
      AbstractTaobaoJstApi getCountApi, 
      AbstractTaobaoJstApi getCountByIdApi, String storeType) throws Exception {
    String url = paramSystemService.getEcApiUrl(EcApiUrlEnum.JST_RDS);
    Date sysDate = new Date();

    StringBuffer scheduleTypeBuffer = new StringBuffer(TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY);
    scheduleTypeBuffer.append(scheduleTypeNamePrefix);
    int prefixLength = scheduleTypeNamePrefix.length();

//  Map<String, String> params = null;
    
    TaskScheduleConfig taskScheduleConfig = null;
    String scheduleType = null;
    
    try {
      T lastModelInstance = null;
      Long count;
      
      List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(storeType);
      String storeId = null;
      if (aomsShopList != null && aomsShopList.size() != 0) {
        for (int i = 0; i < aomsShopList.size(); i++) {
          AomsshopT aomsshopT = aomsShopList.get(i);
          storeId = aomsshopT.getAomsshop001();
          scheduleTypeBuffer.append(OntimeCommonUtil.SCHEDULE_NAME_DELIMITER)
              .append(storeId).append(OntimeCommonUtil.SCHEDULE_NAME_DELIMITER)
              .append(storeType);
          /*
           * 获取定时任务的schedule配置， 此配置内存放了该批次的定时任务的最后更新时间，每笔最大更新条数
           */
          scheduleType = scheduleTypeBuffer.toString();
          logger.info("--定时任务开始--{}--", scheduleType);
          
          taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
          // taskScheduleConfig不存在就產一個新的
          // 如果没有设置更新时间，默认从很久以前开始同步
          if (taskScheduleConfig.getLastUpdateTime() == null
              || "".equals(taskScheduleConfig.getLastUpdateTime())) {
            taskScheduleConfig.setLastUpdateTime(getInitDateTime());
            taskScheduleConfig.setMaxReadRow(2000);
            taskScheduleConfig.setMaxPageSize(50);
            taskScheduleConfig.setStoreId(storeId);
            taskScheduleConfig.setRunType(TaskScheduleConfigRunTypeEnum.PULL.getRunType());

            taskService.saveTaskTaskScheduleConfig(taskScheduleConfig);
          }
          // 记录执行时间
          taskService.newTransaction4SaveLastRunTime(scheduleType, null); 
          logger.info("当前排程:{}, 店铺ID:{}", scheduleType, storeId);
          logger.info("获取同步前的上次同步时间{}", taskScheduleConfig.getLastUpdateTime());
          
          taskScheduleConfig.setEndDateByMonth(sysDate, 12);
          
          // params = new HashMap<String, String>();
          // // 設定起始時間
          // params.put("startTime", taskService.getLastUpdateTime(scheduleType));
          // // 設定結束時間
          // params.put("endTime", DateTimeTool.format(new Date()));
          // // 設定升冪
          // params.put("sequence", "ASC");
          // // 新增schedultType参数-------add by lizhi 20150722
          // params.put("scheduleType", scheduleType);
          // // 設定最多取得筆數
          // params.put("limit", taskScheduleConfig.getMaxReadRow() + "");
          // // 設定比較的時間欄位
          // params.put("comparisonCol", "jdp_modified");
          //
          // // 設定是哪個排程執行的
          // params.put("schedule_type", scheduleType);

          JstParam jstParam = new JstParam();
          // 设定起始时间
          jstParam.setStartTime(taskService.getLastUpdateTime(scheduleType));
          // 设定结束时间
          jstParam.setEndTime(DateTimeTool.getTodayBySecond());
          // 设定排序为升序
          jstParam.setSequence("ASC");
          // 设定排程类型
          jstParam.setScheduleType(scheduleType);
          // 设定最多取得笔数
          jstParam.setLimit(taskScheduleConfig.getMaxReadRow());
          // 设定比较的时间栏位
          jstParam.setComparisonCol(AbstractTaobaoJstApi.COMPARISON_COL_JDP_MODIFIED);
          // 设定店铺昵称
          jstParam.setStoreNick(aomsshopT.getAomsshop009());
          
          // 记录原始数据到日志时会使用
          jstParam.setScheduleType(scheduleType);
          jstParam.setStoreId(storeId);

          PostBean postBean = new PostBean(getCountApi.getApi(), jstParam);
          // 本次區間是否有資料
          // count = taobaoJstSyncApiService.getCountByApi(params, getCountApiEnum.getApi());
          count = taobaoJstSyncService.getCount(url, postBean);

          // 如果區間內有資料
          while (count > 0L) {
            // 如果區間內資料大於上限
            if (count > taskScheduleConfig.getMaxReadRow()) {
              // 搜寻区间内资料，重新设定API
              postBean.setApi(getListApi.getApi());
              String lastDataModified =
                  // taobaoJstSyncApiService.getLastDataUpdateTimeByApi(params, getListApiEnum.getApi());
                  taobaoJstSyncService.getLastDataUpdateTimeByApi(url, postBean);
              // 如果在同一秒筆數大於上限
              if (jstParam.getStartTime().equals(lastDataModified)) {
                // 重置lastModelInstance临时变量
                lastModelInstance = null;
                while (count > 0) {
                  // 设定ID参数
                  jstParam.setId((lastModelInstance != null)
                      ? CommonUtil.getId(lastModelInstance, clazz) : "0");
                  // params.put("id", (lastModelInstance != null) ?
                  // CommonUtil.getId(lastModelInstance, clazz) : "0");
                  // 以ID为排序取资料。重新设定API
                  postBean.setApi(getListByIdApi.getApi());
                  // lastModelInstance = (T)
                  // taobaoJstSyncApiService.newTransaction4SaveByApi(params,
                  // getListByIdApi.getApi(), clazz);
                  lastModelInstance = taobaoJstSyncService.saveWithGetListById(url, postBean, clazz);
                  // 更新一秒內资料中最大的Id
                  // params.put("id", CommonUtil.getId(lastModelInstance, clazz));
                  jstParam.setId(CommonUtil.getId(lastModelInstance, clazz));
                  // 這一秒內是否還有資料
                  // count = taobaoJstSyncApiService.getCountByApi(params,
                  // getCountByIdApi.getApi());
                  postBean.setApi(getCountByIdApi.getApi());
                  count = taobaoJstSyncService.getCountById(url, postBean);
                }
                // 一秒之內的資料都更新完後，更新lastUpdateTime
                // params.put("startTime", timeAddOneSecond(params.get("startTime")));
                // taskService.updateLastUpdateTime(scheduleType, params.get("startTime"));
                jstParam.setStartTime(timeAddOneSecond(jstParam.getStartTime()));
                taskService.updateLastUpdateTime(scheduleType, jstParam.getStartTime());
              } else {
                // 本次搜尋資料未在同一秒發生直接儲存，並回傳最後一筆資料
                // lastModelInstance = (T) taobaoJstSyncApiService.newTransaction4SaveByApi(params,
                // getListApi.getApi(), clazz);
                postBean.setApi(getListApi.getApi());
                lastModelInstance = taobaoJstSyncService.saveWithGetList(url, postBean, clazz);
                // 更新lastUpdateTime
                // params.put("startTime", CommonUtil.getJdpModified(lastModelInstance, clazz));
                // taskService.updateLastUpdateTime(scheduleType, params.get("startTime"));
                jstParam.setStartTime(CommonUtil.getJdpModified(lastModelInstance, clazz));
                taskService.updateLastUpdateTime(scheduleType, jstParam.getStartTime());
              }
            } else {
              // 本次搜尋資料未大於上限直接儲存，並回傳最後一筆資料
              // lastModelInstance = (T) taobaoJstSyncApiService.newTransaction4SaveByApi(params,
              // getListApi.getApi(), clazz);
              postBean.setApi(getListApi.getApi());
              lastModelInstance = (T) taobaoJstSyncService.saveWithGetList(url, postBean, clazz);
              // 更新最後時間
              taskService.updateLastUpdateTime(scheduleType, 
                  CommonUtil.getJdpModified(lastModelInstance, clazz));
              break;
            }
            // 搜尋區間內還有沒有資料
            // count = taobaoJstSyncApiService.getCountByApi(params, getCountApi.getApi());
            postBean.setApi(getCountApi.getApi());
            count = taobaoJstSyncService.getCount(url, postBean);
          }

          if (scheduleTypeBuffer.length() > 0) {
            scheduleTypeBuffer.delete(prefixLength, scheduleTypeBuffer.length());
          }
        }
      }
    
    } catch (ParseException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoJstBasicUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "json数据转换异常", e.getMessage(),
          scheduleType, "");
      e.printStackTrace();
      logger.error("ParseException = {}", e.getMessage());
      throw e;
    } catch (ApiException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoJstBasicUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "TaobaoApi异常", e.getMessage(),
          scheduleType, "");
      e.printStackTrace();
      logger.error("ApiException = {}", e.getMessage());
      throw e;
    } catch (SocketTimeoutException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoJstBasicUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "读取资料超时", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("SocketTimeoutException = {}", e.getMessage());
      throw e;
    } catch (DataException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoJstBasicUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "保存数据异常",
          e.getSQLException().getMessage(), scheduleType, "");
      e.printStackTrace();
      logger.error("DataException = {}", e.getSQLException().getMessage());
      throw e;
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoJstBasicUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "获取数据异常", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      throw e;
    }
    logger.info("--定时任务结束--{}--", scheduleType);

    return false;
  }

}
