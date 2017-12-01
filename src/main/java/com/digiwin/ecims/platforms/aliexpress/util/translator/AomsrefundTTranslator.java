package com.digiwin.ecims.platforms.aliexpress.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.refund.IssueAPIDetailDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.refund.IssueAPIIssueDTO;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressDateTimeTool;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;;

public class AomsrefundTTranslator {

  private Object detail;

  public AomsrefundTTranslator(Object detail) {
    super();
    this.detail = detail;
  }
  
  public List<AomsrefundT> doTranslate(String storeId) {
    if (this.detail instanceof IssueAPIDetailDTO) {
      return parseAeIssueToAomsrefundT(storeId);
    } else {
      return new ArrayList<AomsrefundT>();
    }
  }

  private List<AomsrefundT> parseAeIssueToAomsrefundT(String storeId) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    IssueAPIDetailDTO issueDetail = (IssueAPIDetailDTO) this.detail;
    IssueAPIIssueDTO issue = issueDetail.getIssueAPIIssueDTO();
    
    AomsrefundT aomsrefundT = new AomsrefundT();
    
    aomsrefundT.setId(CommonUtil.checkNullOrNot(issue.getId()));
    aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(issue.getIssueStatus()));
    aomsrefundT.setModified(CommonUtil.checkNullOrNot(AliexpressDateTimeTool.turnAeResDateStringToDateString(issue.getGmtModified())));
    // TODO 主键必须有，但是平台不返回，看起来是整单退？？？这里自己给个值，等待测试后验证
    aomsrefundT.setAoms023("REF" + issue.getOrderId());
    aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(issue.getOrderId()));
    
    aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(issue.getIssueStatus()));
    aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(issue.getParentOrderId()));
    aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(AliexpressDateTimeTool.turnAeResDateStringToDateString(issue.getGmtCreate())));
    
    aomsrefundT.setStoreId(storeId);
    aomsrefundT.setStoreType(AliexpressCommonTool.STORE_TYPE);
    
    Date now = new Date();
    aomsrefundT.setAomsstatus("0");
    aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
    aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
    
    aomsrefundTs.add(aomsrefundT);
    
    return aomsrefundTs;
  }
}
