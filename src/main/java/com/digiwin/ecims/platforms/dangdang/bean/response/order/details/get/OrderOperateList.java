package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderOperateList {

	@XmlElement(name = "OperateInfo")
	private List<OperateInfo> operateInfos;

	public List<OperateInfo> getOperateInfos() {
		return operateInfos;
	}

	public void setOperateInfos(List<OperateInfo> operateInfos) {
		this.operateInfos = operateInfos;
	}

	

}