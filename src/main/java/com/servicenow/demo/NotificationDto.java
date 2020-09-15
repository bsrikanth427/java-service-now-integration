package com.servicenow.demo;

public class NotificationDto {

	 
	private String incidentNumber;
	private String assignedTo;
	private String assignmentGroup;
	private String callEnabled;
	private String smsEnabled;
	private String mobileNumber;
	private Integer maxTimeLapsed;
	private Integer calcuatedTimeLapsed;
	private String priority;
	private String smsMessage;
	
	public String getIncidentNumber() {
		return incidentNumber;
	}
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssignmentGroup() {
		return assignmentGroup;
	}
	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	public String getCallEnabled() {
		return callEnabled;
	}
	public void setCallEnabled(String callEnabled) {
		this.callEnabled = callEnabled;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}
	public String getSmsEnabled() {
		return smsEnabled;
	}
	public void setSmsEnabled(String smsEnabled) {
		this.smsEnabled = smsEnabled;
	}
	public Integer getMaxTimeLapsed() {
		return maxTimeLapsed;
	}
	public void setMaxTimeLapsed(Integer maxTimeLapsed) {
		this.maxTimeLapsed = maxTimeLapsed;
	}
	public Integer getCalcuatedTimeLapsed() {
		return calcuatedTimeLapsed;
	}
	public void setCalcuatedTimeLapsed(Integer calcuatedTimeLapsed) {
		this.calcuatedTimeLapsed = calcuatedTimeLapsed;
	}
	
	
}
