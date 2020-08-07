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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "content_devices")
@IdClass(ContentDeviceId.class)
public class ContentDevice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="c_id")
	private int contId;
	@Id
	@Column(name="c_ct_type_id")
	private int ctTypeId;
	@Id
	@Column(name="c_fileName")
	private String contFileName;
	
	@Column(name="manufacturer")
	private String brand;
	@Column(name="model")
	private String model;
	@Column(name="height",columnDefinition = "smallint(5) UNSIGNED default '0'")
	private int height;
	@Column(name="width",columnDefinition = "smallint(5) UNSIGNED default '0'")
	private int width;
	@Column(name="formats",nullable = false)
	private String formats;
	@Column(name = "cd_size")
	private long fileSize;
	@Column(name="cd_base_model")
	private String baseModel;
	@Column(name="cd_random_code")
	private String randomCode;
	
}
