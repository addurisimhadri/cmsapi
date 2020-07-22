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
@Table(name = "content")
@IdClass(ContentId.class)
public class Content  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="c_id")
	private int cId;
	@Id
	@Column(name="c_ct_type_id")
	private int ctTypeId;	
	@Column(name="c_name")
	private String name;
	@Column(name="c_cp_id",columnDefinition="int(11) default '0'")
	private int cpId;
	@Column(name="c_title")
	private String title;
	@Column(name="c_location")
	private String location;
	@Column(name="c_language", columnDefinition="varchar(20) DEFAULT 'ENGLISH'")
	private String language;
	@Column(name="c_genre",columnDefinition="int(11) default '0'")
	private int genre;
	@Column(name="c_mood",columnDefinition="int(11) default '0'")
	private int mood;
	@Column(name="c_ctg_id",columnDefinition="int(11) default '0'")
	private int ctgId;
	@Column(name="c_category")
	private String category;
	@Column(name="c_sub_category")
	private String subCategory;
	@Column(name="c_base_price")
	private String basePrice;
	@Column(name="c_parental_rating")
	private String parentalRating;
	@Column(name="c_upload_source")
	private String uploadSource;
	@Column(name="c_upload_timestamp")
	private java.util.Date uploadTimestamp;
	@Column(name="c_validity_from",nullable = false)
	private java.util.Date validityFrom;
	@Column(name="c_validity_to",nullable = false)
	private java.util.Date validityTo;
	@Column(name="c_status")
	private String status;
	@Column(name="c_sample_name")
	private String sampleName;
	@Column(name="c_xhtml_sample")
	private String xhtmlSample;
	@Column(name="c_wml_sample")
	private String wmlSample;
	
}
