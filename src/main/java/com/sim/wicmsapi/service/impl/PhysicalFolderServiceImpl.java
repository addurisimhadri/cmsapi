package com.sim.wicmsapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.PhysicalFolderRepository;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.service.PhysicalFolderService;

@Service
public class PhysicalFolderServiceImpl implements PhysicalFolderService {

	@Autowired
	PhysicalFolderRepository physicalFolderRepository;
	@Override
	public Optional<PhysicalFolder> findById(int id) {
		return physicalFolderRepository.findById(id);
	}

	@Override
	public PhysicalFolder findByFolderName(String folderName) {
		return physicalFolderRepository.findByFolderName(folderName);
	}

	@Override
	public List<PhysicalFolder> findAll() {
		return physicalFolderRepository.findAll();
	}

}
