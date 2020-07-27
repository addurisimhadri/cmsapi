package com.sim.wicmsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.service.ContentService;

@CrossOrigin
@RestController
@RequestMapping(value = "/cont")
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	@GetMapping(value="/{ctId}/getAll")	
	public Iterable<Content> getEmployee(@PathVariable("ctId") int ctId, Pageable pageable){	
		
		return contentService.getContentByCT(ctId,pageable);
	}

}
