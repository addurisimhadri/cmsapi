package com.sim.wicmsapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.exception.ContentTypeNotFoundException;
import com.sim.wicmsapi.service.ContentTypeService;

@CrossOrigin
@RestController
@RequestMapping(value = "/ct")
public class ContentTypeController {
	private static final Logger logger = LoggerFactory.getLogger(ContentTypeController.class);
	
	@Autowired
	ContentTypeService contentTypeService;
	
	@GetMapping(value = "/getAll")
	public List<ContentType> getContentTypes() {	
		logger.info(""+contentTypeService.getCTs());
		return contentTypeService.getCTs();
	}
	@GetMapping(value = "/get/{id}")
	public List<ContentType> getContentTypes(@PathVariable("id") int id) throws ContentTypeNotFoundException{
		ContentType contentType=null;
		List<ContentType> list=new ArrayList<ContentType>();
		Optional<ContentType> contenttypes=contentTypeService.getContentType(id);
		if(contenttypes.isPresent()) {
			contentType=contenttypes.get();
			list.add(contentType);
		logger.info(""+contentType);
		}
		else
			throw new ContentTypeNotFoundException("Content TYpe Id is not found "+id);
		return list;
	}
}
