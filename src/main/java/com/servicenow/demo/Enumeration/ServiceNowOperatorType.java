package com.servicenow.demo.Enumeration;

public enum ServiceNowOperatorType {
	
	OR("^OR"), AND("^");

	private String id;
	
	private ServiceNowOperatorType (String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
