package com.sim.wicmsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.wicmsapi.entity.ContentProvider;
import com.sim.wicmsapi.service.ContentProviderService;

@CrossOrigin
@RestController
@RequestMapping(value ="/cp")
@ComponentScan(basePackages="com.sim.upload")

public class ContentProviderController {
	
	@Autowired
	ContentProviderService contentProviderService;
	
	@GetMapping(value = "/getAll")
	public List<ContentProvider> getContentProvides() {
		return contentProviderService.getCPs();
	}
	@GetMapping(value = "/get/{name}")
	public ContentProvider getContentProvide(@PathVariable("name") String cpName) {
		return contentProviderService.getContentProvider(cpName);
	}
}
