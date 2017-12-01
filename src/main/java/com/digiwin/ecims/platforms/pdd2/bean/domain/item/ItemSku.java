package com.digiwin.ecims.platforms.pdd2.bean.domain.item;


public class ItemSku {

	    private String spec;
	    private int sku_id;
	    private int sku_quantity;
	    private String outer_id;
	    public void setSpec(String spec) {
	         this.spec = spec;
	     }
	     public String getSpec() {
	         return spec;
	     }

	    public void setSku_id(int sku_id) {
	         this.sku_id = sku_id;
	     }
	     public int getSku_id() {
	         return sku_id;
	     }

	    public void setSku_quantity(int sku_quantity) {
	         this.sku_quantity = sku_quantity;
	     }
	     public int getSku_quantity() {
	         return sku_quantity;
	     }

	    public void setOuter_id(String outer_id) {
	         this.outer_id = outer_id;
	     }
	     public String getOuter_id() {
	         return outer_id;
	     }
  
  
}
