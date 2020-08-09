package com.sim.wicmsapi.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.service.ContentProcessFTPService;

@Component
public class ContentProcessFTPWriter implements ItemWriter<ContentProcessFTP> {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPWriter.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	@Autowired
	ContentProcessFTPService contentProcessFTPService;
	
	@Override
	public void write(List<? extends ContentProcessFTP> items) throws Exception {	
		for (ContentProcessFTP contentProcessFTP : items) {
			contentProcessFTPService.save(contentProcessFTP);
			logger.info(myMarker," {} " ,contentProcessFTP);
		}		
	}

}
