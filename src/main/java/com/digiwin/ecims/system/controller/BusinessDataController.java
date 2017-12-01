package com.digiwin.ecims.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.service.BusinessDataService;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/bizdata")
public class BusinessDataController {

	@Autowired
	private BusinessDataService businessDataService;
	
	public BusinessDataController() {
		super();
	}

	@RequestMapping("/showOrder.do")
	public String showOrder() {
		return "/order/order";
	}
	
	@RequestMapping("/showRefundAndReturn.do")
	public String showRefundAndReturn() {
		return "/refund/refund";
	}
	
	@RequestMapping("/showItem.do")
	public String showItem() {
		return "/item/item";
	}
	
	@RequestMapping(value = "/queryOrder.do", method = RequestMethod.POST)
	@ResponseBody
	public String queryOrder(ModelMap map, String orderId, String created, String sTime, String eTime, 
			String storeType, String storeId, String start, String limit) {
		StringBuffer sBuffer = new StringBuffer();
		if (orderId != null && orderId.length() > 0) {
			sBuffer.append(" AND id='").append(orderId).append("' ");
		}
		if (created != null && created.length() > 0) {
			String createdT = created.substring(0, "yyyy-MM-dd".length());
			boolean hasStime = sTime != null && sTime.length() > 0;
			boolean hasEtime = eTime != null && eTime.length() > 0;
			if (hasStime && !hasEtime) {
				sBuffer.append(" AND aoms006 >= '").append(createdT).append(" ").append(sTime).append("' ");
			} else if (!hasStime && hasEtime) {
				sBuffer.append(" AND aoms006 <= '").append(createdT).append(" ").append(eTime).append("' ");
			} else if (!hasStime && !hasEtime) {
				sBuffer.append(" AND aoms006 BETWEEN ")
				.append(" '").append(createdT).append(" 00:00:00' ")
				.append(" AND '").append(createdT).append(" 23:59:59' ");
			} else {
				sBuffer.append(" AND aoms006 BETWEEN ")
				.append(" '").append(createdT).append(" ").append(sTime).append("' ")
				.append(" AND '").append(createdT).append(" ").append(eTime).append("' ");
			}
		}
		
		if (storeType != null && storeType.length() > 0) {
			sBuffer.append(" AND storeType='").append(storeType).append("' ");
		}
		if (storeId != null && storeId.length() > 0) {
			sBuffer.append(" AND storeId='").append(storeId).append("' ");
		}
		return queryBusinessData(map, AomsordT.class, "AomsordT", sBuffer.toString(), start, limit);
	}
	
	@RequestMapping(value = "/queryRefundAndReturn.do", method = RequestMethod.POST)
	@ResponseBody
	public String queryRefundAndReturn(ModelMap map, String refundId, String storeType, String storeId, 
			String start, String limit) {
		StringBuffer sBuffer = new StringBuffer();
		if (refundId != null && refundId.length() > 0) {
			sBuffer.append(" AND id='").append(refundId).append("' ");
		}
//		if (created != null && created.length() > 0) {
//			String createdT = created.substring(0, "yyyy-MM-dd".length());
//			boolean hasStime = sTime != null && sTime.length() > 0;
//			boolean hasEtime = eTime != null && eTime.length() > 0;
//			if (hasStime && !hasEtime) {
//				sBuffer.append(" AND aoms006 >= '").append(createdT).append(" ").append(sTime).append("' ");
//			} else if (!hasStime && hasEtime) {
//				sBuffer.append(" AND aoms006 <= '").append(createdT).append(" ").append(eTime).append("' ");
//			} else if (!hasStime && !hasEtime) {
//				sBuffer.append(" AND aoms006 BETWEEN ")
//				.append(" '").append(createdT).append(" 00:00:00' ")
//				.append(" AND '").append(createdT).append(" 23:59:59' ");
//			} else {
//				sBuffer.append(" AND aoms006 BETWEEN ")
//				.append(" '").append(createdT).append(" ").append(sTime).append("' ")
//				.append(" AND '").append(createdT).append(" ").append(eTime).append("' ");
//			}
//		}
		
		if (storeType != null && storeType.length() > 0) {
			sBuffer.append(" AND storeType='").append(storeType).append("' ");
		}
		if (storeId != null && storeId.length() > 0) {
			sBuffer.append(" AND storeId='").append(storeId).append("' ");
		}
		return queryBusinessData(map, AomsrefundT.class, "AomsrefundT", sBuffer.toString(), start, limit);
	}
	
	@RequestMapping(value = "/queryItem.do", method = RequestMethod.POST)
	@ResponseBody
	public String queryItem(ModelMap map, String itemId, String storeType, String storeId, String start, String limit) {
		StringBuffer sBuffer = new StringBuffer();
		if (itemId != null && itemId.length() > 0) {
			sBuffer.append(" AND id='").append(itemId).append("' ");
		}
		if (storeType != null && storeType.length() > 0) {
			sBuffer.append(" AND storeType='").append(storeType).append("' ");
		}
		if (storeId != null && storeId.length() > 0) {
			sBuffer.append(" AND storeId='").append(storeId).append("' ");
		}
		return queryBusinessData(map, AomsitemT.class, "AomsitemT", sBuffer.toString(), start, limit);
	}
	
	private <T> String queryBusinessData(ModelMap map, Class<T> clazz, String pojoName, String condition, String start, String limit) {
		PageBean<T> pageBean = null;
		pageBean = businessDataService.findAllBusinessDataByConditionWithLimit(clazz, pojoName, condition, start, limit);
		
		String resultJson = JSONArray.fromObject(pageBean.getRoot()).toString();
		return "{totalProperty:" + pageBean.getTotal() + ",root:" + resultJson + "}";
	}
}
