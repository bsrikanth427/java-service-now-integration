package com.servicenow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicenow.demo.entity.ResolutionSlaConfigEntity;

@Repository
public interface ResolutionSlaRepository extends JpaRepository<ResolutionSlaConfigEntity, Long>{

}
