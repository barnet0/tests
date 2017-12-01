package com.digiwin.ecims.platforms.kaola.bean.response.order;

import com.digiwin.ecims.platforms.kaola.bean.domain.order.GetOrder;

import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;



public class OrderGetResponse extends KaolaBaseResponse{

	
	private GetOrder order;

	public void OrderGet(GetOrder order){
	this.order = order;
	}
	public GetOrder getOrder(){
	return this.order;
	}


}
