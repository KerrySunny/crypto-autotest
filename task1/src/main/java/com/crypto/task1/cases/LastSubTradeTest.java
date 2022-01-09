package com.crypto.task1.cases;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.crypto.task1.parameters.PageParameters;
import com.crypto.task1.util.PageUtil;

public class LastSubTradeTest {

	private static Logger log = Logger.getLogger(LastSubTradeTest.class);
	
	private WebDriver driver;
	
	@BeforeClass()
	public void init(){
		driver = PageParameters.getChromeDriver();
	}
	
	
	@Test(dataProvider = "index",description="进入交易主页",dataProviderClass=PageParameters.class)
	public void accessIndex(Integer n, String url){
		
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	@Test(description="去掉cookie弹框",dependsOnMethods={"accessIndex"})
	public void setCookie(){
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	
    	if(PageUtil.waitPage(driver,js)){
			WebElement cookie = driver.findElement(By.xpath("/html/body/div[1]"));
			if(null != cookie){
				js.executeScript("document.getElementsByClassName('optanon-alert-box-wrapper')[0].style.display='none'");
			}
			
		}
	}
	
	
	@Test(description="切换到目标交易类型",dependsOnMethods={"setCookie"},dataProvider = "getCro",dataProviderClass=PageParameters.class)
	public void getCro(Integer n, String elementPath,String module){
		
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	
		if(PageUtil.waitPage(driver,js)){
			WebElement Cro = driver.findElement(By.xpath(elementPath));
			Point p = Cro.getLocation();
			js.executeScript("scrollTo("+p.getX()+"," + p.getY() + ")");
			js.executeScript("arguments[0].click()",Cro);
			String expected= module;
			Assert.assertEquals(Cro.getText(),module , "选择交易类型成功");
		}
    	
	}
	
	
	@Test(description="进入子类型中最后一个交易页面",dependsOnMethods={"getCro"},dataProvider = "getLastSubTrade",dataProviderClass=PageParameters.class)
	public void getLastSubTrade(Integer n, String elementClass,String baseClass,String quoteClass,
			String subModule,String module,String btnClas){
		
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	
    	if(PageUtil.waitPage(driver,js)){
    		
    		//tbody__row
    		List<WebElement> trades  = driver.findElements(By.className(elementClass));
    		WebElement tradeDes = null;
    		for(WebElement trade:trades){
    			WebElement base = trade.findElement(By.className(baseClass));
    			WebElement quote = trade.findElement(By.className(quoteClass));
    			System.out.println(baseClass+"="+base.getText()+";"+quoteClass+"="+quote.getText());
    			if(subModule.equals(base.getText()) && module.equals(quote.getText())){
    				tradeDes = trade;
    				break;
    			}
    		}
    		
			WebElement trade  = tradeDes.findElement(By.className(btnClas));
			Point p = trade.getLocation();
			js.executeScript("scrollTo("+p.getX()+"," + p.getY() + ")");
			
			log.info("trade="+trade.getText());
			js.executeScript("arguments[0].click()",trade);
			String expected = "Trade";
			Assert.assertEquals(trade.getText(), expected, "进入交易页面成功");
		}
    	
	}
	
}
