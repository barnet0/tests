package com.digiwin.ecims.core.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.request.CmdReqHelper;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

/**
 * LogInterceptor 负责记录日志。 <br>
 * 
 * @author <a href="mailto:senton1101@gmail.com">TianXiangdong</a> at 2010-10-4 下午02:24:51
 * @version 1.0
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  LoginfoOperateService interceptorService;

  // private Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  AomsShopService aomsshopService; // add by mowj 20160215

  // private LogInfoT logInfo=null;

  /**
   * 請求進來時會先跑這方法
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object target)
      throws Exception {
    LogInfoT logInfo = null;
    List<Object> objList = null;

    if (request.getServletPath().equals("/AomsRequestInf/DoRequestInf.do")) {
      String json = CommonUtil.getPostData(request);
      if (json == null || json.length() == 0) {
        return false;
      } else {
        request.setAttribute("body", json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);

        if (maps.containsKey("cmd")) {

          CmdReq[] cmdReqArr = CmdReqHelper.getInstance().doParse(json);
          objList = new ArrayList<Object>();
          for (CmdReq cmdReq : cmdReqArr) {
            logInfo = new LogInfoT(request.getRemoteAddr(), request.getRequestURL().toString(),
                LogInfoBizTypeEnum.OMS_REQUEST.getValueString(),
                "cmd - 平台代号：" + cmdReq.getEcno() +
                (StringTool.isNotEmpty(cmdReq.getStoreid()) ? 
                " - " + aomsshopService.getStoreByStoreId(cmdReq.getStoreid()).getAomsshop011()
                : StringTool.EMPTY),
                        JsonUtil.format(cmdReq), new Date(), cmdReq.getEcno(),
                cmdReq.getStoreid());
            objList.add(logInfo);
          }
          request.setAttribute("body", json);
          request.setAttribute("logObj", objList);
          request.setAttribute("reqType", "cmd");

        } else {

          // 如果不是cmd
          logInfo = new LogInfoT(request.getRemoteAddr(), request.getRequestURL().toString(),
              "OMS-REQUEST", "error", json, new Date());
          request.setAttribute("logObj", logInfo);
          request.setAttribute("reqType", "error");

        }
      }

    }


    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http
   * .HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object,
   * org.springframework.web.servlet.ModelAndView)
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    // TODO Auto-generated method stub
    super.postHandle(request, response, handler, modelAndView);

    // System.out.println();
  }



  /**
   * 整個回應完後會到這方法
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

    if (request.getServletPath().equals("/AomsRequestInf/DoRequestInf.do")) {
      interceptorService.saveLog(request, ex);
    }
  }


}
