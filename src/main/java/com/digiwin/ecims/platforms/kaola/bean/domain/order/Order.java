package com.digiwin.ecims.platforms.kaola.bean.domain.order;


import java.util.List;

public class Order {
	private String buyer_account;
	private String buyer_phone;
	private int    order_status;
	private String order_id;
	private String receiver_name;
	private String receiver_phone;
	private String receiver_province_name;
	private String receiver_city_name;
	private String receiver_district_name;
	private String receiver_address_detail;
	private String receiver_post_code;
	private String pay_success_time;
	private String order_real_price;
	private String order_origin_price;
	private String express_fee;
	private String pay_method_name;
	private String coupon_amount;
	private String finish_time;	
	private String deliver_time;
	private List<OrderSku>  order_skus;
	private String cert_name;
	private String cert_id_no;
	private String tax_fee;
	private String trade_no;
	private int    need_invoice;
	private String invoice_amount;	
	private String invoice_title;
	private String waiting_deliver_time;
	private String waiting_cancel_time;
	private int    presale_order;
	private String pre_deliver_time;
	private List<OrderExpress> order_expresses;
	private String order_time;
	public String getBuyer_account() {
		return buyer_account;
	}
	public void setBuyer_account(String buyer_account) {
		this.buyer_account = buyer_account;
	}
	public String getBuyer_phone() {
		return buyer_phone;
	}
	public void setBuyer_phone(String buyer_phone) {
		this.buyer_phone = buyer_phone;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getReceiver_province_name() {
		return receiver_province_name;
	}
	public void setReceiver_province_name(String receiver_province_name) {
		this.receiver_province_name = receiver_province_name;
	}
	public String getReceiver_city_name() {
		return receiver_city_name;
	}
	public void setReceiver_city_name(String receiver_city_name) {
		this.receiver_city_name = receiver_city_name;
	}
	public String getReceiver_district_name() {
		return receiver_district_name;
	}
	public void setReceiver_district_name(String receiver_district_name) {
		this.receiver_district_name = receiver_district_name;
	}
	public String getReceiver_address_detail() {
		return receiver_address_detail;
	}
	public void setReceiver_address_detail(String receiver_address_detail) {
		this.receiver_address_detail = receiver_address_detail;
	}
	public String getReceiver_post_code() {
		return receiver_post_code;
	}
	public void setReceiver_post_code(String receiver_post_code) {
		this.receiver_post_code = receiver_post_code;
	}
	public String getPay_success_time() {
		return pay_success_time;
	}
	public void setPay_success_time(String pay_success_time) {
		this.pay_success_time = pay_success_time;
	}
	public String getOrder_real_price() {
		return order_real_price;
	}
	public void setOrder_real_price(String order_real_price) {
		this.order_real_price = order_real_price;
	}
	public String getOrder_origin_price() {
		return order_origin_price;
	}
	public void setOrder_origin_price(String order_origin_price) {
		this.order_origin_price = order_origin_price;
	}
	public String getExpress_fee() {
		return express_fee;
	}
	public void setExpress_fee(String express_fee) {
		this.express_fee = express_fee;
	}
	public String getPay_method_name() {
		return pay_method_name;
	}
	public void setPay_method_name(String pay_method_name) {
		this.pay_method_name = pay_method_name;
	}
	public String getCoupon_amount() {
		return coupon_amount;
	}
	public void setCoupon_amount(String coupon_amount) {
		this.coupon_amount = coupon_amount;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	public String getDeliver_time() {
		return deliver_time;
	}
	public void setDeliver_time(String deliver_time) {
		this.deliver_time = deliver_time;
	}
	public List<OrderSku> getOrder_skus() {
		return order_skus;
	}
	public void setOrder_skus(List<OrderSku> order_skus) {
		this.order_skus = order_skus;
	}
	public String getCert_name() {
		return cert_name;
	}
	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}
	public String getCert_id_no() {
		return cert_id_no;
	}
	public void setCert_id_no(String cert_id_no) {
		this.cert_id_no = cert_id_no;
	}
	public String getTax_fee() {
		return tax_fee;
	}
	public void setTax_fee(String tax_fee) {
		this.tax_fee = tax_fee;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public int getNeed_invoice() {
		return need_invoice;
	}
	public void setNeed_invoice(int need_invoice) {
		this.need_invoice = need_invoice;
	}
	public String getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getWaiting_deliver_time() {
		return waiting_deliver_time;
	}
	public void setWaiting_deliver_time(String waiting_deliver_time) {
		this.waiting_deliver_time = waiting_deliver_time;
	}
	public String getWaiting_cancel_time() {
		return waiting_cancel_time;
	}
	public void setWaiting_cancel_time(String waiting_cancel_time) {
		this.waiting_cancel_time = waiting_cancel_time;
	}
	public int getPresale_order() {
		return presale_order;
	}
	public void setPresale_order(int presale_order) {
		this.presale_order = presale_order;
	}
	public String getPre_deliver_time() {
		return pre_deliver_time;
	}
	public void setPre_deliver_time(String pre_deliver_time) {
		this.pre_deliver_time = pre_deliver_time;
	}
	public List<OrderExpress> getOrder_expresses() {
		return order_expresses;
	}
	public void setOrder_expresses(List<OrderExpress> order_expresses) {
		this.order_expresses = order_expresses;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
}

