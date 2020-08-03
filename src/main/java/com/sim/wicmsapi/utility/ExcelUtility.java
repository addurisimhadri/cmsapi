package com.sim.wicmsapi.utility;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.vo.ContentObject;

public class ExcelUtility {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	private ExcelUtility() {}
	
	public static Map<String , ContentObject> parseXsl(String zipFilePath ) {
		Map<String , ContentObject> hashtable = null ;
		Enumeration<?> entries = null;
		try(ZipFile zipFile = new ZipFile(zipFilePath);) {			
			entries = zipFile.entries();
		    while(entries.hasMoreElements()) {
		    	ZipEntry entry = (ZipEntry)entries.nextElement();
			    if((entry.getName().endsWith(".xls")) && !(entry.getName().contains("devices")||entry.getName().contains("Devices"))) {
			    	hashtable = XlSheetParser.init(zipFile.getInputStream(entry));
			    }
		   }
		}
		catch(Exception e) {
			logger.info(myMarker,"Ex:: {}",e.getMessage());
		}
		finally {
			if(entries!=null)entries=null;
		}
		return hashtable;
	}
	public static Map<String ,LinkedHashMap<String,ContentObject >> songParseXsl(String zipFilePath ) {
		logger.info(myMarker, "{}","songParseXsl");
		Map<String ,LinkedHashMap<String,ContentObject >> hashtable = null ;
		Enumeration<?> entries = null;
		boolean status = false;
		try(ZipFile zipFile = new ZipFile(zipFilePath);) {			
			entries  = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
				logger.info(myMarker, "{}",entry.getName());
				if((entry.getName().endsWith(".xls") || entry.getName().endsWith(".xlsx")) && !(entry.getName().contains("devices")||entry.getName().contains("Devices"))) { 
					status = true ;
					hashtable = SongXlSheetParser.init(zipFile.getInputStream(entry));
				}
			}
			if(!status)
				hashtable = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(myMarker,"Ex:: {}",e.getMessage());
		}
		finally
		{
			if(entries!=null)entries=null;
		}
		return hashtable;
	}

}
