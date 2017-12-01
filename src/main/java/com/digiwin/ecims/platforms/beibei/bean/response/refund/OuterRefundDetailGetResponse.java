package com.digiwin.ecims.platforms.beibei.bean.response.refund;

import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.refund.RefundDetailGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterRefundDetailGetResponse extends BeibeiBaseResponse {

  private List<RefundDetailGetDto> data;

  public List<RefundDetailGetDto> getData() {
    return data;
  }

  public void setData(List<RefundDetailGetDto> data) {
    this.data = data;
  }

  public OuterRefundDetailGetResponse() {
  }
  
}
