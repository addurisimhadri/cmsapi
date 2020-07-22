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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "content_provider") 
public class ContentProvider  {
	@Id
	@Column(name="cp_id")
	private int cpId=-1;
	@Column(name="cp_name")
	private String cpName;
	@Column(name="cp_place")
	private String cpLocation;
	@Column(name="contact_person")
	private String contactPerson;
	@Column(name="email")
	private String email;
	@Column(name="content_type_id")
	private String ctId;
	@Column(name="from_date" ,nullable = false)
	private java.util.Date fromDate;
	@Column(name="to_date" ,nullable = false)
	private java.util.Date toDate;
	@Column(name="revenue_share")
	private String revenueShare;
	@Column(name="contact_number")
	private String contactNumber;
	@Column(name="ftpsupport",nullable = false,  columnDefinition="char(1) default 'N'")
	private char ftpSupport='N';
	@Column(name="ftptype")
	private int ftpType;
	@Column(name="client_ftp_ip")
	private String clientFtpIp;
	@Column(name="client_ftp_user")
	private String clientFtpUser;
	@Column(name="client_ftp_pwd")
	private String clientFtpPwd;
	@Column(name="server_ftp_home")
	private String serverFtpHome;
	@Column(name="reports_by_mail",nullable = false,  columnDefinition="char(1) default 'N'")
	private char reportsByMail='N';
	@Column(name="device_group_req",nullable = false,  columnDefinition="char(1) default 'N'")
	private char deviceGroupReq='N';
	@Column(name="any_deviceAs_basedevice",nullable = false,  columnDefinition="char(1) default 'N'")
	private char adASbd='N';
	@Column(name="cp_master", nullable = false,  columnDefinition="char(1) default 'N'")
	private char cpMaster='N';
	@Column(name="cp_country_code")
	private int cpCountryCode;
	@Column(name="cp_currency")
	private String cpCurrency;
	@Column(name="cp_exchange_rate")
	private double cpExchangeRate;
	@Column(name="autoReports" ,nullable = false,  columnDefinition="char(1) default 'N'")
	private char autoReports='N';

}
