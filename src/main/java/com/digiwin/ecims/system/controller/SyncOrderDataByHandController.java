package com.digiwin.ecims.system.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.ontime.service.SchedulerFactoryIntf;
import com.digiwin.ecims.system.bean.ParamBean;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;

@Controller
@RequestMapping("/order")
public class SyncOrderDataByHandController {

  @Autowired
  LoginfoOperateService interceptorService;

  @Autowired
  SchedulerFactoryIntf schedulerFactoryIntf;

  @Resource(name = "syncTaoBaoTbOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncTaoBaoTbOrderDataByHandServiceImpl;
  
  @Resource(name = "syncTaoBaoFxOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncTaoBaoFxOrderDataByHandServiceImpl;
  
  @Resource(name = "syncJingDongOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncJingDongOrderDataByHandServiceImpl;
  
  @Resource(name = "syncYhdOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncYhdOrderDataByHandServiceImpl;
  
  @Resource(name = "syncICBCOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncICBCOrderDataByHandServiceImpl;
  
  @Resource(name = "syncSuningOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncSuningOrderDataByHandServiceImpl;

  @Resource(name = "syncDangDangOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncDangDangOrderDataByHandServiceImpl;
  
  @Resource(name = "syncBaiduOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncBaiduOrderDataByHandServiceImpl;

  // --------------------  
  @Resource(name = "pushTaoBaoTbOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushTaoBaoTbOrderDataByHandServiceImpl;
  
  @Resource(name = "pushTaoBaoFxOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushTaoBaoFxOrderDataByHandServiceImpl;
  
  @Resource(name = "pushJingDongOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushJingDongOrderDataByHandServiceImpl;
  
  @Resource(name = "pushYhdOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushYhdOrderDataByHandServiceImpl;
  
  @Resource(name = "pushICBCOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushICBCOrderDataByHandServiceImpl;
  
  @Resource(name = "pushSuningOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushSuningOrderDataByHandServiceImpl;
  
  @Resource(name = "pushDangDangOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushDangDangOrderDataByHandServiceImpl;
  
  @Resource(name = "pushBaiduOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushBaiduOrderDataByHandServiceImpl;
  
  @Resource(name = "pushPddOrderDataByHandServiceImpl")   //2017/06/29 by cjp 
  private PushOrderDataByHandService pushPddOrderDataByHandServiceImpl;
  
  @Resource(name = "syncPddOrderDataByHandServiceImpl")   //2017/06/29 by cjp 
  private SyncOrderDataByHandService syncPddOrderDataByHandServiceImpl;

  /**
   * 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊,E:网易考拉,J:拼多多
   * 
   * @param storeType
   * @param conditionType
   * @param startDate
   * @param endTime
   * @return
   */
  @RequestMapping("/sync.do")
  @ResponseBody
  public String syncOrderDataByHand(String storeId, String storeType, int conditionType,
      String orderId, String startDate, String endDate) {
    ParamBean paramBean = new ParamBean("MANUAL-SYNC", "syncOrderDataByHand()", storeId, storeType,
        conditionType, orderId, startDate, endDate, new Date());
    SyncOrderDataByHandService syncOrderDataSercie = null;
    switch (storeType) {
      case "A":
        syncOrderDataSercie = syncTaoBaoFxOrderDataByHandServiceImpl;// A-淘宝fx---------------
        break;
      case "0":
        syncOrderDataSercie = syncTaoBaoTbOrderDataByHandServiceImpl;// 0-淘宝---------------
        break;
      case "1":
        syncOrderDataSercie = syncJingDongOrderDataByHandServiceImpl;// 1:京东
        break;
      case "2":
        syncOrderDataSercie = syncYhdOrderDataByHandServiceImpl;// 2:一号店
        break;
      case "3":
        syncOrderDataSercie = syncICBCOrderDataByHandServiceImpl;// 3:工行
        break;
      case "4":
        syncOrderDataSercie = syncSuningOrderDataByHandServiceImpl;// 4:苏宁
        break;
      case "5":
        syncOrderDataSercie = syncDangDangOrderDataByHandServiceImpl;// ,5:当当
        break;
      case "E":
        syncOrderDataSercie = syncBaiduOrderDataByHandServiceImpl;
        break;
      case "J":
          syncOrderDataSercie = syncPddOrderDataByHandServiceImpl;   // 2017/06/30 J:拼多多 by cjp
        break;
      default:
    	  return "{isSuccess:'0',resultSize:0,errCode:'0',errMsg:'无此平台编号'}";  //2017/07/30 by cjp

    }
    String resultMsg = null;
    switch (conditionType) {
      case 1:
        resultMsg = syncOrderDataSercie.syncOrderDataByCreateDate(storeId, startDate, endDate);// 1:按新增时间查询
        break;
      case 2:
        resultMsg = syncOrderDataSercie.syncOrderDataByModifyDate(storeId, startDate, endDate);// 1:按修改时间查询
        break;
      case 3:
        resultMsg = syncOrderDataSercie.syncOrderDataByOrderId(storeId, orderId);// 2:按订单号查询
        break;
      default:
        resultMsg = "{isSuccess:'0',resultSize:0,errCode:'0',errMsg:'无此查询类型'}";
    }
    // syncOrderDataSercie.syncOrderDataByCondition(conditionType, orderId,
    // startDate, endDate);

    try {
      interceptorService.saveManualSyncPushLog(schedulerFactoryIntf.getRunIP(), paramBean,
          resultMsg, new Date());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return resultMsg;
  }

  /**
   * 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
   * 
   * @param storeType
   * @param conditionType
   * @param startDate
   * @param endTime
   * @return
   */
  @RequestMapping("/push.do")
  @ResponseBody
  public String pushOrderDataByHand(String storeId, String storeType, int conditionType,
      String orderId, String startDate, String endDate) {
    ParamBean paramBean = new ParamBean("MANUAL-PUSH", "pushOrderDataByHand()", storeId, storeType,
        conditionType, orderId, startDate, endDate, new Date());
    PushOrderDataByHandService pushOrderDataService = null;
    switch (storeType) {
      case "A":
        pushOrderDataService = pushTaoBaoFxOrderDataByHandServiceImpl;// A-淘宝fx---------------
        break;
      case "0":
        pushOrderDataService = pushTaoBaoTbOrderDataByHandServiceImpl;// 0-淘宝---------------
        break;
      case "1":
        pushOrderDataService = pushJingDongOrderDataByHandServiceImpl;// 1:京东
        break;
      case "2":
        pushOrderDataService = pushYhdOrderDataByHandServiceImpl;// 2:一号店
        break;
      case "3":
        pushOrderDataService = pushICBCOrderDataByHandServiceImpl;// 3:工行
        break;
      case "4":
        pushOrderDataService = pushSuningOrderDataByHandServiceImpl;// 4:苏宁
        break;
      case "5":
        pushOrderDataService = pushDangDangOrderDataByHandServiceImpl;// ,5:当当
        break;
      case "E":
        pushOrderDataService = pushBaiduOrderDataByHandServiceImpl;
        break;
      case "J":
          pushOrderDataService = pushPddOrderDataByHandServiceImpl;
          break;
      default:
        return "{isSuccess:'0',resultSize:0,errCode:'0',errMsg:'无此平台编号'}";
    }

    String resultMsg = null;
    switch (conditionType) {
      case 1:
        resultMsg = pushOrderDataService.pushOrderDataByCreateDate(storeId, startDate, endDate);// 1:按新增时间查询
        break;
      case 2:
        resultMsg = pushOrderDataService.pushOrderDataByModifyDate(storeId, startDate, endDate);// 1:按修改时间查询
        break;
      case 3:
        resultMsg = pushOrderDataService.pushOrderDataByOrderId(storeId, orderId);// 2:按订单号查询
        break;
      case 4:
        resultMsg = pushOrderDataService.pushOrderDataByPayDate(storeId, startDate, endDate);
        break;
      default:
        resultMsg = "{isSuccess:'0',resultSize:0,errCode:'0',errMsg:'无此查询类型'}";
    }
    // pushOrderDataSercie.pushOrderDataByCondition(conditionType, orderId,
    // startDate, endDate);

    try {
      interceptorService.saveManualSyncPushLog(schedulerFactoryIntf.getRunIP(), paramBean,
          resultMsg, new Date());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return resultMsg;
  }

  // public void doSaveLog(ParamBean bean, String resultMsg, Date resTime)
  // throws Exception{
  // LogInfoT loginfoT = new LogInfoT();
  // loginfoT.setIpAddress(schedulerFactoryIntf.getRunIP());
  // loginfoT.setCallMethod(bean.getCallMethod());
  // loginfoT.setBusinessType(bean.getProcessType());
  // loginfoT.setReqType("manual");
  // loginfoT.setReqParam(CommonUtil.objectToJsonString(bean));
  // loginfoT.setReqTime(bean.getNowDate());
  // loginfoT.setResTime(resTime);
  // loginfoT.setRemark(resultMsg);
  //
  // SyncResOrderHandBean returnBean =
  // (SyncResOrderHandBean)CommonUtil.jsonToObject(resultMsg,
  // SyncResOrderHandBean.class);
  //
  // loginfoT.setResSize(BigInteger.valueOf(Long.valueOf(returnBean.getResultSize())));
  //
  // if(returnBean.getIsSuccess().equals("0")){//success
  // loginfoT.setIsSuccess(Boolean.TRUE);
  // loginfoT.setFinal_status("1");
  // }else{//faild
  // loginfoT.setIsSuccess(Boolean.FALSE);
  // loginfoT.setFinal_status("0");
  // loginfoT.setResErrCode(returnBean.getErrCode());
  // loginfoT.setResErrMsg(returnBean.getErrMsg());
  // }
  //
  // interceptorService.newTransaction4SaveLog(loginfoT);
  //
  // }
}
