package com.pardis.server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User extends AbstractDomainClass  {

    private Long userId;
    private String username;
    private String password;
    private String location;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private String encryptedPassword;
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
    //     inverseJoinColumns = @joinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
    private Integer failedLoginAttempts = 0;
    
    
    public User() {
    	 
    }
 
    public User(Long userId, String username, String firstName, String lastName, 
            boolean enabled, String email, String location, String encrytedPassword) {
        super();

        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.email = email;
        this.location = location;
        this.encryptedPassword = encrytedPassword;

    }
    
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        if(!this.roles.contains(role)){
            this.roles.add(role);
        }

        if(!role.getUsers().contains(this)){
            role.getUsers().add(this);
        }
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }
}
