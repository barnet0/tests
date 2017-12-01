package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Item_image_list {
private int business_id;
private int id;

private String image_type;

private String image_url;

private int item_id;

private int order_value;

public void setBusiness_id(int business_id){
this.business_id = business_id;
}
public int getBusiness_id(){
return this.business_id;
}
public void setId(int id){
this.id = id;
}
public int getId(){
return this.id;
}
public void setImage_type(String image_type){
this.image_type = image_type;
}
public String getImage_type(){
return this.image_type;
}
public void setImage_url(String image_url){
this.image_url = image_url;
}
public String getImage_url(){
return this.image_url;
}
public void setItem_id(int item_id){
this.item_id = item_id;
}
public int getItem_id(){
return this.item_id;
}
public void setOrder_value(int order_value){
this.order_value = order_value;
}
public int getOrder_value(){
return this.order_value;
}

}
