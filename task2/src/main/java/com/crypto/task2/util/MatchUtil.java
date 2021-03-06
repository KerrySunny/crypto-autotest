package com.crypto.task2.util;

import java.util.List;
import java.util.Map;

public class MatchUtil {

	/**
	 * data.status
	 * data.list[0].name
	 * data.enum  [1,2,3]
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Object getJsonValueByPattern(Object obj,PatternBean pattern) throws Exception{
		//.先分割，然后才是[]来分割
		//a-zA-Z：字段名,直接获取值
		//包含[],说明是list,先根据字段名获取对象，再根据下标获取值
		//获取的值不是基本数据类型或者是String 则报异常
		//如果值是null，则不再匹配，直接报异常

		String[] patt = pattern.getPattern().split("\\.");
		Object tmp = obj;//
		for(int i =0;i<patt.length;i++){
			String fieldName = patt[i];
			int index = fieldName.indexOf("[");

			if(index!= -1){
				String indexStr = fieldName.substring(index,fieldName.length());
				boolean isCorrectIndex = indexStr.matches("\\[(\\d)(.*)\\]$");
				if (!isCorrectIndex) throw new Exception();

				indexStr = indexStr.replace("[", "");
				indexStr = indexStr.replace("]", "");
				int indexNum = Integer.parseInt(indexStr);

				fieldName = fieldName.substring(0,index);
				tmp =  ReflectUtil.getValueByField(tmp, fieldName);
				List list = (List)tmp;
				tmp = list.get(indexNum);

			}else{
				tmp =  ReflectUtil.getValueByField(tmp, fieldName);
			}



		}

		return tmp;
	}


	public static Object getJsonValueByPattern(Map obj, String pattern) throws Exception{
		//.先分割，然后才是[]来分割
		//a-zA-Z：字段名,直接获取值
		//包含[],说明是list,先根据字段名获取Map，再根据下标获取值
		//获取的值不是基本数据类型或者是String 则报异常
		//如果值是null，则不再匹配，直接报异常
		// Map  list base-data

		String[] patt = pattern.split("\\.");
		Map tmp = obj;//
		for(int i =0;i<patt.length-1;i++){
			String fieldName = patt[i];
			int index = fieldName.indexOf("[");

			if(index!= -1){
				String indexStr = fieldName.substring(index,fieldName.length());
				boolean isCorrectIndex = indexStr.matches("\\[(\\d)(.*)\\]$");
				if (!isCorrectIndex) throw new Exception("数组下标非法，参照data.product[0]");

				indexStr = indexStr.replace("[", "");
				indexStr = indexStr.replace("]", "");
				int indexNum = Integer.parseInt(indexStr);

				fieldName = fieldName.substring(0,index);
				List<Map> list =(List<Map>)tmp.get(fieldName);
				tmp = list.get(indexNum);

			}else{
				tmp =  (Map)tmp.get(fieldName);
			}
		}

		if(tmp == null) throw new Exception(pattern+":无字段匹配");

		return  tmp.get(patt[patt.length-1]);
	}

	public static void main(String[] args) {
		String str = "[10]";
		boolean b = str.matches("\\[(\\d)(.*)\\]$");
		System.out.println(b);


	}
}
