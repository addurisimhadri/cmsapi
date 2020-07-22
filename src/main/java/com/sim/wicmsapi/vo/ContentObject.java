package com.sim.wicmsapi.vo;

import java.util.Date;

public class ContentObject  {
	
	private String title = "";
	private String contentName = "";
	private String cpContentName = "";
	private String webSample = "";
	private String wapSample = "";
	private String keyword = "";	
	private String shortDesc = "";
	private String longDesc = "";
	private String wmlSample = "";	
	private Date validFrom = null;
	private Date validTo = null;
	private String category = ""; 
	private String subCategory="";
	private int rating = 0;
	private int genre = 0;
	private int mood=0;
	private int purchaseType = 0;
	private int purchasePrice = 0;
	private String currencyType ="";
	private int cpId=-1;
	private String location="";
	private GameMetaContentObject gmcObject;
	private String parentalRating="0";
	private SongMetaContentObject smcObject;
	private String language;
	private String metaLanguages="English";
	public String getMetaLanguages() {
		return metaLanguages;
	}
	public void setMetaLanguages(String metaLanguages) {
		this.metaLanguages = metaLanguages;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public SongMetaContentObject getSmcObject() {
		return smcObject;
	}
	public void setSmcObject(SongMetaContentObject smcObject) {
		this.smcObject = smcObject;
	}
	public String getParentalRating() {
		return parentalRating;
	}
	public void setParentalRating(String parentalRating) {
		this.parentalRating = parentalRating;
	}
	public GameMetaContentObject getGmcObject() {
		return gmcObject;
	}
	public void setGmcObject(GameMetaContentObject gmcObject) {
		this.gmcObject = gmcObject;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getCpContentName() {
		return cpContentName;
	}
	public void setCpContentName(String cpContentName) {
		this.cpContentName = cpContentName;
	}
	public String getWebSample() {
		return webSample;
	}
	public void setWebSample(String webSample) {
		this.webSample = webSample;
	}
	public String getWapSample() {
		return wapSample;
	}
	public void setWapSample(String wapSample) {
		this.wapSample = wapSample;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	public String getWmlSample() {
		return wmlSample;
	}
	public void setWmlSample(String wmlSample) {
		this.wmlSample = wmlSample;
	}
	
	
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getGenre() {
		return genre;
	}
	public void setGenre(int genre) {
		this.genre = genre;
	}
	public int getMood() {
		return mood;
	}
	public void setMood(int mood) {
		this.mood = mood;
	}
	public int getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}
	public int getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}


	

}//class
