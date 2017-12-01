package com.digiwin.ecims.ontime.bean.thread.log;

/**
 * 推送资料的主要信息
 *
 * @author zaregoto
 * @since 2015.10.09
 */
public class PostReqParamMajorData {

    private String id;

    private String orderPayTime;

    private String aomscrtdt;

    private String aomsmoddt;

    private String status;

    public PostReqParamMajorData() {
        super();
    }

    public PostReqParamMajorData(String id, String orderPayTime, String aomsmoddt, String status) {
        super();
        this.id = id;
        this.orderPayTime = orderPayTime;
        this.aomsmoddt = aomsmoddt;
        this.status = status;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the orderPayTime
     */
    public String getOrderPayTime() {
        return orderPayTime;
    }

    /**
     * @param orderPayTime the orderPayTime to set
     */
    public void setOrderPayTime(String orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    /**
     * @return the aomscrtdt
     */
    public String getAomscrtdt() {
        return aomscrtdt;
    }

    /**
     * @param aomscrtdt the aomscrtdt to set
     */
    public void setAomscrtdt(String aomscrtdt) {
        this.aomscrtdt = aomscrtdt;
    }

    /**
     * @return the aomsmoddt
     */
    public String getAomsmoddt() {
        return aomsmoddt;
    }

    /**
     * @param aomsmoddt the aomsmoddt to set
     */
    public void setAomsmoddt(String aomsmoddt) {
        this.aomsmoddt = aomsmoddt;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
