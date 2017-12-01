package com.digiwin.ecims.platforms.aliexpress.bean.sync;

import java.util.Date;

public class SyncDataResult {

  private long syncDataCount;
  
  private Date syncLastUpdateTime;

  public long getSyncDataCount() {
    return syncDataCount;
  }

  public void setSyncDataCount(long syncDataCount) {
    this.syncDataCount = syncDataCount;
  }

  public Date getSyncLastUpdateTime() {
    return syncLastUpdateTime;
  }

  public void setSyncLastUpdateTime(Date syncLastUpdateTime) {
    this.syncLastUpdateTime = syncLastUpdateTime;
  }

  public SyncDataResult() {
  }

  public SyncDataResult(long syncDataCount, Date syncLastUpdateTime) {
    super();
    this.syncDataCount = syncDataCount;
    this.syncLastUpdateTime = syncLastUpdateTime;
  }
  
}
