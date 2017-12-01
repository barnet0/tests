package com.digiwin.ecims.system.bean;

public final class SyncResOrderHandBeanHelper {

  private static SyncResOrderHandBeanHelper srohbh;

  private SyncResOrderHandBeanHelper() {}

  public static SyncResOrderHandBeanHelper getInstance() {
    if (srohbh == null) {
      srohbh = new SyncResOrderHandBeanHelper();
    }
    return srohbh;
  }

  public enum ManuallySyncError {
    SUCCESS("digiwin-sys-success", "執行成功, 共回傳 XXX 筆"), OVER_ROWS_ERROR("digiwin-sys-error-overrows",
        "為提升系統運行效能, 手動拉取最多只能取得 XXX 筆"), SYSTEM_ERROR("digiwin-sys-error", "");

    private String errCode, errMsg;

    private ManuallySyncError(String errCode, String errMsg) {
      this.errCode = errCode;
      this.errMsg = errMsg;
    }

    @Override
    public String toString() {
      return errCode;
    }

    public String getErrCode() {
      return errCode;
    }

    public String getErrMsg() {
      return errMsg;
    }
  }

//  public SyncResOrderHandBean produceReturnBean(ManuallySyncError errorType, String resultSize) {
//    return this.produceReturnBean(errorType, resultSize);
//  }

  public SyncResOrderHandBean produceReturnBean(ManuallySyncError errorType, String resultSize,
      String msg) {
    switch (errorType) {
      case SUCCESS:
        return new SyncResOrderHandBean(Boolean.TRUE, msg);
      case OVER_ROWS_ERROR:
        return new SyncResOrderHandBean(Boolean.FALSE, resultSize,
            ManuallySyncError.OVER_ROWS_ERROR.getErrCode(),
            ManuallySyncError.OVER_ROWS_ERROR.getErrMsg());
      case SYSTEM_ERROR:
        return new SyncResOrderHandBean(Boolean.FALSE, msg);
    }
    return null;
  }

}
