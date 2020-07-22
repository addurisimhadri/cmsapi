package com.sim.wicmsapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentProviderRepository;
import com.sim.wicmsapi.entity.ContentProvider;
import com.sim.wicmsapi.service.ContentProviderService;
@Service
public class ContentProviderServiceImpl implements ContentProviderService {
	
	@Autowired
	ContentProviderRepository contentProviderRepository;

	@Override
	public List<ContentProvider> getCPs() {
		return contentProviderRepository.findAll();
		
	}

	@Override
	public ContentProvider getContentProvider(int cpId) {		
		return contentProviderRepository.getOne(cpId);
	}

	@Override
	public ContentProvider getContentProvider(String cpName) {		
		return contentProviderRepository.getByCpName(cpName);
	}

}
