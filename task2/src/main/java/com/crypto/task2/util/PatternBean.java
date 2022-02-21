package com.crypto.task2.util;

import java.util.Map;

public class PatternBean {
	
	private String pattern;
	
	private Object value;
	
	public PatternBean(){
		
	}
	
    public PatternBean(String pattern,Object value){
		this.pattern=pattern;
		this.value=value;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object getValue() {
		return getRespValue();
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * @Number.1234
	 * @return
	 */
	public Object getRespValue(){
		
		if(this.value != null){
			if(this.value.toString().startsWith("int@")){
				String str= this.value.toString().replace("int@", "");
				return Integer.parseInt(str);
			}else if(this.value.toString().startsWith("double@")){
				String doubleStr= this.value.toString().replace("double@", "");
				return Double.parseDouble(doubleStr);
			}
			
		}
		
		return this.value;
	}
	
	public Object getValueByDefineParam(Map paramDefine){
		if(this.value != null && paramDefine.containsKey(this.value)){
			this.value = paramDefine.get(this.value);
		}
		return this.value;
	}
    
}
