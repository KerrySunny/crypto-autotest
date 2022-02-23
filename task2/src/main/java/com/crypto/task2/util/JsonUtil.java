package com.crypto.task2.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

	public static Map fromJson2Map(String jsonString){
		if (jsonString == null) return new HashMap();

		return JSONObject.parseObject(jsonString,Map.class);
	}


	public static String fromMap2Json(Map jsonMap){

		return JSONObject.toJSONString(jsonMap);
	}




}
