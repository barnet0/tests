package com.digiwin.ecims.platforms.taobao.service.jst.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbTrade;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.jst.AbstractTaobaoJstSyncService;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncTbOrdersService;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpTbTradeService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJstSyncTbOrdersServiceImpl extends AbstractTaobaoJstSyncService implements TaobaoJstSyncTbOrdersService {

  @Autowired
  private BaseDAO baseDAO;

  @Autowired
  private TaobaoJdpTbTradeService tableJdpTbTrade;

  // 将更新lastupdatetime的操作移入到与保存操作在同一个事务中
  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Override
  public <T> T saveWithGetDetail(String url, PostBean jstPostBean, Class<T> clazz)
      throws ApiException, IOException {
    if (jstPostBean.getApi().equals(GET_DETAIL_API.getApi())) {
      return this.newTransaction4SaveByApi(url, jstPostBean, clazz);
    }
    return null;
  }

  @Override
  public <T> T saveWithGetList(String url, PostBean jstPostBean, Class<T> clazz)
      throws ApiException, IOException {
    if (jstPostBean.getApi().equals(GET_LIST_API.getApi())) {
      return this.newTransaction4SaveByApi(url, jstPostBean, clazz);
    }
    return null;
  }

  @Override
  public <T> T saveWithGetListById(String url, PostBean jstPostBean, Class<T> clazz)
      throws ApiException, IOException {
    if (jstPostBean.getApi().equals(GET_LIST_BY_ID_API.getApi())) {
      return this.newTransaction4SaveByApi(url, jstPostBean, clazz);
    }
    return null;
  }


//  private <T> T newTransaction4SaveByApi(String url, PostBean jstPostData, String api, Class<T> clazz)
//      throws ApiException, IOException, NullPointerException {
//    baseDAO.clear();
//    String result = getResponseFromJst(url, jstPostData, api);
//    if (result.equals("")) {
//      return null;
//    }
//
//    List<Object> datas = new ArrayList<Object>();
//    String[] jsonAry = result.split(PostBean.SPLITTER);
//
//    final Class<TaobaoRes> taobaoResClass = TaobaoRes.class;
////    if (api.equals(TaobaoApi.tb.trade.getList) || api.equals(TaobaoApi.tb.trade.getListById)) {
//    for (String json : jsonAry) {
//      TaobaoRes taobaoRes = JsonUtil.jsonToObject(json, taobaoResClass);
//      datas.addAll(tableJdpTbTrade.parseTaobaoResToAomsordT(taobaoRes));
//    }
//    
//    baseDAO.saveOrUpdateByCollectionWithLog(datas);
//    
//    T lastObject = (T) datas.get(datas.size() - 1);
//    String scheduleType = jstPostData.getParams().getScheduleType();
//    if (scheduleType == null) {
//      return lastObject;
//    }
//    taskService.updateLastUpdateTime(scheduleType, CommonUtil.getJdpModified(lastObject, clazz));
//    
//    return lastObject;
//  }


  @Override
  public Long getCount(String url, PostBean jstPostData) throws IOException {
    if (jstPostData.getApi().equals(GET_COUNT_API.getApi())) {
      return getCountByApi(url, jstPostData);
    }
    return TaobaoCommonTool.NO_COUNT_RETURNED;
  }

  @Override
  public Long getCountById(String url, PostBean jstPostData) throws IOException {
    if (jstPostData.getApi().equals(GET_COUNT_BY_ID_API.getApi())) {
      return getCountByApi(url, jstPostData);
    }
    return TaobaoCommonTool.NO_COUNT_RETURNED;
  }

  @Override
  protected void saveJstSourceToLog(PostBean jstPostData, String api, String result) {
    String startTime = jstPostData.getParams().getStartTime() == null ? 
        "N/A" : jstPostData.getParams().getStartTime();
    String endTime = jstPostData.getParams().getEndTime() == null ? 
        "N/A" : jstPostData.getParams().getEndTime();

    String storeId = jstPostData.getParams().getStoreId();
    String scheduleType = jstPostData.getParams().getScheduleType();

    if (AbstractTaobaoTbTrade.isApiInScope(api)) {
      loginfoOperateService.newTransaction4SaveSource(startTime, endTime,
          TaobaoCommonTool.STORE_TYPE, api, result, SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, scheduleType);
    }
    
  }

  @Override
  protected void executeClear() {
    baseDAO.clear();
  }

  @Override
  protected List<? extends Object> parseTaobaojstRes(TaobaoRes taobaoRes, String storeId) 
      throws ApiException, IOException {
    return tableJdpTbTrade.parseTaobaoResToAomsordT(taobaoRes, storeId);
  }

  @Override
  protected <T> void saveData(List<T> datas) {
    baseDAO.saveOrUpdateByCollectionWithLog(datas);
  }

  @Override
  protected void updateLastUpdateTime(String scheduleType, String latestJdpModified) {
    taskService.updateLastUpdateTime(scheduleType, latestJdpModified);
  }

}
