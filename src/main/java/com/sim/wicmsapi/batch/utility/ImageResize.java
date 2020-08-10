package com.sim.wicmsapi.batch.utility;
/*
 
 Project Name: Image Resizing
 Author: Appanna
 Date:  29-Sep-2010
 Description: 
 Image Resizing engine uses Open source IMAGE MAGIC TOOL to process the images from one format to other format.
 Used command line option in the below Class to get the desired result.
 Initialyy We are Taking 3 base images from the User Location after that This Class
 will convert 3 Base images to 36 Different Sizes
 Methods Used: 
				processImageResizes()
				getBaseFiles()
				main()
				
 Variable Notation: Hungarian 
 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

import com.sim.wicmsapi.batch.config.BeanUtil;
import com.sim.wicmsapi.vo.PropertiesBean;

@Component
public class ImageResize{
	private static final Logger logger = LoggerFactory.getLogger(ImageResize.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
		
	static String fileSeparator=File.separator;
	
	String[] strScrnResWEQH=null; 
	String[] strScrnResWGTRH=null;  
	String[] strScrnResWLESR=null; 
	String[] reSizes960x760=null; 
	String[] reSizes960x570=null;
	Process p = null;
	String result="";
	String execPath; 
	
	File destinationDir = null;
	File sourceDir=null;
	 /* myMusicProcessImageResizes() Statrs */
	public String myMusicProcessImageResizes(String sourcefilePath,String destinationfilePath,int contentType,Map<String, String> baseFiles)  {		
		List<String> list = null ;
		File fileImage = null;
		File currentfile1 = null ;
		String srcDirectory=null;
		String  desDirectory=null;
		try { 
			PropertiesBean propertiesBean = BeanUtil.getBean(PropertiesBean.class);
			execPath=propertiesBean.getExPath();
			strScrnResWEQH=propertiesBean.getWeh().split(",");
			strScrnResWGTRH=new String[0];
			strScrnResWLESR=new String[0]; 
			reSizes960x760=propertiesBean.getW7h().split(",");
			reSizes960x570=propertiesBean.getW5h().split(",");
		
			logger.info(myMarker, "propertiesBean {} ",propertiesBean);	
			srcDirectory=sourcefilePath;
			desDirectory=destinationfilePath;
		} 
 		catch(Exception e) {
 			e.printStackTrace();
 			logger.error(myMarker, "Ex {} ", e.toString());		
		}
		try	{
			logger.info(myMarker, "sourceDir {} == {} ", sourceDir,desDirectory);	
			sourceDir = new File(srcDirectory);
			destinationDir = new File(desDirectory);			
			if(!destinationDir.exists()) {
				destinationDir.mkdir();	
			}
			list =new  ArrayList<>(baseFiles.values());
			logger.info(myMarker, "baseFiles {} ",baseFiles.values());			 
			Iterator<String> it=list.iterator();
			while(it.hasNext()) {
				String strElement =it.next();
				fileImage = new File(strElement);
				currentfile1 =fileImage;
				long width = Utility.getWidth(fileImage);
				long height = Utility.getHeight(fileImage);
				logger.info(myMarker, "sourceDir {} == {} == {} ", currentfile1,width,height);
				if(width>100)
					resizeImage(currentfile1, width, height,contentType);
				fileImage = null ;
				currentfile1 = null;
			}	
		} 	               
		catch(Exception e) {
			e.printStackTrace();
			result="NOK";
			logger.error(myMarker,"Error Message  {} ", e.getMessage());
		} 
		finally {
			if(baseFiles != null)
				baseFiles = null;
        }
		return result;		
	}	

	public String processImageResizes(String sourcefilePath,String destinationfilePath,int contentType,Map<String, String> baseFiles)  {		
		List<String> list = null ;
		File fileImage = null;
		File currentfile1 = null ;
		String srcDirectory=null;
		String  desDirectory=null;
		try { 
			PropertiesBean propertiesBean = BeanUtil.getBean(PropertiesBean.class);
			execPath=propertiesBean.getExPath();
			strScrnResWEQH=propertiesBean.getWeh().split(",");
			strScrnResWGTRH=propertiesBean.getWeh().split(",");
			strScrnResWLESR=propertiesBean.getWlh().split(",");
			reSizes960x760=new String[0];
			reSizes960x570=new String[0];
		
			logger.info(myMarker, "propertiesBean {} ",propertiesBean);	
			srcDirectory=sourcefilePath;
			desDirectory=destinationfilePath;
		} 
 		catch(Exception e) {
 			e.printStackTrace();
 			logger.error(myMarker, "Ex {} ", e.toString());		
		}
		try	{
			logger.info(myMarker, "sourceDir {} == {} ", sourceDir,desDirectory);	
			sourceDir = new File(srcDirectory);
			destinationDir = new File(desDirectory);			
			if(!destinationDir.exists()) {
				destinationDir.mkdir();	
			}
			list =new  ArrayList<>(baseFiles.values());
			logger.info(myMarker, "baseFiles {} ",baseFiles.values());			 
			Iterator<String> it=list.iterator();
			while(it.hasNext()) {
				String strElement =it.next();
				fileImage = new File(strElement);
				currentfile1 =fileImage;
				long width = Utility.getWidth(fileImage);
				long height = Utility.getHeight(fileImage);
				logger.info(myMarker, "sourceDir {} == {} == {} ", currentfile1,width,height);
				if(width>100)
					resizeImage(currentfile1, width, height,contentType);
				fileImage = null ;
				currentfile1 = null;
			}	
		} 	               
		catch(Exception e) {
			e.printStackTrace();
			result="NOK";
			logger.error(myMarker,"Error Message  {} ", e.getMessage());
		} 
		finally {
			if(baseFiles != null)
				baseFiles = null;
        }
		return result;		
	}	
	

	public void resizeImage(File currentfile1,long width, long height, int contentType) {
		try {
			if(width == height) {   
				for(int WEQH =0 ; WEQH<strScrnResWEQH.length ; WEQH++) {  
					String sizeWEQH = strScrnResWEQH[WEQH];
					resizeImageExecution(currentfile1, sizeWEQH);
					result="OK";                     
				}
			 }
			 else if(width > height){				
				 resizeImageCT(currentfile1, width, height, contentType);
			 }
			 else {
				 for(int HGTRW =0 ; HGTRW<strScrnResWLESR.length ; HGTRW++) {
					String sizeHGTRW = strScrnResWLESR[HGTRW];
					resizeImageExecution(currentfile1, sizeHGTRW);
					result="OK";
				 } 	
			}  
		} catch (Exception e) {
			logger.error(myMarker,"Ex  :: {} ",e.getMessage());
		}
		
	}
	public void resizeImageCT(File currentfile1,long width, long height, int contentType) {
		try {
			if(contentType==0) {
				 for(int HLESW =0 ; HLESW<strScrnResWGTRH.length ; HLESW++) {   
						String sizeHLESW = strScrnResWGTRH[HLESW];
						resizeImageExecution(currentfile1, sizeHLESW);
						result="OK";
					 }
			}
			else {
			if(width==960 && height==760) {
				for(int HLESW1 =0 ; HLESW1<reSizes960x760.length ; HLESW1++) {
					String sizeHLESW1 = reSizes960x760[HLESW1];
					resizeImageExecution(currentfile1, sizeHLESW1);
					result="OK";
				}
			}
		 	else if(width==960 && height==570) {
				for(int HLESW2 =0 ; HLESW2<reSizes960x570.length ; HLESW2++) {
					String sizeHLESW2 = reSizes960x570[HLESW2];
					resizeImageExecution(currentfile1, sizeHLESW2);
					result="OK";
				}
			}
			}
			
		} catch (Exception e) {
			logger.error(myMarker,"Ex :: {} ",e.getMessage());
		}
		
	}
	public  void resizeImageExecution(File currentfile1,String sizeHW) {
		
		try {
			if(!sizeHW.equals("")) { 
				int index2=currentfile1.getName().lastIndexOf('_');
				String fname2=currentfile1.getName().substring(0,index2+1);
				String fileExten=currentfile1.getName().substring(currentfile1.getName().indexOf('.'));
				String execmd=execPath+" "+sourceDir.getAbsolutePath()+File.separator+currentfile1.getName()+" -resize "+sizeHW+"! "+destinationDir.getAbsolutePath()+File.separator+fname2+sizeHW+fileExten;
				logger.info(myMarker,"execmd:: {} ",execmd);
				p = Runtime.getRuntime().exec(execmd);	
				p.waitFor();
				p.destroy();
				p = null;
			}
		} catch (Exception e) {
			logger.error(myMarker,"Ex:: {} ",e.getMessage());
		}	
	}
	public static void main(String[] args) {	
		 try {
			 Map<String, String> ht = new HashMap<>();
			new ImageResize().myMusicProcessImageResizes("Images","./CuteCats",1,ht);
		} catch (Exception e) {
			logger.error(myMarker,"Ex:: {} ",e.getMessage());
		}			
	}

	public Map<String, String> getBaseFiles(String sourcefilePath) {
		File sourceFile = new File(sourcefilePath);
		Map<String, String> ht = new HashMap<>();
		if(sourceFile.isDirectory()) {
			File[] sourceFiles = sourceFile.listFiles();
			for(int i=0 ; i<sourceFiles.length ; i++) {  
				File reqFile = sourceFiles[i]; 
				if(!reqFile.getName().endsWith(".db")) {	
					long width = Utility.getWidth(reqFile);
					long height = Utility.getHeight(reqFile);
					if(width == height && width==500 && height==500 ) {
						ht.put("WIDTH=HEIGHT",reqFile.getAbsolutePath());
					}
					else 
						if(width > height && width==640 && height==480) {
							ht.put("WIDTH>HEIGHT", reqFile.getAbsolutePath());
						}
						else if(width < height && width==480 && height==640) {
							ht.put("WIDTH<HEIGHT", reqFile.getAbsolutePath());
						}
				}
			}
		}
		return ht;
	} 	


}
