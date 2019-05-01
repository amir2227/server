package com.pardis.server.controllers;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface StatusRepository extends JpaRepository<status,Integer> {

}
