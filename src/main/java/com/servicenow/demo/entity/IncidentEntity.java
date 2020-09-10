package com.servicenow.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="incident")
public class IncidentEntity {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="created_date_time")
	private Date createdDateTime;
	
	@Column(name="assignment_group")
	private String assignmentGroup;
	
	@Column(name="status")
	private String status;
	
	
	@Column(name="updated_date_time")
	private Date updatedDateTime;
	
	@Column(name="assigned_to")
	private String assignedTo;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="severity")
	private String severity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
}
