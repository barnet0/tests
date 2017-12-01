package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 放款信息
 * @author 维杰
 *
 */
public class TpOpenLoanInfoDTO {

  // 否 放款时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800 
  private String loanTime;

  // 否 放款金额
  private Money loanAmount;

  public String getLoanTime() {
    return loanTime;
  }

  public void setLoanTime(String loanTime) {
    this.loanTime = loanTime;
  }

  public Money getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(Money loanAmount) {
    this.loanAmount = loanAmount;
  }
  
  
}
