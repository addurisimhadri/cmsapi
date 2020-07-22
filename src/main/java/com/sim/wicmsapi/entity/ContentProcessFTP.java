package com.sim.wicmsapi.entity;

import java.io.Serializable;
import java.util.Date;

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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content_process_ftp")
@IdClass(ContentProcessFTPId.class)
public class ContentProcessFTP implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="cpf_c_id")
	private  int cpfId;
	@Id
	@Column(name="cpf_ct_type_id")
	private  int contentTypeId;
	@Column(name="cpf_pf_id")
	private  int pfId;
	@Column(name="cpf_cp_id")
	private  int cpId;
	@Column(name="cpf_game_name")
	private  String location;
	@Column(name="cpf_title")
	private  String title;
	@Column(name="cpf_upload_timestamp",nullable = false)
	private  Date uploadTimestamp;
	@Column(name="cpf_process_id")
	private  long processId;
	@Column(name="cpf_process_status")
	private  String processStatus;
	@Column(name="cpf_lastupdated_timestamp",nullable = false)
	private  Date lastUpdatedTimestamp;
	@Column(name="cpf_process_zipfile")
	private  String processZipfile;
	@Column(name="cpf_cp_content_name")
	private  String cpContentName;
	@Column(name="cpf_submit_status")
	private  String submitStatus;
	@Column(name="cpf_upload_type")
	private  String uploadType;
		
	
}
