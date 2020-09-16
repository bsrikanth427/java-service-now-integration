create table incident (
 incident_number VARCHAR(20) NOT NULL  PRIMARY KEY,
 created_on DATETIME,
 assignment_group VARCHAR(60),
 status VARCHAR(20),
 updated_on DATETIME,
 assigned_to VARCHAR(60),
 subject TEXT,
 priority VARCHAR(10),
 severity VARCHAR(10)
 );
 
 
create table response_sla_config (
 id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 assignment_group VARCHAR(60),
 priority VARCHAR(10),
 escalation_level INT,
 time_lapse INT,
 mobile_number VARCHAR(20),
 email_id VARCHAR(40),
 call_enabled VARCHAR(6),
 email_enabled VARCHAR(6),
 max_time_lapse INT
 
);
create table resolution_sla_config (
 id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 assignment_group VARCHAR(60),
 priority VARCHAR(10),
 escalation_level INT,
 time_lapse INT,
 mobile_number VARCHAR(20),
 email_id VARCHAR(40),
 call_enabled VARCHAR(6),
 email_enabled VARCHAR(6),
 max_time_lapse INT
 
);

create table sms_call_info (
 id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
 incident_number VARCHAR(20),
 assigned_to VARCHAR(60),
 sms_status VARCHAR(20),
 call_status VARCHAR(20),
 mobile_number VARCHAR(20),
 created_on DATETIME,
 updated_on DATETIME,
 msg_sid VARCHAR(50),
 call_sid VARCHAR(50),
 counter INT

 );

INSERT INTO ct_test.response_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(1, 'RPA_GRP', '1', 1, 1, '+919703730427', 'bsrikanth427@gmail.com', 'YES', 'YES', 5);

INSERT INTO ct_test.response_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(2, 'RPA_GRP', '1', 2, 6, '+919885228336', 'sboddupally@evoketechnologies.com', 'YES', 'YES', 10);


INSERT INTO ct_test.response_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(3, 'RPA_GRP', '5', 1, 5, '919703730427', 'bsrikanth427@gmail.com', 'YES', 'YES', 10);

INSERT INTO ct_test.response_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(4, 'RPA_GRP', '5', 1, 11, '+919885228336', 'sboddupally@evoketechnologies.com', 'YES', 'YES', 15);


INSERT INTO ct_test.response_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(6, 'RPA_GRP', '2', 2, 15, '+919885228336', 'sboddupally@evoketechnologies.com', 'YES', 'YES', 20);





INSERT INTO ct_test.resolution_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(1, 'RPA_GRP', 'P1', 1, 10, '9703730427', 'bsrikanth427@gmail.com', 'YES', 'YES', 15);

INSERT INTO ct_test.resolution_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(2, 'RPA_GRP', 'P1', 1, 10, '9703730427', 'sboddupally@evoketechnologies.com', 'YES', 'YES', 15);

INSERT INTO ct_test.resolution_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(5, 'RPA_GRP', '2', 1, 1,  '+919703730427', 'bsrikanth427@gmail.com', 'YES', 'YES', 5);

INSERT INTO ct_test.resolution_sla_config
(id, assignment_group, priority, escalation_level, time_lapse, mobile_number, email_id, call_enabled, email_enabled, max_time_lapse)
VALUES(6, 'RPA_GRP', '2', 2, 5, '+919885228336', 'sboddupally@evoketechnologies.com', 'YES', 'YES', 10);


