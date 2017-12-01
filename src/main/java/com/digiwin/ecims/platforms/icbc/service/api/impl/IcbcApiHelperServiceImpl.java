package com.digiwin.ecims.platforms.icbc.service.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery.IcbcRefundQueryResponse;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsrefundTTranslator;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiHelperService;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool.RefundStatus;
import com.digiwin.ecims.ontime.service.TaskService;

@Service
public class IcbcApiHelperServiceImpl implements IcbcApiHelperService {

  @Autowired
  private IcbcApiService icbcApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public boolean updateLocalRefunds(String storeType)
      throws Exception {
    
    List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(IcbcCommonTool.STORE_TYPE);
    if (aomsShopList != null && aomsShopList.size() != 0) {
      for (int i = 0; i < aomsShopList.size(); i++) {
        AomsshopT aomsshopT = aomsShopList.get(i);
        
        String storeId = aomsshopT.getAomsshop001();
        String appKey = aomsshopT.getAomsshop004();
        String appSecret = aomsshopT.getAomsshop005();
        String accessToken = aomsshopT.getAomsshop006();
        
        // 从DB中取出退款状态不为8-退款完成, 9-拒绝退款, 10-退款关闭的退款单
        List<AomsrefundT> aomsrefundTs = taskService.executeQueryByHql(
            "FROM AomsrefundT t " 
                + " WHERE t.storeType = '" + IcbcCommonTool.STORE_TYPE + "'"
                + " AND t.storeId = '" + storeId + "'"
                + " AND t.aoms037 != '" + RefundStatus.SUCCESS.getStatus() + "'"
                + " AND t.aoms037 != '" + RefundStatus.REFUSED.getStatus() + "'"
                + " AND t.aoms037 != '" + RefundStatus.CLOSED.getStatus() + "'");

     // 如果资料有修改过，设定修改时间
        String modiDateStr = DateTimeTool.getTodayBySecond();
        
        for (AomsrefundT aomsrefundT : aomsrefundTs) {
          IcbcRefundQueryResponse response = icbcApiService
              .icbcb2cRefundQuery(appKey, appSecret, accessToken, null, null, 
                  null, aomsrefundT.getAoms024());
          
          boolean success = response.getHead().getReturnMsg().equals("OK");
          if (success) {
            List<AomsrefundT> icbcRefunds =
                new IcbcAomsrefundTTranslator(response, aomsrefundT.getStoreId()).doTranslate();
            if (icbcRefunds != null) { 
              for (AomsrefundT refund : icbcRefunds) {
                refund.setModified(modiDateStr);
              }
              taskService.newTransaction4Save(icbcRefunds, true);
            }
          } // end-if
        } // end-for
      } // end-for
    } // end-if
    
    return true;
  }

}
