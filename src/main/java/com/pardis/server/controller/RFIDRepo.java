package com.pardis.server.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.pardis.server.model.RFID;

@Repository
@Transactional
public interface RFIDRepo extends JpaRepository<RFID, Integer> {

	//find by a special field 
	List<RFID> findByRfid(String Rfid);
	
}
