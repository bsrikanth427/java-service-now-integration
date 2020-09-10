package com.servicenow.demo.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.servicenow.demo.entity.ResponseSlaConfigEntity;
import com.servicenow.demo.repository.ResponseSlaRepository;

@Service
public class IncidentService {

	@Value(value = "${jobs.incident.frequency}")
	private String incidentJobFrequency;

	@Autowired
	ResponseSlaRepository responseSlaRepository;

	@Scheduled(cron = "${jobs.incident.frequency}")
	public void getIncidents() {
		System.out.println("Hello " + Instant.now());
	}
	
	


	public List<ResponseSlaConfigEntity> findResponseSlaConig() {
		return responseSlaRepository.findAll();
	}

}
