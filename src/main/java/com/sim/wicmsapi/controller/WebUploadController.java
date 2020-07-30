package com.sim.wicmsapi.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.process.ContentProcess;
import com.sim.wicmsapi.process.ZipFileProcess;
import com.sim.wicmsapi.service.ContentLangService;
import com.sim.wicmsapi.service.ContentProviderService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.GameMetaService;
import com.sim.wicmsapi.service.PhysicalFolderService;
import com.sim.wicmsapi.service.SongMetaService;
import com.sim.wicmsapi.vo.ApiResponse;
import com.sim.wicmsapi.vo.UploadObject;

@CrossOrigin
@RestController
@RequestMapping(value ="/web")
public class WebUploadController {
	private static final Logger logger = LoggerFactory.getLogger(WebUploadController.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	@Value("${upload.unziplocation}")
	public  String unZipLocation;	
	
	@Value("${upload.destfolder}")
	public  String destFolder;	
	
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
	PhysicalFolderService physicalFolderService;
	
	
	
	@PostMapping(value = "/upload")
	public ApiResponse<Void> uploadSingleFile(@RequestParam("contentId") Integer contentId,@RequestParam("cpId") Integer cpId,@RequestParam("pfId") Integer pfId,@RequestParam("zipFile") MultipartFile file) {
		String status="";
		ContentType contentType=new ContentType();
			if (!file.isEmpty()) {				
				try {
					String folder="action";
					UploadObject uploadObject=new UploadObject();
					Optional<ContentType> contentTypeOp=contentTypeService.getContentType(contentId);
					if(contentTypeOp.isPresent())
						contentType=contentTypeOp.get();
					uploadObject.setContentType(contentType);
					uploadObject.setCtId(contentId);
					uploadObject.setCpId(cpId);
					uploadObject.setCtName(contentType.getContentName());
					uploadObject.setSrcDir(unZipLocation);
					uploadObject.setFolder(folder);					
					
					PhysicalFolder physicalFolder=new PhysicalFolder();
					Optional<PhysicalFolder> physicalFolderOP= physicalFolderService.findById(pfId);
					if(physicalFolderOP.isPresent()) {
						physicalFolder=physicalFolderOP.get();
					}
					
					
					String destpath=physicalFolder.getLocation();
					uploadObject.setDestDir(destpath);
					uploadObject.setPfId(physicalFolder.getId());
					uploadObject.setCpName(physicalFolder.getFolderName());
					
										
					switch (contentType.getContentId()) {			
					case 31:
					case 32:
					case 42:
					case 19:
						/*
						 * content upload to Zip location
						 */
						
						status=ZipFileProcess.singleUpload(file, unZipLocation,uploadObject);
						/*
						 * Content Processing
						 */
						ContentProcess.contentProcess(uploadObject,contentService, contentTypeService,gameMetaService);
						
						/*
						 * ZipFile Name delete from unZiplocation
						 */
						String uploadExtractLoc = uploadObject.getSrcDir();
						if(!uploadExtractLoc.endsWith(File.separator)) uploadExtractLoc = uploadExtractLoc+File.separator;
						if( new File(uploadExtractLoc+uploadObject.getZipFileName()).exists() ) {
							String str=uploadExtractLoc+uploadObject.getZipFileName();							
							logger.info(myMarker, "{} is Deleted",str);
							org.apache.commons.io.FileUtils.deleteDirectory( new File(uploadExtractLoc+uploadObject.getZipFileName()) );
							
						}	
						File zipfile=new File(uploadObject.getZipFilePath());
						Files.delete(Paths.get(uploadObject.getZipFilePath()));
						logger.info(myMarker," Zip file Deleted {} ",zipfile.getName());
												
						break;
					case 11:
					case 13:
						
						break;

					default:
						break;
					}					
				} catch (Exception e) {
					logger.info(myMarker,"Ex:: {} ",e.getMessage());
				}
				
			}else {
				logger.info("Files are empty..");
			}
			return new ApiResponse<>(HttpStatus.CREATED.value(), status, null);
		}
	
	
	
}
