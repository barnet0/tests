package com.digiwin.ecims.ontime.service.impl;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.bean.thread.PostTask;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.CheckColEnum;
import com.digiwin.ecims.system.enums.PostTaskDefaultParamEnum;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;
import com.digiwin.ecims.system.service.ReLogProcessService;
import com.digiwin.ecims.system.service.ontime.util.RePostRunnable;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

@Service("basePostServiceImpl") public class BasePostServiceImpl implements BasePostService {

    private static final Logger logger = LoggerFactory.getLogger(BasePostServiceImpl.class);

    @Autowired private ThreadPoolTaskExecutor threadpoolTaskExecutor;

    @Autowired private TaskService taskService;

    @Autowired private ParamSystemService paramSystemService;

    @Autowired private ReLogProcessService reLogProcessService;

    /**
     * 根据资料的单头ID与DB中线程数量最大值的设定，动态创建所需Runnable线程
     *
     * @param taskScheduleConfig
     * @param idTotalSize
     * @param dataList
     * @param clazz
     * @return
     * @throws IndexOutOfBoundsException
     * @author zaregoto
     */
    /*private <T> List<PostRunnable<T>> createRunnable(TaskScheduleConfig taskScheduleConfig,
        int idTotalSize, List<T> dataList, Class<T> clazz) throws IndexOutOfBoundsException {
        List<PostRunnable<T>> postRunnables = new ArrayList<PostRunnable<T>>();
        int maxRunnable = taskScheduleConfig.getMaxRunnable();
        // int dateSize = dataList.size();
        // int runnableDataSize = dateSize % maxRunnable == 0 ? dateSize / maxRunnable : dateSize /
        // maxRunnable + 1;

        // add by mowj 20151010
        List<String> dataIdxList = seperateData(maxRunnable, idTotalSize, dataList);

        // for (int i = 0; i < maxRunnable; i++) { // mark by mowj 20151010
        for (int i = 0; i < dataIdxList.size(); i++) { // add by mowj 20151010 根据单头ID的分配结果动态配置线程数量
            PostRunnable<T> postRunnable = new PostRunnable<T>();
            // mark by mowj 20151010
            // postRunnable.addPostDatalLists(dataList
            // .subList(i * runnableDataSize, (i + 1) * runnableDataSize > dateSize ? dateSize : (i + 1) *
            // runnableDataSize));

            // logger.info("postRunnable[" + i + "]");
            if (i + 1 != dataIdxList.size()) {
                postRunnable.addPostDatalLists(dataList
                    .subList(Integer.parseInt(dataIdxList.get(i)),
                        Integer.parseInt(dataIdxList.get(i + 1))));
                // List<T> tempList = dataList.subList(Integer.parseInt(dataIdxList.get(i)),
                // Integer.parseInt(dataIdxList.get(i + 1)));
                // for (T t : tempList) {
                // logger.info(((AomsordT)t).getId());
                // }

            } else {
                postRunnable.addPostDatalLists(
                    dataList.subList(Integer.parseInt(dataIdxList.get(i)), dataList.size()));
                // List<T> tempList = dataList.subList(Integer.parseInt(dataIdxList.get(i)),
                // dataList.size());
                // for (T t : tempList) {
                // logger.info(((AomsordT)t).getId());
                // }
            }

            postRunnable.setClazz(clazz);
            postRunnable.setTaskSchedulePostConfig(taskScheduleConfig);
            postRunnables.add(postRunnable);
        }
        return postRunnables;
    }*/

