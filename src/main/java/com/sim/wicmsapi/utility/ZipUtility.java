package com.sim.wicmsapi.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.enterag.utils.zip.EntryInputStream;
import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;

public class ZipUtility {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtility.class);
	/*private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                	fos.write(buffer, 0, len);
                }
                fos.close();         
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }*/
	public static boolean extractZipContentWithZip64File(String absolutePath ,String contentLocation ,String contentName) {
		boolean status = true;
		File f = null;
		FileEntry entry1 = null;
		Zip64File zipFile = null;
		try {	    	
			zipFile = new Zip64File(absolutePath);
			List<FileEntry> list = zipFile.getListFileEntries();
			Iterator<FileEntry> it  = list.iterator();
			//logger.info("ZipFileEntryList "+list);
			String zipFileName = absolutePath.substring(absolutePath.lastIndexOf(File.separator)+1, absolutePath.lastIndexOf("."));	 
			entry1 = zipFile.getFileEntry(contentName+File.separator);
			//logger.info("entry1entry1entry1 "+entry1);
			if(entry1 == null ) {
				new File(contentLocation+contentName).mkdir();
				contentLocation = contentLocation+contentName+File.separator;
			}
			//logger.info("unziplocation::"+contentLocation);
			while(it.hasNext()) {
				FileEntry entry = (FileEntry)it.next();
				if(entry.isDirectory()) {
					File dirFile = 	new File(contentLocation+entry.getName());
					if(dirFile.exists()) {	
						if(!dirFile.getName().equals(zipFileName)) {
							File[] files = dirFile.listFiles();
							for(int i=0 ; i <files.length ; i++){
								files[i].delete();
							}	
							files=null;
						}
					}
					else {
						dirFile.mkdir();
					}
					dirFile=null;
					continue;						
				}
				else {
					if(!((entry.getName().endsWith(".db")))) {
						File f1 = new File(contentLocation+entry.getName());
						if(!f1.exists()) {
							f1.createNewFile();	
						}
						EntryInputStream eis = zipFile.openEntryInputStream(entry.getName());
						copyFile(eis,f1);
						f1=null;
					}
				}
				entry=null;
			}
		    zipFile.close();
		}
	    catch(Exception e) {
	    	logger.error("Ex::"+e);
	    	e.printStackTrace();	    	
	    	status = false;	    	
	    }
		finally {
			if(zipFile!=null)zipFile=null;
			if(f!=null)f=null;
			if(entry1!=null)entry1=null;
		}
	    return status ;
	}
	public static void copyFile(InputStream in, File targetFile) throws IOException{
		
		try(OutputStream out = new FileOutputStream(targetFile);) {
			byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
        in.close();
		in=null;
		targetFile=null;
	}
	public static String getTruncatedString(String str,int len)
	{
		return (str.equals("")  || str.length()<len)?str:str.substring(0,len);
	}
}
