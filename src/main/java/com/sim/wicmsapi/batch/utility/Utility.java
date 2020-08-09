package com.sim.wicmsapi.batch.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Utility {	
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private Utility() {
		
	}
	public static  long getWidth(File fileName){
		long width =0;
		if(fileName.getName().endsWith(".bmp")) {
			BufferedImage img;
			try {
				img = ImageIO.read(fileName);
				if(img != null)
					width = img.getWidth();
			}
			catch (IOException e) {				
				logger.error(myMarker,"getWidth Ex: {} ",e.getMessage());
			}		
		}
		else {
			ImageIcon icon = new ImageIcon(fileName.getAbsolutePath());
			width = icon.getIconWidth();
		}
        return width;
	}
	public static  long getHeight(File fileName){
		long height =0;	
		if(fileName.getName().endsWith(".bmp")) {
			BufferedImage img;
			try {
				img = ImageIO.read(fileName);
				if(img != null)
					height = img.getHeight();
			}
			catch (IOException e) {
				logger.error(myMarker,"getHeight Ex: {} ",e.getMessage());
			}		
		}
		else {
			ImageIcon icon = new ImageIcon(fileName.getAbsolutePath());
			height = icon.getIconHeight();
		}
        return height;
	}
	public static String[] getWidthHeightbasedonFileName(String fileName, String sourcePath) {
		String widthxheight = "";
		String []widthheight = new String[2];
		try{
			if(!fileName.equalsIgnoreCase("Thumbs.db") && fileName.contains(".") && fileName.contains("_")) {
				String fileNamewothoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
				if(fileNamewothoutExtension.endsWith("_") || fileNamewothoutExtension.endsWith("x")|| fileNamewothoutExtension.endsWith("X")) {
					File srcFilename = new File(sourcePath);
					widthheight[0] = Utility.getWidth(srcFilename)+"";
					widthheight[1] = Utility.getHeight(srcFilename)+"";
			}else{
				widthxheight = fileName.substring(fileName.lastIndexOf('_')+1, fileName.lastIndexOf('.'));
				String temptest="";
				temptest = widthxheight.contains("x")?"x":"X";
				if(widthxheight.contains(temptest)){
					widthheight[0] = widthxheight.substring(0, widthxheight.indexOf(temptest));
					widthheight[1] = widthxheight.substring(widthxheight.indexOf(temptest)+1);
				}
			}
		}
		else {
			if(!fileName.equalsIgnoreCase("Thumbs.db")){
				File srcFilename = new File(sourcePath);
				widthheight[0] = getWidth(srcFilename)+"";
				widthheight[1] = getHeight(srcFilename)+"";
			}
			else
			{
				widthheight[0] = "0";
				widthheight[1] = "0";	
			}
		}
		}
		catch(Exception e) {
			logger.error(myMarker,"getWidthHeightbasedonFileName Ex: {} ",e.getMessage());
		}
		return widthheight;
	}

}
