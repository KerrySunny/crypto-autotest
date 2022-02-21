package com.crypto.task2.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
	

	public static Object getValueByField(Object obj,String fieldName){
		PropertyDescriptor pro;
		try {
			pro = new PropertyDescriptor(fieldName, obj.getClass());
			Method me = pro.getReadMethod();
			return me.invoke(obj);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
