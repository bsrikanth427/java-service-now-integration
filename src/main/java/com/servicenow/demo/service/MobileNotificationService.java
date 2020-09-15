package com.servicenow.demo.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
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
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Range;
import com.servicenow.demo.Dto.NotificationDto;
import com.servicenow.demo.Enumeration.AssignmentGroupType;
import com.servicenow.demo.Enumeration.NotificationStatusType;
import com.servicenow.demo.Enumeration.NotificationType;
import com.servicenow.demo.Enumeration.PriorityType;
import com.servicenow.demo.Enumeration.YorN;
import com.servicenow.demo.entity.SmsCallInfoEntity;
import com.servicenow.demo.repository.ResponseSlaRepository;
import com.servicenow.demo.repository.SmsCallInfoRepository;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
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
	SmsCallInfoRepository smsCallInfoRepository;

	@Autowired
	DataSource dataSource;

	public String sendNotification(String toNumber, String msg, String notificationType) {
		Twilio.init(accountSid, authToken);
		try {
			if (notificationType.equalsIgnoreCase("SMS")) {
				Message message = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), msg).create();
				log.info("Message sent successfully to :  {} and msg-sid {} : ", toNumber, message.getSid());
				return message.getSid();
	 		} else {
				Call call = Call.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber),
						new URI("http://demo.twilio.com/docs/voice.xml")).create();
				log.info("Call initiated to :  {} and call-sid {} : ", toNumber, call.getSid());
				return call.getSid();
			}
		} catch (URISyntaxException e) {
			log.info("error sending twilio notification : ",e.getMessage());
		}
		return null;
	}

	public void updateSmsAndCallStatus() {

	}

	@Scheduled(cron = "${jobs.response.sla.frequency}")
	public void notifyResponseSla() {
		log.info("running response-sla-config job");
		log.info("Executing procedure Response-SLA-Config");
		List<Map<String, Object>> resList = executeProcedure("getResponseSlaConfig");
		notify("Response", resList);
	}

	@Scheduled(cron = "${jobs.resolution.sla.frequency}")
	public void notifyResolutionSla() {
		log.info("Executing procedure Resolution-SLA-Config");
		List<Map<String, Object>> resList = executeProcedure("getResolutionSlaConfig");
		notify("Resolution", resList);
	}

	//@Scheduled(cron = "${jobs.test.frequency}")
	public void notifyTestSms() {
		log.info("Executing procedure Resolution-SLA-Config");
		List<Map<String, Object>> resList = executeProcedure("testProc");
		notify("Response", resList);
		log.info("<<notifyTestSms job finished>>");
	}

	@Scheduled(cron = "${jobs.update.sms.call.status}")
	public void updateSmsCallInfo() {
		log.info("<<running update-sms-call-status job>>");
		List<String> statuses = Arrays.asList(NotificationStatusType.SMS_DELIVERED.getName(), NotificationStatusType.COMPLETED.getName());
		log.info("statuses : {} ",statuses);
		List<SmsCallInfoEntity> entities = smsCallInfoRepository.findByStatusNotIn(statuses);
		if(!CollectionUtils.isEmpty(entities)) {
			Twilio.init(accountSid, authToken);
			Map<String, Message> msgMap = getMessages();
			Map<String, Call> callMap = getCalls();
		
			entities.stream().forEach(e -> {
				updateMessageStatus(msgMap, e);
				updateCallStatus(callMap, e);
			});
			log.info("updating status for :  {} ", entities);
			smsCallInfoRepository.saveAll(entities);
		}
		log.info("<<update-sms-call-status job finished>>");
	}

	private List<Map<String, Object>> executeProcedure(String procedureName) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
		SqlParameterSource in = new MapSqlParameterSource();
		Map<String, Object> resultMap = jdbcCall.execute(in);
		log.info(resultMap.toString());
		return (List<Map<String, Object>>) resultMap.get("#result-set-1");
	}

	private void notify(String slaType, List<Map<String, Object>> resList) {
		Map<String, Integer> notificationsCountMap = getNotificationSentCount();
		resList.stream().forEach(map -> {
			NotificationDto dto = prepareNotificationDto(map);
			if(canSendNotification(dto.getIncidentNumber(), notificationsCountMap)) {
				SmsCallInfoEntity smsCallInfo = createSmsCallInfo(dto);
				if (dto.getSmsEnabled().equalsIgnoreCase(YorN.YES.name())) {
					sendSms(slaType, dto, smsCallInfo);
				}
				if (dto.getCallEnabled().equalsIgnoreCase(YorN.YES.name())) {
					initiateCall(dto, smsCallInfo);
				}
				smsCallInfoRepository.save(smsCallInfo);
			}
		});
	}

	private void initiateCall(NotificationDto dto, SmsCallInfoEntity smsCallInfo) {
		String callSid = sendNotification(dto.getMobileNumber(), null, NotificationType.CALL.name());
		smsCallInfo.setCallSid(callSid);
		smsCallInfo.setCallStatus(NotificationStatusType.QUEUED.getName());
	}

	private void sendSms(String slaType, NotificationDto dto, SmsCallInfoEntity smsCallInfo) {
		String msg = prepareSmsMessage(dto.getPriority(), dto.getIncidentNumber(), dto.getAssignmentGroup(),
				dto.getMaxTimeLapsed(), slaType);
		String msgSid = sendNotification(dto.getMobileNumber(), msg, NotificationType.SMS.name());
		smsCallInfo.setSmsStatus(NotificationStatusType.QUEUED.getName());
		smsCallInfo.setMsgSid(msgSid);
	}
	
	public Map<String, Integer> getNotificationSentCount(){
		List<Object[]> smsCallList = smsCallInfoRepository.findNotificationSentCount();
		Map<String, Integer> map = new HashMap<>();
		smsCallList.forEach(obj -> {
			String  incidentNumber = (String) obj[1];
			BigInteger count = (BigInteger) obj[0];
			map.put(incidentNumber, count.intValue());
		});
		return map;
		
	}
	
	private Boolean canSendNotification(String incidentNumber, Map<String, Integer> map) {
		Integer numberOfTimesCallTriggered = map.get(incidentNumber);
		if(numberOfTimesCallTriggered != null && numberOfTimesCallTriggered.intValue() <3) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private SmsCallInfoEntity createSmsCallInfo(NotificationDto dto) {
		SmsCallInfoEntity smsCallInfo = new SmsCallInfoEntity();
		smsCallInfo.setIncidentNumber(dto.getIncidentNumber());
		smsCallInfo.setCreatedOn(new Date());
		smsCallInfo.setUpdatedOn(new Date());
		smsCallInfo.setMobileNumber(dto.getMobileNumber());
		smsCallInfo.setAssignedTo(dto.getAssignedTo());
		return smsCallInfo;
	}

	

	private NotificationDto prepareNotificationDto(Map<String, Object> map) {
		NotificationDto dto = new NotificationDto();
		dto.setIncidentNumber(map.get("incident_number").toString());
		dto.setAssignedTo(map.get("assigned_to") != null ? map.get("assigned_to").toString() : null);
		dto.setAssignmentGroup(map.get("assignment_group").toString());
		dto.setCallEnabled(map.get("call_enabled").toString());
		dto.setSmsEnabled(map.get("email_enabled").toString());
		dto.setMobileNumber(map.get("mobile_number").toString());
		dto.setMaxTimeLapsed((Integer) map.get("max_time_lapse"));
		BigDecimal calTimeLapsed = (BigDecimal) map.get("cal_time_lapsed");
		dto.setCalcuatedTimeLapsed(calTimeLapsed.intValue());
		dto.setPriority(map.get("priority").toString());
		return dto;
	}

	private String prepareSmsMessage(String priority, String incidentNumber, String grp, Integer timeLeft,
			String slaType) {
		priority = PriorityType.getById(Integer.parseInt(priority)).getName();
		grp = AssignmentGroupType.getById(grp).getName();
		String msg = "Hi - Priority " + priority + " Incident " + incidentNumber + " is received to Queue " + grp
				+ ". You have " + timeLeft + " minutes left to " + slaType + " to meet the desired SLA.";
		return msg;
	}

	private void updateCallStatus(Map<String, Call> callMap, SmsCallInfoEntity e) {
		Call call = callMap.get(e.getCallSid());
		if (call != null) {
			e.setCallStatus(call.getStatus().name());
			e.setUpdatedOn(new Date());
		}
	}

	private void updateMessageStatus(Map<String, Message> msgMap, SmsCallInfoEntity e) {
		Message msg = msgMap.get(e.getMsgSid());
		if (msg != null) {
			e.setSmsStatus(msg.getStatus().name());
			e.setUpdatedOn(new Date());
		}
	}

	private Map<String, Call> getCalls() {
		ResourceSet<Call> calls = Call.reader().read();
		Map<String, Call> callMap = new HashMap<>();
		calls.forEach(call -> {
			callMap.put(call.getSid(), call);
		});
		return callMap;
	}

	private Map<String, Message> getMessages() {
		ResourceSet<Message> messages = Message.reader()
				.setDateSent(Range.open(new DateTime(2020, 9, 15, 0, 0, 0), new DateTime(2020, 9, 20, 0, 0, 0))).read();
		Map<String, Message> msgMap = new HashMap<>();
		messages.forEach(msg -> {
			msgMap.put(msg.getSid(), msg);
		});
		return msgMap;
	}

}
