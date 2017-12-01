package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Saleproperty {

	@XmlElement(name = "sale_prop_name_id")
	private String sale_prop_name_id;

	@XmlElement(name = "sale_prop_name")
	private String sale_prop_name;

	@XmlElement(name = "sale_prop_value_id")
	private String sale_prop_value_id;

	@XmlElement(name = "sale_prop_value")
	private String sale_prop_value;

	public Saleproperty() {

	}

	public Saleproperty(String sale_prop_name_id, String sale_prop_name,
			String sale_prop_value_id, String sale_prop_value) {
		super();
		this.sale_prop_name_id = sale_prop_name_id;
		this.sale_prop_name = sale_prop_name;
		this.sale_prop_value_id = sale_prop_value_id;
		this.sale_prop_value = sale_prop_value;
	}

	public String getSale_prop_name_id() {
		return sale_prop_name_id;
	}

	public void setSale_prop_name_id(String sale_prop_name_id) {
		this.sale_prop_name_id = sale_prop_name_id;
	}

	public String getSale_prop_name() {
		return sale_prop_name;
	}

	public void setSale_prop_name(String sale_prop_name) {
		this.sale_prop_name = sale_prop_name;
	}

	public String getSale_prop_value_id() {
		return sale_prop_value_id;
	}

	public void setSale_prop_value_id(String sale_prop_value_id) {
		this.sale_prop_value_id = sale_prop_value_id;
	}

	public String getSale_prop_value() {
		return sale_prop_value;
	}

	public void setSale_prop_value(String sale_prop_value) {
		this.sale_prop_value = sale_prop_value;
	}

}
