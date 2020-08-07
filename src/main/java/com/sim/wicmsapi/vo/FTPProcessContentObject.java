package com.sim.wicmsapi.vo;

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
public class FTPProcessContentObject extends ContentObject{
	private int contentId=0;
	private int contentTypeId=0;
	private int convert=0;
	private String physicalLocation="";
	private String processId="";
	private String contentURL = "";
	private String previewURL = "";
	private String thumbnail1URL = "";
	private String thumbnail2URL = "";
	private String thumbnail3URL = "";
	private java.sql.Timestamp updateTimeStamp=null;
	private String uploadType;

}
