package com.digiwin.ecims.platforms.pdd2.bean.domain.item;

import java.util.List;

public class GoodsInfo {

	    private String goods_id;
	    private String goods_sn;
	    private String goods_type;
	    private String goods_category;
	    private String last_category;
	    private String is_refundable;
	    private String shipment_limit_second;
	    private String goods_name;
	    private String image_url;
	    private int goods_quantity;
	    private String is_onsale;
	    private String group_required_customer_num;
	    private List<SkuList> sku_list;
	    public void setGoods_id(String goods_id) {
	         this.goods_id = goods_id;
	     }
	     public String getGoods_id() {
	         return goods_id;
	     }

	    public void setGoods_sn(String goods_sn) {
	         this.goods_sn = goods_sn;
	     }
	     public String getGoods_sn() {
	         return goods_sn;
	     }

	    public void setGoods_type(String goods_type) {
	         this.goods_type = goods_type;
	     }
	     public String getGoods_type() {
	         return goods_type;
	     }

	    public void setGoods_category(String goods_category) {
	         this.goods_category = goods_category;
	     }
	     public String getGoods_category() {
	         return goods_category;
	     }

	    public void setLast_category(String last_category) {
	         this.last_category = last_category;
	     }
	     public String getLast_category() {
	         return last_category;
	     }

	    public void setIs_refundable(String is_refundable) {
	         this.is_refundable = is_refundable;
	     }
	     public String getIs_refundable() {
	         return is_refundable;
	     }

	    public void setShipment_limit_second(String shipment_limit_second) {
	         this.shipment_limit_second = shipment_limit_second;
	     }
	     public String getShipment_limit_second() {
	         return shipment_limit_second;
	     }

	    public void setGoods_name(String goods_name) {
	         this.goods_name = goods_name;
	     }
	     public String getGoods_name() {
	         return goods_name;
	     }

	    public void setImage_url(String image_url) {
	         this.image_url = image_url;
	     }
	     public String getImage_url() {
	         return image_url;
	     }

	    public void setGoods_quantity(int goods_quantity) {
	         this.goods_quantity = goods_quantity;
	     }
	     public int getGoods_quantity() {
	         return goods_quantity;
	     }

	    public void setIs_onsale(String is_onsale) {
	         this.is_onsale = is_onsale;
	     }
	     public String getIs_onsale() {
	         return is_onsale;
	     }

	    public void setGroup_required_customer_num(String group_required_customer_num) {
	         this.group_required_customer_num = group_required_customer_num;
	     }
	     public String getGroup_required_customer_num() {
	         return group_required_customer_num;
	     }

	    public void setSku_list(List<SkuList> sku_list) {
	         this.sku_list = sku_list;
	     }
	     public List<SkuList> getSku_list() {
	         return sku_list;
	     }


}
