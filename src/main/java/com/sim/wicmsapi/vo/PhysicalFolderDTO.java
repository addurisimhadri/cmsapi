package com.sim.wicmsapi.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhysicalFolderDTO {	
	private int id;
	private String folderName;
	@JsonIgnore
	private String location;
	

}
