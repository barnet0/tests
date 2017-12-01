package com.digiwin.ecims.system.enums;

/**
 * 参照苏宁的OrderOperateType，定义系统的同步或推送方法的执行类型
 * @author 维杰
 * @since 2015.10.12
 */
public enum WorkOperateTypeEnum {
	/**
	 * 手动执行 
	 */
	IS_MANUALLY("Manually"),
	
	/**
	 * 排程定时执行
	 */
	IS_SCHEDULE("Schedule"),
	
	/**
	 * 检查排程定时执行 
	 */
	IS_CHECK_SCHEDULE("CheckSchedule");
	
	private String name;
	private WorkOperateTypeEnum(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
