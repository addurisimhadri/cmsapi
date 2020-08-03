package com.sim.wicmsapi.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.SongMetaContentObject;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;


public class SongXlSheetParser {
	private static final Logger logger = LoggerFactory.getLogger(SongXlSheetParser.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	public static Map<String ,LinkedHashMap<String,ContentObject >> init(InputStream inputStream) {		
		Map<String, LinkedHashMap<String,ContentObject >> contentObjects = new Hashtable<String, LinkedHashMap<String,ContentObject >>();
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
				logger.info(myMarker, "total sheets {} ",totalSheet);
				if (totalSheet > 0) {					
					for (int j = 0; j < totalSheet; j++) {
						
					}//for
				}//if				
				s = workbook.getSheet(0);
				contentObjects = getHeadingFromXlsFile(s);
			}catch (IOException e) {
				e.printStackTrace();
			}//catch

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fileInputStream.close();
				fileInputStream=null;
			}catch (IOException e) {
				e.printStackTrace();
			}//catch
		}//finally
		contentObjects.remove("");
		return contentObjects;
	}//init

	public static Map<String ,LinkedHashMap<String,ContentObject >> getHeadingFromXlsFile(Sheet sheet) {
		logger.info(myMarker, "total sheets getHeadingFromXlsFile");
		ContentObject contentObject =null;
		SongMetaContentObject smcObject=null;
		Map<String ,LinkedHashMap<String,ContentObject >> contentObj = new Hashtable<>();
		LinkedHashMap<String,ContentObject > langContentObj = new LinkedHashMap<String,ContentObject >();
		int columnCount = sheet.getColumns();		
		int rowsCount = sheet.getRows();		
		rowsCount = rowsCount - 1;
		String title = "";
		String contentName = "";
		String metaLanguage = "";
		boolean isAnotherContent = false;
		logger.info(myMarker, " rows {}  columns {}",rowsCount,columnCount);
		try {
			for(int i = 0; i < rowsCount; i++) {				
				contentObject = new ContentObject();
				smcObject = new SongMetaContentObject();
				for (int j = 0; j < columnCount; j++) {					
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("ContentName") ||sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Content Name")) {						
						if(!sheet.getCell(j, i + 1).getContents().trim().equals("") ) {
							contentName = sheet.getCell(j, i + 1).getContents();
							contentObject.setContentName(contentName.trim());
							contentObject.setCpContentName(contentName.trim());
						}
						if ( (i + 2)==(rowsCount+1) || (!sheet.getCell(j, i + 2).getContents().equals("") ) ) {
							isAnotherContent = true;
						}

					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Title")) {
						title = sheet.getCell(j, i + 1).getContents();
						title = ZipUtility.getTruncatedString(title,isAnotherContent?ContentConstants.META_TITLE_LENGTH:ContentConstants.TITLE_LENGTH);
						contentObject.setTitle(title.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Content Language")) {
						contentObject.setLanguage(sheet.getCell(j, i + 1).getContents().trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Meta Language")) {
						metaLanguage = sheet.getCell(j, i + 1).getContents();
						metaLanguage = ZipUtility.getTruncatedString(metaLanguage,isAnotherContent?ContentConstants.META_C_METALANGUAGE_LENGTH:ContentConstants.C_METALANGUAGE_LENGTH);
						contentObject.setMetaLanguages(metaLanguage.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Web Sample")) {
						contentObject.setWebSample(sheet.getCell(j, i + 1).getContents().trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("wap Sample") || sheet.getCell(j, 0).getContents().equalsIgnoreCase("Wap Sample")) {
						contentObject.setWapSample(sheet.getCell(j, i + 1).getContents().trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Keywords") || sheet.getCell(j, 0).getContents().equalsIgnoreCase("Keyword")) {
						String keyword = sheet.getCell(j, i + 1).getContents();
						keyword = replacequote(keyword);
						contentObject.setKeyword(keyword.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Category")) {
						contentObject.setCategory(sheet.getCell(j, i + 1).getContents().trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Sub Category")) {
						contentObject.setSubCategory(sheet.getCell(j, i + 1).getContents().trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Short Description")) {
						String shortDesc = sheet.getCell(j, i + 1).getContents();
						shortDesc = replacequote(shortDesc);
						contentObject.setShortDesc(shortDesc.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Long Description")) {
						String longDesc = sheet.getCell(j, i + 1).getContents();
						longDesc = replacequote(longDesc);
						contentObject.setLongDesc(longDesc.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Wap Wml Sample")) {
						contentObject.setWmlSample(sheet.getCell(j, i + 1).getContents().trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Valid From")) {
						contentObject.setValidFrom(DateUtility.convertStringtoTS(sheet.getCell(j, i + 1).getContents().trim()));
					}			
					
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Valid To")) {
						contentObject.setValidTo(DateUtility.convertStringtoTS(sheet.getCell(j, i + 1).getContents().trim()));
					}	
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("rent price")) {
						try {
							smcObject.setRentPrice(!sheet.getCell(j, i + 1).getContents().equals("")?Integer.parseInt(sheet.getCell(j, i + 1).getContents().trim()):0);
						} catch(NumberFormatException ne) {
							smcObject.setRentPrice(0);
						}
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("purchase price")) {
						try {
							contentObject.setPurchasePrice(!sheet.getCell(j, i + 1).getContents().equals("")?Integer.parseInt(sheet.getCell(j, i + 1).getContents().trim()):0);
						} catch(NumberFormatException ne) {
							contentObject.setPurchasePrice(0);
						}
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("parental rating")) {
						contentObject.setParentalRating(!sheet.getCell(j, i + 1).getContents().equals("")?(sheet.getCell(j, i + 1).getContents().trim()):"0");
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Content Producer")) {
						smcObject.setContentOwner((replacequote(sheet.getCell(j, i + 1).getContents().trim())));
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Release Date")) {
						smcObject.setReleaseDate(DateUtility.convertStringtoTS(sheet.getCell(j, i + 1).getContents().trim()));
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Directors") || sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Director") ) {
						String directors = replacequote(sheet.getCell(j, i + 1).getContents());
						directors = ZipUtility.getTruncatedString(directors,isAnotherContent?ContentConstants.META_C_DIRECTORS_LENGTH:ContentConstants.C_DIRECTORS_LENGTH);
						smcObject.setDirectors(directors.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Producers")) {
						String producers = replacequote(sheet.getCell(j, i + 1).getContents());
						producers = ZipUtility.getTruncatedString(producers,isAnotherContent?ContentConstants.META_C_PRODUCRERS_LENGTH:ContentConstants.C_PRODUCRERS_LENGTH);
						smcObject.setProducers(producers.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Music Directors")) {
						String music_directors = replacequote(sheet.getCell(j, i + 1).getContents());
						music_directors = ZipUtility.getTruncatedString(music_directors,isAnotherContent?ContentConstants.META_C_MUSIC_DIRECTORS_LENGTH:ContentConstants.C_MUSIC_DIRECTORS_LENGTH);
						smcObject.setMusicDirectors(music_directors.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Actors")) {
						String actors = replacequote(sheet.getCell(j, i + 1).getContents());
						actors = ZipUtility.getTruncatedString(actors,isAnotherContent?ContentConstants.META_C_ACTORS_LENGTH:ContentConstants.C_ACTORS_LENGTH);
						smcObject.setActors(actors.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Actress")) {
						String actress = replacequote(sheet.getCell(j, i + 1).getContents());
						actress = ZipUtility.getTruncatedString(actress,isAnotherContent?ContentConstants.META_C_ACTORS_LENGTH:ContentConstants.C_ACTRESS_LENGTH);
						smcObject.setActress(actress.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Singers")) {
						String singers = replacequote(sheet.getCell(j, i + 1).getContents());
						singers = ZipUtility.getTruncatedString(singers,isAnotherContent?ContentConstants.META_C_SINGERS_LENGTH:ContentConstants.C_SINGERS_LENGTH);
						smcObject.setSingers(singers.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Choreographer")) {
						String choreographer = replacequote(sheet.getCell(j, i + 1).getContents());
						choreographer = ZipUtility.getTruncatedString(choreographer,isAnotherContent?ContentConstants.META_C_CHOREOGRAPHER_LENGTH:ContentConstants.C_CHOREOGRAPHER_LENGTH);	
						smcObject.setChoreographer(choreographer.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Production Companies")) {
						String production_companies = replacequote(sheet.getCell(j, i + 1).getContents());
						production_companies = ZipUtility.getTruncatedString(production_companies,isAnotherContent?ContentConstants.META_C_PRODUCTION_COMPAINES_LENGTH:ContentConstants.C_PRODUCTION_COMPAINES_LENGTH);	
						smcObject.setProdCompanies(production_companies.trim());
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Album Name")) {
						smcObject.setAlbum(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Artist Name")) {
						smcObject.setArtist(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
					}
					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Lyrics") || sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Lyricist")) {
						smcObject.setLyrics(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
					}

					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Review")) {
						smcObject.setReview(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
					}

					if (sheet.getCell(j, 0).getContents().trim().equalsIgnoreCase("Trivia")) {
						smcObject.setTrivia(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
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
					if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Currency Type")) {
						contentObject.setCurrencyType(replacequote(sheet.getCell(j, i + 1).getContents().trim()));
					}
					if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Production Countries")) {
						String production_countries = replacequote(sheet.getCell(j, i + 1).getContents().trim());
						production_countries = ZipUtility.getTruncatedString(production_countries,isAnotherContent?ContentConstants.META_C_PRODUCTION_COUNTIRES_LENGTH:ContentConstants.C_PRODUCTION_COUNTIRES_LENGTH);	
						smcObject.setProductionCountries(production_countries);
					}
					if (sheet.getCell(j, 0).getContents().equalsIgnoreCase("Rating")) {
						int c_rating = 4;
						try {
							c_rating = Integer.parseInt((sheet.getCell(j, i + 1).getContents().trim()));
						}catch(NumberFormatException ne) {
							c_rating = 4;
						}c_rating = c_rating>99?99:c_rating;
						contentObject.setRating(c_rating);
					}
				}
				if (contentObject != null) {
					if(!metaLanguage.trim().equals("")) {
						contentObject.setSmcObject(smcObject);
						langContentObj.put(metaLanguage.trim(), contentObject);
						logger.info(myMarker, " {} ",contentObject);
					}
					contentObject = null;
				}
				if (langContentObj != null && isAnotherContent) {
					contentObj.put(contentName.trim(),langContentObj);
					langContentObj = null;
					langContentObj = new LinkedHashMap<String,ContentObject>();
					isAnotherContent = false;
				}		
				
			}//for rows		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return contentObj;
	}//getHeadingFromXlsFile

	
	
	
	public static String replacequote(String str) {
		char ch='\'';
		return str.replace(ch+"","\\'");
	}//replace quote

	public static void main(String[] args) {
		/*try {
			SongXlSheetParser xlReader = new SongXlSheetParser();
			xlReader.init(new FileInputStream("F:\\Appanna old system backup as on 30-Mar-2015\\D Drive\\Content\\Wallpapers\\Space\\Ravangram.xls"));
			xlReader.init(new FileInputStream("F:\\Appanna old system backup as on 30-Mar-2015\\D Drive\\Content\\SFC\\NuvvaNuvva\\NuvvaNuvva1.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}//main

}//class
