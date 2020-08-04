package com.sim.wicmsapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
@Entity
@Table(name = "content_lang")
@IdClass(ContentLangId.class)
public class ContentLang implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cl_id")
	private int id;
	
	@Id
	@Column(name="cl_code")
	private String code;
	@Column(name="cl_language")
	private String name;
	@Column(name="cl_active")
	private int active;
		
	
}
