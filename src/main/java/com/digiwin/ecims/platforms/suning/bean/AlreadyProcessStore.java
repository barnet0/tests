package com.digiwin.ecims.platforms.suning.bean;

import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.OrderOperateType;

public class AlreadyProcessStore {
  private int maxReadRows, alreadyReadRows;
  private OrderOperateType operateType;

  public AlreadyProcessStore(int maxReadRows, int alreadyReadRows) {
    this.maxReadRows = maxReadRows;
    this.alreadyReadRows = alreadyReadRows;
  }

  public AlreadyProcessStore(int maxReadRows, int alreadyReadRows, OrderOperateType operateType) {
    this.maxReadRows = maxReadRows;
    this.alreadyReadRows = alreadyReadRows;
    this.operateType = operateType;
  }

  public void addThisTimeProcessRows(int thisTimeProcessRows) {
    this.alreadyReadRows += thisTimeProcessRows;
  }

  public boolean isFull() {
    if (operateType == null) {
      return alreadyReadRows >= maxReadRows;
    } else {
      if (operateType == OrderOperateType.IS_MANUALLY
          || operateType == OrderOperateType.IS_CHECK_SCHEDULE) {
        // 手動不卡筆數上的限制
        return Boolean.FALSE;
      } else {
        return alreadyReadRows >= maxReadRows;
      }
    }
  }
}
