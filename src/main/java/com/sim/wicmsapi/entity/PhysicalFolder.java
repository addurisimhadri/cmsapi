package com.sim.wicmsapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "physical_folder")
public class PhysicalFolder { 	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pf_id")
	private int id;
	@Column(name="pf_folderName")
	private String folderName;
	@Column(name="pf_location")
	@JsonIgnore
	private String location;
	

}
