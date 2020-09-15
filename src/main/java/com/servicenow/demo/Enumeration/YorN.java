package com.servicenow.demo.Enumeration;

public enum YorN {

	YES("YES"), NO("NO");

	private String id;
	
	private YorN (String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
