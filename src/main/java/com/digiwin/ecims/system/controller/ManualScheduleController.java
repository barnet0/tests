package com.digiwin.ecims.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.service.AomsShopService;

@Controller
@RequestMapping("/manualschedule")
public class ManualScheduleController {
	
	@Autowired
	AomsShopService aomsshopService;
	
	@RequestMapping("/showManualSchedule.do")
	public String showManualSchedule(){
		return "/manualschedule/manualschedule";
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manualScheduleCombo.do")
	@ResponseBody
	public String getComboList(String storeType,HttpServletRequest request,HttpServletResponse response){
		return JSONArray.fromObject(
				aomsshopService.getStoreByStoreType(storeType)).toString();
	}
	
	
	
}
