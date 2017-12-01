package com.digiwin.ecims.core.util;

import java.util.Random;

public class StringToolUtils {
	
	/**
	 * 自动生成64位的支付令牌
	 * @return
	 */
	public static String base64(){
		char[] tempChar = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < 32; i++) {
			result.append(tempChar[new Random().nextInt(tempChar.length)]);
		}		
		//System.out.println("***********base64:"+result);
		return result.toString().trim();
	}

}
