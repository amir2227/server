package com.pardis.server.controllers;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pardis.server.domain.RFID;

@Repository
@Transactional
public interface RFIDRepo extends JpaRepository<RFID, Integer> {

	//find by a special field 
	List<RFID> findByRfid(String Rfid);
	
}
