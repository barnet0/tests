package com.digiwin.ecims.platforms.baidu.bean.merchant.client.model;

public class Request<E>
{
  E body;
  RequestHeader header;
  
  public E getBody()
  {
    return (E)this.body;
  }
  
  public void setBody(E body)
  {
    this.body = body;
  }
  
  public RequestHeader getHeader()
  {
    return this.header;
  }
  
  public void setHeader(RequestHeader header)
  {
    this.header = header;
  }
}
