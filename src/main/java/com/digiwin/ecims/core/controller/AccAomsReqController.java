package com.digiwin.ecims.core.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.open.api.sdk.JdException;
import com.taobao.api.ApiException;

import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.CmdService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.JsonUtil;

/**
 * 統一入口接入，專門用於接受請求
 * 
 */
@Controller
@RequestMapping("/AomsRequestInf")
public class AccAomsReqController {
  private static final Logger logger = LoggerFactory.getLogger(AccAomsReqController.class);
  @Autowired
  CmdService cmdService;

  public static final String DEFAULT_CHARSET = "UTF-8";

  /**
   * OMS對象接口，URI = /AomsRequestInf/DoRequestInf.do
   * 
   * @param request
   * @param response
   * @return response String json
   */
  @RequestMapping(value = "/DoRequestInf.do")
  @ResponseBody
  public String doAccAomsRequest(HttpServletRequest request, HttpServletResponse response) {
	  
	  System.out.println("---------------------------");
	  
    String jsonString = null;
    // try {
    // } catch (IllegalAccessException | InvocationTargetException | IOException e) {
    // ResponseError resError = new ResponseError("_132", ErrorMessageBox._132 +
    // e.getMessage().toString());
    // logger.error("解析JSON出錯:" + e.getMessage());
    // return "{\"error\":" + CommonUtil.objectToJsonString(resError) + "}";
    // }

    // 做分派 cmd or query or cmd+query
    // jsonString=CommonUtil.getPostData(request);
    jsonString = (String) request.getAttribute("body");
    String type = request.getAttribute("reqType").toString();
    String resJson;
    try {
      resJson = doJsonSwitch(jsonString, type, request);
      request.setAttribute("res", resJson);

      // cmd response return
      if (type.equals("cmd")) {
        resJson = "{\"cmdRes\":" + resJson + "}";
      }
      return resJson;

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      ResponseError resError =
          new ResponseError("_132", ErrorMessageBox._132 + e.getMessage().toString());
      logger.error("解析JSON出錯:{}", e.getMessage());
      return "{\"error\":" + JsonUtil.format(resError) + "}";
    }
  }

  /**
   * 根據request Json分派service cmd or query or cmd+query
   * 
   * @param json 從request拿到的json格式的requestBody
   * @return 回傳 response json 格式
   * @throws Exception
   * @throws JdException
   * @throws ApiException
   */
  private String doJsonSwitch(String json, String type, HttpServletRequest request)
      throws ApiException, JdException, Exception {
    if (json != null && type.equals("cmd")) {
      // cmd的結果回傳 json格式
      // modi by mowj 20150906
      List<CmdRes> cmdresList = cmdService.start(json);
      String cmdResString = JsonUtil.format(cmdresList);
      //
      return cmdResString;
    } else {
      // _000 json 不能為空
      String responseJson = JsonUtil.format(CommonUtil.responseErrorJson("_000"));
      logger.warn("验证出錯:json不能為空");
      return "{\"error\":" + responseJson + "}";
    }
  }
}
