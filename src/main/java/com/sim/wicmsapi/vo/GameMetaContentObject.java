package com.sim.wicmsapi.vo;

public class GameMetaContentObject  {
	
	private String homeLink;
	private String contentOwner;
	private String gameType;
	private String quality="SD";
	private String splitBuild="0";
	
	
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getSplitBuild() {
		return splitBuild;
	}
	public void setSplitBuild(String splitBuild) {
		this.splitBuild = splitBuild;
	}
	public String getHomeLink() {
		return homeLink;
	}
	public void setHomeLink(String homeLink) {
		this.homeLink = homeLink;
	}
	public String getContentOwner() {
		return contentOwner;
	}
	public void setContentOwner(String contentOwner) {
		this.contentOwner = contentOwner;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
}
