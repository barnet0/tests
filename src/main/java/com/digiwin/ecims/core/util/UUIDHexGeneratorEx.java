package com.digiwin.ecims.core.util;

import java.util.Random;


/**
 * 产生全局唯一的字符串
 * @author aibo zeng
 *
 */

public class UUIDHexGeneratorEx {
	
   /**
    * 返回 32 个字符的 UUID
    * @return
    */
/*   public static String gen(){
	   return (String)(new UUIDHexGenerator().generate(null, null));
   }
*/   
   /**
    * 返回 16 个字符的 UUID : MM dd hh mm ss SSS + 三位随机数
    * 场合：POS使用的 session id 
    * @return
    */
   public static synchronized String gen16(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<3;i++){
		   sb.append(rand.nextInt(9));	      
	   }
	   return DateTimeTool.getToday("MMddHHmmssSSS")+sb.toString();
   }
   
   /**
    * 返回 18 个字符的 UUID : MM dd hh mm ss SSS + 三位随机数
    * 场合：上传文件的文件名 
    * @return
    */
   public static synchronized String gen18(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<3;i++){
		   sb.append(rand.nextInt(9));	      
	   }
	   return DateTimeTool.getToday("yyMMddHHmmssSSS")+sb.toString();
   }   
   /*
    * 产生 6个数字的随机码
    * 场合：临时验证码
    */
   public static String gen6Digital(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<6;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }
   
   /*
    * 产生 2个数字的随机码
    * 场合：银行帐户验证值
    */
   public static String gen2Digital(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<2;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }

   /*
    * 产生 n个数字的随机码
    * 场合：
    */
   public static String genDigital(int n){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<n;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }

   public static void main(String[] args){
	   System.out.println(UUIDHexGeneratorEx.gen16());
	   System.out.println(UUIDHexGeneratorEx.gen16());
	   System.out.println(UUIDHexGeneratorEx.gen16());
	   System.out.println(UUIDHexGeneratorEx.gen2Digital());
   }
   
}
