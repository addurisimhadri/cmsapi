package com.sim.wicmsapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
	
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public java.util.Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(java.util.Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getSmId() {
		return smId;
	}
	public void setSmId(int smId) {
		this.smId = smId;
	}
	public int getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getArtistId() {
		return artistId;
	}
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public String getProducers() {
		return producers;
	}
	public void setProducers(String producers) {
		this.producers = producers;
	}
	
	
	public String getMusicDirectors() {
		return musicDirectors;
	}
	public void setMusicDirectors(String musicDirectors) {
		this.musicDirectors = musicDirectors;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getActress() {
		return actress;
	}
	public void setActress(String actress) {
		this.actress = actress;
	}
	public String getSingers() {
		return singers;
	}
	public void setSingers(String singers) {
		this.singers = singers;
	}
	
	public String getChoreographer() {
		return choreographer;
	}
	public void setChoreographer(String choreographer) {
		this.choreographer = choreographer;
	}
	public String getProdCompanies() {
		return prodCompanies;
	}
	public void setProdCompanies(String prodCompanies) {
		this.prodCompanies = prodCompanies;
	}
	public int getContentLengthTime() {
		return contentLengthTime;
	}
	public void setContentLengthTime(int contentLengthTime) {
		this.contentLengthTime = contentLengthTime;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getTrivia() {
		return trivia;
	}
	public void setTrivia(String trivia) {
		this.trivia = trivia;
	}
	public String getContentOwner() {
		return contentOwner;
	}
	public void setContentOwner(String contentOwner) {
		this.contentOwner = contentOwner;
	}
	public String getProductionCountries() {
		return productionCountries;
	}
	public void setProductionCountries(String productionCountries) {
		this.productionCountries = productionCountries;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getMetaLanguages() {
		return metaLanguages;
	}
	public void setMetaLanguages(String metaLanguages) {
		this.metaLanguages = metaLanguages;
	}
	public int getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}

	
}
