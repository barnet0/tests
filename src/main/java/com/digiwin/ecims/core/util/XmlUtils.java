package com.digiwin.ecims.core.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class XmlUtils {
	
//	private static XmlUtils xu = null; // mark by mowj 20150928
	private static XmlUtils xu = new XmlUtils(); // add by mowj 20150928
	/**
	  * 此类使用单例模式，方法取得单例类对象
	  * */
	public static XmlUtils getInstance(){
		// mark by mowj 20150928 start
//		if(xu == null){
//			xu = new XmlUtils();
//		}
		// mark by mowj 20150928 end
		return xu;
	}

	public static void main(String[] args){
		
	}
	
	/**
	 * 以UTF-8编码序列化Java类到XMl字符串，没有XML头信息
	 * @param o
	 * @return
	 * @throws JAXBException 
	 */
	public String javaBean2Xml(Object o) throws JAXBException {
		return javaBean2Xml(o, "");
	}
	
	/**
	 * 以指定编码序列化Java类到XMl字符串，没有XML头信息
	 * @param object
	 * @return
	 * @throws JAXBException
	 */
	public String javaBean2Xml(Object object, String encoding) throws JAXBException {
	  return javaBean2Xml(object, encoding, true);
	}
	
	public String javaBean2Xml(Object object, String encoding, boolean ignoreXmlHead) throws JAXBException {
      JAXBContext context = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = context.createMarshaller();
      
//    是否省略xml头信息（<?xml version="1.0" encoding="UTF-8" standalone="yes"?>）
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, ignoreXmlHead);
      if (encoding.length() != 0) {
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
      }
      
      Writer xml = new StringWriter();
      //marshaller.marshal(request, System.out);
      marshaller.marshal(object, xml);
      //System.out.println(xml.toString());
      
      return xml.toString();
    }
	
	/**
	 * @throws JAXBException 
	 * xml2JavaBean
	 * 將Xml轉成javaBean
	 * @param args
	 * @throws  
	 */
	public Object xml2JavaBean(String xml,Class<?> class1) throws JAXBException{
		StringBuffer xmlStr = new StringBuffer(xml);
		Object o = null;
    	try {
			JAXBContext context = JAXBContext.newInstance(class1);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
		    o = unmarshaller.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
		    //System.out.println(o);
			
//			StringReader sr = new StringReader(xml);			
//			Response rep = (Response)unmarshaller.unmarshal(sr);
//			System.out.println(rep);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		}
		return o;
    }

}
