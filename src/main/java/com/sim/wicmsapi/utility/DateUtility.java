package com.sim.wicmsapi.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class DateUtility {
	private static  Logger logger = LoggerFactory.getLogger(DateUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	/**
	 * This function convert String to Timestamp
	 */	
	public static java.sql.Timestamp convertStringtoTimeStamp(String format,String text) {		
		java.sql.Timestamp timestamp = null;
		try{
	        SimpleDateFormat dateFormat  = new SimpleDateFormat(format);
		    java.util.Date parsedDate    = dateFormat.parse(text);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
		}
		catch(Exception e ) {
			e.printStackTrace();	
		}
		return timestamp; 
    }
	public static java.sql.Timestamp convertStringtoTS(String text) {		
		java.sql.Timestamp timestamp = null;
		String format="dd/MM/yyyy";
		if(text.contains("-"))
			format="dd-MM-yyyy";		
		if(text.equals("")) {
			timestamp = new java.sql.Timestamp(new Date().getTime());
		}else {
			try{
		        SimpleDateFormat dateFormat  = new SimpleDateFormat(format);
			    java.util.Date parsedDate    = dateFormat.parse(text);
				timestamp = new java.sql.Timestamp(parsedDate.getTime());
			}
			catch(Exception e ) {
				logger.error(myMarker, " {} {} ",e.getMessage(), text);
			}
		}
		return timestamp; 
    }
	public static String getValidTo(String validTo) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c11=Calendar.getInstance();
		try {
			Date validDate = formatter.parse(validTo);			
			c11.setTime(validDate);
			int maxYear=c11.get(Calendar.YEAR);
			int yearDiff=c11.get(Calendar.YEAR)-2038;		
			if(maxYear>=2038){		
				c11.add(Calendar.YEAR, -yearDiff);
				c11.set(Calendar.MONTH, 0);
				c11.set(Calendar.DATE, 1);
				Date validDate1=c11.getTime();
				validTo = formatter.format(validDate1);
				logger.info(myMarker,"valid_to in xsl parser :{} valid_Date: {} ",validTo,validDate1);	
			}
		} catch (Exception e) {
			logger.error(myMarker,"valid_to:: {} |Ex: {} ",validTo,e.getMessage());
		}		
		return validTo;		
	}
	
	public static void main(String[] args) {
		
		String text="20/11/2018";
		Timestamp ts= convertStringtoTS(text);
		logger.info(myMarker, " {} ",ts);
	}

}
