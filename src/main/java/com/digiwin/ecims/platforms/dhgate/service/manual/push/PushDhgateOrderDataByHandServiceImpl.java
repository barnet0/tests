package com.digiwin.ecims.platforms.dhgate.service.manual.push;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.impl.BasePostServiceImpl;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;

@Service("pushDhgateOrderDataByHandServiceImpl")
public class PushDhgateOrderDataByHandServiceImpl implements PushOrderDataByHandService {

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
    // TODO Auto-generated method stub
    return null;
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
