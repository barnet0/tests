package com.digiwin.ecims.platforms.kaola.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSearchResponse;

/**
 * 根据状态查询商品信息
 * @author cjp
 *
 */
public class ItemSearchRequest extends KaolaBaseRequest<ItemSearchResponse> {

	private int item_edit_status;
	//private int date_type;
	private String start_time;
	private String end_time;
	private int page_no;
	private int page_size;

	/**
	 * 
	 * @return
	 */
	public Integer getItem_edit_status() {
		return item_edit_status;
	}

	/**
	 * 
	 * @param order_status
	 */
	public void setItem_edit_status(int item_edit_status) {
		this.item_edit_status = item_edit_status;
	}

	/**
	 * 
	 * @return
	 */
/*	public Integer getDate_type() {
		return date_type;
	}*/

	/**
	 * 
	 * @param date_type
	 */
/*	public void setDate_type(int date_type) {
		this.date_type = date_type;
	}*/

	/**
	 * 
	 * @return
	 */
	public String getStart_time() {
		return start_time;
	}

	/**
	 * 
	 * @param start_time
	 */
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	/**
	 * 
	 * @return
	 */
	public String getEnd_time() {
		return end_time;
	}

	/**
	 * 
	 * @param end_time
	 */
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getPage_no() {
		return page_no;
	}

	/**
	 * 
	 * @param page_no
	 */
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getPage_size() {
		return page_size;
	}

	/**
	 * 
	 * @param page_size
	 */
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}



  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("item_edit_status", getItem_edit_status().toString());
	//apiParams.put("date_type", getDate_type().toString());
	apiParams.put("start_time", getStart_time());
	apiParams.put("end_time", getEnd_time());
	apiParams.put("page_no", getPage_no().toString());
	apiParams.put("page_size", getPage_size().toString());
	
    return apiParams;
  }

  @Override
  public String getMType() {
    return "kaola.item.batch.status.get";
  }

  @Override
  public Class<ItemSearchResponse> getResponseClass() {
    return ItemSearchResponse.class;
  }

}
