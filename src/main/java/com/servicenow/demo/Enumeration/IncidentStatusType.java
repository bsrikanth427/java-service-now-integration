package com.servicenow.demo.Enumeration;

public enum IncidentStatusType {

	NEW(1, "New"), IN_PROGRESS(2, "In Progress"), ON_HOLD(3, "On Hold"), RESOLVED(6, "Resolved"),
	CLOSED(7, "Closed"), CANCELED(8, "Canceled");
	
	private int id;
	private String name;
	
	IncidentStatusType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static IncidentStatusType getById(int id) {
	    for(IncidentStatusType e : values()) {
	        if(e.id == id) return e;
	    }
	    return null;
	}
	
	
	
}
