package com.digiwin.ecims.platforms.aliexpress.service.manual.push;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;

@Service("pushAliexpressOrderDataByHandServiceImpl")
public class PushAliexpressOrderDataByHandServiceImpl implements PushOrderDataByHandService {

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private BasePostService basePostService;
  
  @Override
  public String pushOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByOrderId(String storeId, String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByPayDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderDataCountFromDBByCreateDate(String storeId, String startDate,
      String endDate) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", "AomsordT");
    params.put("checkCol", "aoms006");
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("storeType", AliexpressCommonTool.STORE_TYPE);
    params.put("storeId", storeId);
    Long count = taskService.getSelectPojoCount(params);
    return count;
  }

  @Override
  public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params,
      Class<T> clazz) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

}
