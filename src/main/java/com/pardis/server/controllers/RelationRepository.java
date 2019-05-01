package com.pardis.server.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pardis.server.domain.Relation;

@Repository
@Transactional
public interface RelationRepository extends JpaRepository<Relation, Integer> {

	Optional<Relation> findByDeviceId(Integer deviceId);
	List<Relation> findBydeviceId(Integer deviceId);
	
}
