package com.servicenow.demo.service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.servicenow.demo.NotificationDto;
import com.servicenow.demo.Enumeration.AssignmentGroupType;
import com.servicenow.demo.Enumeration.PriorityType;
import com.servicenow.demo.repository.ResponseSlaRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class MobileNotificationService {

	private static final Logger log = LoggerFactory.getLogger(MobileNotificationService.class);

	@Value(value = "${twilio.account.sid}")
	private String accountSid;

	@Value(value = "${twilio.auth.token}")
	private String authToken;

	@Value(value = "${twilio.from.number}")
	private String fromNumber;

	@Value(value = "${jobs.incident.frequency}")
	private String incidentJobFrequency;

	@Autowired
	ResponseSlaRepository responseSlaRepository;
	@Autowired
	DataSource dataSource;

	public void sendNotification(String toNumber, String msg, String notificationType) {
		Twilio.init(accountSid, authToken);
		try {
			if (notificationType.equalsIgnoreCase("SMS")) {
				Message message = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), msg).create();
				log.info(message.getSid());
			} else {
				Call call = Call.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber),
						new URI("http://demo.twilio.com/docs/voice.xml")).create();
				log.info(call.getSid());
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Scheduled(cron = "${jobs.response.sla.frequency}")
	public void notifyResponseSla() {
		log.info("Executing procedure Response-SLA-Config");
		executeProcedure("getResponseSlaConfig","Response");

	}
	
	@Scheduled(cron = "${jobs.resolution.sla.frequency}")
	public void notifyResolutionSla() {
		log.info("Executing procedure Resolution-SLA-Config");
		executeProcedure("getResolutionSlaConfig","Resolution");

	}
	
	@Scheduled(cron = "${jobs.test.frequency}")
	public void notifyTestSms() {
		log.info("Executing procedure Resolution-SLA-Config");
		executeProcedure("testProc","Response");

	}
	
	private void executeProcedure(String procedureName, String slaType) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);

		SqlParameterSource in = new MapSqlParameterSource();
		Map<String, Object> resultMap = jdbcCall.execute(in);

		List<Map<String, Object>> resList = (List<Map<String, Object>>) resultMap.get("#result-set-1");
		resList.stream().forEach(map -> {
			NotificationDto dto = prepareNotificationDto(map);
			if (dto.getSmsEnabled().equalsIgnoreCase("YES")) {
				String msg = prepareSmsMessage(dto.getPriority(), dto.getIncidentNumber(), dto.getAssignmentGroup(),
						dto.getMaxTimeLapsed(), slaType);
				sendNotification(dto.getMobileNumber(), msg, "SMS");
			}
			if (dto.getCallEnabled().equalsIgnoreCase("YES")) {
				sendNotification(dto.getMobileNumber(), null, "CALL");
			}
		});
		log.info(resultMap.toString());
	}

	private NotificationDto prepareNotificationDto(Map<String, Object> map) {
		NotificationDto dto = new NotificationDto();
		dto.setIncidentNumber(map.get("incident_number").toString());
		dto.setAssignedTo(map.get("assigned_to")!=null ? map.get("assigned_to").toString() : null);
		dto.setAssignmentGroup(map.get("assignment_group").toString());
		dto.setCallEnabled(map.get("call_enabled").toString());
		dto.setSmsEnabled(map.get("email_enabled").toString());
		dto.setMobileNumber(map.get("mobile_number").toString());
		dto.setMaxTimeLapsed((Integer)map.get("max_time_lapse"));
		BigDecimal calTimeLapsed = (BigDecimal) map.get("cal_time_lapsed");
		dto.setCalcuatedTimeLapsed(calTimeLapsed.intValue());
		dto.setPriority(map.get("priority").toString());
		return dto;
	}

	private String prepareSmsMessage(String priority, String incidentNumber, String grp, Integer timeLeft,
			String slaType) {
		priority = PriorityType.getById(Integer.parseInt(priority)).getName();
		grp = AssignmentGroupType.getById(grp).getName();
		String msg = "Hi - Priority " + priority + "Incident " + incidentNumber + " is received to Queue " + grp
				+ ". You have " + timeLeft + " minutes left to " + slaType + " to meet the desired SLA.";
		return msg;
	}

}
