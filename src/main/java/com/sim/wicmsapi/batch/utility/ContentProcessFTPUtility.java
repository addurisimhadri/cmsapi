package com.sim.wicmsapi.batch.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		boolean contentFlag=false;
		boolean previewFlag=false;
		boolean thumbnailFlag=false;
		if(!ftpObject.getContentURL().equals("")) {
			contentFlag = processContentURL(ftpObject,contentDeviceService,contentService);	
			if(contentFlag) item.setProcessStatus("Success");
		}
		if(!ftpObject.getPreviewURL().equals("")) {
			previewFlag=processContentPreviewURL(ftpObject,songMetaService);
		}
		if(!ftpObject.getThumbnail1URL().equals("")) {
			thumbnailFlag=processContentThumbnailURL(ftpObject,ftpObject.getThumbnail1URL(),contentService);
		}
		if(!contentFlag || !previewFlag || !thumbnailFlag ){
			logger.info(myMarker,"FTP Content Flag Failed cid {} ",ftpObject.getContentId()+" cttype = "+ftpObject.getContentTypeId());
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
		logger.info(myMarker," Processing the contentFile :{} ",contentFile);
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
						logger.info(myMarker," downloadFileFromZipFileLocation :{} ---- {}",requiredFile ,destinationLocation );
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
			logger.info(myMarker," processContent : ---- {}",fileName  );
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
		String location = upcObject.getPhysicalLocation()+File.separator+"Preview";
		File previewFile = downloadFileFromZipFileLocation(previewURL, location);
		logger.info(myMarker, "processContentPreviewURL------------ {} ",previewFile);
		if(previewFile != null)	{
			status = processContentPreview(previewFile,upcObject,songMetaService);
		}
		return status;
	}

	public static boolean processContentPreview(File previewFile, FTPProcessContentObject upcObject,
			SongMetaService songMetaService) {
		boolean processFlag=true;
		File previewDir = new File(upcObject.getPhysicalLocation()+File.separator+"Preview"+File.separator);
		File[] previews = previewDir.listFiles();
		try {
			logger.info(myMarker, "processContentPreview------------ {} ",previewFile);
			if(previews.length > 0) {
				int contId = upcObject.getContentId();
				int ctTypeId = upcObject.getContentTypeId();
				String previewName = previewFile.getName();
				SongMeta songMeta=songMetaService.findContentCT(contId, ctTypeId);
				songMeta.setAppPreview(previewName);
				songMetaService.save(songMeta);
				
			}
		}catch (Exception e) {
			processFlag=false;
			logger.error(myMarker," processContentPreview Ex::  {} ",e.getMessage());
		}
		return processFlag;
	}
	public static boolean processContentThumbnailURL(FTPProcessContentObject upcObject,String thumbnailURL, ContentService contentService)
	{
		boolean status = false;
		String location = upcObject.getPhysicalLocation()+File.separator+"Thumbnails";		
		List<File> thumbnailFiles = downloadImageFromZipFileLocation(thumbnailURL, location);
		logger.info(myMarker, "processContentThumbnailURL------------ {} ",thumbnailFiles);
		if(!thumbnailFiles.isEmpty()) {
			status = processContentThumbnail(thumbnailFiles,upcObject,contentService);
		}
		return status;
	}
	private static boolean processContentThumbnail(List<File> thumbnailFiles, FTPProcessContentObject upcObject, ContentService contentService) {
		boolean processFlag = true;
		try {
			ImageResize imageresize=new ImageResize();
			String thumbnailLocation = upcObject.getPhysicalLocation()+File.separator+"Thumbnails"+File.separator;
			Iterator<File> it=thumbnailFiles.iterator();
			Map<String,String> baseFiles = new HashMap<>();
			while (it.hasNext()) {
				File thumbnailFile = (File) it.next();
				String thumbnailName = thumbnailFile.getName();
				long width = Utility.getWidth(thumbnailFile);
				long height = Utility.getHeight(thumbnailFile);								
				baseFiles.put(width+"-"+height,thumbnailLocation+thumbnailName);
			}
			logger.info(myMarker, "processContentThumbnail------------ {} ============{} ",thumbnailFiles,baseFiles);
			imageresize.myMusicProcessImageResizes(thumbnailLocation,thumbnailLocation,1,baseFiles);
			insertThumbnailsToDB(thumbnailLocation, upcObject.getContentId(), upcObject.getContentTypeId(), contentService);
		}
		catch (Exception e) {
			processFlag=false;
		}
		return processFlag;
	}

	public static List<File> downloadImageFromZipFileLocation(String sourceLocation, String destinationLocation)
	{
		File requiredFile = null;
		List<File> files=new ArrayList<File>();
		try
		{
			logger.info(myMarker,"destinationLocation {} ",destinationLocation);
			File fs = new File(sourceLocation);
			File[] listFiles = fs.listFiles();
			if(listFiles!=null && listFiles.length >=1){
				for(File destfile : listFiles) {
					if( !destfile.isDirectory() ) {	
						requiredFile = destfile;
						files.add(requiredFile);
						logger.info(myMarker," downloadImageFromZipFileLocation :{} ---- {}",requiredFile ,destinationLocation );
						org.apache.commons.io.FileUtils.copyFileToDirectory(requiredFile,new File(destinationLocation),true);						
					}
				}
			}
		}
		catch (Exception e) {
			logger.info(myMarker,"downloadImageFromZipFileLocation Ex: {} ",e.getMessage());
		}
		return files;
	}
	public static void insertThumbnailsToDB(String thumbnailLocation,int cid,int contentTypeId,ContentService contentService){	
		try {	
			String[] widthxheight = null;
			File thumbnailDir = new File(thumbnailLocation);		
			File[] thumbnailDirList = thumbnailDir.listFiles();			
			String image25x25 = "";
			String image50x50 = "";
			String image100x100 = "";
			for(int k1 = 0 ; k1 < thumbnailDirList.length ; k1++) {
				File fileName = thumbnailDirList[k1];
				String imageName = fileName.getName();
				if(!imageName.equalsIgnoreCase("Thumbs.db")){					
					widthxheight = Utility.getWidthHeightbasedonFileName(imageName.substring(imageName.lastIndexOf('\\')+1),fileName.getAbsolutePath());
					long width1=0;
					long height1=0;
					if(widthxheight!=null){
						width1  = Long.parseLong(widthxheight[0]);
						height1 = Long.parseLong(widthxheight[1]);				
						if( width1 == 25 && height1 == 25 ) image25x25 = imageName;
						if( width1 == 50 && height1 == 50 ) image50x50 = imageName;
						if( width1 == 100 && height1 == 100 ) image100x100 = imageName;
	
					}
				} 
			}
			if( contentTypeId == 6 ) {
				Content content=contentService.findContentCT(cid, contentTypeId);				
				content.setXhtmlSample(File.separator+"Thumbnails"+File.separator+image50x50);
				content.setSampleName(File.separator+"Thumbnails"+File.separator+image100x100);
				content.setWmlSample(File.separator+"Thumbnails"+File.separator+image25x25);
				contentService.save(content);							
			}
		}
		catch (Exception e) {
			logger.error(myMarker,"getWidth Ex: {} ",e.getMessage());
		}	
	}
	//public static void
	
}
