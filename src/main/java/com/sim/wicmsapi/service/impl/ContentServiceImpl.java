package com.sim.wicmsapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentDeviceRepository;
import com.sim.wicmsapi.dao.ContentRepository;
import com.sim.wicmsapi.dao.GameMetaContentRepository;
import com.sim.wicmsapi.dao.PhysicalFolderRepository;
import com.sim.wicmsapi.dao.SongMetaContentRepository;
import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.GameMeta;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.entity.SongMeta;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.vo.ContentDTO;

@Service
public class ContentServiceImpl implements ContentService {
	private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	
	@Autowired
	ContentRepository contentRepository;
	
	@Autowired
	ContentDeviceRepository contentDeviceRepository;
	
	@Autowired
	SongMetaContentRepository songMetaContentRepository;
	
	@Autowired
	GameMetaContentRepository gameMetaContentRepository;
	
	@Autowired
	PhysicalFolderRepository physicalFolderRepository;
	

	@Override
	public Content save(Content content) {
		return contentRepository.save(content);
	}

	@Override
	public Content findContent(String name, int cpId, int ctTypeId) {
		return contentRepository.findContent(name, cpId, ctTypeId);
	}

	@Override
	public List<Content> getContentByCT(int ctTypeId) {
		return contentRepository.findByCtTypeId(ctTypeId);
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

	@Override
	public List<Content> getApprovedContentByCT(int ctTypeId) {
		return contentRepository.getByCtTypeId(ctTypeId);
	}

	@Override
	public void delete(List<ContentDTO> contentDTOs) {
		Iterator<ContentDTO> it=contentDTOs.iterator();
		while (it.hasNext()) {
			ContentDTO contentDTO = it.next();
			Content content=contentRepository.findByContIdAndCtTypeId(contentDTO.getContId(), contentDTO.getCtTypeId());	
			switch (contentDTO.getCtTypeId()) {
			case 6:
				SongMeta songMeta=songMetaContentRepository.findBySmIdAndContentTypeId(content.getContId(), content.getCtTypeId());
				songMetaContentRepository.delete(songMeta);
				
				break;
			case 19:
				GameMeta gameMeta=gameMetaContentRepository.findByGmIdAndContentId(content.getContId(), content.getCtTypeId());
				gameMetaContentRepository.delete(gameMeta);
				break;
			case 31:
				
				break;
			case 42:
				
				break;
				

			default:
				break;
			}
			PhysicalFolder physicalFolder=physicalFolderRepository.getOne(content.getPfId());
			String filePath=physicalFolder.getLocation()+File.separator+content.getLocation();
			if(new File(filePath).exists()){
				try {
					org.apache.commons.io.FileUtils.deleteDirectory( new File(filePath) );
				} catch (IOException e) {
					logger.error(myMarker," {} Directory is not exist  ",new File(filePath).getName());
				}
			}
			contentRepository.delete(content);
			contentDeviceRepository.deleteContentDevice(content.getContId(), content.getCtTypeId());
		}
	}

}
