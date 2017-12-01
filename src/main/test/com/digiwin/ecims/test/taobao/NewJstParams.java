package com.digiwin.ecims.test.taobao;

public class NewJstParams {
  private String startTime;
  
  private String endTime;
  
  private String sequence;
  
  private String scheduleType;
  
  private String limit;
  
  private String comparisonCol;
  
  private String storeNick;
  
  private String storeId;
  
  private String id;

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getSequence() {
    return sequence;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public String getScheduleType() {
    return scheduleType;
  }

  public void setScheduleType(String scheduleType) {
    this.scheduleType = scheduleType;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public String getComparisonCol() {
    return comparisonCol;
  }

  public void setComparisonCol(String comparisonCol) {
    this.comparisonCol = comparisonCol;
  }

  public String getStoreNick() {
    return storeNick;
  }

  public void setStoreNick(String storeNick) {
    this.storeNick = storeNick;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NewJstParams() {
    super();
    // TODO Auto-generated constructor stub
  }

  public NewJstParams(String startTime, String endTime, String sequence, String scheduleType,
      String limit, String comparisonCol, String storeNick, String storeId, String id) {
    super();
    this.startTime = startTime;
    this.endTime = endTime;
    this.sequence = sequence;
    this.scheduleType = scheduleType;
    this.limit = limit;
    this.comparisonCol = comparisonCol;
    this.storeNick = storeNick;
    this.storeId = storeId;
    this.id = id;
  }
  
}
