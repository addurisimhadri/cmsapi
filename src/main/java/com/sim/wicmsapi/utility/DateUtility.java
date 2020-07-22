package com.sim.wicmsapi.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtility {
	private static  Logger logger = LoggerFactory.getLogger(DateUtility.class);
	/**
	 * This function convert String to Timestamp
	 */	
	public static java.sql.Timestamp convertStringtots(String format,String text) {		
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
		if(text.length()!=format.length()) {
			//text.substring(1, format.length()-1);
		}
		try{
	        SimpleDateFormat dateFormat  = new SimpleDateFormat(format);
		    java.util.Date parsedDate    = dateFormat.parse(text);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
		}
		catch(Exception e ) {
			logger.error("text::"+text+"Ex::"+e);
			e.printStackTrace();	
		}
		return timestamp; 
    }
	public static String getValidTo(String valid_to) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c11=Calendar.getInstance();
		try {
			Date valid_Date = formatter.parse(valid_to);			
			c11.setTime(valid_Date);
			int max_year=c11.get(Calendar.YEAR);
			int yearDiff=c11.get(Calendar.YEAR)-2038;		
			if(max_year>=2038){		
				c11.add(Calendar.YEAR, -yearDiff);
				c11.set(Calendar.MONTH, 0);
				c11.set(Calendar.DATE, 1);
				Date validDate=c11.getTime();
				valid_to = formatter.format(validDate);
				logger.info("valid_to in xsl parser :"+valid_to+"|valid_Date:"+valid_Date);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("valid_to::"+valid_to+"|Ex:"+e);
		}		
		return valid_to;		
	}
	
	public static void main(String[] args) {
		
		String text="20/11/2018";
		Timestamp ts= convertStringtoTS(text);
		System.out.println(ts);
	}

}
