package com.sim.wicmsapi.batch.reader;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sim.wicmsapi.dao.ContentProcessFTPRepository;
import com.sim.wicmsapi.entity.ContentProcessFTP;

@Component
public class ContentProcessFTPReader implements ItemReader<ContentProcessFTP> {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPReader.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	@Autowired
	ContentProcessFTPRepository contProcessFTPRepo;
	
	private List<ContentProcessFTP> contentProcessFTPs;

	int index;
	
	@PostConstruct
	public void init() {
		contentProcessFTPs = contProcessFTPRepo.findData();
		index = 0;
	}
	
	@Override
	public ContentProcessFTP read()
			throws Exception {
		ContentProcessFTP entity = null;
		if (contentProcessFTPs != null && contentProcessFTPs.size() > index) {
			entity = contentProcessFTPs.get(index);
			index++;
		}else {
			logger.info(myMarker, "No Data Found..");
		}
		return entity;
	}

}
