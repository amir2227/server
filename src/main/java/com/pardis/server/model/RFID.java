package com.javasampleapproach.jqueryboostraptable.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RFID {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer student_id;
	
	private String username;
	
	private String rfid;

	
	
	
	public RFID(Integer id, Integer student_id, String username, String rfid) {
		super();
		this.id = id;
		this.student_id = student_id;
		this.username = username;
		this.rfid = rfid;
	}
	public RFID() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	@Override
	public String toString() {
		return "RFID [id=" + id + ", student_id=" + student_id + ", username=" + username + ", rfid=" + rfid + "]";
	}
	
	

}
