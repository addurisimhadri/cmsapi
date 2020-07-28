package com.sim.wicmsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.wicmsapi.entity.PhysicalFolder;

@Repository
public interface PhysicalFolderRepository extends JpaRepository<PhysicalFolder, Integer> {
	
	PhysicalFolder findByFolderName(String folderName);

}
