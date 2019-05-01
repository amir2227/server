package com.pardis.server.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pardis.server.domain.Device;
import com.pardis.server.domain.Relation;

@Controller
public class WebController {

	@Autowired
	private DeviceRepository deviceRepo;
	
	@Autowired
	private RelationRepository relationRepo;
	

	
	

	
	//return index.html
	    @RequestMapping("/")
	    String index(){
	        return "index";
	    }
	
	@GetMapping(value="/device")
    public String homepage(Model model,@RequestParam(defaultValue="0") int page){
		//data have devices information
		model.addAttribute("rel",relationRepo.findAll(new PageRequest(page,400)));
		model.addAttribute("data",deviceRepo.findAll(new PageRequest(page, 4)));
		model.addAttribute("currentPage", page);
		 
        return "dev";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
         return "login";
    }

	
	//save device body into the database
	@PostMapping("/saveDevice")
	public String save(Device d) {
		deviceRepo.save(d);
		return "redirect:/device";
	}
	
	@PostMapping("/saveRelation")
	public String saveRelation(Relation r) {
		String name= r.getSensorName();
		List<Device> d = deviceRepo.findByName(name);
		Device de = d.get(0);
		r.setDeviceId(de.getId());
		relationRepo.save(r);
		return "redirect:/device";
	}

	//delete device by id
	@GetMapping("/delete")
	public String deleteDevice(Integer id) {
	deviceRepo.deleteById(id);
	return "redirect:/device";
	}
	
	@GetMapping("/findOneDevice")
	@ResponseBody
	public Optional<Device> findOne(Integer id) {
		return deviceRepo.findById(id);
	}
	
	@GetMapping("/post")
	@ResponseBody
	public List<status> findpost(Integer id) {
		
		return deviceRepo.findById(id).get().getStatuses();
		
		
	}
	

}

