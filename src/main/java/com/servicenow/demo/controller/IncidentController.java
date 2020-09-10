package com.servicenow.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicenow.demo.entity.ResponseSlaConfigEntity;
import com.servicenow.demo.service.IncidentService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
	
	@Autowired IncidentService incidentService;

	@GetMapping("/")
	public ResponseEntity<List<ResponseSlaConfigEntity>> getIncidentTickets() {
		return new ResponseEntity<List<ResponseSlaConfigEntity>>(incidentService.findResponseSlaConig(), HttpStatus.OK);
	}
}
