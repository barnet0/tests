package com.digiwin.ecims.system.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.HttpGetUtils;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.EcPlatformInfo;
import com.digiwin.ecims.system.service.ShopMaintainService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 店铺基础资料管理，包括平台的appkey、appsecret与access_token(又叫sessionkey)的管理
 * @author 维杰
 *
 */
@Controller
@RequestMapping("/shopmaintain")
public class ShopMaintainController {

	@Autowired
	private ShopMaintainService shopMaintainService;
	
	public ShopMaintainController() {
	}
	
	@RequestMapping("/showShop.do")
	public String showOnTimeTask() {
		return "/shopmaintain/shopmaintain";
	}
	
	@RequestMapping(value = "/queryShopMaintain.do", method = RequestMethod.POST)
	@ResponseBody
	public String queryShopMaintain(ModelMap map, String start, String limit) {
		PageBean<AomsshopT> pageBean = null;
		pageBean = shopMaintainService.findAllShopDataByCondition(start, limit);
		
		String shopJson = JSONArray.fromObject(pageBean.getRoot()).toString();
		return "{totalProperty:" + pageBean.getTotal() + ",root:" + shopJson + "}";
	}
	
	@RequestMapping(value = "/queryEcInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public String queryEcInfo() {
		List<EcPlatformInfo> ecPlatformInfos = null;
		try {
			ecPlatformInfos = shopMaintainService.findAllEcPlatformInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		String shopJson = "";
		try {
			shopJson = mapper.writeValueAsString(ecPlatformInfos);
			return "{totalProperty:" + ecPlatformInfos.size() + ",root:" + shopJson + "}";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/checkShopId.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkShopId(String shopId) { 
		if (shopId != null) {
			return shopMaintainService.shopExist(shopId);
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = "/checkRedirUrl.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkRedirUrl(String redirUrl) {
		if (StringUtils.isNotBlank(redirUrl) && StringUtils.isNotEmpty(redirUrl)) {
			try {
				return HttpGetUtils.getInstance().isUrlExists(redirUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/addShop.do", method = RequestMethod.POST)
	@ResponseBody
	public String editShop(ModelMap map, String jsonShop, AomsshopT aomsshopT, HttpServletRequest request) {
		JSONObject shopJsonObject = JSONObject.fromObject(jsonShop);

		String id = shopJsonObject.getString("shopMaintain.shopid");
		aomsshopT.setAomsshop001(id);
		
		String name = shopJsonObject.getString("shopMaintain.shopname");
		aomsshopT.setAomsshop002(name);
		
		String shopTypeCode = shopJsonObject.getString("shopMaintain.shoptypecode");
		aomsshopT.setAomsshop003(shopTypeCode);
		
		String appKey = shopJsonObject.getString("shopMaintain.shopappkey");
		aomsshopT.setAomsshop004(appKey);
		
		String appSecret = shopJsonObject.getString("shopMaintain.shopappsecret");
		aomsshopT.setAomsshop005(appSecret);
		
		String accessToken = shopJsonObject.getString("shopMaintain.shopaccesstoken");
		aomsshopT.setAomsshop006(accessToken);
		
		String refreshToken = shopJsonObject.getString("shopMaintain.shoprefreshtoken");
		aomsshopT.setAomsshop007(refreshToken);
		
		String rawJson = shopJsonObject.getString("shopMaintain.shoprawjson");
		aomsshopT.setAomsshop008(rawJson);
		
		String shopNameInEc = shopJsonObject.getString("shopMaintain.shopnameinec");
		aomsshopT.setAomsshop009(shopNameInEc);
		
		String redirUrl = shopJsonObject.getString("shopMaintain.shopredirecturl");
		aomsshopT.setAomsshop010(redirUrl);
		
		String shopType = shopJsonObject.getString("shopMaintain.shoptype");
		aomsshopT.setAomsshop011(shopType);
		
		aomsshopT = shopMaintainService.saveOrUpdateAomsshopT(aomsshopT);
		if (aomsshopT != null) {
			return "{success: true, msg: '保存成功!'}";
		} else {
			return "{success: false, msg: '保存失败!'}";
		}
	}

	@RequestMapping(value = "/editShop.do", method = RequestMethod.POST)
	@ResponseBody
	public String editShop(ModelMap map, String shopId) {
		String shopJson = "";
		AomsshopT aomsshopT = shopMaintainService.findShopById(shopId);
		if (aomsshopT != null) {
			shopJson = JSONObject.fromObject(aomsshopT).toString();
		}
		return shopJson;
	}
}
