package com.sim.wicmsapi.utility;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sim.wicmsapi.vo.ContentObject;

public class ExcelUtility {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtility.class);
	public static Hashtable<String , ContentObject> parseXsl(String zipFilePath ) {
		Hashtable<String , ContentObject> hashtable = null ;
		Enumeration<?> entries = null;
		boolean status = false;
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			//logger.info(""+zipFile.size());
			entries = zipFile.entries();
		    while(entries.hasMoreElements()) {
		    	ZipEntry entry = (ZipEntry)entries.nextElement();
		    	//logger.info("entry::"+entry);
			    if((entry.getName().endsWith(".xls")) && !(entry.getName().contains("devices")||entry.getName().contains("Devices"))) {
			        status = true ;
			       // logger.info("--->"+zipFile.getInputStream(entry).read());
			        hashtable = XlSheetParser.init(zipFile.getInputStream(entry));
			    }
		   }
		    zipFile.close();
		   if(!status)
			   hashtable = null;
		}
		catch(Exception e) {
			logger.error("Ex:: "+e);
		}
		finally {
			if(entries!=null)entries=null;
		}
		return hashtable;
	}
	public static Hashtable<String ,LinkedHashMap<String,ContentObject >> songParseXsl(String zipFilePath )
	{
		Hashtable<String ,LinkedHashMap<String,ContentObject >> hashtable = null ;
		Enumeration<?> entries = null;
		ZipFile zipFile=null;
		boolean status = false;
		try {
			zipFile = new ZipFile(zipFilePath);
			entries  = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
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
			
		}
		finally
		{
			if(entries!=null)entries=null;
		}
		return hashtable;
	}

}
