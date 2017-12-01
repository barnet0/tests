package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Activity {

	@XmlElement(name = "activity_id")
	private String activityId;
	
	@XmlElement(name = "activity_type")
	private String activityType;

	@XmlElement(name = "activity_name")
	private String activityName;

	public Activity(){
		
	}
	
	//icbc.order.detail用到
	public Activity(String activityId,String activityType,String activityName){
		this.activityId=activityId;
		this.activityType=activityType;
		this.activityName=activityName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	
}
