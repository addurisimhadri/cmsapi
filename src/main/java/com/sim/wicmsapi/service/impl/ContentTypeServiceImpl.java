package com.sim.wicmsapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentTypeRepository;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.service.ContentTypeService;

@Service
public class ContentTypeServiceImpl implements ContentTypeService {
	
	@Autowired
	ContentTypeRepository contentTypeRepository;

	public Optional<ContentType> getContentType(int contentId) {		
		return contentTypeRepository.findById(contentId);
	}

	@Override
	public List<ContentType> getCTs() {		
		return contentTypeRepository.getContentTypes();
	}

	@Override
	public ContentType save(ContentType contentType) {
		return contentTypeRepository.save(contentType);
	}

	@Override
	public List<ContentType> getCTs(long ctTypeId,String active) {
		return contentTypeRepository.findByContentIdGreaterThanQuery(ctTypeId,active) ;
	}

}
