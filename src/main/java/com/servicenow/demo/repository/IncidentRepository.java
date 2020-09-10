package com.servicenow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicenow.demo.entity.IncidentEntity;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long>{

}
