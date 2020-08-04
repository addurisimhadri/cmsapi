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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "songs_meta")
@IdClass(SongContentId.class)
public class SongMeta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="sm_c_id")
	private int smId;
	@Id
	@Column(name="sm_c_ct_type_id")
	private int contentTypeId;
	@Column(name="sm_artist_id")
	private String artistId;
	@Column(name="sm_album_id")
	private int albumId;
	@Column(name="sm_producers")
	private String producers;
	@Column(name="sm_music_directors")
	private String musicDirectors;
	@Column(name="sm_actors")
	private String actors;
	@Column(name="sm_actress")
	private String actress;
	@Column(name="sm_singers")
	private String singers;	
	@Column(name="sm_choreographer")
	private String choreographer;
	@Column(name="sm_prod_companies")
	private String prodCompanies;
	@Column(name="sm_content_length_time")
	private int contentLengthTime;
	@Column(name="sm_lyrics")
	private String lyrics;
	@Column(name="sm_review")
	private String review;
	@Column(name="sm_trivia")
	private String trivia;
	@Column(name="sm_content_owner")
	private String contentOwner;
	@Column(name="sm_production_countries")
	private String productionCountries;
	@Column(name="sm_artist")
	private String artist;
	@Column(name="sm_meta_languages")
	private String metaLanguages="English";
	@Column(name="sm_rent_price")
	private int rentPrice;
	@Column(name="sm_album")
	private String album;
	@Column(name="sm_release_date",nullable = false)
	private java.util.Date releaseDate;
	@Column(name="sm_directors")
	private String directors;
	
}
