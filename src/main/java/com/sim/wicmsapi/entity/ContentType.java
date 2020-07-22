package com.sim.wicmsapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author appanna
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "content_types") 
public class ContentType  {	
	
	@Id
	@Column(name = "content_id")
	private long contentId;	
	@Column(name = "content_name")
	private String contentName;	
	@Column(name = "max_id")
	private int maxId;	
	@Column(name = "deliverykind")
	private String deliveryKind;	
	@Column(name = "content_name_alias")
	private String contentNameAlias;	
	@Column(name = "active")
	private String active;	
	@Column(name = "xmllink_req")
	private String xmlLinkReq;	
	
}
