package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EnergySubsidy {
	
	@XmlElement(name = "individual_or_company")
	private String individual_or_company;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "code")
	private String code;
	
	@XmlElement(name = "bank")
	private String bank;
	
	@XmlElement(name = "banking_account")
	private String banking_account;
	
	public EnergySubsidy() {
		
	}

	public String getIndividual_or_company() {
		return individual_or_company;
	}

	public void setIndividual_or_company(String individual_or_company) {
		this.individual_or_company = individual_or_company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBanking_account() {
		return banking_account;
	}

	public void setBanking_account(String banking_account) {
		this.banking_account = banking_account;
	}

		
}