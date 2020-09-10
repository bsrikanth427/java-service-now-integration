package com.servicenow.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendSimpleMessage(String to, String from, String subject, String text) {
		
		log.info("sending email To: {} , Subject: {} ", to,subject);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}

}
