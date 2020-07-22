package com.sim.wicmsapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "games_meta")
@IdClass(GameContentId.class)
public class GameMeta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="gm_id")
	private int gmId;
	@Id
	@Column(name="content_id")
	private int contentId;
	@Column(name="gm_game_type")
	private String gameType;
	@Column(name="gm_content_owner")
	private String contentOwner;
	@Column(name="gm_home_link")
	private String homeLink;
	@Column(name="gm_quality" , columnDefinition="Varchar(20) default 'SD'")
	private String quality="SD";
	@Column(name="gm_split_build", columnDefinition="Varchar(20) default 'No'")
	private String splitBuild="No";
	
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
	public int getGmId() {
		return gmId;
	}
	public void setGmId(int gmId) {
		this.gmId = gmId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getContentOwner() {
		return contentOwner;
	}
	public void setContentOwner(String contentOwner) {
		this.contentOwner = contentOwner;
	}
	
	public String getHomeLink() {
		return homeLink;
	}
	public void setHomeLink(String homeLink) {
		this.homeLink = homeLink;
	}
	
	
	
	
	/*
	 
	@Column(name="gm_no_of_playes")
	private int noOfPlayes;
	@Column(name="gm_dimentions")
	private int dimentions;
	@Column(name="gm_difficulty_levels")
	private String difficultyLevels;
	@Column(name="gm_series")
	private String series;
	@Column(name="gm_score")
	private String score;
	@Column(name="gm_inbuilt_ads",nullable = false,  columnDefinition="char(1) default 'N'")
	private char inbuiltAds;
	 public int getNoOfPlayes() {
		return noOfPlayes;
	}
	public void setNoOfPlayes(int noOfPlayes) {
		this.noOfPlayes = noOfPlayes;
	}
	public int getDimentions() {
		return dimentions;
	}
	public void setDimentions(int dimentions) {
		this.dimentions = dimentions;
	}
	public String getDifficultyLevels() {
		return difficultyLevels;
	}
	public void setDifficultyLevels(String difficultyLevels) {
		this.difficultyLevels = difficultyLevels;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public char getInbuiltAds() {
		return inbuiltAds;
	}
	public void setInbuiltAds(char inbuiltAds) {
		this.inbuiltAds = inbuiltAds;
	}  
	  
	  
	  
	 */

}
