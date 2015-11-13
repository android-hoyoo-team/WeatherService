package org.cz.project.entity.table;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "JhPanWeather.dbo.Fcst_Live_Index")
@NamedQueries({ @NamedQuery(name = "FcstLiveIndex.findAll", query = "SELECT g FROM FcstLiveIndex g")})
public class FcstLiveIndex extends IdEntity {
	private Integer date;
	private String time;
	private String stationnum;
	private String fcst_type;
	private String fcst_grade;
	private String fcst_text;
	private String fcst_desc;
	private Date renewTime;
	
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
	public String getFcst_type() {
		return fcst_type;
	}
	public void setFcst_type(String fcst_type) {
		this.fcst_type = fcst_type;
	}
	public String getFcst_text() {
		return fcst_text;
	}
	public void setFcst_text(String fcst_text) {
		this.fcst_text = fcst_text;
	}
	public String getFcst_grade() {
		return fcst_grade;
	}
	public void setFcst_grade(String fcst_grade) {
		this.fcst_grade = fcst_grade;
	}
	public String getFcst_desc() {
		return fcst_desc;
	}
	public void setFcst_desc(String fcst_desc) {
		this.fcst_desc = fcst_desc;
	}
	
}
