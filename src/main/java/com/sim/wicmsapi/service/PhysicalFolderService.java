package com.sim.wicmsapi.service;

import java.util.List;
import java.util.Optional;

import com.sim.wicmsapi.entity.PhysicalFolder;

public interface PhysicalFolderService {
	
	Optional<PhysicalFolder> findById(int id);
	PhysicalFolder findByFolderName(String folderName);
	List<PhysicalFolder> findAll();

}
