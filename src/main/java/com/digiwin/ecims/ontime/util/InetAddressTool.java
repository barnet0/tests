package com.digiwin.ecims.ontime.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InetAddress[] iadds = InetAddressTool.getLocalIPs();
        for(InetAddress iadd : iadds){
        	System.out.println(iadd.getHostAddress());
        	System.out.println(iadd.getHostName());
        }
		System.out.println(InetAddressTool.getLocalIpv4());
	}
	
	/**
	 * 获取本机 IP  ipv4
	 * @return  192.168.42.29
	 */
//	public static String getLocalIpv4(){
//		String ip = "";
//		try {
//			ip = InetAddress.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		return ip;
//	}
	
	/**
	 * 获取本机 IP  ipv4
	 * @return
	 */
	public static String getLocalIpv4() {
		try {  
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();  
		    while (netInterfaces.hasMoreElements()) {
		        NetworkInterface ni = netInterfaces.nextElement();  
		        if (!ni.isUp() || ni.isVirtual() || ni.isLoopback()) {
		        	continue; //不是使用中的網卡
		        }
		        
//		        System.out.println("isUp:" + ni.isUp());
//		        System.out.println("isVirtual:" + ni.isVirtual());
//		        System.out.println("DisplayName:" + ni.getDisplayName());  
//		        System.out.println("Name:" + ni.getName());  

		        Enumeration<InetAddress> ips = ni.getInetAddresses();
		        while (ips.hasMoreElements()) {  
		        	InetAddress ia = ips.nextElement();
		        	if (!ia.isSiteLocalAddress()) {
		        		continue;
		        	}
//		        	System.out.println("isSiteLocalAddress:" + ia.isSiteLocalAddress());
//		        	System.out.println("isAnyLocalAddress:" + ia.isAnyLocalAddress());
//		        	System.out.println("isLinkLocalAddress:" + ia.isLinkLocalAddress());
//		        	System.out.println("isLoopbackAddress：" + ia.isLoopbackAddress());
//		        	System.out.println("isMulticastAddress：" + ia.isMulticastAddress());
//		        	System.out.println("isMCGlobal:" + ia.isMCGlobal());
//		            System.out.println("IP:" + ia.getHostAddress());  
		        	return ia.getHostAddress();
		        }
//		        System.out.println("---------------------------------");
		    }  
		    return "127.0.0.1";
		} catch (Exception e) {  
		    e.printStackTrace(); 
		    return "127.0.0.1";
		}  	
	}
	

	/**
	 * 获取本地主机ip列表 
	 * @return  
	 *  两个 192.168.42.29(ipv4)  fe80:0:0:0:f510:61a9:91b3:50fc%14(ipv6) 
	 */
	public static InetAddress[] getLocalIPs() {
		InetAddress[] mArLocalIP = null;
		try {
			mArLocalIP = InetAddress.getAllByName(InetAddress.getLocalHost()
					.getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mArLocalIP;
	}

}
