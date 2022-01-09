package com.crypto.task2.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
public class HttpClientUtil {
	
	/**
	 * http的get请求
	 * @param url
	 */
	public static HttpClientTem get(HttpClientTem clientTem) {
		String url = clientTem.getUrl();
		String charset = clientTem.getCharsetName();
		Header[] h = clientTem.getResponseHeaders();
		HttpGet httpGet = new HttpGet(url);
		return executeRequest(httpGet, clientTem);
	}
	
	/**
	 * http的get请求，增加异步请求头参数
	 * @param url
	 */
	public static HttpClientTem ajaxGet(HttpClientTem clientTem) {
		Map<String, String> dataMap = clientTem.getRequestData().get(clientTem.getUrl());
		String charset = clientTem.getCharsetName()==null?"UTF-8":clientTem.getCharsetName();
		String url = clientTem.getUrl();
		Header[] h = clientTem.getResponseHeaders();
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
		if(h != null){
			httpGet.setHeaders(h);
		}
		return executeRequest(httpGet, clientTem);
	}

	/**
	 * http的post请求，传递map格式参数
	 */
	public static HttpClientTem post(HttpClientTem clientTem) {
		Map<String, String> dataMap = clientTem.getRequestData().get(clientTem.getUrl());
		String charset = clientTem.getCharsetName()==null?"UTF-8":clientTem.getCharsetName();
		String url = clientTem.getUrl();
		Header[] h = clientTem.getResponseHeaders();
		
		HttpPost httpPost = new HttpPost(url);
		if(h != null){
			httpPost.setHeaders(h);
		}
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, clientTem);
	}


	/**
	 * http的post请求，增加异步请求头参数，传递map格式参数
	 */
	public static HttpClientTem ajaxPost(HttpClientTem clientTem) {
		String url = clientTem.getUrl();
		Map<String, String> dataMap = clientTem.getRequestData().get(clientTem.getUrl());
		String charset = clientTem.getCharsetName()==null?"UTF-8":clientTem.getCharsetName();
		Header[] h = clientTem.getResponseHeaders();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		if(h != null){
			httpPost.setHeaders(h);
		}
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();//参数键值对
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, clientTem);
	}


	/**
	 * http的post请求，增加异步请求头参数，传递json格式参数
	 */
	public static HttpClientTem ajaxPostJson(HttpClientTem clientTem) {
		String url  = clientTem.getUrl();
		Header[] h = clientTem.getResponseHeaders();
		String charset = clientTem.getCharsetName()==null?"UTF-8":clientTem.getCharsetName();
		String jsonString = clientTem.getRequestDataJson().get(clientTem.getUrl());
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		if(h != null){
			httpPost.setHeaders(h);
		}
//		try {
			StringEntity stringEntity = new StringEntity(jsonString, charset);// 解决中文乱码问题
			stringEntity.setContentEncoding(charset);
			stringEntity.setContentType("application/json");//数据传递格式
			httpPost.setEntity(stringEntity);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		return executeRequest(httpPost, clientTem);
	}

	/**
	 * 执行一个http请求，传递HttpGet或HttpPost参数
	 */
	public static HttpClientTem executeRequest(HttpUriRequest httpRequest, HttpClientTem clientTem) {
		String charset = clientTem.getCharsetName()==null?"UTF-8":clientTem.getCharsetName();
		Header[] h = clientTem.getResponseHeaders();
		
		CloseableHttpClient httpclient;
		Map<String,String> resMap = new HashMap<String,String>();
		if ("https".equals(httpRequest.getURI().getScheme())){
			httpclient = createSSLInsecureClient();
		}else{
			httpclient = HttpClients.createDefault();
		}
		String result = "";
		try {
			try {
				if(h!=null){
					httpRequest.setHeaders(h);
				}
				CloseableHttpResponse response = httpclient.execute(httpRequest);
				
				if(h == null){
					h = response.getHeaders("Set-Cookie");
					clientTem.setResponseHeaders(h);
				}
				HttpEntity entity = null;
				try {
					entity = response.getEntity();//响应返回的实体
					result = EntityUtils.toString(entity, charset);
				} finally {
					EntityUtils.consume(entity);
					response.close();
				}
				clientTem.setStatus(response.getStatusLine().getStatusCode());
			} finally {
				httpclient.close();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		resMap.put(clientTem.getUrl(), result);
		clientTem.setReponseData(resMap);
		return clientTem;
	}
	
	/**
	 * 创建 SSL连接
	 */
	public static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustStrategy() {
				//@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
				//@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException ex) {
			throw new RuntimeException(ex);
		}
	}
}