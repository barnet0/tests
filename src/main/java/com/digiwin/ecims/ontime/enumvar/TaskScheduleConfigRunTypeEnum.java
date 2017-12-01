package com.digiwin.ecims.ontime.enumvar;

public enum TaskScheduleConfigRunTypeEnum {
	PUSH("PUSH"),
	PULL("PULL");
	
	private String runType;
	
	TaskScheduleConfigRunTypeEnum(String runType) {
		this.runType = runType;
	}

	public String getRunType() {
		return runType;
	}
	
}
