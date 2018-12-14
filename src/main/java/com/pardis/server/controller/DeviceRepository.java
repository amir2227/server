package com.pardis.server.controller;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pardis.server.model.Device;

@Repository
@Transactional
public interface DeviceRepository extends JpaRepository<Device, Integer> {

	List<Device> findByName(String Name);
	Optional<Device> findByRole(Integer Role);
}
