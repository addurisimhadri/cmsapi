package com.sim.wicmsapi.utility;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.GameMeta;
import com.sim.wicmsapi.service.GameMetaService;
import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.UploadObject;

public class ContentUtility {
	private static final Logger logger = LoggerFactory.getLogger(ContentUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	private ContentUtility() {
		
	}
	public static Content storeContent(ContentObject contentObject,ContentType contentType,Content contentexists,GameMetaService gameMetaService) {
		
		Content content=new Content();
		try {
			if(contentexists==null) {
				content.setContId(contentType.getMaxId()+1);
				content.setLocation(contentObject.getLocation());
			}
			else {
				content.setContId(contentexists.getContId());
				content.setLocation(contentexists.getLocation());
			}
			content.setCtTypeId(contentType.getContentId());
			content.setName(contentObject.getCpContentName());
			content.setCpId(contentObject.getCpId());
			content.setPfId(contentObject.getPfId());
			content.setTitle(contentObject.getTitle());
			content.setLanguage("English");
			content.setGenre(contentObject.getGenre());
			content.setMood(contentObject.getMood());
			content.setCategory(contentObject.getCategory());
			content.setSubCategory(contentObject.getSubCategory());
			content.setBasePrice(contentObject.getPurchasePrice()+"");
			content.setParentalRating(contentObject.getParentalRating());
			content.setUploadSource(contentObject.getSource());
			content.setUploadTimestamp(new Date());
			content.setValidityFrom(contentObject.getValidFrom());
			content.setValidityTo(contentObject.getValidTo());
			content.setStatus("1");
			content.setSampleName(contentObject.getWebSample());
			content.setXhtmlSample(contentObject.getWapSample());
			content.setWmlSample(contentObject.getWmlSample());
			
			/*
			 * Meta content store 
			 */
			if(contentType.getContentId()==19) {
				GameMeta gameMeta=ContentUtility.storeGameMeta(contentObject, content);
				gameMetaService.save(gameMeta);
			}
			
		} catch (Exception e) {
			logger.info(myMarker,"Ex:: {}",e.getMessage());
		}
		return content;
	}

	public static GameMeta storeGameMeta(ContentObject contentObject, Content content) {
		
		GameMeta gameMeta=new GameMeta();
		try {
			gameMeta.setGmId(content.getContId());
			gameMeta.setContentId(content.getCtTypeId());
			String gameType="";
			switch (content.getCtTypeId()) {
			case 19:
				gameType="HTML5Games";
				break;
			case 13:
				gameType="Android";
				break;
			case 11:
				gameType="Java";
				break;
			default:
				break;
			}
			gameMeta.setGameType(gameType);
			gameMeta.setContentOwner("");
			gameMeta.setHomeLink(contentObject.getGmcObject().getHomeLink());
			gameMeta.setQuality(contentObject.getGmcObject().getQuality());
			gameMeta.setSplitBuild(contentObject.getGmcObject().getSplitBuild());
			
		} catch (Exception e) {
			logger.info(myMarker,"Ex:: {} ",e.getMessage());
		}	
		return gameMeta;
	}
	public static void checkUploadExtractLoc(UploadObject uploadObject){
		try {
		String uploadExtractLoc = uploadObject.getSrcDir();
		if(!uploadExtractLoc.endsWith(File.separator)) uploadExtractLoc = uploadExtractLoc+File.separator;
		/*
		 * We can Check the Zip File Name exists or not , if Zip File name exists we can delete.
		 */
		if( new File(uploadExtractLoc+uploadObject.getZipFileName()).exists() && new File(uploadExtractLoc+uploadObject.getZipFileName()).isDirectory() ) {
			org.apache.commons.io.FileUtils.deleteDirectory( new File(uploadExtractLoc+uploadObject.getZipFileName()) );
			String delete=uploadExtractLoc+uploadObject.getZipFileName();
			logger.info(myMarker," {} Delete existing folder", delete);
		}
		/*
		 * we are unzip the zip file.
		 */
		String zipFilePath=uploadObject.getZipFilePath();
		String startextra=uploadObject.getZipFileName()+" on "+new java.util.Date();
		logger.info(myMarker," Upzipping Started of {}", startextra);
		boolean status=ZipUtility.extractZipContentWithZip64File(zipFilePath,uploadExtractLoc,uploadObject.getZipFileName());
		logger.info(myMarker," Upzipping Ended of {} {} ", startextra,status);		
		
		}catch (Exception e) {
			logger.error(myMarker, "Ex: {}",e.getMessage());
		}
	}
	
	public static void copyContentZipToDest(UploadObject uploadObject,Content contentexist,ContentObject contentObject,Map<String, File> folderMap,String contentName) {
		String appendFolderName = FolderUtility.getFolderString();
		String destpath=uploadObject.getDestDir();
		try {
			
			String destinationPathTemp="";
			if(contentexist==null) {
				destinationPathTemp=destpath.endsWith(File.separator)?destpath+uploadObject.getCpId()+File.separator+appendFolderName:destpath+File.separator+uploadObject.getCpId()+File.separator+appendFolderName;
			}else {
				destinationPathTemp=destpath.endsWith(File.separator)?destpath+contentexist.getLocation():destpath+File.separator+contentexist.getLocation();
			}			
			if(!destinationPathTemp.endsWith(File.separator)) destinationPathTemp = destinationPathTemp+File.separator;
			logger.info(myMarker,"destinationPathTemp:: {} ",destinationPathTemp);
			File destFile1 = new File(destinationPathTemp);		
			if (!destFile1.exists()) {
				destFile1.mkdirs();
			}			
			contentObject.setLocation(uploadObject.getCpId()+File.separator+appendFolderName+File.separator+contentObject.getContentName());
			destinationPathTemp=destinationPathTemp.replace(File.separator+contentName,"");
			logger.info(myMarker, "destinationPathTemp :{}",destinationPathTemp);
			boolean status = FolderUtility.copyFolderContentToDest(folderMap,destinationPathTemp , contentName);
			logger.info(myMarker, "Copy content to Destination Directory:{}",status);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(myMarker,"Ex:: {} ",e.getMessage());
		}
		
	}

}
