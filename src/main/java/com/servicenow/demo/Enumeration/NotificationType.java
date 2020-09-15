package com.servicenow.demo.Enumeration;

public enum NotificationType {
	
	SMS("SMS"), CALL("CALL");

	private String id;
	
	private NotificationType (String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
