package com.digiwin.ecims.platforms.taobao.service.area;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.bean.area.AreaNode;
import com.digiwin.ecims.core.bean.area.AreaResponse;

public interface StandardAreaService {

	/**获取所有标准省市区信息的树状结构根节点，并存储到Ehcache缓存
	 * @return
	 * @throws ApiException 
	 */
	public AreaNode returnRootNode() throws ApiException;
	
	
	/**初始化标准省市区数据
	 * @param key
	 * @param areaData
	 * @return
	 */
	public boolean saveAreaToCache();
	
	/**根据省市区的关键字，返回淘宝标准省市区
	 * @param province 省关键字
	 * @param city 市关键字
	 * @param district 区关键字
	 * @return AreaResponse
	 * @author 维杰
	 * @since 2015.07.24
	 */
	public AreaResponse getStandardAreaNameByKeyWord(String province, String city, String district);
	
}
