package com.servicenow.demo.Enumeration;

public enum NotificationStatusType {

	QUEUED("QUEUED", "queued"),
	COMPLETED("COMPLETED", "completed"),
	SMS_DELIVERED("DELIVERED","delivered"),
	FAILED("FAILED", "failed");
	
	private String id;
	private String name;
	
	private NotificationStatusType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static NotificationStatusType getById(String id) {
	    for(NotificationStatusType e : values()) {
	        if(e.id.equalsIgnoreCase(id)) return e;
	    }
	    return null;
	}
}
