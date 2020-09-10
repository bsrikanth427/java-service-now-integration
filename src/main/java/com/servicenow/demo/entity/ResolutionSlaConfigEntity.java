package com.servicenow.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resolution_sla_config")
public class ResolutionSlaConfigEntity {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="assignment_group")
	private String assignmentGroup;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="escalation_level")
	private Integer escalationLevel;
	
	@Column(name="time_lapse")
	private Integer timeLapse;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="call_enabled")
	private String callEnabled;
	
	@Column(name="email_enabled")
	private String emailEnabled;
	
	@Column(name="max_time_lapse")
	private Integer maxTimeLapse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getEscalationLevel() {
		return escalationLevel;
	}

	public void setEscalationLevel(Integer escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	public Integer getTimeLapse() {
		return timeLapse;
	}

	public void setTimeLapse(Integer timeLapse) {
		this.timeLapse = timeLapse;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCallEnabled() {
		return callEnabled;
	}

	public void setCallEnabled(String callEnabled) {
		this.callEnabled = callEnabled;
	}

	public String getEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(String emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	public Integer getMaxTimeLapse() {
		return maxTimeLapse;
	}

	public void setMaxTimeLapse(Integer maxTimeLapse) {
		this.maxTimeLapse = maxTimeLapse;
	}
	
	
	
	
	
	
	
	
}
