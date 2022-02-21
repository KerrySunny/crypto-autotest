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

import com.crypto.task2.common.ResponseBean;

import net.sf.json.JSONObject;
public class HttpClient {
	
	public static final String CHARSET="UTF-8";
	
	/**
	 * http的get请求
	 * @param url
	 */
	public static ResponseBean get(String url,Header[] reqHeader) {
//		URI uri = null;
//		try {
//			uri = new URIBuilder()
//			        .setScheme("http")
//			        .setHost("www.google.com")
//			        .setPath("/search")
//			        .setParameter("q", "httpclient")
//			        .setParameter("btnG", "Google Search")
//			        .setParameter("aq", "f")
//			        .setParameter("oq", "")
//			        .build();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		HttpGet httpget = new HttpGet(uri);
		HttpGet httpGet = new HttpGet(url);
		return executeRequest(httpGet,reqHeader);
	}
	


	/**
	 * http的post请求，传递map格式参数
	 */
	public static ResponseBean post(String url, Map<String, String> dataMap,Header[] reqHeader) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, CHARSET);
				formEntity.setContentEncoding(CHARSET);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost,reqHeader);
	}

	
	
	/**
	 * http的get请求，增加异步请求头参数
	 * @param url
	 */
	public static ResponseBean ajaxGet(String url,Header[] reqHeader) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
		return executeRequest(httpGet,reqHeader);
	}

	
	/**
	 * http的post请求，增加异步请求头参数，传递map格式参数
	 */
	public static ResponseBean ajaxPost(String url, Map<String, String> dataMap,Header[] reqHeader) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, CHARSET);
				formEntity.setContentEncoding(CHARSET);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost,reqHeader);
	}

	/**
	 * http的post请求，增加异步请求头参数，传递json格式参数
	 */
	public static ResponseBean ajaxPostJson(String url, String jsonString,Header[] reqHeader) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
//		try {
			StringEntity stringEntity = new StringEntity(jsonString, CHARSET);// 解决中文乱码问题
			stringEntity.setContentEncoding(CHARSET);
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		return executeRequest(httpPost,reqHeader);
	}

	
	/**
	 * 执行一个http请求，传递HttpGet或HttpPost参数
	 */
	public static ResponseBean executeRequest(HttpUriRequest httpRequest,Header[] reqHeader) {
		
		ResponseBean responseBean = new ResponseBean();
		CloseableHttpClient httpclient;
		
		if ("https".equals(httpRequest.getURI().getScheme())){
			httpclient = createSSLInsecureClient();
		}else{
			httpclient = HttpClients.createDefault();
		}
		
		try {
			try {
				if(reqHeader!=null){
					httpRequest.setHeaders(reqHeader);
				}
				CloseableHttpResponse response = httpclient.execute(httpRequest);
				
				HttpEntity entity = null;
				try {
					entity = response.getEntity();//响应返回的实体
					String result = EntityUtils.toString(entity, CHARSET);
					responseBean.setResponse(result);
				} finally {
					EntityUtils.consume(entity);
					response.close();
				}
				responseBean.setStatus(response.getStatusLine().getStatusCode());
				responseBean.setRespHeader(response.getAllHeaders());
				
			} finally {
				httpclient.close();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return responseBean;
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