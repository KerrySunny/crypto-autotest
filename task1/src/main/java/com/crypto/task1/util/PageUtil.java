package com.crypto.task1.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PageUtil {
	  public static boolean waitPage(WebDriver driver,JavascriptExecutor js){
	    	String winState = (String)js.executeScript(PageCons.RETWEEN_WINDOW_STATE);
	    	//!PageCons.WINDOW_STATE_INTERACTIVE.equals(winState) && 
	    	while(!PageCons.WINDOW_STATE_COMPLETE.equals(winState)){//
				winState = (String)js.executeScript(PageCons.RETWEEN_WINDOW_STATE);
				System.out.println("winState="+winState);
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			}
	    	return true;
	    }
	    
	    public static void smoothlyScrolling(JavascriptExecutor js, int start, int speed, int end, boolean direction) {
	        if (direction) {
	            for (int i = start; i <= end; i += speed) {
	                js.executeScript("scrollTo(0," + i + ")");
	            }
	        } else {
	            for (int i = end; i >= start; i -= speed) {
	                js.executeScript("scrollTo(0," + i + ")");
	            }
	        }
	    }
}
