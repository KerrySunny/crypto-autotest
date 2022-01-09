package com.crypto.task2.util;

import java.util.Map;

import org.apache.http.Header;
/**
 * 通过sessionId和url存储接口数据
 * 如单接口访问不需要登录，如需要验证token类信息，不走此功能
 * 如soap协议也不走此功能
 * @author Kerry
 *
 */
public class HttpClientTem {

	private Header[] requestHeaders;

	private Header[] responseHeaders;
	
	/**
	 * 请求url
	 */
	private String url;
	
	private Map<String,String> reponseData;
	
	private Map<String,Map> requestData;
	
	private Map<String,String> requestDataJson;
	
	private String charsetName;
	
	/**
	 * 会话id
	 */
	private String sessionId;
	
	/**
	 * 接口状态
	 */
	private int status;

	public HttpClientTem(){}
	
	public HttpClientTem(String url,Header[] requestHeaders,Header[] responseHeaders){
		this.url=url;
		this.requestHeaders=requestHeaders;
		this.responseHeaders=responseHeaders;
	}
	public Header[] getRequestHeaders() {
		return requestHeaders;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setRequestHeaders(Header[] requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public Header[] getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Header[] responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public Map<String, String> getReponseData() {
		return reponseData;
	}

	public void setReponseData(Map<String, String> reponseData) {
		this.reponseData = reponseData;
	}

	public void setRequestData(Map<String, Map> requestData) {
		this.requestData = requestData;
	}


	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public Map<String, Map> getRequestData() {
		return requestData;
	}

	public Map<String, String> getRequestDataJson() {
		return requestDataJson;
	}

	public void setRequestDataJson(Map<String, String> requestDataJson) {
		this.requestDataJson = requestDataJson;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
