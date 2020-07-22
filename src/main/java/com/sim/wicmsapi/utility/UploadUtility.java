package com.sim.wicmsapi.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadUtility {
	private static final Logger logger = LoggerFactory.getLogger(UploadUtility.class);
	
	 public static void copyFile(String srcPath, String destPath){		 	
	        try {
	        	logger.info("srcPath"+srcPath+"|destPath::"+destPath);
	        	copyDirectory(new File(srcPath), new File(destPath));
			} catch (IOException e) {				
				e.printStackTrace();
			}
	 }
	 
	 public static void copyFileIO(String srcPath, String destPath) throws IOException{
		 logger.info("srcPath"+srcPath+"|destPath::"+destPath);
		 FileUtils.copyDirectory(new File(srcPath), new File(destPath));
	 } 
	 
	 public static void copyDirectory(File sourceDir, File targetDir)
			 throws IOException {
        if (sourceDir.isDirectory()) {
            copyDirectoryRecursively(sourceDir, targetDir);
        } else {
            Files.copy(sourceDir.toPath(), targetDir.toPath());
        }
    }
	 private static void copyDirectoryRecursively(File source, File target)
			 throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }
        for (String child : source.list()) {
            copyDirectory(new File(source, child), new File(target, child));
        }
	 }
	 
	 
}
