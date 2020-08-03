package com.sim.wicmsapi.controller;

import
 java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.wicmsapi.entity.ContentProvider;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.process.ContentProcess;
import com.sim.wicmsapi.process.SongContentProcess;
import com.sim.wicmsapi.service.ContentLangService;
import com.sim.wicmsapi.service.ContentProcessFTPService;
import com.sim.wicmsapi.service.ContentProviderService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.GameMetaService;
import com.sim.wicmsapi.service.PhysicalFolderService;
import com.sim.wicmsapi.service.SongMetaService;
import com.sim.wicmsapi.utility.FTPUploadUtility;
import com.sim.wicmsapi.utility.UploadUtility;
import com.sim.wicmsapi.vo.ApiResponse;
import com.sim.wicmsapi.vo.FTPUploadObject;
import com.sim.wicmsapi.vo.UploadObject;

@CrossOrigin
@RestController
@RequestMapping(value ="/ftp")
@ComponentScan(basePackages="com.sim.upload")
public class FTPUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FTPUploadController.class);
	@Value("${upload.unziplocation}")
	public  String UPLOADED_FOLDER;	
	
	@Value("${upload.destfolder}")
	public  String DEST_FOLDER;	
	
	
	@Autowired
	ContentTypeService  contentTypeService;
	
	@Autowired
	ContentProviderService contentProviderService;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	GameMetaService gameMetaService;
	
	@Autowired
	SongMetaService songMetaService;
	
	@Autowired
	ContentLangService contentLangService;
	
	@Autowired
	ContentProcessFTPService contentProcessFTPService;
	
	@Autowired
	PhysicalFolderService physicalFolderService;
	
	 
	@GetMapping(value = "/getZileFileNames/{ccpId}/{cctype}")
	public List<FTPUploadObject> getZileFileNames(@PathVariable("ccpId") Integer cpId,@PathVariable("cctype") Integer contentId) {
		ContentProvider contentProvider=contentProviderService.getContentProvider(cpId);
		ContentType contentType=contentTypeService.getContentType(contentId).get();		
		//File file = new File(contentProvider.getServerFtpHome()+File.separator+contentType.getContentName().toUpperCase());
		File file = new File("F:\\Practice\\ftp\\Samsung"+File.separator+contentType.getContentName().toUpperCase());
		return FTPUploadUtility.getZipFileNames(file);
	}
	@PostMapping(value = "/upload")
	public  ApiResponse<Void> ftpUpload(@RequestParam("contentId") Integer contentId,@RequestParam("cpId") Integer cpId,@RequestParam("pfId") Integer pfId,@RequestParam("zipFileNames") String[] zipFileNames) {
		String status="";					
		try {
			UploadObject uploadObject=new UploadObject();						
			ContentType contentType=contentTypeService.getContentType(contentId).get();
			ContentProvider contentProvider=contentProviderService.getContentProvider(cpId);
			
			uploadObject.setContentType(contentType);
			uploadObject.setCtId(contentId);
			uploadObject.setCpId(cpId);
			uploadObject.setCtName(contentType.getContentName());
			uploadObject.setSrcDir(UPLOADED_FOLDER);
			UploadUtility.setPhisicalFolder(pfId, uploadObject, physicalFolderService);
			Map<String, String> contentLangMap =contentLangService.getLangMap();
			for (int i = 0; i < zipFileNames.length; i++) {
				String zipFileName = zipFileNames[i];
				String zipFilePath= "F:\\Practice\\ftp\\Samsung"+File.separator+contentType.getContentName().toUpperCase()+File.separator+zipFileName;
				uploadObject.setZipFilePath(zipFilePath);
				String zipFileName1  = zipFileName.substring(0,zipFileName.indexOf("zip")-1);
				uploadObject.setZipFileName(zipFileName1);
				uploadObject.setSource("FTP");
				switch (contentType.getContentId()) {	
				case 6:
				case 7:
				case 41:
					SongContentProcess.songContentProcess(uploadObject,contentService, contentTypeService,songMetaService,contentLangMap,contentProcessFTPService);
					break;
				case 31:
				case 32:
				case 42:
				case 19:
					
					/*
					 * Content Processing
					 */
					ContentProcess.contentProcess(uploadObject,contentService, contentTypeService,gameMetaService);
					File zipfile=new File(uploadObject.getZipFilePath());
					if(zipfile.exists()) {
						logger.info(zipfile.getName()+" Zip file Deleted");
						zipfile.delete();
					}
					break;
				case 11:
				case 13:
					
					break;

				default:
					break;
				}	
			}	
			logger.info("uploadObject::"+uploadObject+"|lenngth::"+zipFileNames.length);				
			String destpath=DEST_FOLDER+File.separator+contentType.getContentName();
			uploadObject.setDestDir(destpath);				
		} catch (Exception e) {
			e.printStackTrace();
		}			
		//return status;
		status="success";
		return new ApiResponse<>(HttpStatus.CREATED.value(), status, null);
	}
	
}
