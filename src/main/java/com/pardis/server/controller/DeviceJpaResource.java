/***
 * @author amir-reza abbasi
 */
package com.javasampleapproach.jqueryboostraptable.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.javasampleapproach.jqueryboostraptable.model.Device;
import com.javasampleapproach.jqueryboostraptable.model.RFID;
import com.javasampleapproach.jqueryboostraptable.model.Relation;
import com.javasampleapproach.jqueryboostraptable.model.status;

@RestController
public class DeviceJpaResource {
	//first add the repository's to controller class
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	
	
	@Autowired
	private RFIDRepo rfidRepo;
	
	@Autowired
	private StatusRepository statusRepository;

	//this getMapping return all device that save in database in json context
	@GetMapping(path="/jpa/device")
	public List<Device> RetrieveAllDevice(){
		return deviceRepository.findAll();
	}
	
	@GetMapping(path="/relation")
	public List<Relation> RetrieveAllRelation(){
		return relationRepository.findAll();
	}
	
	@GetMapping(path="/relation/{id}")
	public List<Relation> RetrieveRelation(@PathVariable int id){
		//find device by id 
		List<Relation> relation = relationRepository.findBydeviceId(id);
		//if device not exist show an exception
		if(relation.isEmpty()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		
		return relation;
	}
	
	//find a special device by id
	@GetMapping(path="/jpa/device/{id}")
	public Optional<Device> RetrieveDevice(@PathVariable int id){
		//find device by id 
		Optional<Device> device = deviceRepository.findById(id);
		//if device not exist show an exception
		if(!device.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		
		return device;
	}
	
	//find statuses of a device by device id
	@GetMapping(path="/jpa/device/{id}/post")
	public List<status> RetrieveAllDevice(@PathVariable int id){
		Optional<Device> deviceOptional= deviceRepository.findById(id);
		if(!deviceOptional.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		//return statuses in json context
			return deviceOptional.get().getStatuses();
		
	}
	/*
	@GetMapping(path="/device/{id}/{i}/{d}/{f}/post")
	public Optional<Device>[] RetrieveAllDeviceps(@PathVariable int id,@PathVariable int i,@PathVariable int d,@PathVariable int f){
		//find device by id 
		Optional<Device> deviceOptional= deviceRepository.findById(id);
		Optional<Device> deviceOptional2= deviceRepository.findById(i);
		Optional<Device> deviceOptional3= deviceRepository.findById(d);
		Optional<Device> deviceOptional4= deviceRepository.findById(f);
		//if device not exist show an exception
		if(!deviceOptional.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		if(!deviceOptional2.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		if(!deviceOptional3.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		if(!deviceOptional4.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		Optional<Device>[] device = new Optional[4];
		device[0]=deviceOptional;
		device[1]=deviceOptional2;
		device[2]=deviceOptional3;
		device[3]=deviceOptional4;
		
		return device;
	}
	*/
	@GetMapping(path="/device/{id}/{i}/{d}/{f}/post")
	public List<status>[] RetrieveAllDevicep(@PathVariable int id,@PathVariable int i,@PathVariable int d,@PathVariable int f){
		Optional<Device> deviceOptional= deviceRepository.findById(id);
		Optional<Device> deviceOptional2= deviceRepository.findById(i);
		Optional<Device> deviceOptional3= deviceRepository.findById(d);
		Optional<Device> deviceOptional4= deviceRepository.findById(f);
		if(!deviceOptional.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		if(!deviceOptional2.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		if(!deviceOptional3.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		if(!deviceOptional4.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
		//return statuses in json context

		List<status> s0 = deviceOptional.get().getStatuses();
		List<status> s1 = deviceOptional2.get().getStatuses();
		List<status> s2 = deviceOptional3.get().getStatuses();
		List<status> s3 = deviceOptional4.get().getStatuses();
		List<status>[] list = new List[4];
		list[0]= s0;
		list[1]= s1;
		list[2]= s2;
		list[3]= s3;
		
			return list;
		
	}
	//save a status body that send by a device into the database
	//{id} is device_id
	@PostMapping(path="/jpa/device/{id}/post")
	public ResponseEntity<Object> CreatStatus(@PathVariable int id,@RequestBody status Status){
		
		
		Optional<Device> deviceOptional= deviceRepository.findById(id);
		if(!deviceOptional.isPresent()) {
			throw new DeviceNotFoundException("id-"+id);
		}
	Device device= deviceOptional.get();
	//a list of relation that sensor device_id = id
	List<Relation> Lrelation = relationRepository.findBydeviceId(id);
	//if exist relationship for device
	if(!Lrelation.isEmpty()) {
		//get the device type
		String type= device.getType();
		//get the value
		String des = Status.getValue();
		//string array for connector name
		String[] con = new String[Lrelation.size()];
		//To connect a sensor device to multiple operator devices we need a loop like this
		for(int i=0; i < Lrelation.size();i++) {
			Relation rel = Lrelation.get(i);
			System.out.println(rel);
			con[i] = rel.getConnector();
			System.out.println(con[i]);
			//find connected operator device
			List<Device> Ldevice = deviceRepository.findByName(con[i]);
			System.out.println(Ldevice);
			Device dev = Ldevice.get(0);
			System.out.println(dev);
			//find status id of operator device
			int sid = dev.getStatuses().get(0).getId();
			System.out.println(sid);
			//do an action by sensor device type
			switch(type) {
			//if RFID look for input rfid if exist in the database put the operator device on else off
			case "RFID":
				  List<RFID> s = rfidRepo.findByRfid(des);
				  //if RFID exist
				  if(!s.isEmpty()) {
					  //set the operator device on
				status st = new status(sid,"on",dev);
				statusRepository.save(st);
				  }else {
					  //else set the operator device off
						status st = new status(sid,"off",dev);
						statusRepository.save(st);
				  }
				  break;
			//if Temp&Hum get the degree and deg(more or less) if condition true put the operator device on else off 	  
			case "Temp":
				  Integer degree = rel.getDegree();
				  System.out.println(degree);
				  int result = Integer.parseInt(des);
				  //if deg=1 , the result should be greater than the degree
				  if(rel.getDeg()==1 && result > degree) {
					  //set the operator device on
				  status st = new status(sid,"on",dev);
				  System.out.println(st);
				  System.out.println(dev);
				  statusRepository.save(st);
				//if deg=0 , the result should be less than the degree
				  }else if(rel.getDeg()==0 && result < degree) {
					//set the operator device on
					  status st = new status(sid,"on",dev);
					  System.out.println(st);
					  System.out.println(dev);
					  statusRepository.save(st);
				  }else {
					  //else set the operator device off
					  status st = new status(sid,"off",dev);
					  statusRepository.save(st);
				  }
				break;
				//in other case if sensor device sends 1(senses something) put the operator device on else off
			case "Other":
				//if sensor device send "1"
				  if(des.contains("1")) {
					  //set the operator device on
				  status st = new status(sid,"on",dev);
				  statusRepository.save(st);
				  }else {
					  //else set the operator device off
					  status st = new status(sid,"off",dev);
					  statusRepository.save(st);
				  }
				break;
			}
		}
	}
				 
	Status.setDevice(device);
	//save the status
	statusRepository.save(Status);
	
		URI Location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Status.getId()).toUri();
		
		return ResponseEntity.created(Location).build();
		

		
	}
	
	
}
