package com.digiwin.ecims.ontime.util;

import java.util.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4J {
	
	public static Document stringToXml(String str) {
		try {
			Document document = null;
			document = DocumentHelper.parseText(str);
			return document;
		} catch (DocumentException e) {
			String msg = e.getMessage();
			// Logger.log("In method stringToXml of
			// "+this.getClass().getName()+" : "+msg);
			return sub(str, msg);
		}
	}
	
	public static Document sub(String str, String msg) {
		char ch = '"';
		int a1 = msg.indexOf(ch);
		msg = msg.substring(a1 + 1);
		int b1 = msg.indexOf(ch);
		msg = msg.substring(0, b1);
		// int length=sss.length();
		String strall = null;

		/*
		 * while(true){ int c=str.indexOf(sss); if(c<=0){ break; } String
		 * str1=str.substring(0,c); String str2=str.substring(c+length);
		 * str=str1+str2; strall=str; }
		 */
		strall = replace(str, msg);

		System.out.println(strall);
		try {
			Document document = null;
			document = DocumentHelper.parseText(strall);
			return document;
		} catch (DocumentException e) {
			// Logger.log("In method sub of RReadfile : "+e.getMessage());
			return sub(str, e.getMessage());
		}
	}

	public static String replace(String str, String msg) {
		String strall = null;
		if (msg.equals("&#x13")) {
			strall = str.replaceAll(msg + ";", "!!");
		}
		if (msg.equals("&#x1A")) {
			strall = str.replaceAll(msg + ";", "->");
		}
		if (msg.equals("&#x01")) {
			strall = str.replaceAll(msg + ";", "");
		}
		return strall;
	}
	
	/**
	 * 构造xml时把某些字符转换一下，不然xml会解析不了
	 */
	public static String convertChar(String str){
	
		String xml = str.replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;")
						.replaceAll("&", "&amp;")
						.replaceAll("'", "&apos;")
						.replaceAll("\"", "&quot;")
						.replaceAll(":", "@lehua;");
		
		return xml;
	}
	
	/**
	 * 把xml中的特殊字符反转为应该的字符
	 */
	public static String InvertChar(String xml){
		
		String str = xml.replaceAll("&lt;","<")         
						.replaceAll("&gt;",">")         
						.replaceAll("&amp;","&")        
						.replaceAll("&apos;","'")       
						.replaceAll("&quot;","\"")      
						.replaceAll("@lehua;",":");   
		
		return str;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<HashMap> getDataValueList(Document document){
		Element root = document.getRootElement();
		Element ServiceEle=root.element("Service");
		Element Seperator = ServiceEle.element("Seperator");
		String defaultFlag=":";
		String SeperatorF=defaultFlag;
		if(null!=Seperator){
			SeperatorF=Seperator.getText();
		}
		else{
			SeperatorF=defaultFlag;
		}
		ArrayList<HashMap> dataList=null;
		dataList=new ArrayList<HashMap>();//数据

		Element data=ServiceEle.element("Data");
	    if(null!=data){		    	
	    	Element dataSet=data.element("DataSet");
	    	String fieldValue=dataSet.attributeValue("Field");
	    	StringTokenizer token=new StringTokenizer(fieldValue,SeperatorF);				
	    	ArrayList<String> pList=new ArrayList<String>();//字段属性
	    	while(token.hasMoreTokens()){
				String t=token.nextToken().trim();
				pList.add(t);
			}
			int pCount = pList.size();//字段个数
//			System.out.println("头属性的个数 = " + pCount);
			
			int index = 0;
			Iterator it = dataSet.elementIterator("Row");
			while(it.hasNext()){
				index++;
				
				try {
					Element element = (Element) it.next();
					String datas = element.attributeValue("Data");
					String[] token1 = datas.split(SeperatorF);
					HashMap<String,String> map=new HashMap<String,String>();
					int vCount = token1.length;//值的个数
					for (int i = 0; i < pCount; i++) {
						String t = "";
						if(i < vCount) t = InvertChar(token1[i].trim());
						map.put(pList.get(i), t);
					}
					dataList.add(map);
//					System.out.println("第[" + index + "]行数据 = " + datas+";属性的个数 = " + vCount);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			
//			System.out.println("数据总行数 = " + index);
		}else{
			String status = ServiceEle.elementText("Status");
			String error = ServiceEle.elementText("Error");
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("status", status);
			map.put("error", error);
			dataList.add(map);
		}
		return dataList;
	}
	
	public static List<HashMap> getDataValueListNew(Document document){
		Element root = document.getRootElement();
		Element ServiceEle=root.element("Service");
		Element Seperator = ServiceEle.element("Seperator");
		String defaultFlag=":";
		String SeperatorF=defaultFlag;
		if(null!=Seperator){
			SeperatorF=Seperator.getText();
		}
		else{
			SeperatorF=defaultFlag;
		}
		ArrayList<HashMap> dataList=null;
		dataList=new ArrayList<HashMap>();//数据
		String status = ServiceEle.elementText("Status");
		String error = ServiceEle.elementText("Error");
		HashMap<String,String> outMap=new HashMap<String,String>();
		Element data=ServiceEle.element("Data");
	    if(null!=data){		    	
	    	Element dataSet=data.element("DataSet");
			String fieldValue=dataSet.attributeValue("Field");
			StringTokenizer token=new StringTokenizer(fieldValue,SeperatorF);				
			ArrayList<String> pList=new ArrayList<String>();//字段属性
			while(token.hasMoreTokens()){
				String t=token.nextToken();
				pList.add(t);
			}
//			System.out.println("属性的个数:"+pList.size());
			Iterator it = dataSet.elementIterator("Row");
			while(it.hasNext()){
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("status", status);
				map.put("error", error);
				int k=0;
				Element element = (Element) it.next();
				String datas=element.attributeValue("Data");
				String[] token1 = datas.split(SeperatorF);
				System.out.println("getDataValueListNew**********MAP的数据个数:**********token1:"+token1.length);
				for(int i=0;i<token1.length;i++){
					String t=token1[i];
					map.put(pList.get(k), t);
					k++;
				}

				dataList.add(map);					
			}
		}else{
			outMap.put("status", status);
			outMap.put("error", error);
			dataList.add(outMap);
		}
		return dataList;
	}
	
	
	public static List<HashMap> getDataValueListPlus(Document document){
		Element root = document.getRootElement();
		Element ServiceEle=root.element("Service");
		Element Seperator = ServiceEle.element("Seperator");
		String defaultFlag=":";
		String SeperatorF=defaultFlag;
		if(null!=Seperator){
			SeperatorF=Seperator.getText();
		}
		else{
			SeperatorF=defaultFlag;
		}
		ArrayList<HashMap> dataList=null;
		dataList=new ArrayList<HashMap>();//数据
		String status = ServiceEle.elementText("Status");
		String error = ServiceEle.elementText("Error");
		HashMap<String,String> map=null;
		
		Element data=ServiceEle.element("Data");
	    if(null!=data){		    	
	    	Element dataSet=data.element("DataSet");
			String fieldValue=dataSet.attributeValue("Field");
			StringTokenizer token=new StringTokenizer(fieldValue,SeperatorF);				
			ArrayList<String> pList=new ArrayList<String>();//字段属性
			while(token.hasMoreTokens()){
				String t=token.nextToken();
				pList.add(t);
			}
//			System.out.println("属性的个数:"+pList.size());
			/*
			Iterator it = dataSet.elementIterator("Row");
			while(it.hasNext()){
				int k=0;
				Element element = (Element) it.next();
				String datas=element.attributeValue("Data");
				StringTokenizer token1=new StringTokenizer(datas,SeperatorF);
				System.out.println("**********MAP的数据个数:**********token1:"+token1.countTokens());
				map=new HashMap<String,String>();
				map.put("status", status);
				map.put("error", error);
				while(token1.hasMoreTokens()){
					String t=token1.nextToken();
					map.put(pList.get(k), t);
					k++;
				}
//				System.out.println("属性的个数是k:"+k);
				dataList.add(map);					
			}
			*/
			Iterator it = dataSet.elementIterator("Row");
			while(it.hasNext()){
				map=new HashMap<String,String>();
				map.put("status", status);
				map.put("error", error);
				int k=0;
				Element element = (Element) it.next();
				String datas=element.attributeValue("Data");
				String[] token1 = datas.split(SeperatorF);
				System.out.println("getDataValueListPlus**********MAP的数据个数:**********token1:"+token1.length);
				for(int i=0;i<token1.length;i++){
					String t=token1[i];
					map.put(pList.get(k), t);
					k++;
				}

				dataList.add(map);					
			}
		}else{
			dataList.add(map);
		}
		return dataList;
	}
	
	
	

	/**
	 * @param args
	 */
	public static void main(String args[]){
		
		String str = "<STD_IN Origin=\"TIPTOP\"><Service Name=\"SetData\"><ObjectID>DeliveryNote</ObjectID><ServiceId>TIPTOP</ServiceId><Operate>ADJUST</Operate><Seperator>:</Seperator><User>EC_USER</User><Factory>ARROW</Factory><IgnoreError>N</IgnoreError><Data Format=\"Join\"><DataSet Field=\"formType:formName:formNum:ecFormNum:date:totalQty: type:carNumber:dealerId:dealerName:status:lineId:productId:productName: spec:color:colorNumber:unit:quantity:remark:orderNum:deduct:orderItem\"><Row Data=\"11G: :11G4-13110001:SOR-131113-000055:13/11/23:1.000:1: :01BJBJ:北京乐华:06:1:106103930001521:APG10L393P 浴室柜:995*508*620:亮光白+浅灰印花: :PCS:1.000: :1S22-13110002:1:1\" /></DataSet></Data></Service></STD_IN>";
		
		try {
			Document document = DocumentHelper.parseText(str);
			Element root = document.getRootElement();
			Element ServiceEle=root.element("Service");
			Element Seperator = ServiceEle.element("Seperator");
			String defaultFlag=":";
			String SeperatorF=defaultFlag;
			if(null!=Seperator){
				SeperatorF=Seperator.getText();
			}
			else{
				SeperatorF=defaultFlag;
			}
			ArrayList<HashMap> dataList=null;
			dataList=new ArrayList<HashMap>();//数据

			Element data=ServiceEle.element("Data");
		    if(null!=data){		    	
		    	Element dataSet=data.element("DataSet");
		    	String fieldValue=dataSet.attributeValue("Field");
		    	StringTokenizer token=new StringTokenizer(fieldValue,SeperatorF);				
		    	ArrayList<String> pList=new ArrayList<String>();//字段属性
		    	while(token.hasMoreTokens()){
					String t=token.nextToken().trim();
					pList.add(t);
				}
				int pCount = pList.size();//字段个数
				System.out.println("头属性的个数 = " + pCount);
				
				int index = 0;
				Iterator it = dataSet.elementIterator("Row");
				while(it.hasNext()){
					index++;
					
					try {
						Element element = (Element) it.next();
						String datas = element.attributeValue("Data");
						String[] token1 = datas.split(SeperatorF);
						HashMap<String,String> map=new HashMap<String,String>();
						int vCount = token1.length;//值的个数
						for (int i = 0; i < pCount; i++) {
							String t = "";
							if(i < vCount) t = InvertChar(token1[i].trim());
							map.put(pList.get(i), t);
						}
						dataList.add(map);
						System.out.println("第[" + index + "]行数据 = " + datas+";属性的个数 = " + vCount);
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				
				System.out.println("数据总行数 = " + index);
			}else{
				String status = ServiceEle.elementText("Status");
				String error = ServiceEle.elementText("Error");
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("status", status);
				map.put("error", error);
				dataList.add(map);
			}
		    
//			return dataList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
