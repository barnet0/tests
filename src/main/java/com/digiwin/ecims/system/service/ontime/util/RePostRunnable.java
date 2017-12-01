package com.digiwin.ecims.system.service.ontime.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.PostDataObject;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.util.MySpringContext;

public class RePostRunnable<T> implements Runnable {

  private LoginfoOperateService loginfoOperateService =
      MySpringContext.getContext().getBean(LoginfoOperateService.class);

  private static final Logger logger = LoggerFactory.getLogger(RePostRunnable.class);

  private String postUrl = null;

  private List<List<T>> postDatalLists;

  private TaskScheduleConfig taskScheduleConfig;

  private Map<String, Object> invoiceIdMappingLogIdMap = null;

  private int maxRow;

  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    logger.info("线程启动开始推送‧");

    String result = null;
    List<PostDataObject<T>> postDataObjects = transformPostDatas();
    logger.info("本次要推送的資料共{}組", postDataObjects.size());
    postUrl = taskScheduleConfig.getPostUrl();
    for (PostDataObject<T> postDataObject : postDataObjects) {
      Date startDate = new Date();
      logger.info("執行續啟動開始推送第{}組", postDataObjects.indexOf(postDataObject));
      result = doPost(postDataObject);
      Map<String, String> resultMaps = null;
      try {
        resultMaps = new ObjectMapper().readValue(result, Map.class);
      } catch (Exception e) {
        resultMaps.put("result", "fail");
        resultMaps.put("error", e.getMessage());
        e.printStackTrace();
      } finally {
        Date endDate = new Date();
        if (resultMaps.get("result").equals("fail")) {
          saveFailLogInfoT(resultMaps, postDataObject, startDate, endDate);
          logger.error("第{}組推送失敗‧", postDataObjects.indexOf(postDataObject));
        } else {
          saveSuccessLogInfoT(postDataObject, startDate, endDate);
        }
        logger.info("第{}組推送成功‧", postDataObjects.indexOf(postDataObject));
      }
    }
    logger.info("執行續推送完畢‧");

  }

  private void saveSuccessLogInfoT(PostDataObject<T> postDataObject, Date startDate, Date endDate) {
    List<LogInfoT> logInfoTs = new ArrayList<LogInfoT>();
    List<T> data = postDataObject.getData();
    LogInfoT logInfoT = null;
    for (T t : data) {
      if (t instanceof AomsordT) {
        AomsordT aomsordT = (AomsordT) t;
        logInfoT = (LogInfoT) getInvoiceIdMappingLogIdMap().get(aomsordT.getId());
      } else if (t instanceof AomsitemT) {
        AomsitemT aomsitemT = (AomsitemT) t;
        logInfoT = (LogInfoT) getInvoiceIdMappingLogIdMap().get(aomsitemT.getId());
      } else {
        AomsrefundT aomsrefundT = (AomsrefundT) t;
        logInfoT = (LogInfoT) getInvoiceIdMappingLogIdMap().get(aomsrefundT.getId());
      }

      if (!logInfoTs.contains(logInfoT)) {
        logInfoT.setReqTime(startDate);
        logInfoT.setResTime(endDate);
        logInfoT.setPushLimits(logInfoT.getPushLimits() - 1);
        logInfoT.setFinalStatus("1");
        logInfoTs.add(logInfoT);
      }

    }
    loginfoOperateService.newTransaction4UpdateLogByCollection(logInfoTs);
  }

  private void saveFailLogInfoT(Map<String, String> resultMaps, PostDataObject<T> postDataObject, 
      Date startDate, Date endDate) {
    List<LogInfoT> logInfoTs = new ArrayList<LogInfoT>();
    List<T> data = postDataObject.getData();
    LogInfoT logInfoT = null;
    for (Object object : data) {
      if (object instanceof AomsordT) {
        AomsordT aomsordT = (AomsordT) object;
        logInfoT = (LogInfoT) getInvoiceIdMappingLogIdMap().get(aomsordT.getId());
      } else if (object instanceof AomsitemT) {
        AomsitemT aomsitemT = (AomsitemT) object;
        logInfoT = (LogInfoT) getInvoiceIdMappingLogIdMap().get(aomsitemT.getId());
      } else {
        AomsrefundT aomsrefundT = (AomsrefundT) object;
        logInfoT = (LogInfoT) getInvoiceIdMappingLogIdMap().get(aomsrefundT.getId());
      }

      if (!logInfoTs.contains(logInfoT)) {
        logInfoT.setReqTime(startDate);
        logInfoT.setResTime(endDate);
        logInfoT.setResCode("fail");
        logInfoT.setResMsg(resultMaps.get("error") == null ? "錯誤未返回" : resultMaps.get("error"));
        logInfoT.setPushLimits(logInfoT.getPushLimits() - 1);
        logInfoTs.add(logInfoT);
      }

    }
    loginfoOperateService.newTransaction4UpdateLogByCollection(logInfoTs);
  }

  private List<PostDataObject<T>> transformPostDatas() {
    List<PostDataObject<T>> postDataObjects = new ArrayList<PostDataObject<T>>();
    int postDataSize = postDatalLists.size();
    int pageCount = postDataSize % maxRow == 0 ? postDataSize / maxRow : postDataSize / maxRow + 1;
    // 平均分配單據
    for (int index = 0; index < pageCount; index++) {

      List<List<T>> postDataPojo = postDatalLists.subList(index * maxRow,
          (index + 1) == pageCount ? postDatalLists.size() : (index + 1) * maxRow);

      List<T> objects = new ArrayList<T>();

      for (List<T> list : postDataPojo) {
        objects.addAll(list);
      }
      PostDataObject<T> postDataObject = new PostDataObject<T>();
      postDataObject.setPlant(taskScheduleConfig.getPlant());
      postDataObject.setService(taskScheduleConfig.getService());
      postDataObject.setTotalRecord(objects.size());
      postDataObject.setUser(taskScheduleConfig.getUser());
      postDataObject.setEntId(taskScheduleConfig.getEntId());
      postDataObject.setCompanyId(taskScheduleConfig.getCompanyId());
      postDataObject.setObjects(objects);
      postDataObjects.add(postDataObject);
    }

    return postDataObjects;
  }

  public void addPostDatalLists(List<List<T>> postDatalLists) {
    this.postDatalLists = postDatalLists;
  }

  // public TaskSchedulePostConfig getTaskSchedulePostConfig() {
  // return taskSchedulePostConfig;
  // }
  //
  // public void setTaskSchedulePostConfig(TaskSchedulePostConfig taskSchedulePostConfig) {
  // this.taskSchedulePostConfig = taskSchedulePostConfig;
  // }

  /**
   * @return the taskScheduleConfig
   */
  public TaskScheduleConfig getTaskScheduleConfig() {
    return taskScheduleConfig;
  }

  /**
   * @param taskScheduleConfig the taskScheduleConfig to set
   */
  public void setTaskScheduleConfig(TaskScheduleConfig taskScheduleConfig) {
    this.taskScheduleConfig = taskScheduleConfig;
  }

  public int getMaxRow() {
    return maxRow;
  }

  public void setMaxRow(int maxRow) {
    this.maxRow = maxRow;
  }

  public Map<String, Object> getInvoiceIdMappingLogIdMap() {
    return invoiceIdMappingLogIdMap;
  }

  public void setInvoiceIdMappingLogIdMap(Map<String, Object> invoiceIdMappingLogIdMap) {
    this.invoiceIdMappingLogIdMap = invoiceIdMappingLogIdMap;
  }


  public String doPost(Object postDataObject) {
    URL url = null;
    HttpURLConnection connection = null;
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = null;
    OutputStream outputStream = null;
    try {
      String jsonStr = JsonUtil.format(postDataObject);
      url = new URL(this.postUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setUseCaches(false);
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5 * 60 * 1000);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Length", jsonStr.length() + "");
      outputStream = connection.getOutputStream();
      String encodeString = URLEncoder.encode(jsonStr, "UTF-8");
      logger.info("本次推送的json：{}", jsonStr);
      outputStream.write(encodeString.getBytes());
      outputStream.flush();
    } catch (Exception ex) {
      ex.printStackTrace();
      return "{\"result\":\"fail\",\"error\":\"" + ex.getMessage() + "\"}";
    } finally {
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException ex) {
          ex.printStackTrace();
          return "{\"result\":\"fail\",\"error\":\"" + ex.getMessage() + "\"}";
        }
      }
      try {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
          buffer.append(line);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        return "{\"result\":\"fail\",\"error\":\"" + ex.getMessage() + "\"}";
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException ex) {
            ex.printStackTrace();
            return "{\"result\":\"fail\",\"error\":\"" + ex.getMessage() + "\"}";
          }
        }
        if (connection != null) {
          connection.disconnect();
        }
      }
    }
    return buffer.toString();
  }
}
