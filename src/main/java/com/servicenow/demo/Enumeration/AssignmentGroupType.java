package com.servicenow.demo.Enumeration;

public enum AssignmentGroupType {

	CHAT_BOT_GROUP("a0da54af1bcf14100ad60f6e6e4bcb00", "Chatbot_Group"),
	RPA_GROUP("90cad4af1bcf14100ad60f6e6e4bcbfb","RPA_Group");
	
	private String id;
	private String name;
	
	private AssignmentGroupType(String id, String name) {
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
}
