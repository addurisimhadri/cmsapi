package com.sim.wicmsapi.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentDTO {
	private int contId;
	private int ctTypeId;
	private String name;
	private String title;
	private String sampleName;
	private String status;
}
