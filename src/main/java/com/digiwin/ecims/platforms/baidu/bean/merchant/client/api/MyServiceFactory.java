package com.digiwin.ecims.platforms.baidu.bean.merchant.client.api;

public class MyServiceFactory {

  public static MyOrderService createOrderService(String url)
  {
    MyOrderService cs = new MyOrderService(url);
    return cs;
  }
  
  public static MyItemService createItemService(String url)
  {
    MyItemService cs = new MyItemService(url);
    return cs;
  }
}
