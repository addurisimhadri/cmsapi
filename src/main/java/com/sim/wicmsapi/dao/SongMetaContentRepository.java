package com.sim.wicmsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.wicmsapi.entity.SongContentId;
import com.sim.wicmsapi.entity.SongMeta;

public interface SongMetaContentRepository extends JpaRepository<SongMeta, SongContentId> {
	
}
