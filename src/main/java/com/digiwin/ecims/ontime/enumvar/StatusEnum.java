package com.digiwin.ecims.ontime.enumvar;

/**
 *  * 
 * StatusEnum (1有效和2无效)
 * @author aibo zeng
 *
 */

public enum StatusEnum {
	
	VALID("1","有效"),
	INVALID("2","无效"),
	PUSH("3","推送");
	
	private String code = "";
	private String name = "";
	private String desc = "";
	
	
	private StatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(String code) {
		for(StatusEnum each : StatusEnum.values()) {
			if(each.getCode().equals(code) )
				return each.getName();
		}
		return "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
