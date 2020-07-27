package com.sim.wicmsapi.utility;

import java.util.Date;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.GameMeta;
import com.sim.wicmsapi.vo.ContentObject;

public class ContentUtility {
	
	public static Content storeContent(ContentObject contentObject,ContentType contentType,Content contentexists) {
		
		Content content=new Content();
		try {
			if(contentexists==null) {
				content.setContId(contentType.getMaxId()+1);
				content.setLocation(contentObject.getLocation());
			}
			else {
				content.setContId(contentexists.getContId());
				content.setLocation(contentexists.getLocation());
			}
			content.setCtTypeId(contentType.getContentId());
			content.setName(contentObject.getCpContentName());
			content.setCpId(contentObject.getCpId());
			content.setTitle(contentObject.getTitle());
			content.setLanguage("English");
			content.setGenre(contentObject.getGenre());
			content.setMood(contentObject.getMood());
			content.setCategory(contentObject.getCategory());
			content.setSubCategory(contentObject.getSubCategory());
			content.setBasePrice(contentObject.getPurchasePrice()+"");
			content.setParentalRating(contentObject.getParentalRating());
			content.setUploadSource("WEB");
			content.setUploadTimestamp(new Date());
			content.setValidityFrom(contentObject.getValidFrom());
			content.setValidityTo(contentObject.getValidTo());
			content.setStatus("1");
			content.setSampleName(contentObject.getWebSample());
			content.setXhtmlSample(contentObject.getWapSample());
			content.setWmlSample(contentObject.getWmlSample());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public static GameMeta storeGameMeta(ContentObject contentObject, Content content) {
		
		GameMeta gameMeta=new GameMeta();
		try {
			gameMeta.setGmId(content.getContId());
			gameMeta.setContentId(content.getCtTypeId());
			String gameType="";
			switch (content.getCtTypeId()) {
			case 19:
				gameType="HTML5Games";
				break;
			case 13:
				gameType="Android";
				break;
			case 11:
				gameType="Java";
				break;
			default:
				break;
			}
			gameMeta.setGameType(gameType);
			gameMeta.setContentOwner("");
			gameMeta.setHomeLink(contentObject.getGmcObject().getHomeLink());
			gameMeta.setQuality(contentObject.getGmcObject().getQuality());
			gameMeta.setSplitBuild(contentObject.getGmcObject().getSplitBuild());
			
		} catch (Exception e) {
			
		}
		
		
		return gameMeta;
	}

}
