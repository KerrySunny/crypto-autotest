package com.crypto.task2.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.crypto.task2.bean.PubReqParam;
import com.crypto.task2.bean.WeathAPIData;
import com.crypto.task2.util.PatternBean;

public class WeathAPI extends PubReqParam implements Serializable {
	
	public static final String URL = "/weatherAPI/opendata/weather.php";
	
	public static final String METHOD = "GET";

	private WeathAPIData request;//请求字段
	
	private Map reqHeader;//请求头
	
	private Map respHeader;//响应头
	
	private Map response;//接口返回字段
	
	private String status;
	
	
	private  List<PatternBean> patterns;//匹配断言字段  ${dataType}:value可以这样去定义,匹配selfParam的key
	
	private Map assertMap;
	
	private Map selfParam;// ${dataType}
	
	public String getUrlWithParam() {
		if(this.request != null){
			String tmp =URL+"?dataType="+this.getRequest().getDataType()+"&lang="+this.getRequest().getLang();
			return tmp;
		}
		return URL;
	}
	
	public String getUrl(){
		return URL;
	}

	public Map getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(Map reqHeader) {
		this.reqHeader = reqHeader;
	}

	public Map getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(Map respHeader) {
		this.respHeader = respHeader;
	}


	public List<PatternBean> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<PatternBean> patterns) {
		this.patterns = patterns;
	}

	public Map getSelfParam() {
		return selfParam;
	}

	public void setSelfParam(Map selfParam) {
		this.selfParam = selfParam;
	}

	public WeathAPIData getRequest() {
		return request;
	}

	public void setRequest(WeathAPIData request) {
		this.request = request;
	}

	public Map getResponse() {
		return response;
	}

	public void setResponse(Map response) {
		this.response = response;
	}

	public Map getAssertMap() {
		return assertMap;
	}

	public void setAssertMap(Map assertMap) {
		this.assertMap = assertMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
