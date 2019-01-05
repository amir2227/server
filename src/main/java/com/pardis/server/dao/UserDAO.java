package com.pardis.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pardis.server.controller.UserRepo;
import com.pardis.server.model.Role;
import com.pardis.server.model.User;
import com.pardis.server.services.RoleService;
import com.pardis.server.services.UserService;


 
@Repository
public class UserDAO {
 
    // Config in WebSecurityConfig
    @Autowired
    private StrongPasswordEncryptor passwordEncoder;
 
    @Autowired
    private UserRepo userRepo;
    
    private RoleService roleService;
    
    private UserService userService;
 

    
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
 
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
 
        list.addAll(USERS_MAP.values());
        return list;
    }
 
    public static String encrypt(String strClearText,String strKey) throws Exception{
    	String strData="";
    	
    	try {
    		SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
    		Cipher cipher=Cipher.getInstance("Blowfish");
    		cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
    		byte[] encrypted=cipher.doFinal(strClearText.getBytes());
    		strData=new String(encrypted);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception(e);
    	}
    	return strData;
    }
    public static String decrypt(String strEncrypted,String strKey) throws Exception{
    	String strData="";
    	
    	try {
    		SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
    		Cipher cipher=Cipher.getInstance("Blowfish");
    		cipher.init(Cipher.DECRYPT_MODE, skeyspec);
    		byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
    		strData=new String(decrypted);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception(e);
    	}
    	return strData;
    }
    private void assignUsersToUserRole(String username) {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    if (user.getUsername().equals(username)) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
    private void assignUsersToAdminRole(String username) {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals(username)) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
    
    public User createUser(User form) throws Exception {
       Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encryptPassword(form.getPassword());
        List<Role> roles = (List<Role>) roleService.listAll();
 
        User user = new User(userId, form.getUsername(), 
                form.getFirstName(), form.getLastName(), true, form.getEmail(),form.getLocation(), encrytedPassword);
 System.out.println(form.getFirstName());
        USERS_MAP.put(userId, user);
      Role role = new Role();
       String token= encrypt(form.getLocation(),"amir");
        user.setToken(token);
        if(form.getFirstName().equalsIgnoreCase("admin")) {
        assignUsersToAdminRole(form.getUsername());
        }else {
            assignUsersToUserRole(form.getUsername());
            }
        System.out.println(token);
        userRepo.save(user);
        return user;
 
    }
}