    /**
     * 根据资料的单头ID与DB中线程数量最大值的设定，动态创建所需Callable线程
     *
     * @param taskScheduleConfig
     * @param idTotalSize
     * @param dataList
     * @param clazz
     * @return
     * @throws IndexOutOfBoundsException
     * @author zaregoto
     */
    private <T> List<PostTask<T>> createPostTasks(TaskScheduleConfig taskScheduleConfig,
        int idTotalSize, List<T> dataList, Class<T> clazz) throws IndexOutOfBoundsException {
        List<PostTask<T>> postTasks = new ArrayList<PostTask<T>>();
        int maxRunnable = taskScheduleConfig.getMaxRunnable();
        // int dateSize = dataList.size();
        // int runnableDataSize = dateSize % maxRunnable == 0 ? dateSize / maxRunnable : dateSize /
        // maxRunnable + 1;

        // add by mowj 20151010
        List<String> dataIdxList = seperateData(maxRunnable, idTotalSize, dataList);

        // for (int i = 0; i < maxRunnable; i++) { // mark by mowj 20151010
        for (int i = 0; i < dataIdxList.size(); i++) { // add by mowj 20151010 根据单头ID的分配结果动态配置线程数量
            PostTask<T> postTask = new PostTask<T>();
            // mark by mowj 20151010
            // postRunnable.addPostDatalLists(dataList
            // .subList(i * runnableDataSize, (i + 1) * runnableDataSize > dateSize ? dateSize : (i + 1) *
            // runnableDataSize));

            // logger.info("postRunnable[" + i + "]");
            if (i + 1 != dataIdxList.size()) {
                postTask.addPostDatalLists(dataList.subList(Integer.parseInt(dataIdxList.get(i)),
                    Integer.parseInt(dataIdxList.get(i + 1))));
                // List<T> tempList = dataList.subList(Integer.parseInt(dataIdxList.get(i)),
                // Integer.parseInt(dataIdxList.get(i + 1)));
                // for (T t : tempList) {
                // logger.info(((AomsordT)t).getId());
                // }

            } else {
                postTask.addPostDatalLists(
                    dataList.subList(Integer.parseInt(dataIdxList.get(i)), dataList.size()));
                // List<T> tempList = dataList.subList(Integer.parseInt(dataIdxList.get(i)),
                // dataList.size());
                // for (T t : tempList) {
                // logger.info(((AomsordT)t).getId());
                // }
            }

            postTask.setClazz(clazz);
            postTask.setTaskSchedulePostConfig(taskScheduleConfig);
            postTasks.add(postTask);
        }
        return postTasks;
    }

