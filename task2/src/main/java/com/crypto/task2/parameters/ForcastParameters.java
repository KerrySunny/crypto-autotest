package com.crypto.task2.parameters;

import org.testng.annotations.DataProvider;

public class ForcastParameters {
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
