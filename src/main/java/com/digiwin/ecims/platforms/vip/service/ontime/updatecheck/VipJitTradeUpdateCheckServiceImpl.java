package com.digiwin.ecims.platforms.vip.service.ontime.updatecheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;

@Service("vipJitTradeUpdateCheckServiceImpl")
@Scope("prototype")
public class VipJitTradeUpdateCheckServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  private static final Logger looger =
      LoggerFactory.getLogger(VipJitTradeUpdateCheckServiceImpl.class);

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private CheckUpdateService checkUpdateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    // TODO Auto-generated method stub

    return false;
  }

}
