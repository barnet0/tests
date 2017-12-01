package com.digiwin.ecims.platforms.baidu.bean.merchant.client.model;

public class Response<E> {

	E body;
	ResponseHeader header = null;

	public E getBody() {
		return (E) this.body;
	}

	public void setBody(E body) {
		this.body = body;
	}

	public ResponseHeader getHeader() {
		return this.header;
	}

	public void setHeader(ResponseHeader header) {
		this.header = header;
	}
}
