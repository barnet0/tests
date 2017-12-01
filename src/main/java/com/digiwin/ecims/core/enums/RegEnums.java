package com.digiwin.ecims.core.enums;

public enum RegEnums {

	NUMBER("[0-9]*"),
	
	FEE("[0-9]*|[0-9]*.[0-9]*");
	
	private String regExpString;
	
	RegEnums(String regExpString) {
		this.regExpString = regExpString;
	}
	
	/**
	 * @return the regExpString
	 */
	public String getRegExpString() {
		return regExpString;
	}
	
}
