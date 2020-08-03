package com.sim.wicmsapi.process;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.SongMeta;
import com.sim.wicmsapi.service.ContentProcessFTPService;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.service.ContentTypeService;
import com.sim.wicmsapi.service.SongMetaService;
import com.sim.wicmsapi.utility.ExcelUtility;
import com.sim.wicmsapi.utility.FolderUtility;
import com.sim.wicmsapi.utility.SongContentUtility;
import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.UploadObject;

public class SongContentProcess {
	private static final Logger logger = LoggerFactory.getLogger(SongContentProcess.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	private SongContentProcess() {
		
	}
	public static void songContentProcess(UploadObject uploadObject, ContentService contentService,
			ContentTypeService contentTypeService, SongMetaService songMetaService, Map<String, String> contentLangMap, ContentProcessFTPService contentProcessFTPService) {
		Map<String ,LinkedHashMap<String,ContentObject >> ht = null;
		LinkedHashMap<String,ContentObject > langHt= null;
		ContentType contentType=uploadObject.getContentType();
		String zipFilePath=uploadObject.getZipFilePath();
		String destpath=uploadObject.getDestDir();
		try {			
			/*
			 * Reading the content information from excel sheet
			 */
			ht= ExcelUtility.songParseXsl(zipFilePath);
			
			if( ht != null && ht.size() >= 1) {
				String appendFolderName="";
				Set<String> keys = ht.keySet();
				Iterator<String> keyIt=keys.iterator();
				while(keyIt.hasNext()) {
					String contentName =  keyIt.next();
					langHt = ht.get(contentName);
					ContentObject contentObj =  langHt.get("English");
					if(contentObj != null) {
						/*
						 * We are checking the content available or not.
						 */
						Content contentexist= contentService.findContent(contentName,uploadObject.getCpId(), contentType.getContentId());
						String destinationPathTemp="";
						if(contentexist==null) {
							appendFolderName= FolderUtility.getFolderString();							
							destinationPathTemp=destpath.endsWith(File.separator)?destpath+uploadObject.getCpId()+File.separator+appendFolderName:destpath+File.separator+uploadObject.getCpId()+File.separator+appendFolderName;
						}else {
							destinationPathTemp=destpath.endsWith(File.separator)?destpath+contentexist.getLocation().replaceAll(File.separator+contentName,""):destpath+File.separator+contentexist.getLocation().replaceAll(File.separator+contentName,"");
						}
						logger.info(myMarker,"destinationPathTemp {} ",destinationPathTemp);
						File destFile1 = new File(destinationPathTemp);
						if(!destFile1.exists()) {
							destFile1.mkdirs();						
						}//if
						File destFile  = new File(destinationPathTemp+File.separator+contentName);
						if(!destFile.exists()) {
							destFile.mkdirs();						
						}//if
						if(destFile.exists() && destFile.isDirectory()) {
							String metaLanguages = "";	
							File file1 = destFile;
							if(file1.isDirectory()) {
								langHt =ht.get(file1.getName());
								if(langHt != null) {
									String metaLanguage = "";
									Set<String> langEntries = langHt.keySet();		
									Iterator<String> it = langEntries.iterator();									
									Iterator<String> langIt = langEntries.iterator();
									metaLanguages = StringUtils.join(langIt, ", ");
									while(it.hasNext()) {
										metaLanguage = (String)it.next();										
										if(!metaLanguage.equals("")  &&  contentLangMap.containsValue(metaLanguage.replace(metaLanguage.charAt(0), Character.toUpperCase(metaLanguage.charAt(0)))) ) {											
											ContentObject contentObject = (ContentObject)langHt.get(metaLanguage);
											if(contentObject != null) {
												Pattern pattern = Pattern.compile("[^A-Za-z0-9_]");
												Matcher matcher = pattern.matcher(contentObject.getContentName());
												if(!matcher.find()){
													File previewDir  = new File(destinationPathTemp+File.separator+contentObject.getContentName()+File.separator+"Preview");
													if(!previewDir.exists()) {
															previewDir.mkdirs();						
													}//if
													File thumbnailsDir  = new File(destinationPathTemp+File.separator+contentObject.getContentName()+File.separator+"Thumbnails");
													if(!thumbnailsDir.exists()) {
															thumbnailsDir.mkdirs();						
													}//if
													contentObject.setCpId(uploadObject.getCpId());
													contentObject.setMetaLanguages(metaLanguages);
													contentObject.setSource(uploadObject.getSource());
													contentObject.setCategory(uploadObject.getCtName());
													contentObject.setSubCategory(uploadObject.getCpName());
													contentObject.setLocation(uploadObject.getCpId()+File.separator+appendFolderName+File.separator+contentObject.getContentName());
													if(metaLanguage.equalsIgnoreCase("English")) {
														Content content=SongContentUtility.storeSongContent(contentObject, contentType, contentexist);
														logger.info("Ex:: "+content);
														content=contentService.save(content);
														SongMeta songMeta=SongContentUtility.storeSongMetaContent(contentObject, content);
														songMetaService.save(songMeta);
														
														ContentProcessFTP contentProcessFTP=SongContentUtility.storeFTPContent(uploadObject, content);
														contentProcessFTPService.save(contentProcessFTP);
														
														if(contentexist==null)
															contentType.setMaxId(content.getContId());
													}
												}
											}
											
										}
										
									}
								}
							}
						}
					}else {
						logger.info("ContentObject is null");
					}
				}
				contentTypeService.save(contentType);
			}else {
				logger.info("No data in excel sheet");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Ex::"+e);
		}
		
		
		
	}

}
