package com.crypto.task2.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PersonBean {

	private String test ;
	
	private Msg msg;
	
	private List list;
	

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Msg getMsg() {
		return msg;
	}

	public void setMsg(Msg msg) {
		this.msg = msg;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		PersonBean p = new PersonBean();
		p.setTest("123");
		Msg msg = new Msg();
		msg.setAge(28);
		msg.setName("Kerry");
		msg.setHeight(1.75);
		p.setMsg(msg);
		
		List list = new ArrayList();
		BeanTst tst = new BeanTst();
		tst.setName("lianglizou");
		list.add(tst);
		p.setList(list);
		
		PatternBean pattern = new PatternBean();
		pattern.setPattern("list[0].name");
		pattern.setValue("lianglizou");
		try {
			Object j = MatchUtil.getJsonValueByPattern(p,pattern);
			System.out.println(j.equals(pattern.getValue()));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}

class BeanTst{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
