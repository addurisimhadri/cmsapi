package com.sim.wicmsapi.utility;

import java.util.Date;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.ContentType;
import com.sim.wicmsapi.entity.SongMeta;
import com.sim.wicmsapi.vo.ContentObject;
import com.sim.wicmsapi.vo.UploadObject;

public class SongContentUtility {
	//private static final Logger logger = LoggerFactory.getLogger(SongContentUtility.class);
	public static Content storeSongContent(ContentObject contentObject,ContentType contentType,Content contentexists) {
		Content content=new Content();
		
		try {
			if(contentexists==null) {
				content.setCId(contentType.getMaxId()+1);
				content.setLocation(contentObject.getLocation());
			}
			else {
				content.setCId(contentexists.getCId());
				content.setLocation(contentexists.getLocation());
			}
			content.setCtTypeId(contentType.getContentId());
			content.setName(contentObject.getCpContentName());
			content.setCpId(contentObject.getCpId());
			content.setTitle(contentObject.getTitle());
			content.setLanguage(contentObject.getLanguage());
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
			content.setStatus("-1");
			content.setSampleName(contentObject.getWebSample());
			content.setXhtmlSample(contentObject.getWapSample());
			content.setWmlSample(contentObject.getWmlSample());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return content;
		
	}
	public static SongMeta storeSongMetaContent(ContentObject contentObject, Content content) {
		SongMeta songMeta=new SongMeta();
		try {
			songMeta.setSmId(content.getCId());
			songMeta.setContentTypeId(content.getCtTypeId());
			songMeta.setDirectors(contentObject.getSmcObject().getDirectors());
			songMeta.setActors(contentObject.getSmcObject().getActors());
			songMeta.setActress(contentObject.getSmcObject().getActress());
			songMeta.setAlbum(contentObject.getSmcObject().getAlbum());
			songMeta.setAlbumId(contentObject.getSmcObject().getAlbumId());
			songMeta.setArtist(contentObject.getSmcObject().getArtist());
			songMeta.setArtistId(contentObject.getSmcObject().getArtistId());
			songMeta.setContentOwner(contentObject.getSmcObject().getContentOwner());
			songMeta.setChoreographer(contentObject.getSmcObject().getChoreographer());
			songMeta.setLyrics(contentObject.getSmcObject().getLyrics());
			songMeta.setMusicDirectors(contentObject.getSmcObject().getMusicDirectors());
			songMeta.setMetaLanguages(contentObject.getMetaLanguages());
			songMeta.setProdCompanies(contentObject.getSmcObject().getProdCompanies());
			songMeta.setProducers(contentObject.getSmcObject().getProducers());
			songMeta.setProductionCountries(contentObject.getSmcObject().getProductionCountries());
			songMeta.setReleaseDate(contentObject.getSmcObject().getReleaseDate());
			songMeta.setRentPrice(contentObject.getSmcObject().getRentPrice());
			songMeta.setReview(contentObject.getSmcObject().getReview());
			songMeta.setSingers(contentObject.getSmcObject().getSingers());
			songMeta.setTrivia(contentObject.getSmcObject().getTrivia());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return songMeta;
	}
	public static ContentProcessFTP storeFTPContent(UploadObject uploadObject, Content content) {
		
		ContentProcessFTP contentProcessFTP=new ContentProcessFTP();
		try {
			//contentProcessFTP.setCpfId(content.getcId());
			contentProcessFTP.setContentTypeId(content.getCtTypeId());
			contentProcessFTP.setPfId(0);
			contentProcessFTP.setTitle(content.getTitle());
			contentProcessFTP.setProcessId(TransId.getCpTransId());
			contentProcessFTP.setCpContentName(content.getName());
			contentProcessFTP.setCpId(content.getCpId());
			contentProcessFTP.setLastUpdatedTimestamp(new Date());
			contentProcessFTP.setLocation(content.getLocation());
			contentProcessFTP.setProcessStatus("Success");
			contentProcessFTP.setProcessZipfile(uploadObject.getZipFileName());
			contentProcessFTP.setUploadTimestamp(content.getUploadTimestamp());
			contentProcessFTP.setSubmitStatus("In Queue");
			contentProcessFTP.setUploadType("FTP");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentProcessFTP;
		
	}
}