    /**
     * 将资料按单头分配给每个线程
     *
     * @param maxRunnableCount 最大执行线程数
     * @param idTotalSize      单头ID总数
     * @param dataList         资料列表
     * @return
     */
    private <T> List<String> seperateData(int maxRunnableCount, int idTotalSize, List<T> dataList)
        throws IndexOutOfBoundsException {
        // 每个线程分配的最大单头ID数量
        int idMaxSizeInEachThread = 0;
        idMaxSizeInEachThread =
            idTotalSize / maxRunnableCount + (idTotalSize % maxRunnableCount == 0 ? 0 : 1);

        /**
         * 新建List，假设变量名为idxList，记录每个线程在dataList中需要取得的数据的索引范围。 因此设定idxList的初始大小为 maxRunnableCount。
         *
         * 假设每个线程i分割dataList的起点为x,终点为y, 则x=idxList[i],y=idxList[i + 1] - 1
         *
         * Eg. 第一个线程，取dataList[idxList[0]] ~ dataList[idxList[1] - 1]之间的资料, 第二个线程，取dataList[idxList[1]]
         * ~ dataList[idxList[2] - 1]之间的资料, ... 第N-1个线程，取dataList[idxList[n-1]] ~ dataList[idxList[n] -
         * 1]之间的资料 第N个线程，取dataList[idxList[n]] ~ dataList[idxList[n + 1] -1]之间的资料, ...
         * 最后一个线程Ne，取dataList[idxList[Ne]] ~ dataList[dataList.size() - 1]之间的资料
         *
         * 示例： 假设dataList.size() = 65, maxRunnableCount = 2 则idMaxSizeInEachThread = 65 / 2 + 1 = 33
         * 则每个线程分配到的数据索引范围为： t1: 0 ~ 32 (头尾包含) t2: 33 ~ 64 (头尾包含，尾部为dataList的最后一个元素) 因此，idxList包含的值为：
         * idxList[0] = 0 idxList[1] = 33
         *
         */
        List<String> dataIdxForEachThreadList = new ArrayList<String>(maxRunnableCount);
        dataIdxForEachThreadList.add("0");
        // 计数器
        int counter = 0;
        // 上一次记录的单头ID的临时变量
        String prevId = "";
        // 本次单头ID的临时变量
        String currentId = "";

        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) instanceof AomsordT) {
                AomsordT aomsordT = (AomsordT) dataList.get(i);
                currentId = aomsordT.getId();
            } else if (dataList.get(i) instanceof AomsrefundT) {
                AomsrefundT aomsrefundT = (AomsrefundT) dataList.get(i);
                currentId = aomsrefundT.getId();
            } else if (dataList.get(i) instanceof AomsitemT) {
                AomsitemT aomsitemT = (AomsitemT) dataList.get(i);
                currentId = aomsitemT.getId();
            } else {
                throw new RuntimeException("Unknown class " + dataList.get(i).getClass()
                    + " occurred when prepare data to each thread!!");
            }
            if (!prevId.equals(currentId)) {
                prevId = currentId;
                counter++;
                // 这里减1是因为，有可能下一笔单身还是这个单头ID，所以要等到下一个不同的单头ID再算索引值
                if (counter - 1 == idMaxSizeInEachThread) {
                    counter = 0;
                    currentId = "";
                    prevId = "";
                    // 这里不用加减1是因为在使用List.subList(beginIndex,toIndex)时，toIndex是不被包含的，
                    // 而且此时的i已经是下一个线程资料的范围的起点，所以就不用加
                    dataIdxForEachThreadList.add(i + "");
                    // 此处i减1是为了抵消循环的i++，将i保持在下一个不同的单头第一次出现的位置
                    i--;
                }
            }
        }

        return dataIdxForEachThreadList;
    }

    /**
     * 获取资料中单头的数量
     *
     * @param dataList
     * @return
     */
    private <T> int getIdTotalSizeFromDataList(List<T> dataList) {
        int i = 0;
        String previousId = "";
        String currentId = "";
        for (T t : dataList) {
            if (t instanceof AomsordT) {
                AomsordT aomsordT = (AomsordT) t;
                currentId = aomsordT.getId();
            } else if (t instanceof AomsrefundT) {
                AomsrefundT aomsrefundT = (AomsrefundT) t;
                currentId = aomsrefundT.getId();
            } else if (t instanceof AomsitemT) {
                AomsitemT aomsitemT = (AomsitemT) t;
                currentId = aomsitemT.getId();
            } else {
                throw new RuntimeException("Unknown class " + t.getClass()
                    + " occurred when prepare data to each thread!!");
            }
            if (!currentId.equals(previousId))
                i++;
            previousId = currentId;
        }
        return i;
    }

    private <T> List<RePostRunnable<T>> createRePushMulitRunnable(
        TaskScheduleConfig taskScheduleConfig, int maxRow, int runnableCount, List<T> postData,
        Map<String, Object> invoiceMappingLogIdMap) throws IndexOutOfBoundsException {
        List<RePostRunnable<T>> runnables = new ArrayList<RePostRunnable<T>>();
        List<List<T>> postDataList = dataSort(postData);
        int aomsListSize = postDataList.size();
        int length = aomsListSize / runnableCount + aomsListSize % runnableCount == 0 ? 0 : 1;

        // 平均分配單據
        for (int i = 0; i < runnableCount; i++) {
            if (i * length >= aomsListSize) {
                break;
            }

            RePostRunnable<T> runnable = new RePostRunnable<T>();
            runnable.addPostDatalLists(postDataList
                .subList(i * length, i == runnableCount - 1 ? aomsListSize : (i + 1) * length));
            runnable.setTaskScheduleConfig(taskScheduleConfig);
            runnable.setMaxRow(maxRow);
            runnable.setInvoiceIdMappingLogIdMap(invoiceMappingLogIdMap);
            runnables.add(runnable);

        }

        return runnables;
    }


    private <T> List<List<T>> dataSort(List<T> postData) throws IndexOutOfBoundsException {
        HashMap<String, List<T>> map = new HashMap<String, List<T>>();
        for (T t : postData) {
            if (t instanceof AomsordT) {
                if (map.get(((AomsordT) t).getId()) == null) {
                    List<T> list = new ArrayList<T>();
                    list.add(t);
                    map.put(((AomsordT) t).getId(), list);
                } else {
                    List<T> list = map.get(((AomsordT) t).getId());
                    list.add(t);
                }
            } else if (t instanceof AomsitemT) {
                if (map.get(((AomsitemT) t).getId()) == null) {
                    List<T> list = new ArrayList<T>();
                    list.add(t);
                    map.put(((AomsitemT) t).getId(), list);
                } else {
                    List<T> list = map.get(((AomsitemT) t).getId());
                    list.add(t);
                }
            } else {
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
        List<List<T>> postDataList = new ArrayList<List<T>>();
        for (List<T> list : map.values()) {
            postDataList.add(list);
        }
        return postDataList;
    }

    @Override
    public <T> void doPost(TaskScheduleConfig taskScheduleConfig, List<T> dataList, Class<T> clazz)
        throws IOException, RejectedExecutionException, NullPointerException, InterruptedException,
        ExecutionException {
        this.doPost(taskScheduleConfig, getIdTotalSizeFromDataList(dataList), dataList, clazz);
    }

    /**
     * 执行推送排程的所有线程
     *
     * @param taskScheduleConfig
     * @param idTotalSize
     * @param dataList
     * @param clazz
     * @throws IOException
     * @throws InterruptedException
     * @author zaregoto
     */
    private <T> void doPost(TaskScheduleConfig taskScheduleConfig, int idTotalSize,
        List<T> dataList, Class<T> clazz)
        throws IOException, RejectedExecutionException, NullPointerException, InterruptedException,
        ExecutionException {
        //        List<PostRunnable<T>> runnables =
        //            createRunnable(taskScheduleConfig, idTotalSize, dataList, clazz);
        List<PostTask<T>> postTasks =
            createPostTasks(taskScheduleConfig, idTotalSize, dataList, clazz);
        List<Future<Boolean>> postTaskFutures = new ArrayList<Future<Boolean>>();
        logger.info("排定推送线程 for {}", taskScheduleConfig);
        Date current = new Date();
        for (PostTask<T> postTask : postTasks) {
            postTask.setThreadCreateDate(current);
            Future<Boolean> future = threadpoolTaskExecutor.submit(postTask);
            postTaskFutures.add(future);
        }

        try {
            for (Future<Boolean> future : postTaskFutures) {
                Boolean result = future.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        } catch (ExecutionException e) {
            logger.error("推送线程执行异常...{}", e.getCause());
            e.printStackTrace();
            throw e;
        } finally {
        }
    }

    @Override public <T> void doRePost(TaskScheduleConfig taskScheduleConfig, List<T> dataList,
        Class<T> clazz)
        throws IOException, RejectedExecutionException, NullPointerException, InterruptedException {
        doRePost(taskScheduleConfig, getIdTotalSizeFromDataList(dataList), dataList, clazz);
    }

    private <T> void doRePost(TaskScheduleConfig taskScheduleConfig, int idTotalSize,
        List<T> dataList, Class<T> clazz)
        throws IOException, RejectedExecutionException, NullPointerException, InterruptedException {
        List<RePostRunnable<T>> runnables =
            createRePushMulitRunnable(taskScheduleConfig, taskScheduleConfig.getMaxPushRow(),
                taskScheduleConfig.getMaxRunnable(), dataList,
                reLogProcessService.getInvoiceIdMappingLogId(clazz.getSimpleName()));
        logger.info("排定线程");

        for (RePostRunnable<T> rePostRunnable : runnables) {
            threadpoolTaskExecutor.execute(rePostRunnable);
        }
    }

    @Override public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz)
        throws Exception {
        return timeOutExecute(taskScheduleConfig, clazz, WorkOperateTypeEnum.IS_SCHEDULE,
            CheckColEnum.IS_SCHEDULE);
    }

    @Override public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz,
        WorkOperateTypeEnum workOpEnum, CheckColEnum checkColEnum) throws Exception {
        return timeOutExecute(taskScheduleConfig, clazz, workOpEnum, checkColEnum,
            taskScheduleConfig.getLastUpdateTime(), DateTimeTool.formatToMillisecond(new Date()));
    }

    @Override public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz,
        WorkOperateTypeEnum workOpEnum, CheckColEnum checkColEnum, String startDate, String endDate)
        throws Exception {
        return timeOutExecute(taskScheduleConfig, clazz, workOpEnum, checkColEnum, startDate,
            endDate, "");
    }

    @Override public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz,
        WorkOperateTypeEnum workOpEnum, CheckColEnum checkColEnum, String startDate, String endDate,
        String storeId) throws Exception {
        // 如果没有设置推送排程，则使用系统参数设置
        if (StringUtils.isEmpty(taskScheduleConfig.getPostUrl()) || StringUtils
            .isEmpty(taskScheduleConfig.getPostIp()) || StringUtils
            .isEmpty(taskScheduleConfig.getPlant()) || StringUtils
            .isEmpty(taskScheduleConfig.getUser()) || StringUtils
            .isEmpty(taskScheduleConfig.getService()) || StringUtils
            .isEmpty(taskScheduleConfig.getPlant()) || StringUtils
            .isEmpty(taskScheduleConfig.getEntId()) || StringUtils
            .isEmpty(taskScheduleConfig.getCompanyId())) {
            taskScheduleConfig.setPlant((String) paramSystemService
                .getSysParamByKey(PostTaskDefaultParamEnum.PLANT.getParamKeyName()));
            taskScheduleConfig.setEntId((String) paramSystemService
                .getSysParamByKey(PostTaskDefaultParamEnum.ENT_ID.getParamKeyName()));
            taskScheduleConfig.setCompanyId((String) paramSystemService
                .getSysParamByKey(PostTaskDefaultParamEnum.COMPANY_ID.getParamKeyName()));
            switch (clazz.getSimpleName()) {
                case AomsordT.BIZ_NAME:
                    taskScheduleConfig.setService((String) paramSystemService.getSysParamByKey(
                        PostTaskDefaultParamEnum.ORDER_SERVICE.getParamKeyName()));
                    break;
                case AomsrefundT.BIZ_NAME:
                    taskScheduleConfig.setService((String) paramSystemService.getSysParamByKey(
                        PostTaskDefaultParamEnum.REFUND_SERVICE.getParamKeyName()));
                    break;
                case AomsitemT.BIZ_NAME:
                    taskScheduleConfig.setService((String) paramSystemService
                        .getSysParamByKey(PostTaskDefaultParamEnum.ITEM_SERVICE.getParamKeyName()));
                    break;
                default:
                    break;
            }
            taskScheduleConfig.setUser((String) paramSystemService
                .getSysParamByKey(PostTaskDefaultParamEnum.USER.getParamKeyName()));
            String postUrlPath = (String) paramSystemService
                .getSysParamByKey(PostTaskDefaultParamEnum.POST_URL_PATH.getParamKeyName());
            String postIp = (String) paramSystemService
                .getSysParamByKey(PostTaskDefaultParamEnum.POST_IP.getParamKeyName());
            taskScheduleConfig.setPostUrl("http://" + postIp + postUrlPath);
            taskScheduleConfig.setPostIp(postIp);

            taskScheduleConfig.setStoreId(storeId);

            taskScheduleConfig.setLastUpdateTime(new tempUpdateTask().getInitDateTime());
            // add by mowj 20160322 由于是新创建的任务，所以需要将lastUpdateTime设置成sDate
            startDate = taskScheduleConfig.getLastUpdateTime();

            taskService.saveTaskTaskScheduleConfig(taskScheduleConfig);
        }

        String scheduleType = taskScheduleConfig.getScheduleType();
        logger.info("开始推送{}", scheduleType);

        // [0]=平台名称;[1]=推送资料表;[2]=平台代码
        String[] scheduleCodes;
        scheduleCodes = scheduleType.split("#");

        if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
            taskService.newTransaction4SaveLastRunTime(scheduleType, null); // 記錄執行時間
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("pojo", scheduleCodes[1]);
        params.put("limit", taskScheduleConfig.getMaxReadRow() + "");
        params.put("storeType", scheduleCodes[2]);// 057
        if (storeId != null && storeId.trim().length() > 0) {
            params.put("storeId", storeId);
        }
        params.put("startDate", startDate);
        // modify by mowj 20160922 添加推送的时间范围控制
        params.put("endDate", getPostEndDate(startDate, endDate, (String) paramSystemService
            .getSysParamByKey(PostTaskDefaultParamEnum.PUSH_TIME_SPAN.getParamKeyName())));
        params.put("checkCol", checkColEnum.toString());
        long count = taskService.getSelectPojoCount(params);
        final long totalCount = count;
        List<T> dataList = null;
        // List<String> idList = null; // add by mowj 20151009
        // mark on 20161111
        //    while (count > 0) {
        if (count > 0) {
            logger.info("本次推送资料单头数目：{}", count);
            // // 获取要推送资料的ID列表
            // idList = taskService.getSelectPojosIdNormally(params); // add by mowj 20151009
            if (count > taskScheduleConfig.getMaxReadRow()) {
                logger.info("获取区间资料‧");
                // dataList = taskService.getSelectPojos(params, idList, clazz); // add by mowj 20151009
                dataList = taskService.getSelectPojos(params, clazz, workOpEnum);

                // if (CommonUtil.getAomsmoddt(dataList.get(dataList.size() - 1), clazz)
                // .equals(params.get("startDate"))) { // mark by mowj 20151106
                if (CommonUtil.getAomsmoddt(dataList.get(dataList.size() - 1), clazz)
                    .equals(CommonUtil.getAomsmoddt(dataList.get(0), clazz))) {
                    logger.info("本区间资料同一毫秒内大于{}", taskScheduleConfig.getMaxReadRow());
                    params.put("id", "0");
                    while (count > 0) {
                        logger.info("本次推送资料数目：{}", count); // add by mowj 20151010
                        // idList = taskService.getSelectPojosIdWhenDataMoreThanSettingInOneSecond(params); //
                        // add by mowj 20151010
                        // dataList = taskService.getSelectPojosById(params, idList, clazz); // add by mowj
                        // 20151010
                        dataList = taskService.getSelectPojosById(params, clazz, workOpEnum);
                        // doPost(taskScheduleConfig, idList.size(), dataList, clazz); // add by mowj 20151009
                        this.doPost(taskScheduleConfig, dataList, clazz);
                        // 准备下一次的资料
                        params
                            .put("id", CommonUtil.getId(dataList.get(dataList.size() - 1), clazz));
                        count = taskService.getSelectPojoCountById(params);

                    }
                    logger.info("本一秒钟资料处理完毕");
                    params.put("startDate",
                        DateTimeTool.timeAddOneMillisecond(params.get("startDate")));
                    if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
                        taskService.updateLastUpdateTime(scheduleType, params.get("startDate"));
                    }

                } else {
                    logger.info("本区间资料{}笔,未在同一毫秒发生", taskScheduleConfig.getMaxReadRow());
                    // doPost(taskScheduleConfig, idList.size(), dataList, clazz); // add by mowj 20151009
                    this.doPost(taskScheduleConfig, dataList, clazz);

                    params.put("startDate",
                        CommonUtil.getAomsmoddt(dataList.get(dataList.size() - 1), clazz));
                    if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
                        taskService.updateLastUpdateTime(scheduleType, params.get("startDate"));
                    }
                }

            } else {
                logger.info("本区间资料未达上限{}笔", taskScheduleConfig.getMaxReadRow());
                // dataList = taskService.getSelectPojos(params, idList, clazz); // add by mowj 20151009
                dataList = taskService.getSelectPojos(params, clazz, workOpEnum);

                // doPost(taskScheduleConfig, idList.size(), dataList, clazz); // add by mowj 20151009
                this.doPost(taskScheduleConfig, dataList, clazz);

                params.put("startDate",
                    CommonUtil.getAomsmoddt(dataList.get(dataList.size() - 1), clazz));
                if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
                    taskService.updateLastUpdateTime(scheduleType, params.get("startDate"));
                }
                // mark on 20161111
                //        break;
            }

            count = taskService.getSelectPojoCount(params);
        }
        if (count <= 0) {
            // add by mowj 20160922 添加推送的时间范围控制，当没有数据时，直接用最后时间更新lastUpdateTime
            if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
                taskService.updateLastUpdateTime(scheduleType, params.get("endDate"));
            }
        }

        logger.info("结束推送{}", scheduleType);
        return totalCount;
    }

    @Override
    public <T> boolean timeOutExecuteById(TaskScheduleConfig taskScheduleConfig, String id,
        Class<T> clazz) throws Exception {
        logger.info("准备推送{}", id);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("pojo", clazz.getSimpleName());
        params.put("id", id);

        List<T> dataList = taskService.getSelectPojoById(params, clazz);
        try {
            doPost(taskScheduleConfig, 1, dataList, clazz);
        } catch (IOException | RejectedExecutionException | NullPointerException | InterruptedException e) {
            logger.error(
                "Exception Occurred When push single id:{}. Exception Type is:{}. Error Message is: {}",
                id, e.getClass(), e.getLocalizedMessage());
            throw e;
        }
        logger.info("结束推送{}", id);
        return true;
    }


    /**
     * @param initStartDate 排程的lastUpdateTime
     * @param initEndDate   当前时间（精确到毫秒）
     * @param pushTimeSpan
     * @return
     */
    private String getPostEndDate(String initStartDate, String initEndDate, String pushTimeSpan) {
        if (pushTimeSpan.equals("00:00:00")) {
            return initEndDate;
        }
        DateTime initStartDt = null;
        if (initStartDate.contains(".")) {
            initStartDt =
                DateTimeTool.parseToDateTime(initStartDate, DateTimeTool.MILLI_SECOND_FORMAT);
        } else {
            initStartDt = DateTimeTool.parseToDateTime(initStartDate, DateTimeTool.SECOND_FORMAT);
        }
        DateTime pushTimeSpanDt =
            DateTimeTool.parseToDateTime(pushTimeSpan, DateTimeTool.SHORT_SECOND_FORMAT);
        DateTime initEndDt =
            DateTimeTool.parseToDateTime(initEndDate, DateTimeTool.MILLI_SECOND_FORMAT);

        DateTime tempEndDt = initStartDt.plusHours(pushTimeSpanDt.getHourOfDay())
            .plusMinutes(pushTimeSpanDt.getMinuteOfHour())
            .plusSeconds(pushTimeSpanDt.getSecondOfMinute());

        DateTime finalEndDate = null;
        if (tempEndDt.isAfter(initEndDt)) {
            finalEndDate = initEndDt;
        } else {
            finalEndDate = tempEndDt;
        }
        String endDate = finalEndDate.toString(DateTimeTool.MILLI_SECOND_FORMAT);

        return endDate;
    }
}


class tempUpdateTask extends UpdateTask {
    public String getInitDateTime() {
        return super.getInitDateTime();
    }
}
