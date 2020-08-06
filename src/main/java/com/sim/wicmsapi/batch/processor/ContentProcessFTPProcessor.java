package com.sim.wicmsapi.batch.processor;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.ContentProvider;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.service.ContentProviderService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.PhysicalFolderService;
import com.sim.wicmsapi.utility.ZipUtility;

@Component
public class ContentProcessFTPProcessor implements ItemProcessor<ContentProcessFTP, ContentProcessFTP> {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPProcessor.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");	
	
	@Value("${upload.unziplocation}")
	public  String UPLOADED_FOLDER;	
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	ContentTypeService  contentTypeService;
	
	@Autowired
	ContentProviderService contentProviderService;
	
	@Autowired
	PhysicalFolderService physicalFolderService;
	
	@Override
	public ContentProcessFTP process(ContentProcessFTP item) throws Exception {
		
		
		ContentType contentType=contentTypeService.getContentType(item.getContentTypeId()).get();
		ContentProvider contentProvider=contentProviderService.getContentProvider(item.getCpId());
		PhysicalFolder physicalFolder=  physicalFolderService.findById(item.getPfId()).get();
		
		String cpPath = contentProvider.getServerFtpHome()+File.separator+contentType.getContentName().toUpperCase()+File.separator;
		if(new File(cpPath+item.getProcessZipfile()).exists()) {
			
			
			
		}else {
			item.setProcessStatus("Failed ("+item.getProcessZipfile()+" is not found)");
			logger.info(myMarker, " ContentProcessFTP  {} ",item);
		}
		
		
		logger.info(myMarker, " ContentProcessFTP  {} ",item);
		
		return item;
	}
	
	public void checkUploadExtractLoc(String zipFileName,String cpPath, ContentType contentType,ContentProvider contentProvider,PhysicalFolder physicalFolder) {
		
		String zipFileName1  = zipFileName;
		boolean isZipFile = false;
		if( zipFileName.endsWith(".zip") ) {
			zipFileName1 = zipFileName.substring(0,zipFileName.indexOf("zip")-1);
			isZipFile = true;
		}
		String uploadExtractLoc = UPLOADED_FOLDER;
		if(!uploadExtractLoc.endsWith(File.separator)) uploadExtractLoc = uploadExtractLoc+File.separator;
		uploadExtractLoc = uploadExtractLoc+"UnzipZipFileContent"+File.separator+contentProvider.getCpId()+File.separator+contentType.getContentId()+File.separator;
		File uploadExtractFile = new File(uploadExtractLoc);
		String zipFilePath ="";
		zipFilePath=cpPath+zipFileName;
		if(!uploadExtractFile.exists()) {
				uploadExtractFile.mkdirs();
		}
		logger.info(myMarker, "zipFilePath: {} uploadExtractLoc {}{} ",cpPath,uploadExtractLoc,zipFileName);
		
		boolean status = ZipUtility.extractZipContentWithZip64File(cpPath+zipFileName, uploadExtractLoc,zipFileName1);
		String startextra=zipFileName+" on "+new java.util.Date();
		logger.info(myMarker," Upzipping Ended of {} {} ", startextra,status);
		
	}

}
