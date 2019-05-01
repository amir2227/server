package com.pardis.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pardis.server.controllers.UserRepo;
import com.pardis.server.domain.User;
 
@Repository
public class UserDAO {
 
    // Config in WebSecurityConfig
    @Autowired
    private StrongPasswordEncryptor passwordEncoder;
 
    @Autowired
    private UserRepo userRepo;
    
 

    
    private static final Map<Long, User> USERS_MAP = new HashMap<>();
 
    public Long getMaxUserId() {
        long max = 0;
        for (Long id : USERS_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }
 
    //
 
    public User findUserByUserName(String username) {
        Collection<User> Users = USERS_MAP.values();
        for (User u : Users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
 
    public User findUserByEmail(String email) {
        Collection<User> Users = USERS_MAP.values();
        for (User u : Users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }
    
    public User findUserByLocation(String location) {
        Collection<User> Users = USERS_MAP.values();
        for (User u : Users) {
            if (u.getLocation().equals(location)) {
                return u;
            }
        }
        return null;
    }
 
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
 
        list.addAll(USERS_MAP.values());
        return list;
    }
 
    public User createUser(User form) {
        Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encryptPassword(form.getPassword());
 
        User user = new User(userId, form.getUsername(), 
                form.getFirstName(), form.getLastName(), true, form.getEmail(), form.getLocation(), encrytedPassword);
 
        USERS_MAP.put(userId, user);
      
        userRepo.save(user);
        return user;
    }
 
}