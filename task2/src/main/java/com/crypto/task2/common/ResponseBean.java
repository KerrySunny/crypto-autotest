package com.crypto.task2.common;

import org.apache.http.Header;

public class ResponseBean {
	private String response;
	
	private int status;
	
	private Header[] respHeader;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Header[] getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(Header[] respHeader) {
		this.respHeader = respHeader;
	}
}
