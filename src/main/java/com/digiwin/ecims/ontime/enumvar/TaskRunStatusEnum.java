package com.digiwin.ecims.ontime.enumvar;

/**
 * 定时作业当前运行状态
 * 
 * @author lizhi
 *
 */

public enum TaskRunStatusEnum {
	NO_LOAD("1","未加载","不在定时调度里"),
	RUNNING("2","运行中","在定时调度里运行"),
    PAUSED("3","已暂停","在定时调度里非激活状态"),
	
    NULL("","","");
    
    private String code = "";
	private String name = "";
	private String desc = "";
	
	private TaskRunStatusEnum(String code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}

	public static String getName(String code) {
		for(TaskRunStatusEnum each : TaskRunStatusEnum.values()) {
			if(each.getCode().equals(code) )
				return each.getName();
		}
		return "";
	}

	public static String getDesc(String code) {
		for(TaskRunStatusEnum each : TaskRunStatusEnum.values()) {
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
