package com.digiwin.ecims.ontime.enumvar;

/**
 * 分组信息。不同运行环境的子系统可以按 Group 分别执行
 * 
 * @author lizhi
 *
 */

public enum TaskGroupTypeEnum {
	Platform("1","PlatformEnv","内部管理系统"),
	WS("2","WebServiceEnv","系统对接"),
	
    NULL("","","");
    
    private String code = "";
	private String name = "";
	private String desc = "";
	
	private TaskGroupTypeEnum(String code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}

	public static String getName(String code) {
		for(TaskGroupTypeEnum each : TaskGroupTypeEnum.values()) {
			if(each.getCode().equals(code) )
				return each.getName();
		}
		return "";
	}

	public static String getDesc(String code) {
		for(TaskGroupTypeEnum each : TaskGroupTypeEnum.values()) {
			if(each.getCode().equals(code) )
				return each.getDesc();
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	    
}
