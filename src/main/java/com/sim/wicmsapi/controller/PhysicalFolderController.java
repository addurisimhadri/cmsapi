package com.sim.wicmsapi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.exception.EmployeeIdNotFoundException;
import com.sim.wicmsapi.service.PhysicalFolderService;
import com.sim.wicmsapi.vo.ApiResponse;
import com.sim.wicmsapi.vo.PhysicalFolderDTO;

@CrossOrigin
@RestController
@RequestMapping(value = "/pf")
public class PhysicalFolderController {
	private static final Logger logger = LoggerFactory.getLogger(WebUploadController.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private static final String ACTION = "Physical Folder id is ##ID## Not exist.";
	private static final String ACTION_1 = "##ID##";
	@Autowired
	PhysicalFolderService physicalFolderService;
	
	@GetMapping(value = "/getAll")
	public List<PhysicalFolder> getAll(){		
		return physicalFolderService.findAll();
	}
	@GetMapping(value = "/getid/{id}")
	public ApiResponse<PhysicalFolder> findById(@PathVariable("id") int id) throws EmployeeIdNotFoundException {
		Optional<PhysicalFolder> physicalFolderOP= physicalFolderService.findById(id);
		if(!physicalFolderOP.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));	
		return new ApiResponse<>(HttpStatus.OK.value(), "Physical Folder Deatails are Getting SuccessFully", physicalFolderOP.get());	
	}
	@GetMapping(value = "/getName/{id}")
	public ApiResponse<PhysicalFolder> findByName(@PathVariable("name") String name) throws EmployeeIdNotFoundException {
		Optional<PhysicalFolder> physicalFolderOP= physicalFolderService.findByFolderName(name);
		if(!physicalFolderOP.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, name+""));	
		return new ApiResponse<>(HttpStatus.OK.value(), "Physical Folder Deatails are Getting SuccessFully", physicalFolderOP.get());	
	}
	
	@PostMapping(value = "/add")
	public ApiResponse<PhysicalFolder> add(@RequestBody PhysicalFolderDTO physicalFolderDTO){
		PhysicalFolder physicalFolder=null;
		try {
			physicalFolder=convertObjToE(physicalFolderDTO, new TypeReference<PhysicalFolder>(){});
			physicalFolderService.save(physicalFolder);
		} catch (Exception e) {
			logger.info(myMarker, "Ex::  {} ", e.getMessage());
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "Physical Folder Created SuccessFully", physicalFolder);
		
	}
	
	public  PhysicalFolder convertObjToE(Object o, TypeReference<PhysicalFolder> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}

}
