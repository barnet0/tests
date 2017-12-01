package com.digiwin.ecims.platforms.dangdang.bean.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ToWmsOrderExpressBillGetResponseMessage {

	@JsonProperty("Key")
	private String key;
	
	@JsonProperty("Value")
	private String value;

	public ToWmsOrderExpressBillGetResponseMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ToWmsOrderExpressBillGetResponseMessage(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
