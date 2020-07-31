package com.sim.wicmsapi.utility;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sim.wicmsapi.vo.FTPUploadObject;

public class FTPUploadUtility {
	/**
	   * The number of bytes in a kilobyte (2^10).
	   */
	  public static final int KBYTE = 1024;

	  /**
	   * The number of bytes in a megabyte (2^20).
	   */
	  public static final int MBYTE = KBYTE * KBYTE;

	  /**
	   * The number of bytes in a gigabyte (2^30).
	   */
	  public static final int GBYTE = MBYTE * KBYTE;
	
	public static	List<FTPUploadObject> getZipFileNames(File file) {
		List<FTPUploadObject> ftpUploadObjects= new ArrayList<>();
		FTPUploadObject ftpUploadObject=null;
		File[] listOfFiles = file.listFiles();
		System.out.println("============================"+listOfFiles.length+"==============="+file.getAbsolutePath());
		for(int j=0 ; j<listOfFiles.length ; j++) {
			ftpUploadObject= new FTPUploadObject();
			File file1 = listOfFiles[j];
			long timeStamp = file1.lastModified();
			java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
			java.util.Date dt1 = new java.util.Date(timeStamp);
			String uploadTime = sdf1.format(dt1);
			String fileSize= formatFileLength(file1);
			ftpUploadObject.setZipFileName(file1.getName());
			ftpUploadObject.setUploadTime(uploadTime);
			ftpUploadObject.setFileSize(fileSize);
			ftpUploadObjects.add(ftpUploadObject);
		}
		System.out.println("====================================="+ftpUploadObjects);
		return ftpUploadObjects;
	}
	/**
	   * Returns a nicer representation of the length of the file. The file length
	   * is returned as bytes, kilobytes or megabytes, with the unit attached.
	   */
	  public static String formatFileLength(File file) {
	    return formatFileLength(file.length());
	  }

	  /**
	   * Returns a nicer representation of the number as a file length. The number
	   * is returned as bytes, kilobytes or megabytes, with the unit attached.
	   */
	  public static String formatFileLength(long length) {
	    DecimalFormat format = new DecimalFormat("###0.##");
	    double num = length;
	    String unit;

	    if (length < KBYTE) {
	      unit = "B";
	    } else if (length < MBYTE) {
	      num /= KBYTE;
	      unit = "KB";
	    } else if (length < GBYTE) {
	      num /= MBYTE;
	      unit = "MB";
	    } else {
	      num /= GBYTE;
	      unit = "GB";
	    }

	    return format.format(num) + unit;
	  }
}
