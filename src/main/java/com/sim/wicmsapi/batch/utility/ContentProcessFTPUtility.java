package com.sim.wicmsapi.batch.utility;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentDevice;
import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.SongMeta;
import com.sim.wicmsapi.service.ContentDeviceService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.SongMetaService;
import com.sim.wicmsapi.vo.FTPProcessContentObject;

public class ContentProcessFTPUtility {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private ContentProcessFTPUtility() {}
		
	public static boolean processContentDownload(FTPProcessContentObject ftpObject,ContentDeviceService contentDeviceService,ContentService contentService,ContentProcessFTP item,SongMetaService songMetaService)	{
		boolean contentFlag=false,previewFlag=false,thumbnailFlag=false,banner1Flag=false,banner2Flag=false;
		if(!ftpObject.getContentURL().equals(""))
		{
			contentFlag = processContentURL(ftpObject,contentDeviceService,contentService);	
			if(contentFlag) item.setProcessStatus("Success");
		}
		if(!ftpObject.getPreviewURL().equals(""))
		{
			previewFlag = processContentPreviewURL(ftpObject,songMetaService);
		}
		return contentFlag;
		
	}

	private static boolean processContentURL(FTPProcessContentObject ftpObject,ContentDeviceService contentDeviceService, ContentService contentService) {		
		boolean status = false;
		String contentURL = ftpObject.getContentURL();
		String location = ftpObject.getPhysicalLocation();
		java.sql.Timestamp updateTimeStamp = new java.sql.Timestamp(new java.util.Date().getTime());
		ftpObject.setUpdateTimeStamp(updateTimeStamp);
		File contentFile = downloadFileFromZipFileLocation(contentURL, location);
		if(contentFile != null)	{
			logger.info(myMarker," Processing the contentFile :{} ",contentFile.getName());
			status = processContent(contentFile,ftpObject,contentDeviceService,contentService);
		}
		return status;
	}
	public static File downloadFileFromZipFileLocation(String sourceLocation, String destinationLocation)
	{
		File requiredFile = null;
		try	{
			File fs = new File(sourceLocation);
			File[] listFiles = fs.listFiles();
			if(listFiles!=null && listFiles.length >=1) {
				for(File destfile : listFiles) 	{
					if( !destfile.isDirectory() ) {
						requiredFile = destfile;						
						org.apache.commons.io.FileUtils.copyFileToDirectory(requiredFile,new File(destinationLocation),true);
					}					
				}
			}
		}
		catch (Exception e) {
			logger.error(myMarker," downloadFileFromZipFileLocation Ex::  {} ",e.getMessage());
		}
		return requiredFile;
	}
	
	public static boolean processContent(File file,FTPProcessContentObject upcObject,ContentDeviceService contentDeviceService, ContentService contentService) {
		boolean processFlag = true;
		try {
			ContentDevice contentDevice=new ContentDevice();
			int contId = upcObject.getContentId();
			int ctTypeId = upcObject.getContentTypeId();
			
			contentDeviceService.deleteContentDevice(contId, ctTypeId);
			
			String fileName = file.getName();
			String fileFormat = fileName.substring(fileName.lastIndexOf('.')+1);
			long size = file.length();
			long rand=ThreadLocalRandom.current().nextLong( 910000000001L, 910999999999L);
			String randNum=Long.toHexString(rand).toUpperCase();
			if(ctTypeId == 42 && fileName.contains("_")) {
				String widthheight = fileName.substring(fileName.lastIndexOf('_')+1,fileName.lastIndexOf('.'));
				int  width = Integer.parseInt(widthheight.substring(0,widthheight.indexOf('x')));
				int  height = Integer.parseInt(widthheight.substring(widthheight.indexOf('x')+1));
				contentDevice.setWidth(width);
				contentDevice.setHeight(height);
			}
			contentDevice.setContId(contId);
			contentDevice.setCtTypeId(ctTypeId);
			contentDevice.setContFileName(fileName);
			contentDevice.setFileSize(size);
			contentDevice.setFormats(fileFormat);
			contentDevice.setRandomCode(randNum);
			
			contentDeviceService.save(contentDevice);
			
			
			Content content=contentService.findContentCT(contId, ctTypeId);
			content.setStatus("1");
			contentService.save(content);
		}catch (Exception e) {
			processFlag=false;
			logger.error(myMarker," processContent Ex::  {} ",e.getMessage());
		}
		
		return processFlag;		
	}
	public static boolean processContentPreviewURL(FTPProcessContentObject upcObject, SongMetaService songMetaService) {
		boolean status = false;
		String previewURL = upcObject.getPreviewURL();
		String location = upcObject.getPhysicalLocation()+"/Preview";
		File previewFile = downloadFileFromZipFileLocation(previewURL, location);
		if(previewFile != null)	{
			status = processContentPreview(previewFile,upcObject,songMetaService);
		}
		return status;
	}

	public static boolean processContentPreview(File previewFile, FTPProcessContentObject upcObject,
			SongMetaService songMetaService) {
		File previewDir = new File(upcObject.getPhysicalLocation()+"/Preview/");
		File[] previews = previewDir.listFiles();
		if(previews.length > 0) {
			int contId = upcObject.getContentId();
			int ctTypeId = upcObject.getContentTypeId();
			String previewName = previewFile.getName();
			SongMeta songMeta=songMetaService.findContentCT(contId, ctTypeId);
			songMeta.setAppPreview(previewName);
			songMetaService.save(songMeta);
			
		}
		return false;
	}
}
