package com.sim.wicmsapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.SongMetaContentRepository;
import com.sim.wicmsapi.entity.SongMeta;
import com.sim.wicmsapi.service.SongMetaService;

@Service
public class SongMetaServiceImpl implements SongMetaService {
	
	@Autowired
	SongMetaContentRepository songMetaContentRepository;

	@Override
	public SongMeta save(SongMeta songMeta) {
		return songMetaContentRepository.save(songMeta);
	}

	@Override
	public SongMeta findContentCT(int contId, int ctTypeId) {
		return songMetaContentRepository.findBySmIdAndContentTypeId(contId, ctTypeId);
	}

}
