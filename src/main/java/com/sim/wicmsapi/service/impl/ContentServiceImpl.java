package com.sim.wicmsapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentRepository;
import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	ContentRepository contentRepository;

	@Override
	public Content save(Content content) {
		return contentRepository.save(content);
	}

	@Override
	public Content findContent(String name, int cpId, int ctTypeId) {
		return contentRepository.findContent(name, cpId, ctTypeId);
	}

}
