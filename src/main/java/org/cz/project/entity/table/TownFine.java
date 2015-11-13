package org.cz.project.entity.table;

// Generated 2015-7-5 23:00:03 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * TownFineId generated by hbm2java
 */
@Entity
@Table(name = "JhPanWeather.dbo.town_fine")
@NamedQueries({ @NamedQuery(name = "findAllTownFine", query = "SELECT g FROM TownFine g")})
public class TownFine implements java.io.Serializable {
	@Id
	private Long id;
	private Serializable mid;
	private String areaCode;
	private Date fcstTime;
	private String stid;
	private Short fh;
	private Short ww03;
	private Short ww06;
	private Short ww12;
	private Short t;
	private Short tmax12;
	private Short tmin12;
	private Short pr03;
	private Short pr06;
	private Short pr12;
	private Short pr24;
	private Short rh;
	private Short wd;
	private Short ws;
	private Short cloud;
	private Integer vv;
	private Short slp;

	public TownFine() {
	}

	public TownFine(Long id) {
		this.id = id;
	}

	public TownFine(Long id, Serializable mid, String areaCode,
			Date fcstTime, String stid, Short fh, Short ww03, Short ww06,
			Short ww12, Short t, Short tmax12, Short tmin12, Short pr03,
			Short pr06, Short pr12, Short pr24, Short rh, Short wd, Short ws,
			Short cloud, Integer vv, Short slp) {
		this.id = id;
		this.mid = mid;
		this.areaCode = areaCode;
		this.fcstTime = fcstTime;
		this.stid = stid;
		this.fh = fh;
		this.ww03 = ww03;
		this.ww06 = ww06;
		this.ww12 = ww12;
		this.t = t;
		this.tmax12 = tmax12;
		this.tmin12 = tmin12;
		this.pr03 = pr03;
		this.pr06 = pr06;
		this.pr12 = pr12;
		this.pr24 = pr24;
		this.rh = rh;
		this.wd = wd;
		this.ws = ws;
		this.cloud = cloud;
		this.vv = vv;
		this.slp = slp;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Serializable getMid() {
		return this.mid;
	}

	public void setMid(Serializable mid) {
		this.mid = mid;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Date getFcstTime() {
		return this.fcstTime;
	}

	public void setFcstTime(Date fcstTime) {
		this.fcstTime = fcstTime;
	}

	public String getStid() {
		return this.stid;
	}

	public void setStid(String stid) {
		this.stid = stid;
	}

	public Short getFh() {
		return this.fh;
	}

	public void setFh(Short fh) {
		this.fh = fh;
	}

	public Short getWw03() {
		return this.ww03;
	}

	public void setWw03(Short ww03) {
		this.ww03 = ww03;
	}

	public Short getWw06() {
		return this.ww06;
	}

	public void setWw06(Short ww06) {
		this.ww06 = ww06;
	}

	public Short getWw12() {
		return this.ww12;
	}

	public void setWw12(Short ww12) {
		this.ww12 = ww12;
	}

	public Short getT() {
		return this.t;
	}

	public void setT(Short t) {
		this.t = t;
	}

	public Short getTmax12() {
		return this.tmax12;
	}

	public void setTmax12(Short tmax12) {
		this.tmax12 = tmax12;
	}

	public Short getTmin12() {
		return this.tmin12;
	}

	public void setTmin12(Short tmin12) {
		this.tmin12 = tmin12;
	}

	public Short getPr03() {
		return this.pr03;
	}

	public void setPr03(Short pr03) {
		this.pr03 = pr03;
	}

	public Short getPr06() {
		return this.pr06;
	}

	public void setPr06(Short pr06) {
		this.pr06 = pr06;
	}

	public Short getPr12() {
		return this.pr12;
	}

	public void setPr12(Short pr12) {
		this.pr12 = pr12;
	}

	public Short getPr24() {
		return this.pr24;
	}

	public void setPr24(Short pr24) {
		this.pr24 = pr24;
	}

	public Short getRh() {
		return this.rh;
	}

	public void setRh(Short rh) {
		this.rh = rh;
	}

	public Short getWd() {
		return this.wd;
	}

	public void setWd(Short wd) {
		this.wd = wd;
	}

	public Short getWs() {
		return this.ws;
	}

	public void setWs(Short ws) {
		this.ws = ws;
	}

	public Short getCloud() {
		return this.cloud;
	}

	public void setCloud(Short cloud) {
		this.cloud = cloud;
	}

	public Integer getVv() {
		return this.vv;
	}

	public void setVv(Integer vv) {
		this.vv = vv;
	}

	public Short getSlp() {
		return this.slp;
	}

	public void setSlp(Short slp) {
		this.slp = slp;
	}

}