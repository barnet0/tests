package com.digiwin.ecims.platforms.dhgate.service.ontime.updatecheck;

import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("dhgateTradeUpdateCheckServiceImpl")
public class DhgateTradeUpdateCheckServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
