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
@Table(name = "Probe_Aws_PerHour")
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
		private Date renewTime;
		private Double CO;
		private Double NO2;
		private Double O3;
		private Double SO2;
		private Integer aqi;
		private String mainPool;
		private Double pm1;
		private Double pm10;
		private Double pm25;
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
		public Integer getT() {
			return t;
		}
		public void setT(Integer t) {
			this.t = t;
		}
		public Integer getTMax() {
			return TMax;
		}
		public void setTMax(Integer tMax) {
			TMax = tMax;
		}
		public Integer getTMin() {
			return TMin;
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
		public Integer getFf() {
			return Ff;
		}
		public void setFf(Integer ff) {
			Ff = ff;
		}
		public Integer getFfMin() {
			return FfMin;
		}
		public void setFfMin(Integer ffMin) {
			FfMin = ffMin;
		}
		public Integer getFfMax() {
			return FfMax;
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
		public Integer getP() {
			return p;
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
		public Date getRenewTime() {
			return renewTime;
		}
		public void setRenewTime(Date renewTime) {
			this.renewTime = renewTime;
		}
		public Double getCO() {
			return CO;
		}
		public void setCO(Double cO) {
			CO = cO;
		}
		public Double getNO2() {
			return NO2;
		}
		public void setNO2(Double nO2) {
			NO2 = nO2;
		}
		public Double getO3() {
			return O3;
		}
		public void setO3(Double o3) {
			O3 = o3;
		}
		public Double getSO2() {
			return SO2;
		}
		public void setSO2(Double sO2) {
			SO2 = sO2;
		}
		public Integer getAqi() {
			return aqi;
		}
		public void setAqi(Integer aqi) {
			this.aqi = aqi;
		}
		public String getMainPool() {
			return mainPool;
		}
		public void setMainPool(String mainPool) {
			this.mainPool = mainPool;
		}
		public Double getPm1() {
			return pm1;
		}
		public void setPm1(Double pm1) {
			this.pm1 = pm1;
		}
		public Double getPm10() {
			return pm10;
		}
		public void setPm10(Double pm10) {
			this.pm10 = pm10;
		}
		public Double getPm25() {
			return pm25;
		}
		public void setPm25(Double pm25) {
			this.pm25 = pm25;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
}
