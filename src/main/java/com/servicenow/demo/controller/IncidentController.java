package com.servicenow.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicenow.demo.service.IncidentService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
	
	@Autowired IncidentService incidentService;

	@GetMapping("/")
	public ResponseEntity<Void> getIncidentTickets() throws ClientProtocolException, IOException, URISyntaxException, JSONException, ParseException {
		incidentService.getIncidents();
		return null;
	}
}
