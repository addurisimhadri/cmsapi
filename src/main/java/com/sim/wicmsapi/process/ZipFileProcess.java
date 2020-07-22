package com.sim.wicmsapi.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sim.wicmsapi.vo.UploadObject;

public class ZipFileProcess {
	private static final Logger logger = LoggerFactory.getLogger(ZipFileProcess.class);
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

}
