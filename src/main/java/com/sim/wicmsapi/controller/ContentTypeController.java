package com.sim.wicmsapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.exception.ContentTypeNotFoundException;
import com.sim.wicmsapi.service.ContentTypeService;

@RestController
@RequestMapping(value = "/ct")
public class ContentTypeController {
	@Autowired
	ContentTypeService contentTypeService;
	
	@GetMapping(value = "/getcts")
	public List<ContentType> getContentTypes() {		
		return contentTypeService.getCTs();
	}
	@GetMapping(value = "/getcts/{id}")
	public ContentType getContentTypes(@RequestParam("id") long id) throws ContentTypeNotFoundException{
		ContentType contentType=null;
		Optional<ContentType> contenttypes=contentTypeService.getContentType(id);
		if(contenttypes.isPresent())
			contentType=contenttypes.get();
		else
			throw new ContentTypeNotFoundException("Content TYpe Id is not found "+id);
		return contentType;
	}
}
