package com.sim.wicmsapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentProcessFTPRepository;
import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.service.ContentProcessFTPService;

@Service
public class ContentProcessFTPServiceImpl implements ContentProcessFTPService {
	
	@Autowired
	ContentProcessFTPRepository contentProcessFTPRepository;

	@Override
	public ContentProcessFTP save(ContentProcessFTP contentProcessFTP) {
		return contentProcessFTPRepository.save(contentProcessFTP);
	}

}
