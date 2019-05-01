package com.pardis.server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pardis.server.domain.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);
}
