package org.cz.project.entity.table;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "JhPanWeather.dbo.Probe_Aws_PerHour")
@NamedQueries({ @NamedQuery(name = "findAllProbeAwsPerHours", query = "SELECT g FROM ProbeAwsPerHour g")})
public class ProbeAwsPerHour implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private Integer date;
	private String time;
	private String areaCode;
	private String stationNum;
	private Integer t;
	private Integer TMax;
	private Integer TMin;
	private Integer Fd;
	private Integer Ff;
	private Integer FfMin;
	private Integer FfMax;
	private Integer rain;
	private Integer p;
	private Integer RH;
	private Integer v;
	private Integer ST;
	private Integer STMax;
	private Integer STMin;
	private Date Renewtime;
	public Date getRenewtime() {
		return Renewtime;
	}
	public void setRenewtime(Date renewtime) {
		Renewtime = renewtime;
	}
	//		private Double CO;
	//		private Double NO2;
	//		private Double O3;
	//		private Double SO2;
	//		private Integer aqi;
	//		private String mainPool;
	//		private Double pm1;
	//		private Double pm10;
	//		private Double pm25;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDate() {
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getStationNum() {
		return stationNum;
	}
	public void setStationNum(String stationNum) {
		this.stationNum = stationNum;
	}
	public float getT() {
		if(t==null)
			return 0;
		return (float) (t/10.0);
	}
	public void setT(Integer t) {
		this.t = t;
	}
	public float getTMax() {
		if(TMax==null)
			return 0;
		return (float) (TMax/10.0);
	}
	public void setTMax(Integer tMax) {
		TMax = tMax;
	}
	public float getTMin() {
		if(TMin==null)
			return 0;
		return (float) (TMin/10.0);
	}
	public void setTMin(Integer tMin) {
		TMin = tMin;
	}
	public Integer getFd() {
		return Fd;
	}
	public void setFd(Integer fd) {
		Fd = fd;
	}
	public float getFf() {
		if(Ff==null)
			return 0;
		return (float) (Ff/10.0);
	}
	public void setFf(Integer ff) {
		Ff = ff;
	}
	public float getFfMin() {
		if(FfMin==null)
			return 0;
		return (float) (FfMin/10.0);
	}
	public void setFfMin(Integer ffMin) {
		FfMin = ffMin;
	}
	public float getFfMax() {
		if(FfMax==null)
			return 0;
		return (float) (FfMax/10.0);
	}
	public void setFfMax(Integer ffMax) {
		FfMax = ffMax;
	}
	public Integer getRain() {
		return rain;
	}
	public void setRain(Integer rain) {
		this.rain = rain;
	}
	public float getP() {
		if(p==null)
			return 0;
		return (float) (p/10.0);
	}
	public void setP(Integer p) {
		this.p = p;
	}
	public Integer getRH() {
		return RH;
	}
	public void setRH(Integer rH) {
		RH = rH;
	}
	public Integer getV() {
		return v;
	}
	public void setV(Integer v) {
		this.v = v;
	}
	public Integer getST() {
		return ST;
	}
	public void setST(Integer sT) {
		ST = sT;
	}
	public Integer getSTMax() {
		return STMax;
	}
	public void setSTMax(Integer sTMax) {
		STMax = sTMax;
	}
	public Integer getSTMin() {
		return STMin;
	}
	public void setSTMin(Integer sTMin) {
		STMin = sTMin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
