package com.digiwin.ecims.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 序列号生成器
 * @author Administrator
 *
 */
public class SequenceBuilder {
	/**
	 * 获取序列号 = 年份+日期+小时+分钟+秒钟+四位随机数 = 18位
	 */
	public static String getSequence(){
		Calendar calendar = Calendar.getInstance();
		StringBuilder strBld = new StringBuilder();
		int year = calendar.get(Calendar.YEAR);
		strBld.append(year);
		int month = calendar.get(Calendar.MONTH)+1;
		if(month < 10){
			strBld.append("0").append(month);
		}else{
			strBld.append(month);
		}
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if(day < 10){
			strBld.append("0").append(day);
		}else{
			strBld.append(day);
		}
		
		int hour=calendar.get(Calendar.HOUR);//小时
       
		if(hour < 10){
			strBld.append("0").append(hour);
		}else{
			strBld.append(hour);
		}
		int minute = calendar.get(Calendar.MINUTE);//分           
	        
		if(minute < 10){
			strBld.append("0").append(minute);
		}else{
			strBld.append(minute);
		}
		
		int second = calendar.get(Calendar.SECOND);//秒 
		if(second < 10){
			strBld.append("0").append(second);
		}else{
			strBld.append(second);
		}
		
		String subfix = ""+((int)(Math.random()*9000)+1000);
		strBld.append(subfix);
		
		return strBld.toString();
	}
	
	public static void main(String[] args){
		System.out.println(getSequence());
	}
}
