package com.sim.wicmsapi.utility;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class FolderUtility {
	public static final Logger logger = LoggerFactory.getLogger(FolderUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	private FolderUtility() {}
	public static String getFolderString() {
		java.util.Calendar gc=new java.util.GregorianCalendar();
		String msgid="",temps="";
		temps=Integer.toString(gc.get(Calendar.YEAR));
		msgid=msgid+temps.substring(2);
		temps=Integer.toString(gc.get(Calendar.MONTH)+1);
		if(temps.length()<2) temps="0"+temps;
		msgid=msgid+temps+File.separator;
		temps=Integer.toString(gc.get(Calendar.DATE));
		if(temps.length()<2) temps="0"+temps;
		msgid=msgid+temps;
		temps=Integer.toString(gc.get(Calendar.HOUR_OF_DAY));
		msgid=msgid+temps+File.separator;
		temps=Integer.toString(gc.get(Calendar.MINUTE));
	    if(temps.length()<2) temps="0"+temps;
	    msgid=msgid+temps;
	    temps=Integer.toString(gc.get(Calendar.SECOND));
	    if(temps.length()<2) temps="0"+temps;
	    msgid=msgid+temps;
		return msgid;
	}
	public static Map<String, File> readFolder(String destinationPath) {
		Map<String, File> fileMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		File destFileDir = new File(destinationPath);
		File[] destFileArray = destFileDir.listFiles();		
		String destFileName = "";
		try {
			logger.info(myMarker,"destFileDir:: {}", destFileDir.getCanonicalPath());
			logger.info(myMarker,"destFileArray:: {}", destFileArray.length);
			if(destFileArray.length>0) {
				for(File destfile : destFileArray) {
					destFileName = destfile.getName();
					if( destfile.isDirectory() ) {//if xls file is there
						fileMap.put(destFileName, destfile);
					}//else
				}//for
			}
		}catch(Exception e) {
			logger.error(myMarker,"Ex:::: {}", e.getMessage());
		}//catch
		return fileMap;
	}//readFolder
	
	public static boolean copyFolderContentToDest(Map<String, File> folderMap ,String contentLocation ,String contentName) {
		boolean status = false;
		File contentDir = null;
		File destDir = null;
		try {	
			logger.info(myMarker,"Ex:::: {} {} {}", contentLocation,contentName,folderMap);
			if( folderMap!=null && folderMap.get(contentName)!=null ) {			
				contentDir = folderMap.get(contentName);										
				destDir = new File(contentLocation);			
				/*
				 * Using Copy directory and not moveDirectory as moveDirectory gives the Exception if directory already exist.
				 */
				org.apache.commons.io.FileUtils.copyDirectoryToDirectory( contentDir, destDir);
				status = true;
				logger.info("contentDir: "+contentDir.getAbsolutePath()+"| contentLocation: "+contentLocation+"| contentName "+contentName);
			}
			
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	logger.error(myMarker,"Ex:::: {}", e.getMessage());
	    	status = false;	    	
	    }finally {
	    	contentDir=null;
	    	destDir = null;
		}
		return status ;

	}//extractMusicFolderContent
}
