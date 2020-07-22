package com.sim.wicmsapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.GameMetaContentRepository;
import com.sim.wicmsapi.entity.GameMeta;
import com.sim.wicmsapi.service.GameMetaService;

@Service
public class GameMetaServiceImpl implements GameMetaService {

	@Autowired
	GameMetaContentRepository gameMetaContentRepository;
	
	@Override
	public GameMeta save(GameMeta gameMeta) {
		
		return gameMetaContentRepository.save(gameMeta);
	}

}
