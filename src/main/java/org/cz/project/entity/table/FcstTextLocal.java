package org.cz.project.entity.table;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "JhPanWeather.dbo.Fcst_Text_Local")
@NamedQueries({ @NamedQuery(name = "FcstTextLocal.findAll", query = "SELECT g FROM FcstTextLocal g")})
public class FcstTextLocal extends IdEntity {
	private Integer date;
	private String time;
	private String stationnum;
	private Integer fcst_type;
	private String fcst_title;
	private String fcst_text;
	private Date renewTime;
	private Date datetime;
	
	public Date getRenewTime() {
		return renewTime;
	}
	public void setRenewTime(Date renewTime) {
		this.renewTime = renewTime;
	}
	public int getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStationnum() {
		return stationnum;
	}
	public void setStationnum(String stationnum) {
		this.stationnum = stationnum;
	}
	public int getFcst_type() {
		return fcst_type;
	}
	public void setFcst_type(Integer fcst_type) {
		this.fcst_type = fcst_type;
	}
	public String getFcst_title() {
		return fcst_title;
	}
	public void setFcst_title(String fcst_title) {
		this.fcst_title = fcst_title;
	}
	public String getFcst_text() {
		return fcst_text;
	}
	public void setFcst_text(String fcst_text) {
		this.fcst_text = fcst_text;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
}
