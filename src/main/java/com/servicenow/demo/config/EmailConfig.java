package com.servicenow.demo.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	@Value("${mail.server.host}")
    private String HOST;
	@Value("${mail.server.port}")
    private String PORT;
	@Value("${mail.server.protocol}")
    private String PROTOCOL;
	@Value("${mail.server.username}")
    private String USERNAME;
	@Value("${mail.server.password}")
    private String PASSWORD;
   
	@Bean
	public JavaMailSender mailSender() throws IOException{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost(HOST);
		mailSender.setPort(Integer.parseInt(PORT));
		mailSender.setUsername(USERNAME);
		mailSender.setProtocol(PROTOCOL);
		mailSender.setPassword(PASSWORD);
		mailSender.setJavaMailProperties(javaMailProperties(mailSender));
		return mailSender;
	}
	
	
	private Properties javaMailProperties(JavaMailSenderImpl mailSender) throws IOException {
		Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
        return props;
	}
	

}
