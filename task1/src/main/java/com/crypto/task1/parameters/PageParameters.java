package com.crypto.task1.parameters;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;

public class PageParameters {
	  
    /**
     * 访问地址
     * @return
     */
	@DataProvider(name="index")
	  public static Object[][] index() {
	    return new Object[][] {
	      new Object[] { 1, "https://crypto.com/exchange/markets"},
	    };
	 }
	
	/**
	 * 交易类型 xpath路径，交易类型代码
	 * @return
	 */
	@DataProvider(name="getCro")
	  public static Object[][] getCro() {
	    return new Object[][] {
	      new Object[] { 1, "//*[@id=\"app\"]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div/div[6]","CRO"},
	    };
	 }
	
	/**
	 * 表格行class选择器，子类型class选择器，父类型备注选择器，目标子类型值，父类型值，交易按钮class选择器
	 * @return
	 */
	@DataProvider(name="getLastSubTrade")
	  public static Object[][] getLastSubTrade() {
	    return new Object[][] {
//	      new Object[] { 1, "tbody__row","base","quote","ETH","/CRO","btn-trade"},
	      new Object[] { 1, "tbody__row","base","quote","XRP","/CRO","btn-trade"},
	    };
	 }
	
	/**
	 * 表格行class选择器，子类型class选择器，父类型备注选择器，目标子类型值，父类型值，交易按钮class选择器
	 * @return
	 */
	@DataProvider(name="getFirstSubTrade")
	  public static Object[][] getFirstSubTrade() {
	    return new Object[][] {
	      new Object[] { 1, "tbody__row","base","quote","ETH","/CRO","btn-trade"},
	    };
	 }
	
	
	/**
	 * 获取浏览器驱动
	 * @return
	 */
	public static WebDriver getChromeDriver(){
		ChromeOptions op = new ChromeOptions();
    	op.setPageLoadStrategy(PageLoadStrategy.NONE);//NONE  EAGER NORMAL
    	WebDriver driver = new ChromeDriver(op);
		return driver;
	}
}
