package com.servicenow.demo.Enumeration;

public enum PriorityType {

	P1(1, "P1"), P2(2, "P2"), P3(3, "P3"), P4(4, "P4"), P5(5, "P5");
	
	private int id;
	private String name;
	
	PriorityType(int id, String name) {
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
	
	public static PriorityType getById(int id) {
	    for(PriorityType e : values()) {
	        if(e.id == id) return e;
	    }
	    return null;
	}
}
