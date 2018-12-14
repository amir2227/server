package com.pardis.server.controller;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pardis.server.model.Relation;

@Repository
@Transactional
public interface RelationRepository extends JpaRepository<Relation, Integer> {

	Optional<Relation> findByDeviceId(Integer deviceId);
	List<Relation> findBydeviceId(Integer deviceId);
	
}
