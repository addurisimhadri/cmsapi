package com.sim.wicmsapi.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.GameMetaContentObject;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class XlSheetParser {
	private static  Logger logger = LoggerFactory.getLogger(XlSheetParser.class);
	public static Map<String , ContentObject> init(InputStream inputStream) {		
		Map<String , ContentObject> contentObjects = new Hashtable<String , ContentObject>();
		InputStream fileInputStream = null;
		try {
			fileInputStream = inputStream;
			WorkbookSettings ws = null;
			Workbook workbook = null;
			Sheet s = null;
			int totalSheet = 0;	
			try {
				ws = new WorkbookSettings();
				ws.setLocale(new Locale("en", "EN"));
				workbook = Workbook.getWorkbook(fileInputStream, ws);
				totalSheet = workbook.getNumberOfSheets();
				if (totalSheet > 0) {					
					for (int j = 0; j < totalSheet; j++) {					
					}
				}				
				s = workbook.getSheet(0);
				contentObjects = getHeadingFromXlsFile(s);
	
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
			fileInputStream.close();
			fileInputStream=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	contentObjects.remove("");
		return contentObjects;
	}
	public static Map<String , ContentObject> getHeadingFromXlsFile(Sheet sheet) {
		ContentObject contentObject =null;
		GameMetaContentObject gmcObject=null;
		Map<String , ContentObject> contentObj = new Hashtable<>();
		int columnCount = sheet.getColumns();		
		int rowsCount = sheet.getRows();
		rowsCount = rowsCount - 1;
		String title = "";
		String contentName = "";
		for (int i = 0; i < rowsCount; i++) {
			try {
			contentObject = new ContentObject();
			gmcObject=new GameMetaContentObject();
			for (int j = 0; j < columnCount; j++) {
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("ContentName") ||sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Content Name")) {
					contentName = sheet.getCell(j, i + 1).getContents();
					if(!contentName.equals("")){
						contentObject.setContentName(contentName.trim());
						contentObject.setCpContentName(contentName.trim());
					}
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Title")) {
					title = sheet.getCell(j, i + 1).getContents();
					contentObject.setTitle(title);
				}				
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Web Sample")) {
					contentObject.setWebSample(sheet.getCell(j, i + 1).getContents());
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("wap Sample") || sheet.getCell(j, 0).getContents().equalsIgnoreCase("Wap Sample")) {
					contentObject.setWapSample(sheet.getCell(j, i + 1).getContents());
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Keywords") || sheet.getCell(j, 0).getContents().equalsIgnoreCase("Keyword")) {
					String keyword = sheet.getCell(j, i + 1).getContents();
					keyword = replacequote(keyword);
					contentObject.setKeyword(keyword);
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Category")) {
					contentObject.setCategory(sheet.getCell(j, i + 1)
							.getContents());
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Sub Category")) {
					contentObject.setSubCategory(sheet.getCell(j, i + 1)
							.getContents());
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Short Description")) {
					String shortDesc = sheet.getCell(j, i + 1).getContents();
					shortDesc = replacequote(shortDesc);
					contentObject.setShortDesc(shortDesc);
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Long Description")) {
					String longDesc = sheet.getCell(j, i + 1).getContents();
					longDesc = replacequote(longDesc);
					contentObject.setLongDesc(longDesc);
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Wap Wml Sample")) {
					contentObject.setWmlSample(sheet.getCell(j, i + 1)
							.getContents());
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Valid From")) {
					contentObject.setValidFrom(DateUtility.convertStringtoTS(sheet.getCell(j, i + 1).getContents()));
				}				
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Valid To")) {								
					String valid_to=sheet.getCell(j, i + 1).getContents();
					contentObject.setValidTo(DateUtility.convertStringtoTS(valid_to));
				}				
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Rating")) {
					int c_rating = 4;
					try{
						c_rating = Integer.parseInt((sheet.getCell(j, i + 1).getContents()));
					}catch(NumberFormatException ne){
						c_rating = 4;
					}c_rating = c_rating>99?99:c_rating;
					contentObject.setRating(c_rating);
				}
				if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Mood"))	{
					try {
						contentObject.setMood(!sheet.getCell(j, i + 1).getContents().equals("")?Integer.parseInt((sheet.getCell(j, i + 1).getContents())):0);
					}catch(NumberFormatException ne) {
						contentObject.setMood(1);//Party
					}
				}

				if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Genre")) {
					try {
						contentObject.setGenre(!sheet.getCell(j, i + 1).getContents().equals("")?Integer.parseInt((sheet.getCell(j, i + 1).getContents())):0);
					} catch(NumberFormatException ne) {
						contentObject.setGenre(0);
					}
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Purchase Price")) {
					try {
						contentObject.setPurchasePrice(!sheet.getCell(j, i + 1).getContents().equals("")?Integer.parseInt(sheet.getCell(j, i + 1).getContents().trim()):0);
					} catch(NumberFormatException ne) {
						contentObject.setPurchasePrice(0);
					}
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("parental rating")) {
					contentObject.setParentalRating(!sheet.getCell(j, i + 1).getContents().equals("")?(sheet.getCell(j, i + 1).getContents().trim()):"0");
				}
				if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Currency Type")) {
					contentObject.setCurrencyType(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
				}
				if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Quality")) {
					gmcObject.setQuality(!sheet.getCell(j, i + 1).getContents().equals("")?(sheet.getCell(j, i + 1).getContents().trim()):"SD");
				}
				if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Split Build")) {
					gmcObject.setSplitBuild(!sheet.getCell(j, i + 1).getContents().equals("")?(sheet.getCell(j, i + 1).getContents().trim()):"0");
				}
				if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Home Link")) {
					gmcObject.setHomeLink(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
				}
			}
			if (contentObject != null) {
				contentObject.setGmcObject(gmcObject);
				contentObj.put(contentName.trim(),contentObject);
				contentObject = null;
			}
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
		}
		return contentObj;
	}	
	public static String replacequote(String str) {
		char ch='\'';
		return str.replace(ch+"","\\'");
	}	
	public static void main(String[] args) {
		try {
			Map<String , ContentObject> contentObjects=XlSheetParser.init(new FileInputStream("F:\\Appanna old system backup as on 30-Mar-2015\\D Drive\\Content\\Games\\Action\\BorderwarDefencePatrol_SFC\\BorderwarDefencePatrol_SFC.xls"));
			System.out.println(contentObjects);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
