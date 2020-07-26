package com.sim.wicmsapi.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentRepository;
import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.vo.ContentDTO;

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

	@Override
	public List<Content> getContentByCT(int ctTypeId,Pageable pageable) {
		return contentRepository.findByCtTypeId(ctTypeId,pageable);
	}

	@Override
	public Content findContentCT(int contId, int ctTypeId) {
		return contentRepository.findByContIdAndCtTypeId(contId, ctTypeId);
	}

	@Override
	public void updateStatus(List<ContentDTO> contentDTOs) {		
		Iterator<ContentDTO> it=contentDTOs.iterator();
		while (it.hasNext()) {
			ContentDTO contentDTO = it.next();
			Content content=contentRepository.findByContIdAndCtTypeId(contentDTO.getContId(), contentDTO.getCtTypeId());
			content.setStatus("2");
			contentRepository.save(content);  		
		}
		
	}

}
