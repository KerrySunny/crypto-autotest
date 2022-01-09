package com.crypto.task2.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	
	public static Map fromJson2Map(String jsonString){
		
		return (Map)JSON.parse(jsonString);
	}
	
	
	public static Map fromMap2Json(Map jsonMap){
		
		return (Map)JSON.toJSON(jsonMap);
	}
	
	
}
