package com.sim.wicmsapi.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.exception.EmployeeIdNotFoundException;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.PhysicalFolderService;
import com.sim.wicmsapi.utility.PhysicalFolderUtility;
import com.sim.wicmsapi.vo.ApiResponse;
import com.sim.wicmsapi.vo.PhysicalFolderDTO;

@CrossOrigin
@RestController
@RequestMapping(value = "/pf")
public class PhysicalFolderController {
	private static final Logger logger = LoggerFactory.getLogger(PhysicalFolderController.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private static final String ACTION = "Physical Folder id is ##ID## Not exist.";
	private static final String ACTION_1 = "##ID##";
	
	@Value("${upload.destfolder}")
	public  String destFolder;
	
	@Autowired
	PhysicalFolderService physicalFolderService;
	
	@Autowired
	ContentTypeService contentTypeService;
	
	@GetMapping(value = "/getAll")
	public List<PhysicalFolderDTO> getAll(){
		List<PhysicalFolderDTO> physicalFolderDTOs=new ArrayList<>();
		List<PhysicalFolder> physicalFolders=physicalFolderService.findAll();
		PhysicalFolderUtility.convertListEtoVo(physicalFolders, physicalFolderDTOs);
		return physicalFolderDTOs;
		
	}
	@GetMapping(value = "/{cttypeId}/getAll")
	public List<PhysicalFolderDTO> getCtType(@PathVariable("cttypeId") int cttypeId){
		List<PhysicalFolderDTO> physicalFolderDTOs=new ArrayList<>();		
		List<PhysicalFolder> physicalFolders=physicalFolderService.getCtTypeFolders(cttypeId);
		PhysicalFolderUtility.convertListEtoVo(physicalFolders, physicalFolderDTOs);
		logger.info(myMarker, "physicalFolderDTOs::  {} ", physicalFolderDTOs);
		return physicalFolderDTOs;
	}
	@GetMapping(value = "/getid/{id}")
	public ApiResponse<PhysicalFolderDTO> findById(@PathVariable("id") int id) throws EmployeeIdNotFoundException {
		Optional<PhysicalFolder> physicalFolderOP= physicalFolderService.findById(id);
		PhysicalFolderDTO physicalFolderDTO=null;
		if(physicalFolderOP.isPresent()) {
			physicalFolderDTO=PhysicalFolderUtility.convertEToObj(physicalFolderOP.get(), new TypeReference<PhysicalFolderDTO>(){});
		}else {
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));	
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "Physical Folder Deatails are Getting SuccessFully", physicalFolderDTO);	
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
			String separator=File.separator;
			logger.info(myMarker, "physicalFolderDTO::  {} ", physicalFolderDTO);
			physicalFolder=PhysicalFolderUtility.convertObjToE(physicalFolderDTO, new TypeReference<PhysicalFolder>(){});
			destFolder=destFolder.replace("/", separator);
			if(!destFolder.endsWith(File.separator)) destFolder = destFolder+File.separator;
			Optional<ContentType> contentTypeOP=contentTypeService.getContentType(physicalFolderDTO.getContentTypeId());
			String location =destFolder+contentTypeOP.get().getContentName()+File.separator+physicalFolderDTO.getFolderName();
			physicalFolder.setLocation(location);
			logger.info(myMarker, "physicalFolder::  {} ", physicalFolder);
			physicalFolderService.save(physicalFolder);
		} catch (Exception e) {
			logger.info(myMarker, "Ex::  {} ", e.getMessage());
			e.printStackTrace();
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "Physical Folder Created SuccessFully", physicalFolder);
		
	}
	
	
}
