package com.sim.wicmsapi.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.process.ContentProcess;
import com.sim.wicmsapi.process.ZipFileProcess;
import com.sim.wicmsapi.service.ContentLangService;
import com.sim.wicmsapi.service.ContentProviderService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.GameMetaService;
import com.sim.wicmsapi.service.SongMetaService;
import com.sim.wicmsapi.vo.UploadObject;

@CrossOrigin
@RestController
@RequestMapping(value ="/web")
@ComponentScan(basePackages="com.sim.upload")
public class WebUploadController {
	private static final Logger logger = LoggerFactory.getLogger(WebUploadController.class);
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
	
	@PostMapping(value = "/upload")
	public String uploadSingleFile(@RequestParam("cctype") Integer contentId,@RequestParam("ccpId") Integer cpId,@RequestParam("folder") String folder,@RequestParam("file") MultipartFile file) {
		String status="";
			if (!file.isEmpty()) {				
				try {
					UploadObject uploadObject=new UploadObject();						
					ContentType contentType=contentTypeService.getContentType(contentId).get();
					
					uploadObject.setContentType(contentType);
					uploadObject.setCtId(contentId);
					uploadObject.setCpId(cpId);
					uploadObject.setCtName(contentType.getContentName());
					uploadObject.setSrcDir(UPLOADED_FOLDER);
					uploadObject.setFolder(folder);					
					
					String destpath=DEST_FOLDER+File.separator+contentType.getContentName();
					uploadObject.setDestDir(destpath);
										
					switch (contentType.getContentId()) {			
					case 31:
					case 32:
					case 42:
					case 19:
						/*
						 * content upload to Zip location
						 */
						
						status=ZipFileProcess.SingleUpload(file, UPLOADED_FOLDER,uploadObject);
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return status; 
		}
	
	
	
}
