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
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoFxRefund;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.jst.AbstractTaobaoJstSyncService;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncFxRefundsService;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpFxRefundService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJstSyncFxRefundsServiceImpl extends AbstractTaobaoJstSyncService implements TaobaoJstSyncFxRefundsService {

  @Autowired
  private BaseDAO baseDAO;

  @Autowired
  private TaobaoJdpFxRefundService tableJdpFxRefund;

  // 将更新lastupdatetime的操作移入到与保存操作在同一个事务中
  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public <T> T saveWithGetDetail(String url, PostBean jstPostBean, Class<T> clazz)
      throws ApiException, IOException {
    throw new UnsupportedOperationException();
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

    if (AbstractTaobaoFxRefund.isApiInScope(api)) {
      loginfoOperateService.newTransaction4SaveSource(startTime, endTime,
          TaobaoCommonTool.STORE_TYPE_FX, api, result, SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(),
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
    return tableJdpFxRefund.parseTaobaoResToAomsrefundT(taobaoRes, storeId);
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
