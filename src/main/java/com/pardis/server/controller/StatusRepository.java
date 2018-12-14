package com.pardis.server.controller;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pardis.server.model.status;


@Repository
@Transactional
public interface StatusRepository extends JpaRepository<status,Integer> {

}
