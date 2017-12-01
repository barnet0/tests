package com.digiwin.ecims.core.util;

/**
 * 
 * @author Sen
 *
 */
public class StringFormat {

	public StringFormat(){}
	
	/**
	 * 為了因應使用者不知道java class命名規則
	 * @param name 使用者認定的 domainObject Name
	 * @return 轉成java class命名規則的class  name
	 */
	public static String classNameFormat(String name){
		String[] nameArray=name.split("_");//有底線的話開頭第一個又要是大寫了
		StringBuffer className=new StringBuffer();
		for(String singleName:nameArray){
			singleName=singleName.toLowerCase();//我管你的全部先變小寫
			className.append(singleName=singleName.toUpperCase().charAt(0) +singleName.substring(1));		//轉小寫後我只把第一碼變大寫
			//拿第0個轉大寫，再把0之後1開始的組回來
		}
		return className.toString();
	}
	
	public static String propertyNameFormat(String name){
		String[] nameArray=name.split("_");//有底線的話開頭第一個又要是大寫了
		StringBuffer propertyName=new StringBuffer();
		propertyName.append(nameArray[0]=nameArray[0].toLowerCase());
		for(int i=1;i<nameArray.length;i++){
			nameArray[i]=nameArray[i].toLowerCase();//我管你的全部先變小寫
			propertyName.append(nameArray[i]=nameArray[i].toUpperCase().charAt(0) +nameArray[i].substring(1));		//轉小寫後我只把第一碼變大寫
			//拿第0個轉大寫，再把0之後1開始的組回來
		}
		return propertyName.toString();
	}
	
	public static String[] propertyTypeArray(String columnType){
		String[] type=new String[2];
		switch(columnType){
		case "nvarchar"	:{
			type[0]="String ";
			type[1]="=\"\";";
			break;
		}
		case "numeric" :{
			type[0]="Double ";
			type[1]="=0.0;";
			break;
		}
		case "datetime" :{
			type[0]="Date ";
			type[1]=";";
			break;
		}
		case "int" :{
			type[0]="Integer ";
			type[1]="=0;";
		}
		default :{
			type[0]=classNameFormat(columnType)+"PK ";
			type[1]=";";
		}
	}
		return type;
	}
}
