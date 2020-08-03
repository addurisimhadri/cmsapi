package com.sim.wicmsapi.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.service.PhysicalFolderService;
import com.sim.wicmsapi.vo.UploadObject;

public class UploadUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private UploadUtility() {
		
	}
	
	
	 public static void copyFile(String srcPath, String destPath){		 	
	        try {
	        	String str="srcPath"+srcPath+"|destPath::"+destPath;
	        	logger.info(myMarker," {} ",str);
	        	copyDirectory(new File(srcPath), new File(destPath));
			} catch (IOException e) {				
				logger.error(myMarker, "Ex: {}", e.getMessage());
			}
	 }
	 
	 public static void copyFileIO(String srcPath, String destPath) throws IOException{
		 String str="srcPath"+srcPath+"|destPath::"+destPath;
		 logger.info(myMarker, " {} ",str);
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
	 
	 public static void setPhisicalFolder(int pfId, UploadObject uploadObject, PhysicalFolderService physicalFolderService) {
		 
		 try {
			 PhysicalFolder physicalFolder=new PhysicalFolder();
				Optional<PhysicalFolder> physicalFolderOP= physicalFolderService.findById(pfId);
				if(physicalFolderOP.isPresent()) {
					physicalFolder=physicalFolderOP.get();
				}			
				String destpath=physicalFolder.getLocation();
				uploadObject.setDestDir(destpath);
				uploadObject.setPfId(physicalFolder.getId());
				uploadObject.setCpName(physicalFolder.getFolderName());
		} catch (Exception e) {
			logger.error(myMarker, "Ex:: {}", e.getMessage());
		}
	 }
	 public static boolean verifyZipContent(String zipFilePath , String folderName) {
				boolean status = false;
				File f = null;
				ZipEntry entry = null;
				try( ZipFile zipFile = new ZipFile(zipFilePath);)	{					
					String zipFilePath1 = zipFile.getName();
					f = new File(zipFilePath1);
					String zipFileName = f.getName();				
					zipFileName = zipFileName.substring(0,zipFileName.indexOf("zip")-1);
					String zipEntry = zipFileName+"/"+folderName;
					logger.info(myMarker,"zipEntry {} ",zipEntry);
					entry = zipFile.getEntry(zipEntry);
					if(entry != null)
						status = true;
				}
				catch(Exception e) {
					logger.error(myMarker, "Ex: {} ", e.getMessage());
					e.printStackTrace();
				}
				finally{
					if(f!=null)f=null;
					if(entry!=null)entry=null;
				}
				return status;
		}
	 
	 
}
