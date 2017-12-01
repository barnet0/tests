package com.digiwin.ecims.ontime.bean.thread;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.bean.thread.log.PostReqParam;
import com.digiwin.ecims.ontime.bean.thread.log.PostReqParamMajorData;
import com.digiwin.ecims.ontime.model.PostDataObject;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.Callable;

public class PostTask<T> implements Callable<Boolean> {
    private static final Logger logger = LoggerFactory.getLogger(PostTask.class);
    private static int CONNECT_TIMEOUT = 10 * 1000;
    private static int READ_TIMEOUT = 2 * 60 * 1000;
    private static String URL_ENCODING_CHARSET = "UTF-8";
    // 实例化时及初始化服务类
    private LoginfoOperateService loginfoOperateService =
        MySpringContext.getContext().getBean(LoginfoOperateService.class);
    private String postUrl = null;
    private List<T> dataList;
    private Date threadCreateDate;
    private Date postDate;
    private Date postResDate;
    private TaskScheduleConfig taskScheduleConfig;
    private Class<T> clazz;

    public TaskScheduleConfig getTaskSchedulePostConfig() {
        return taskScheduleConfig;
    }

    public void setTaskSchedulePostConfig(TaskScheduleConfig taskScheduleConfig) {
        this.taskScheduleConfig = taskScheduleConfig;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void setThreadCreateDate(Date threadCreateDate) {
        this.threadCreateDate = threadCreateDate;
    }

    @Override public Boolean call() throws Exception {
        logger.info("---start---线程启动开始推送,定时任务:{}‧", taskScheduleConfig.getScheduleType());

        String responseResult = null;
        boolean isPushSuccess = true;

        // add by mowj 20150827
        // 推送成功的日志信息
        List<LogInfoT> postSuccessLogInfoTs = new ArrayList<LogInfoT>();

        List<PostDataObject<T>> postDataObjects = this.preparePostDataObjects(this.dataList, clazz);
        int postDataObjectsGroupSize = postDataObjects.size(); // add by mowj 20150827
        postUrl = taskScheduleConfig.getPostUrl();
        for (PostDataObject<T> postDataObject : postDataObjects) {
            int postDataObjectGroupIdx = postDataObjects.indexOf(postDataObject) + 1;
            int postDataObjectDataSize = postDataObject.getData().size();

            logger.info("线程启动开始推送{},第{}组/共{}组.本组共{}笔", clazz, postDataObjectGroupIdx,
                postDataObjectsGroupSize, postDataObjectDataSize);
            this.postDate = new Date();

            responseResult = this.doPost(postDataObject);

            this.postResDate = new Date();
            Map<String, String> resultMaps = null;
            try {
                resultMaps = new ObjectMapper().readValue(responseResult, Map.class);
            } catch (Exception ex) {
                ex.printStackTrace();
                if (resultMaps == null) {
                    resultMaps = new HashMap<String, String>();
                }
                resultMaps.put("result", "fail");
                resultMaps.put("error", ex.getMessage());
                logger.error(ex.getMessage(), ex);
                throw ex;
            } finally {
                if (resultMaps.get("result").equals("fail")) {
                    saveErrorLogInfoT(resultMaps, postDataObject);
                    // 将结果添加到日志文件备查
                    logger.error("第{}组推送失败,资料类型为{},响应为 {}", postDataObjectGroupIdx, clazz,
                        responseResult);
                    isPushSuccess = false; // add by mowj 20151218
                }
                // ---如果成功，记录发送开始的时间及响应的时间
                else {
                    String postThreadName =
                        taskScheduleConfig.getScheduleType() + "@" + Thread.currentThread()
                            .getName();
                    // 推送情况记录到日志
                    PostReqParam postReqParam = new PostReqParam();
                    postReqParam.setPostThreadName(postThreadName);
                    postReqParam.setGroupTotal(postDataObjectsGroupSize);
                    postReqParam.setGroupIndex(postDataObjectGroupIdx);
                    postReqParam.setDataTotalInGroup(postDataObjectDataSize);
                    postReqParam.setThreadCreateDate(threadCreateDate);
                    postReqParam.setPostRequestDate(this.postDate);
                    postReqParam.setPostResponseDate(this.postResDate);
                    postReqParam.setPostCostMilliSeconds(
                        this.postResDate.getTime() - this.postDate.getTime());
                    for (T t : postDataObject.getData()) {
                        if (t instanceof AomsordT) {
                            AomsordT aomsordT = (AomsordT) t;
                            postReqParam.setFromTable("aomsord_t");
                            // 设定推送资料的主要信息
                            PostReqParamMajorData majorData = new PostReqParamMajorData();
                            majorData.setId(aomsordT.getId());
                            majorData.setOrderPayTime(aomsordT.getAoms024());
                            majorData.setStatus(aomsordT.getAoms003());
                            majorData.setAomscrtdt(aomsordT.getAomscrtdt());
                            majorData.setAomsmoddt(aomsordT.getAomsmoddt());

                            postReqParam.getMajorData().add(majorData);
                        } else if (t instanceof AomsrefundT) {
                            AomsrefundT aomsrefundT = (AomsrefundT) t;
                            postReqParam.setFromTable("aomsrefund_t");
                            // 设定推送资料的主要信息
                            PostReqParamMajorData majorData = new PostReqParamMajorData();
                            majorData.setId(aomsrefundT.getId());
                            majorData.setStatus(aomsrefundT.getAoms037());
                            majorData.setAomscrtdt(aomsrefundT.getAomscrtdt());
                            majorData.setAomsmoddt(aomsrefundT.getAomsmoddt());

                            postReqParam.getMajorData().add(majorData);
                        } else if (t instanceof AomsitemT) {
                            AomsitemT aomsitemT = (AomsitemT) t;
                            postReqParam.setFromTable("aomsitem_t");
                            // 设定推送资料的主要信息
                            PostReqParamMajorData majorData = new PostReqParamMajorData();
                            majorData.setId(aomsitemT.getId());
                            majorData.setStatus(aomsitemT.getAoms007());
                            majorData.setAomscrtdt(aomsitemT.getAomscrtdt());
                            majorData.setAomsmoddt(aomsitemT.getAomsmoddt());

                            postReqParam.getMajorData().add(majorData);
                        } else {
                            ;
                        }
                    }
                    String reqParamString = JsonUtil.formatByMilliSecond(postReqParam);

                    LogInfoT logInfoT = new LogInfoT();
                    logInfoT.setIpAddress(taskScheduleConfig.getPostIp());
                    logInfoT.setCallMethod(taskScheduleConfig.getPostUrl());
                    logInfoT.setBusinessType(LogInfoBizTypeEnum.ECI_PUSH.getValueString());
                    logInfoT.setReqType("taskSchedule");
                    logInfoT.setReqParam(reqParamString);
                    logInfoT.setResSize(BigInteger.ONE);
                    logInfoT.setIsSuccess(true);
                    logInfoT.setReqTime(this.postDate);
                    logInfoT.setResTime(this.postResDate);
                    logInfoT.setResCode(null);
                    logInfoT.setResMsg(responseResult);
                    logInfoT.setRemark(taskScheduleConfig.getScheduleType());
                    logInfoT.setPushLimits(5);
                    logInfoT.setFinalStatus("1");
                    logInfoT.setErrBillType(null);
                    logInfoT.setErrBillId(null);

                    postSuccessLogInfoTs.add(logInfoT);
                }
                // 将结果添加到日志文件备查
                logger.info("第{}组推送结束,资料类型为{},耗时{}毫秒,响应为 {}", postDataObjectGroupIdx, clazz,
                    this.postResDate.getTime() - this.postDate.getTime(), responseResult);

                isPushSuccess = true;
            }
        }
        // add by mowj 20150827
        savePostSuccessLogInfoT(postSuccessLogInfoTs);

        logger.info("---end---线程推送完毕.定时任务:{}", taskScheduleConfig.getScheduleType());

        return isPushSuccess;
    }

    public void addPostDatalLists(List<T> dataList) {
        this.dataList = dataList;
    }

    private String doPost(Object postDataObject) {
        // add on 20161111
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        String result = "";
        try {
            HttpPost httpost = null;
            try {
                httpost = new HttpPost(postUrl);
                httpost.addHeader("Content-Type", "application/x-www-form-urlencoded");
                HttpClient httpclient = new DefaultHttpClient();

                httpost.setEntity(new StringEntity(
                    URLEncoder.encode(JsonUtil.format(postDataObject), URL_ENCODING_CHARSET),
                    URL_ENCODING_CHARSET));

                httpclient.getParams()
                    .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
                httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, READ_TIMEOUT);

                HttpResponse response = httpclient.execute(httpost);

                // response
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity(), URL_ENCODING_CHARSET);
                } else {
                    throw new Exception(
                        "OUCH！ has error!, return http code: " + response.getStatusLine()
                            .getStatusCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (httpost != null) {
                    httpost.releaseConnection();
                }
            }

            //     result = HttpPostUtils.getInstance().send_common_string(
            //          postUrl, URLEncoder.encode(JsonUtil.format(postDataObject), URL_ENCODING_CHARSET),
            //          CONNECT_TIMEOUT, READ_TIMEOUT, URL_ENCODING_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            result = "{\"result\":\"fail\",\"error\":\"" + e.getMessage() + "\"}";
        }
        return result;

    }

