package com.sim.wicmsapi.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.GameMeta;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.GameMetaService;
import com.sim.wicmsapi.utility.ContentUtility;
import com.sim.wicmsapi.utility.ExcelUtility;
import com.sim.wicmsapi.utility.FolderUtility;
import com.sim.wicmsapi.utility.ZipUtility;
import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.UploadObject;

public class UploadProcess {
	private static final Logger logger = LoggerFactory.getLogger(UploadProcess.class);
	public static String SingleUpload(MultipartFile file,String UPLOADED_FOLDER, UploadObject uploadObject) {		
		String status="";
		try {
			UPLOADED_FOLDER="/home/appanna/zipfile";
			byte[] bytes = file.getBytes();
			File dir = new File(UPLOADED_FOLDER);
			if (!dir.exists())
				dir.mkdirs();		
			File uploadFile = new File(dir.getAbsolutePath()+ File.separator +file.getOriginalFilename());
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			outputStream.write(bytes);
			outputStream.close();
			uploadObject.setZipFilePath(uploadFile.getAbsolutePath());
			String zipFileName  = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("zip")-1);
			uploadObject.setZipFileName(zipFileName);
			status = status +  " Successfully uploaded file=" + file.getOriginalFilename();
			
		} catch (Exception e) {
			status = status +  "Failed to upload " + file.getOriginalFilename()+ " " + e.getMessage();
        }
		logger.info("status::"+status);		
		return status;	
	}
	public static void contentProcess(UploadObject uploadObject,ContentService contentService, ContentTypeService contentTypeService, GameMetaService gameMetaService) {
		Hashtable<String , ContentObject> ht = null;
		try {
			ContentType contentType=uploadObject.getContentType();
			String zipFilePath=uploadObject.getZipFilePath();
			logger.info("zipFilePath:: "+zipFilePath);
			String destpath=uploadObject.getDestDir();
			/*
			 * Reading the content information from excel sheet
			 */
			ht= ExcelUtility.parseXsl(zipFilePath);
			/*
			 * UnZipLocation
			 */
			String uploadExtractLoc = uploadObject.getSrcDir();
			if(!uploadExtractLoc.endsWith("/")) uploadExtractLoc = uploadExtractLoc+File.separator;
			/*
			 * We can Check the Zip File Name exists or not , if Zip File name exists we can delete.
			 */
			if( new File(uploadExtractLoc+uploadObject.getZipFileName()).exists() && new File(uploadExtractLoc+uploadObject.getZipFileName()).isDirectory() ) {
				org.apache.commons.io.FileUtils.deleteDirectory( new File(uploadExtractLoc+uploadObject.getZipFileName()) );
				logger.info(uploadExtractLoc+uploadObject.getZipFileName()+" Delete existing folder");
			}
			/*
			 * we are unzip the zip file.
			 */
			logger.info("Upzipping Started of "+uploadObject.getZipFileName()+" on "+new java.util.Date());
			boolean status=ZipUtility.extractZipContentWithZip64File(zipFilePath,uploadExtractLoc,uploadObject.getZipFileName());
			logger.info("Upzipping Ended of "+uploadObject.getZipFileName()+" on "+new java.util.Date()+"|status::"+status);
			/*
			 * 
			 */
			Map<String, File> folderMap = FolderUtility.readFolder( uploadExtractLoc+uploadObject.getZipFileName() );
			logger.info("folderMap:"+folderMap);
			/*
			 * Content processing start
			 */
			if( ht != null && ht.size() >= 1) {
				Enumeration<String> entries = ht.keys();
				String contentName = "";
				while( entries.hasMoreElements() )	{
					contentName = (String) entries.nextElement();
					String appendFolderName = FolderUtility.getFolderString();
					if(contentName!=null) {
						ContentObject contentObject = (ContentObject)ht.get(contentName);
						if(contentObject != null) {
							/*
							 * We are checking the content available or not.
							 */
							Content contentexist= contentService.findContent(contentName,uploadObject.getCpId(), contentType.getContentId());
							String destinationPathTemp="";
							if(contentexist==null) {
								destinationPathTemp=destpath.endsWith(File.separator)?destpath+uploadObject.getCpId()+File.separator+appendFolderName:destpath+File.separator+uploadObject.getCpId()+File.separator+appendFolderName;
							}else {
								destinationPathTemp=destpath.endsWith(File.separator)?destpath+contentexist.getLocation():destpath+File.separator+contentexist.getLocation();
							}
							contentObject.setCpId(uploadObject.getCpId());
							if(!destinationPathTemp.endsWith("/")) destinationPathTemp = destinationPathTemp+"/";
							logger.info("destinationPathTemp::"+destinationPathTemp);
							File destFile1 = new File(destinationPathTemp);		
							if (!destFile1.exists()) {
								destFile1.mkdirs();
							}
							destFile1=null;
							contentObject.setLocation(uploadObject.getCpId()+File.separator+appendFolderName+File.separator+contentObject.getContentName());
							status = FolderUtility.copyFolderContentToDest(folderMap, destinationPathTemp.replaceAll(File.separator+contentName,""), contentName);
							//logger.info("Copy content to Destination Directory:"+status);
							Content content= ContentUtility.storeContent(contentObject, contentType,contentexist);
							content=contentService.save(content);
							/*
							 * Meta content store 
							 */
							if(contentType.getContentId()==19 || contentType.getContentId()==13 || contentType.getContentId()==11) {
								GameMeta gameMeta=ContentUtility.storeGameMeta(contentObject, content);
								gameMetaService.save(gameMeta);
							}
							if(contentexist==null)
								contentType.setMaxId(content.getContId());
						}
					}
				}
				contentTypeService.save(contentType);
				logger.info(""+contentType);
			}
			/*
			 * ZipFile Name delete from unZiplocation
			 */
			if( new File(uploadExtractLoc+uploadObject.getZipFileName()).exists() ) {
				try {
					logger.info(uploadExtractLoc+uploadObject.getZipFileName()+ " is Deleted");
					org.apache.commons.io.FileUtils.deleteDirectory( new File(uploadExtractLoc+uploadObject.getZipFileName()) );
				}catch(Exception e) {
					e.printStackTrace();
					logger.info(uploadExtractLoc+uploadObject.getZipFileName()+ " not Deleted");
				}
			}	
		}
		catch (Exception e) {
			logger.info("Ex::"+e);
			e.printStackTrace();
		}
	}

}
