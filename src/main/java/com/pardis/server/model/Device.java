package com.pardis.server.model;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
//create table Device
@Entity
public class Device {

	
	//table fields 
	@Id
	@GeneratedValue
	private Integer id;
	
	private String type;

	private String name;
	
	private String location;
	
	private Integer role;
	
	
	@OneToMany(mappedBy="device")
	private List<status> statuses;

	public List<status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<status> statuses) {
		this.statuses = statuses;
	}

	protected Device() {
		
	}
	
	public Device(Integer id,String type, String name, String location, Integer role) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.location = location;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", location=" + location + ", role=" + role + ", type=" + type + "]";
	}
	
}