    private List<PostDataObject<T>> preparePostDataObjects(List<T> dataList, Class<T> clazz)
        throws IndexOutOfBoundsException {
        // HashMap<String, List<T>> map = new HashMap<String, List<T>>(); // mark by mowj 20151010
        Map<String, List<T>> map = new LinkedHashMap<String, List<T>>();
        if (clazz.equals(AomsordT.class)) {
            for (T t : dataList) {
                if (map.get(((AomsordT) t).getId()) == null) {
                    List<T> list = new ArrayList<T>();
                    list.add(t);
                    map.put(((AomsordT) t).getId(), list);
                } else {
                    List<T> list = map.get(((AomsordT) t).getId());
                    list.add(t);
                }
            }
        } else if (clazz.equals(AomsitemT.class)) {
            for (T t : dataList) {
                if (map.get(((AomsitemT) t).getId()) == null) {
                    List<T> list = new ArrayList<T>();
                    list.add(t);
                    map.put(((AomsitemT) t).getId(), list);
                } else {
                    List<T> list = map.get(((AomsitemT) t).getId());
                    list.add(t);
                }
            }
        } else {
            for (T t : dataList) {
                if (map.get(((AomsrefundT) t).getId()) == null) {
                    List<T> list = new ArrayList<T>();
                    list.add(t);
                    map.put(((AomsrefundT) t).getId(), list);
                } else {
                    List<T> list = map.get(((AomsrefundT) t).getId());
                    list.add(t);
                }
            }
        }

        List<PostDataObject<T>> postDataObjects = new ArrayList<PostDataObject<T>>();
        PostDataObject<T> postDataObject = null;
        for (List<T> list : map.values()) {
            if (postDataObject == null
                || Integer.valueOf(postDataObject.getTotalRecord()) > taskScheduleConfig
                .getMaxPushRow()) {
                postDataObject = new PostDataObject<T>();
                postDataObject.setUser(taskScheduleConfig.getUser());
                postDataObject.setPlant(taskScheduleConfig.getPlant());
                postDataObject.setService(taskScheduleConfig.getService());
                postDataObject.setEntId(taskScheduleConfig.getEntId());
                postDataObject.setCompanyId(taskScheduleConfig.getCompanyId());

                postDataObjects.add(postDataObject);
            }
            postDataObject.addObject(list);
            postDataObject.setTotalRecord(postDataObject.getData().size());
        }
        return postDataObjects;
    }

