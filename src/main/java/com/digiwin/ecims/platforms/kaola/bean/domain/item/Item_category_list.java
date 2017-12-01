package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Item_category_list {
	private int business_id;

	private int category_id;

	private int id;

	private int item_id;

	private int rank;

	public void setBusiness_id(int business_id){
	this.business_id = business_id;
	}
	public int getBusiness_id(){
	return this.business_id;
	}
	public void setCategory_id(int category_id){
	this.category_id = category_id;
	}
	public int getCategory_id(){
	return this.category_id;
	}
	public void setId(int id){
	this.id = id;
	}
	public int getId(){
	return this.id;
	}
	public void setItem_id(int item_id){
	this.item_id = item_id;
	}
	public int getItem_id(){
	return this.item_id;
	}
	public void setRank(int rank){
	this.rank = rank;
	}
	public int getRank(){
	return this.rank;
	}
}
