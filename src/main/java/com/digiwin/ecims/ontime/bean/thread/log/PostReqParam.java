package com.digiwin.ecims.ontime.bean.thread.log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 推送记录实体
 *
 * @author zaregoto
 * @since 2015.08.27
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class PostReqParam {

    /**
     * 推送本组资料的线程名
     */
    private String postThreadName;

    /**
     * 推送资料总的分组数量
     */
    private int groupTotal;

    /**
     * 本组资料所在组的索引
     */
    private int groupIndex;

    /**
     * 本组推送资料ID的数量（订单为订单号，退单为退单号或订单号，铺货为平台商品编号）
     */
    private int dataTotalInGroup;

    /**
     * 推送线程添加到线程池的时间
     */
    private Date threadCreateDate;

    /**
     * 本组资料推送开始时间
     */
    private Date postRequestDate;

    /**
     * 本组资料推送接收到响应的时间
     */
    private Date postResponseDate;

    /**
     * 本组资料推送开始与接收到响应的用时
     */
    private long postCostMilliSeconds;

    /**
     * 本组资料的来源表名
     */
    private String fromTable;

    /**
     * 本组资料主要概况
     */
    private List<PostReqParamMajorData> majorData;

    public PostReqParam() {
        super();
        this.majorData = new ArrayList<PostReqParamMajorData>();
    }

    /**
     * 返回推送本组资料的线程名
     *
     * @return 推送本组资料的线程名
     */
    public String getPostThreadName() {
        return postThreadName;
    }

    /**
     * 设定推送本组资料的线程名
     *
     * @param postThreadName 推送本组资料的线程名
     */
    public void setPostThreadName(String postThreadName) {
        this.postThreadName = postThreadName;
    }

    /**
     * 返回推送资料总的分组数量
     *
     * @return 推送资料总的分组数量
     */
    public int getGroupTotal() {
        return groupTotal;
    }

    /**
     * 设定推送资料总的分组数量
     *
     * @param groupTotal 推送资料总的分组数量
     */
    public void setGroupTotal(int groupTotal) {
        this.groupTotal = groupTotal;
    }

    /**
     * 返回本组推送资料ID的数量
     *
     * @return 本级推送资料ID的数量
     */
    public int getDataTotalInGroup() {
        return dataTotalInGroup;
    }

    /**
     * 设定本组推送资料ID的数量
     *
     * @param dataTotalInGroup 本组推送资料ID的数量
     */
    public void setDataTotalInGroup(int dataTotalInGroup) {
        this.dataTotalInGroup = dataTotalInGroup;
    }

    /**
     * 返回本组资料所在组的索引
     *
     * @return 本组资料所在组的索引
     */
    public int getGroupIndex() {
        return groupIndex;
    }

    /**
     * 设定本组资料所在组的索引
     *
     * @param groupIndex 本组资料所在组的索引
     */
    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    /**
     * 返回本组资料的来源表名
     *
     * @return 本组资料的来源表名
     */
    public String getFromTable() {
        return fromTable;
    }

    /**
     * 设定本组资料的来源表名
     *
     * @param fromTable 本组资料的来源表名
     */
    public void setFromTable(String fromTable) {
        this.fromTable = fromTable;
    }

    public Date getThreadCreateDate() {
        return threadCreateDate;
    }

    public void setThreadCreateDate(Date threadCreateDate) {
        this.threadCreateDate = threadCreateDate;
    }

    /**
     * 返回本组资料推送开始时间
     *
     * @return 本组资料推送开始时间
     */
    public Date getPostRequestDate() {
        return postRequestDate;
    }

    /**
     * 设定本组资料推送开始时间
     *
     * @param postRequestDate 本组资料推送开始时间
     */
    public void setPostRequestDate(Date postRequestDate) {
        this.postRequestDate = postRequestDate;
    }

    /**
     * 返回本组资料推送响应时间
     *
     * @return 本组资料推送响应时间
     */
    public Date getPostResponseDate() {
        return postResponseDate;
    }

    /**
     * 设定本组资料推送响应时间
     *
     * @param postResponseDate 本组资料推送响应时间
     */
    public void setPostResponseDate(Date postResponseDate) {
        this.postResponseDate = postResponseDate;
    }

    /**
     * 返回本组资料推送开始与接收到响应的用时
     *
     * @return 本组资料推送开始与接收到响应的用时
     */
    public long getPostCostMilliSeconds() {
        return postCostMilliSeconds;
    }

    /**
     * 设定本组资料推送开始与接收到响应的用时
     *
     * @param postCostMilliSeconds 本组资料推送开始与接收到响应的用时
     */
    public void setPostCostMilliSeconds(long postCostMilliSeconds) {
        this.postCostMilliSeconds = postCostMilliSeconds;
    }



    /**
     * 返回本组资料主要概况
     *
     * @return the majorData
     */
    public List<PostReqParamMajorData> getMajorData() {
        return majorData;
    }

    /**
     * 设定本组资料主要概况
     *
     * @param majorData the majorData to set
     */
    public void setMajorData(List<PostReqParamMajorData> majorData) {
        this.majorData = majorData;
    }

}