    private void saveErrorLogInfoT(Map<String, String> resultMaps,
        PostDataObject<T> postDataObject) {
        String postThreadName = taskScheduleConfig.getScheduleType();
        // 防止重复记录单头
        HashMap<String, LogInfoT> logInfoTMap = new HashMap<String, LogInfoT>();
        List<T> data = postDataObject.getData();
        for (T t : data) {
            LogInfoT logInfoT = null;
            if (t instanceof AomsordT) {
                AomsordT aomsordT = (AomsordT) t;
                logInfoT = loginfoOperateService
                    .isLogInfoExist(LogInfoBizTypeEnum.ECI_PUSH.getValueString(), AomsordT.BIZ_NAME,
                        aomsordT.getId());
                if (logInfoT != null) {

                } else {
                    if (logInfoTMap.get(aomsordT.getId()) == null) {
                        logInfoT = new LogInfoT();
                    } else {
                        logInfoT = logInfoTMap.get(aomsordT.getId());
                    }
                }
                logInfoT.setIpAddress(taskScheduleConfig.getPostIp());
                logInfoT.setCallMethod(taskScheduleConfig.getPostUrl());
                logInfoT.setBusinessType(LogInfoBizTypeEnum.ECI_PUSH.getValueString());
                logInfoT.setReqType("taskSchedule");
                logInfoT.setResSize(BigInteger.ONE);
                logInfoT.setIsSuccess(false);
                logInfoT.setReqTime(this.postDate);
                logInfoT.setResTime(new Date());
                logInfoT.setResCode("fail");
                logInfoT
                    .setResMsg(resultMaps.get("error") == null ? "错误未返回" : resultMaps.get("error"));
                logInfoT.setRemark(postThreadName);
                logInfoT.setPushLimits(5);
                logInfoT.setFinalStatus("0");
                logInfoT.setErrBillType(AomsordT.BIZ_NAME);
                logInfoT.setErrBillId(aomsordT.getId());
                logInfoT.setErrStoreId(aomsordT.getStoreId());
                logInfoT.setErrStoreType(aomsordT.getStoreType());
                logInfoTMap.put(aomsordT.getId(), logInfoT);
            } else if (t instanceof AomsitemT) {
                AomsitemT aomsitemT = (AomsitemT) t;
                logInfoT = loginfoOperateService
                    .isLogInfoExist(LogInfoBizTypeEnum.ECI_PUSH.getValueString(),
                        AomsitemT.BIZ_NAME, aomsitemT.getId());
                if (logInfoT != null) {

                } else {
                    if (logInfoTMap.get(aomsitemT.getId()) == null) {
                        logInfoT = new LogInfoT();
                    } else {
                        logInfoT = logInfoTMap.get(aomsitemT.getId());
                    }
                }
                logInfoT = new LogInfoT();
                logInfoT.setIpAddress(taskScheduleConfig.getPostIp());
                logInfoT.setCallMethod(taskScheduleConfig.getPostUrl());
                logInfoT.setBusinessType(LogInfoBizTypeEnum.ECI_PUSH.getValueString());
                logInfoT.setReqType("taskSchedule");
                logInfoT.setResSize(BigInteger.ONE);
                logInfoT.setIsSuccess(false);
                logInfoT.setReqTime(this.postDate);
                logInfoT.setResTime(new Date());
                logInfoT.setResCode("fail");
                logInfoT
                    .setResMsg(resultMaps.get("error") == null ? "错误未返回" : resultMaps.get("error"));
                logInfoT.setRemark(postThreadName);
                logInfoT.setPushLimits(5);
                logInfoT.setFinalStatus("0");
                logInfoT.setErrBillType(AomsitemT.BIZ_NAME);
                logInfoT.setErrBillId(aomsitemT.getId());
                logInfoT.setErrStoreId(aomsitemT.getStoreId());
                logInfoT.setErrStoreType(aomsitemT.getStoreType());
                logInfoTMap.put(aomsitemT.getId(), logInfoT);
            } else {
                AomsrefundT aomsrefundT = (AomsrefundT) t;
                logInfoT = loginfoOperateService
                    .isLogInfoExist(LogInfoBizTypeEnum.ECI_PUSH.getValueString(),
                        AomsrefundT.BIZ_NAME, aomsrefundT.getId());
                if (logInfoT != null) {

                } else {
                    if (logInfoTMap.get(aomsrefundT.getId()) == null) {
                        logInfoT = new LogInfoT();
                    } else {
                        logInfoT = logInfoTMap.get(aomsrefundT.getId());
                    }
                }
                logInfoT = new LogInfoT();
                logInfoT.setIpAddress(taskScheduleConfig.getPostIp());
                logInfoT.setCallMethod(taskScheduleConfig.getPostUrl());
                logInfoT.setBusinessType(LogInfoBizTypeEnum.ECI_PUSH.getValueString());
                logInfoT.setReqType("taskSchedule");
                logInfoT.setResSize(BigInteger.ONE);
                logInfoT.setIsSuccess(false);
                logInfoT.setReqTime(this.postDate);
                logInfoT.setResTime(new Date());
                logInfoT.setResCode("fail");
                logInfoT
                    .setResMsg(resultMaps.get("error") == null ? "错误未返回" : resultMaps.get("error"));
                logInfoT.setRemark(postThreadName);
                logInfoT.setPushLimits(5);
                logInfoT.setFinalStatus("0");
                logInfoT.setErrBillType(AomsrefundT.BIZ_NAME);
                logInfoT.setErrBillId(aomsrefundT.getId());
                logInfoT.setErrStoreId(aomsrefundT.getStoreId());
                logInfoT.setErrStoreType(aomsrefundT.getStoreType());
                logInfoTMap.put(aomsrefundT.getId(), logInfoT);
            }
        }
    /*
     * 修正下列的 exception, fixed by xavier on 20150907 Exception in thread "pool-1029-thread-1"
     * java.lang.ClassCastException: java.util.HashMap$Values cannot be cast to java.util.List at
     * com.mercuryecinf.core.util.PostRunnable.saveLogInfoT(PostRunnable.java:214) at
     * com.mercuryecinf.core.util.PostRunnable.run(PostRunnable.java:85) at
     * java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145) at
     * java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
     */
        // loginfoOperateService.newTransaction4SaveLogByCollection((List<LogInfoT>)
        // logInfoTs.values());
        List<LogInfoT> saveLogData = new ArrayList<LogInfoT>();
        saveLogData.addAll(logInfoTMap.values());
        loginfoOperateService.newTransaction4SaveOrUpdateLogByCollection(saveLogData);
    }

    /**
     * @param postSuccessLogInfoTs
     * @author 维杰
     * @since 2015.08.27
     */
    private void savePostSuccessLogInfoT(List<LogInfoT> postSuccessLogInfoTs) {
        // 推送情况记录到日志
        loginfoOperateService.newTransaction4SaveLogByCollection(postSuccessLogInfoTs);
    }
}
