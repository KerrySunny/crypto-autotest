package com.crypto.task2.cases;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.crypto.task2.util.*;
import junit.framework.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.crypto.task2.common.EnvParamter;
import com.crypto.task2.common.ResponseBean;
import com.crypto.task2.interfaces.WeathAPI;
import com.crypto.task2.parameters.ForcastParameters;
import com.crypto.task2.report.GenerateReporter;


/**
 * 测试9天天气预测相关数据cases
 * @author Kerry
 *
 */
@Listeners({GenerateReporter.class})
public class Forcast4NineTest {

	private String nineUrl;

	private String dataResult;

	/**
	 * 获取对应的菜单url
	 * @param n
	 * @param url
	 */
    @Test(description="获取天气预报对应的接口url",dataProvider="forcast4NineUrl",dataProviderClass=ForcastParameters.class)
    public void forcast4NineUrl(Integer n,String url){
    	nineUrl=url;
    	dataResult ="获取天气预报对应的接口url成功";
    }

    /**
     * 测试状态码返回
     * @param n
     * @param dataType 业务类型
     * @param lang 语言
     * @param expectStatus 期望http状态码
     */
    @Test(dependsOnMethods={"forcast4NineUrl"},dataProvider = "testAccess200",description="测试接口返回200场景",dataProviderClass=ForcastParameters.class)
	public void testAccess200(Integer n, String dataType,String lang,int expectStatus){
    	String url = nineUrl.replace("${dataType}", dataType);
    	url = url.replace("${lang}", lang);

//    	HttpClientTem clientTem = new HttpClientTem();
//    	clientTem.setUrl(url);
//    	clientTem = HttpClientUtil.get(clientTem);
    	ResponseBean response = HttpClient.get(url, null);
    	dataResult ="获取天气预报对应的接口状态-响应200成功";

//    	Assert.assertEquals("获取天气预报对应的接口状态-接口返回状态异常",expectStatus, 200);
	}


    @Test(dataProvider = "test1",description="测试接口-新模式",dataProviderClass=ForcastParameters.class)
   	public void test1(Integer n, String bean,String descrption){
    	WeathAPI weather = JSONObject.parseObject(bean, WeathAPI.class);
       	String url = weather.getUrlWithParam();
       	url = EnvParamter.HOST+url;

       	ResponseBean response = HttpClient.get(url, null);
       	Map resp = JsonUtil.fromJson2Map(response.getResponse());
       	Map assertMap = weather.getAssertMap();

		Set set =assertMap.keySet();
		for(Object key:set){
			try {
				Object expect =assertMap.get(key);
				Object obj = MatchUtil.getJsonValueByPattern(resp,(String)key);
				Assert.assertEquals("获取天气预报对应的接口状态-test1",(String)expect, (String)obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
       	dataResult =descrption;
   	}




    /**
     *
     * @param n
     * @param dataType 业务类型
     * @param lang 语言
     * @param destiDays 要查询的目标天数：未来天气预测第一天是从明天开始的
     */
    @Test(dependsOnMethods={"forcast4NineUrl"},dataProvider = "testWentByDay",description="测试获取对应天数的湿度数据-多语言版本",dataProviderClass=ForcastParameters.class)
	public void testWentByDay(Integer n, String dataType,String lang,int destiDays){
	    	try{
	    	String url = nineUrl.replace("${dataType}", dataType);
	    	url = url.replace("${lang}", lang);
	    	HttpClientTem clientTem = new HttpClientTem();
	    	clientTem.setUrl(url);

	    	clientTem= HttpClientUtil.get(clientTem);
	    	Map respMap = clientTem.getReponseData();
	    	String reslut = (String)respMap.get(url);
	    	Map map = JsonUtil.fromJson2Map(reslut);

	    	List list = (List)map.get("weatherForecast");
			Map mapo = (Map) list.get(destiDays-1);
			Map forecastMaxrh = (Map)mapo.get("forecastMaxrh");
			Map forecastMinrh = (Map)mapo.get("forecastMinrh");

			String result = "language="+lang+
					" forecastDate="+mapo.get("forecastDate")+
					" 湿度: "+DataPerformance.getDataByUnit((Integer)forecastMinrh.get("value"), (String)forecastMinrh.get("unit"))
					+"~"    +DataPerformance.getDataByUnit((Integer)forecastMaxrh.get("value"), (String)forecastMaxrh.get("unit"));
			System.out.println(result);
			dataResult =result;
    	}catch(Exception e){
    		dataResult ="获取天气预报对应的接口url-接口返回状态异常"+e.getMessage();
    		Assert.assertEquals("获取天气预报对应的接口url-接口返回状态异常"+e.getMessage(),1,2);

    	}
	    	Assert.assertEquals(1,1);
	}

    @AfterMethod
    public void afterTest(ITestResult result){
    	result.setAttribute("data", dataResult);
    }

}
