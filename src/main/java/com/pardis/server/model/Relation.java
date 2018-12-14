package com.javasampleapproach.jqueryboostraptable.model;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 


@Entity
public class Relation {

	
	//table fields 
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer deviceId;
	
	private String sensorName;
	
	private String connector;
	
	private Integer degree;
	
	private Integer deg;
	
	
	protected Relation() {
		
	}
	
	public Relation(Integer id, Integer deviceId,String sensorName, String connector, Integer degree, Integer deg) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.sensorName = sensorName;
		this.connector = connector;
		this.degree = degree;
		this.deg = deg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public Integer getDeg() {
		return deg;
	}

	public void setDeg(Integer deg) {
		this.deg = deg;
	}

	@Override
	public String toString() {
		return "Connect [id=" + id + ", deviceId=" + deviceId + ", connector=" + connector + ", degree=" + degree
				+ ", deg=" + deg + "]";
	}



}
