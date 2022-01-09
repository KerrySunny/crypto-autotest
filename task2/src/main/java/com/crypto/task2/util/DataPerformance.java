package com.crypto.task2.util;

public class DataPerformance {

	public static String getDataByUnit(String data,String unit){
		String suffix="";
		if("C".equals(unit)){
			suffix = "℃";
		}else if("percent".equals(unit)){
			suffix ="%";
		}
		return data+suffix;
	}
	
	public static String getDataByUnit(int data,String unit){
		String suffix="";
		if("C".equals(unit)){
			suffix = "℃";
		}else if("percent".equals(unit)){
			suffix ="%";
		}
		return data+suffix;
	}
	
	
}
