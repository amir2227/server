package com.pardis.server.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pardis.server.domain.Device;

@Repository
@Transactional
public interface DeviceRepository extends JpaRepository<Device, Integer> {

	List<Device> findByName(String Name);
	Optional<Device> findByRole(Integer Role);
	Optional<Device> findById(Integer id);
	void deleteById(Integer id);
}
