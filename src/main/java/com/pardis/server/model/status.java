package com.pardis.server.model;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class status {

	@Id
	@GeneratedValue
	private Integer id;
	
	public String value;

	private LocalTime time;
	
public status() {}
	
	public status(Integer id, String value, Device device,LocalTime time) {
		super();
		this.id = id;
		this.value = value;
		this.device = device;
		this.time = time;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Device device;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getId() {
		return id;
	}
	public String getValue() {
		return value;
	}
	public Device getDevice() {
		return device;
	}
	
	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "status [id=" + id + ", value=" + value + ", time=" + time + "]";
	}
	
}
