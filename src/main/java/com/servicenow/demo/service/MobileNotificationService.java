package com.servicenow.demo.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

	public void sendNotification(String toNumber, String msg, String notificationType) throws URISyntaxException {
		Twilio.init(accountSid, authToken);
		if (notificationType.equalsIgnoreCase("SMS")) {
			Message message = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), msg).create();
			log.info(message.getSid());
		} else {
			Call call = Call.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber),
					new URI("http://demo.twilio.com/docs/voice.xml")).create();
			log.info(call.getSid());
		}
	}

}
