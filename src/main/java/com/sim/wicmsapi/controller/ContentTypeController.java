package com.sim.wicmsapi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ContentType getContentTypes(@RequestParam("id") int id) throws ContentTypeNotFoundException{
		ContentType contentType=null;
		Optional<ContentType> contenttypes=contentTypeService.getContentType(id);
		if(contenttypes.isPresent())
			contentType=contenttypes.get();
		else
			throw new ContentTypeNotFoundException("Content TYpe Id is not found "+id);
		return contentType;
	}
}
