package com.digiwin.ecims.ontime.util;
/**
 * 查询条件校验
 * @author sux
 * @return false为空 true不为空
 */
public class ConditionValidate {
	
	public static boolean isEmpty(Object obj){
		if(obj == null || obj.equals("")){
			return true;
		}
		return false;
	}
	
	public static boolean isEmptyStr(Object obj){
		if(obj == null || obj.equals("")){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Object obj){
		if(obj == null || obj.equals("")){
			return false;
		}
		return true;
	}
}
