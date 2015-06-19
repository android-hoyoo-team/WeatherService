package org.cz.project.entity.table;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Probe_Aws_PerHour")
@NamedQueries({ @NamedQuery(name = "findAllProbeEnviPerHours", query = "SELECT g FROM ProbeEnviPerHour g")})
public class ProbeEnviPerHour implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Id
		private Integer Id;
		private Date date;
		private String time;
		private String areaCode;
		private String stationNum;
		private Integer aqi;
		private String mainPool;
		private Double pm1;
		private Double pm10;
		private Double pm25;
		private Double SO2;
		private Double NO2;
		private Double CO;
		private Double O3;
		private Date renewTime;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
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
		public Double getSO2() {
			return SO2;
		}
		public void setSO2(Double sO2) {
			SO2 = sO2;
		}
		public Double getNO2() {
			return NO2;
		}
		public void setNO2(Double nO2) {
			NO2 = nO2;
		}
		public Double getCO() {
			return CO;
		}
		public void setCO(Double cO) {
			CO = cO;
		}
		public Double getO3() {
			return O3;
		}
		public void setO3(Double o3) {
			O3 = o3;
		}
		public Date getRenewTime() {
			return renewTime;
		}
		public void setRenewTime(Date renewTime) {
			this.renewTime = renewTime;
		}
}
