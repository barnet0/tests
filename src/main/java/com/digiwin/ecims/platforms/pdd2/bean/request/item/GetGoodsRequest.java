package com.digiwin.ecims.platforms.pdd2.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetGoodsResponse;

public class GetGoodsRequest extends Pdd2BaseRequest<GetGoodsResponse> {

	// 商品类型 该值为“Onsale”或者“InStock”，分别是代表“在售”和“下架”。
	private String is_onsale;

	// 拼多多skuID外部编码 示例：erp_12131
	private String outer_id;

	// 拼多多商品名称 示例：洗衣液
	private String goods_name;

	private Integer page;

	private Integer page_size;

	/**
	 * @return the is_onsale
	 */
	public String getIs_onsale() {
		return is_onsale;
	}

	/**
	 * @param is_onsale
	 *            the is_onsale to set
	 */
	public void setIs_onsale(String is_onsale) {
		this.is_onsale = is_onsale;
	}

	public String getOuterID() {
		return outer_id;
	}

	public void setOuterID(String outerID) {
		this.outer_id = outerID;
	}

	public String getGoodsName() {
		return goods_name;
	}

	public void setGoodsName(String goodsName) {
		this.goods_name = goodsName;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return page_size;
	}

	public void setPageSize(Integer pageSize) {
		this.page_size = pageSize;
	}

	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();
		apiParams.put("is_onsale", getIs_onsale());
		apiParams.put("outer_id", getOuterID());
		apiParams.put("goods_name", getGoodsName());
		apiParams.put("page", getPage() + "");
		apiParams.put("page_size", getPageSize() + "");

		return apiParams;
	}

	@Override
	public String getMType() {
		return "pdd.goods.list.get";
	}

	@Override
	public Class<GetGoodsResponse> getResponseClass() {
		return GetGoodsResponse.class;
	}

}
