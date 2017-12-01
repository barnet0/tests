package com.digiwin.ecims.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 处理数字
 * 
 * 包括金额
 * 
 * @author aibozeng
 *
 */

public class NumberTool {

	/**
	 * 金额转成为两位小数的字符串
	 * 场合：销售单金额，传给网银
	 * @param money
	 * @return
	 */
	public static String moneyToStr(float money){
		DecimalFormat nf = new DecimalFormat("0.00");
		return nf.format(money);
	}
	
	/**
	 * 固定只保留两位小数
	 * 网银传回来的，转成 double
	 * @param money
	 * @return
	 */
	public static double strToMoney(String money){
		DecimalFormat nf = new DecimalFormat("#0.00");
		Number number = null;
		try {
			number = nf.parse(money);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double retF =  Math.floor(number.floatValue()*100.0)/100;
		return retF;	
	}
	
	public static double toMoney(double money){
		return Math.floor(money*100.0)/100;
	}
	
	public static void main(String[] args){
		//传给网银
		System.out.println(NumberTool.moneyToStr(9000.04f));
		
		//网银传回来的
		System.out.println(NumberTool.strToMoney("9000.04"));
		
		//从数据库取出的
		System.out.println(NumberTool.toMoney(9000.04001f));
		
	}
	
	public static int toInteger (String str){
		if (str == null || str.trim().equals("")){
			return 0;
		} else {
			return Integer.parseInt(str.trim());
		}
	}
	
	public static long toLong (String str){
		if (str == null || str.trim().equals("")){
			return 0;
		} else {
			return Long.parseLong(str.trim());
		}
	}
	
	public static short toShort (String str){
		if (str == null || str.trim().equals("")){
			return 0;
		} else {
			return Short.parseShort(str.trim());
		}
	}
	
	public static BigDecimal getBigDecimal(String obj){
		BigDecimal big =new BigDecimal(0);
		if (obj ==null || "".equals(obj.trim())){
			return new BigDecimal(0);
		}
		else{
			try{
				big = new BigDecimal(obj);
			}catch(Exception e){
				
			}
		}
		
		return big;
	}
	
	
	public static BigDecimal getBigDecimal(Long obj){
		BigDecimal big =new BigDecimal(0);
		if (obj ==null ){
			return new BigDecimal(0);
		}
		else{
			try{
				big = new BigDecimal(obj);
			}catch(Exception e){
				
			}
		}
		
		return big;
	}
}
