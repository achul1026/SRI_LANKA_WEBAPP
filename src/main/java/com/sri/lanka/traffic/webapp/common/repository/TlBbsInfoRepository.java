package com.sri.lanka.traffic.webapp.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sri.lanka.traffic.webapp.common.entity.TlBbsInfo;

@Repository
public interface TlBbsInfoRepository extends JpaRepository<TlBbsInfo, String>{

}
