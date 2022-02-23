package com.crypto.task2.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.alibaba.fastjson.JSONObject;
import com.crypto.task2.bean.WeathAPIData;
import com.crypto.task2.interfaces.WeathAPI;
import com.crypto.task2.util.PatternBean;

public class ForcastParameters {


	@DataProvider(name="test1")
	  public static Object[][] test1() {
		WeathAPI weather = new WeathAPI();

		WeathAPIData request = new WeathAPIData();
		request.setDataType("fnd");
		request.setLang("sc");
		weather.setRequest(request);


		Map reqHeader = new HashMap();
		weather.setReqHeader(new HashMap());

		Map assertMap = new HashMap();
		assertMap.put("weatherForecast[0].week", "星期三");
		assertMap.put("weatherForecast[1].week", "星期四");

		weather.setAssertMap(assertMap);

	    return new Object[][] {
	      new Object[] { 1, JSONObject.toJSONString(weather),"测试中文版本的天气预报-week字段值"

	      }
	    };
	 }


	  /**
     * 9天天气预报访问地址
     * @return
     */
	@DataProvider(name="forcast4NineUrl")
	  public static Object[][] forcast4NineUrl() {
	    return new Object[][] {
	      new Object[] { 1, "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType=${dataType}&lang=${lang}"},
	    };
	 }

	/**
	 * 天气预报数据类型和语言参数
	 * @return
	 */
	@DataProvider(name="testAccess200")
	  public static Object[][] testAccess200() {
	    return new Object[][] {
	      new Object[] { 1,"fnd","tc",200},
	      new Object[] { 1,"fnd","sc",200},
	      new Object[] { 1,"fnd","en",200},
	    };
	 }


	/**需要获取的预测天数，1代表明天，2代表后天
	 * 天气预报数据类型和语言参数
	 * @return
	 */
	@DataProvider(name="testWentByDay")
	  public static Object[][] testWentByDay() {
	    return new Object[][] {
	      new Object[] { 1,"fnd","tc",2},
	      new Object[] { 1,"fnd","sc",2},
	      new Object[] { 1,"fnd","en",2},
	      new Object[] { 1,"fnd","tc",1},
	      new Object[] { 1,"fnd","sc",1},
	      new Object[] { 1,"fnd","en",1},
	    };
	 }

}
