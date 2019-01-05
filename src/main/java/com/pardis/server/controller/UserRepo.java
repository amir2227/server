package guru.springframework.controllers;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.domain.User;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Integer> {

List<User> findByToken(String token);
}
