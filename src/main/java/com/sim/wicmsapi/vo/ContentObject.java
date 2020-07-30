package com.sim.wicmsapi.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
	private int pfId=-1;
	private String source="Web";

}//class
