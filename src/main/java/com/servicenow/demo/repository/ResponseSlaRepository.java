package com.servicenow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicenow.demo.entity.ResponseSlaConfigEntity;

@Repository
public interface ResponseSlaRepository extends JpaRepository<ResponseSlaConfigEntity, Long>{

}
