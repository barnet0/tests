package com.digiwin.ecims.platforms.jingdong.bean.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PictureExternalUploadReturnValue {

	@JsonProperty("picid")
	private String _picId;
	
	@JsonProperty("url")
	private String _url;
	
	/**
	 * @return the _picId
	 */
	public String get_picId() {
		return _picId;
	}

	/**
	 * @param _picId the _picId to set
	 */
	public void set_picId(String _picId) {
		this._picId = _picId;
	}

	/**
	 * @return the _url
	 */
	public String get_url() {
		return _url;
	}

	/**
	 * @param _url the _url to set
	 */
	public void set_url(String _url) {
		this._url = _url;
	}

	public PictureExternalUploadReturnValue() {
	}
	
	public PictureExternalUploadReturnValue(String _picId, String _url) {
		super();
		this._picId = _picId;
		this._url = _url;
	}
	
}
