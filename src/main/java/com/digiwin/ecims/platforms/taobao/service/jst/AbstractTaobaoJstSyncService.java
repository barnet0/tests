package com.digiwin.ecims.platforms.taobao.service.jst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade.TaobaoTbTradeGetDetail;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

public abstract class AbstractTaobaoJstSyncService implements TaobaoJstSyncService {

  protected Logger logger = LoggerFactory.getLogger(AbstractTaobaoJstSyncService.class);
  
  /**
   * 从聚石塔获取响应数据
   * @param url
   * @param jstPostData
   * @return
   * @throws IOException
   */
  protected String getResponseFromJst(String url, PostBean jstPostData)
      throws IOException {
    String result =
        HttpPostUtils.getInstance().send_common_bytearray(
            JsonUtil.format(jstPostData), jstPostData.getApi(), url);
    // 保存原始数据到日志
    saveJstSourceToLog(jstPostData, jstPostData.getApi(), result);
    
    return result;
  }
  
  /**
   * 根据API，返回指定参数区间内的资料数量
   * @param url
   * @param jstPostData
   * @return
   * @throws IOException
   */
  protected Long getCountByApi(String url, PostBean jstPostData)
      throws IOException {
    String result = getResponseFromJst(url, jstPostData);

    return StringTool.isEmpty(result) ? 
        TaobaoCommonTool.NO_COUNT_RETURNED : Long.parseLong(result);
  }
  
  @Override
  public String getLastDataUpdateTimeByApi(String url, PostBean jstPostData)
      throws ApiException, IOException {
    String result = getResponseFromJst(url, jstPostData);
    if (StringTool.isEmpty(result)) {
      return result;
    }

    String[] jsonArray = result.split(PostBean.SPLITTER);

    TaobaoRes taobaoRes = JsonUtil.jsonToObject(jsonArray[jsonArray.length - 1], TaobaoRes.class);
    
    return taobaoRes.getComparisonCol();
  }

  /**
   * 保存聚石塔原始资料到日志
   * @param jstPostData
   * @param api
   * @param result
   */
  protected abstract void saveJstSourceToLog(PostBean jstPostData, String api, String result);
  
  /**
   * 清除缓存
   */
  protected abstract void executeClear();
  
  /**
   * 将从聚石塔取回的资料转换为相应的电商资料
   * @param taobaoRes
   * @param storeId
   * @return
   * @throws ApiException
   * @throws IOException
   */
  protected abstract List<? extends Object> parseTaobaojstRes(TaobaoRes taobaoRes, String storeId) 
      throws ApiException, IOException;

  /**
   * 保存资料
   * @param datas
   */
  protected abstract <T> void saveData(List<T> datas);
  
  /**
   * 更新对应排程的最大更新时间
   * @param scheduleType
   * @param latestJdpModified
   */
  protected abstract void updateLastUpdateTime(String scheduleType, String latestJdpModified);
  
  protected final <T> T newTransaction4SaveByApi(
      String url, PostBean jstPostData, Class<T> clazz)
      throws ApiException, IOException {
//    baseDAO.clear();
    logger.info("清理hibernate会话缓存...");
    executeClear();
    logger.info("获取聚石塔内RDS资料...url: {}, 请求数据: {}", url, JsonUtil.format(jstPostData));
    String result = getResponseFromJst(url, jstPostData);
    if (result.equals("")) {
      logger.info("聚石塔内RDS无相应资料...结束...");
      return null;
    }

    List<Object> datas = new ArrayList<Object>();
    logger.info("解析返回资料...");
    String[] jsonAry = result.split(PostBean.SPLITTER);
    logger.info("返回{}笔资料...", jsonAry.length);
    logger.info("开始反序列化...");
    final Class<TaobaoRes> taobaoResClass = TaobaoRes.class;
//    if (api.equals(TaobaoApi.tb.trade.getList) || api.equals(TaobaoApi.tb.trade.getListById)) {
    long i = 0l;
    for (String json : jsonAry) {
      logger.info("第{}次反序列化开始...", (i + 1));
      TaobaoRes taobaoRes = null;
      // modify on 20161117
      if (jstPostData.getApi().toLowerCase().contains("detail")) {
        taobaoRes = new TaobaoRes(json);
      } else {
        taobaoRes = JsonUtil.jsonToObject(json, taobaoResClass);
      }
//      datas.addAll(tableJdpTbTrade.parseTaobaoResToAomsordT(taobaoRes));
      datas.addAll(parseTaobaojstRes(taobaoRes, jstPostData.getParams().getStoreId()));
      logger.info("反序列化成功...");
      i++;
    }
    
//    baseDAO.saveOrUpdateByCollectionWithLog(datas);
    logger.info("保存资料...");
    saveData(datas);
    
    logger.info("获取修改时间最大的资料...");
    T lastObject = (T) datas.get(datas.size() - 1);
    String scheduleType = jstPostData.getParams().getScheduleType();
    if (scheduleType == null) {
      return lastObject;
    }
//    taskService.updateLastUpdateTime(scheduleType, CommonUtil.getJdpModified(lastObject, clazz));
    String lastUpdateTime = CommonUtil.getJdpModified(lastObject, clazz);
    logger.info("准备更新排程lastUpdateTime到{}", lastUpdateTime);
    updateLastUpdateTime(scheduleType, lastUpdateTime);
    
    logger.info("获取聚石塔内RDS资料...成功...");
    
    return lastObject;
  }
  
}
