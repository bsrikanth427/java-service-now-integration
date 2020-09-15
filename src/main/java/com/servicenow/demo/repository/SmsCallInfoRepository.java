package com.servicenow.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.servicenow.demo.entity.SmsCallInfoEntity;

@Repository
public interface SmsCallInfoRepository extends JpaRepository<SmsCallInfoEntity, Long>{
	
	@Query(value = "select * from sms_call_info e where e.sms_status OR e.call_status NOT IN ?1", nativeQuery = true)
	public List<SmsCallInfoEntity> findByStatusNotIn(List<String> statuses);
	
	@Query(value = "select count(*), incident_number, mobile_number  from sms_call_info group by incident_number, mobile_number", nativeQuery = true)
	public List<Object[]> findNotificationSentCount();
	
	
	
}
