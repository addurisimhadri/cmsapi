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
@Table(name = "games_meta")
@IdClass(GameContentId.class)
public class GameMeta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="gm_id")
	private int gmId;
	@Id
	@Column(name="content_id")
	private int contentId;
	@Column(name="gm_game_type")
	private String gameType;
	@Column(name="gm_content_owner")
	private String contentOwner;
	@Column(name="gm_home_link")
	private String homeLink;
	@Column(name="gm_quality" , columnDefinition="Varchar(20) default 'SD'")
	private String quality="SD";
	@Column(name="gm_split_build", columnDefinition="Varchar(20) default 'No'")
	private String splitBuild="No";
	
}
