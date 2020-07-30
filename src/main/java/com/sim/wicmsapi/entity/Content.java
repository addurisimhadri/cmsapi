package com.sim.wicmsapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
@Entity
@Table(name = "content")
@IdClass(ContentId.class)
public class Content  implements Serializable {
	
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
	@Column(name="c_pf_id",columnDefinition = "UNSIGNED INT(11)")
	@JsonIgnore
	private int pfId;
	@Column(name="c_name")
	private String name;
	@JsonIgnore
	@Column(name="c_cp_id",columnDefinition="int(11) default '0'")
	private int cpId;
	@Column(name="c_title")
	private String title;
	@Column(name="c_location")
	@JsonIgnore
	private String location;
	@JsonIgnore
	@Column(name="c_language", columnDefinition="varchar(20) DEFAULT 'ENGLISH'")
	private String language;
	@Column(name="c_genre",columnDefinition="int(11) default '0'")
	@JsonIgnore
	private int genre;
	@Column(name="c_mood",columnDefinition="int(11) default '0'")
	@JsonIgnore
	private int mood;
	@Column(name="c_ctg_id",columnDefinition="int(11) default '0'")
	@JsonIgnore
	private int ctgId;
	@JsonIgnore
	@Column(name="c_category")
	private String category;
	@JsonIgnore
	@Column(name="c_sub_category")
	private String subCategory;
	@JsonIgnore
	@Column(name="c_base_price")
	private String basePrice;
	@JsonIgnore
	@Column(name="c_parental_rating")
	private String parentalRating;
	@JsonIgnore
	@Column(name="c_upload_source")
	private String uploadSource;
	@JsonIgnore
	@Column(name="c_upload_timestamp")
	private java.util.Date uploadTimestamp;
	@JsonIgnore
	@Column(name="c_validity_from",nullable = false)
	private java.util.Date validityFrom;
	@JsonIgnore
	@Column(name="c_validity_to",nullable = false)
	private java.util.Date validityTo;
	@Column(name="c_status")
	private String status;
	@Column(name="c_sample_name")
	private String sampleName;
	@JsonIgnore
	@Column(name="c_xhtml_sample")
	private String xhtmlSample;
	@JsonIgnore
	@Column(name="c_wml_sample")
	private String wmlSample;
	
}
