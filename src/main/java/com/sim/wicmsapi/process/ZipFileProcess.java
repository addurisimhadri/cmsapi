package com.sim.wicmsapi.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sim.wicmsapi.vo.UploadObject;

public class ZipFileProcess {
	private static final Logger logger = LoggerFactory.getLogger(ZipFileProcess.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private ZipFileProcess() {
		
	}
	public static String singleUpload(MultipartFile file,String unZipLocation, UploadObject uploadObject) {		
		String status="";
		File dir = new File(unZipLocation);
		if (!dir.exists())
			dir.mkdirs();
		File uploadFile = new File(dir.getAbsolutePath()+ File.separator +file.getOriginalFilename());
		try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));){
			byte[] bytes = file.getBytes();
			outputStream.write(bytes);
			uploadObject.setZipFilePath(uploadFile.getAbsolutePath());
			String zipFileName  = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("zip")-1);
			uploadObject.setZipFileName(zipFileName);
			status = status +  " Successfully uploaded file=" + file.getOriginalFilename();			
		} catch (Exception e) {
			status = status +  "Failed to upload " + file.getOriginalFilename()+ " " + e.getMessage();
        }
		logger.info(myMarker,"status:: {}", status);		
		return status;	
	}

}
