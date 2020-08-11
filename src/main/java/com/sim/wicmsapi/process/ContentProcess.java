package com.sim.wicmsapi.process;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.service.ContentProcessFTPService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.GameMetaService;
import com.sim.wicmsapi.utility.ContentUtility;
import com.sim.wicmsapi.utility.ExcelUtility;
import com.sim.wicmsapi.utility.FolderUtility;
import com.sim.wicmsapi.utility.SongContentUtility;
import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.UploadObject;

public class ContentProcess {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcess.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private ContentProcess() {
		
	}
	
	public static void contentProcess(UploadObject uploadObject,ContentService contentService, ContentTypeService contentTypeService, GameMetaService gameMetaService, ContentProcessFTPService contentProcessFTPService) {
		Map<String , ContentObject> ht = null;
		String uploadExtractLoc = uploadObject.getSrcDir();
		ContentType contentType=uploadObject.getContentType();
		String zipFilePath=uploadObject.getZipFilePath();
		logger.info(myMarker,"zipFilePath {} ", zipFilePath);		
		try {		
			
			/*
			 * Reading the content information from excel sheet
			 */
			ht= ExcelUtility.parseXsl(zipFilePath);
			/*
			 * UnZipLocation
			 */			
			ContentUtility.checkUploadExtractLoc(uploadObject);				
			
			/*
			 * 
			 */
			Map<String, File> folderMap = FolderUtility.readFolder( uploadExtractLoc+File.separator+uploadObject.getZipFileName() );
			logger.info(myMarker, "folderMap {} ",folderMap);
			/*
			 * Content processing start
			 */
			if( ht != null && ht.size() >= 1) {
				Set<String> keys = ht.keySet();
				Iterator<String> keyIt=keys.iterator();
				String contentName = "";
				Content contentexist=null;
				Content content=null;
				while( keyIt.hasNext() )	{
					contentName = keyIt.next();						
					if(contentName!=null && ht.get(contentName)!=null) {						
						ContentObject contentObject = ht.get(contentName);						
						contentObject.setCpId(uploadObject.getCpId());
						contentObject.setPfId(uploadObject.getPfId());
						contentObject.setSubCategory(uploadObject.getCpName());
						contentObject.setSource(uploadObject.getSource());
						/*
						 * We are checking the content available or not.
						 */
						contentexist= contentService.findContent(contentName,uploadObject.getCpId(), contentType.getContentId());
						/**
						 * Copy Content Zip location to Destination
						 */						
						ContentUtility.copyContentZipToDest(uploadObject,contentexist,contentObject,folderMap, contentName);
						
						content= ContentUtility.storeContent(contentObject, contentType,contentexist,gameMetaService);
						content=contentService.save(content);
						
						if(content.getCtTypeId()==31) {
							ContentProcessFTP contentProcessFTP=SongContentUtility.storeFTPContent(uploadObject, content);
							contentProcessFTPService.save(contentProcessFTP);
						}
						
						if(contentexist==null)
							contentType.setMaxId(content.getContId());
					}		
					
				}
				contentTypeService.save(contentType);
				logger.info(myMarker,"{}",contentType);
			}
			
		}
		catch (Exception e) {
			logger.info(myMarker,"EX:: {}",e.getMessage());
		}
		
	}

}
