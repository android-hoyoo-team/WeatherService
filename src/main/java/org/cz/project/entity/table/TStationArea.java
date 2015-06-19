package org.cz.project.entity.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "T_Station_Area")
@NamedQueries({ @NamedQuery(name = "findAllTStationAreas", query = "SELECT g FROM TStationArea g")})
public class TStationArea implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	@Column(name="省市")
	private String province;
	@Column(name="地市")
	private String city;
	@Column(name="县域")
	private String county;
	@Column(name="区域编码")
	private String areaCode;
	@Column(name="站名")
	private String stationName;
	@Column(name="站号")
	private String stationNum;
	@Column(name="东经")
	private Float longitude;
	@Column(name="北纬")
	private Float latitude;
	@Column(name="拔高")
	private Float altitude;
	@Column(name="百度东经")
	private Float bLongitude;
	@Column(name="百度北纬")
	private Float bLatitude;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationNum() {
		return stationNum;
	}
	public void setStationNum(String stationNum) {
		this.stationNum = stationNum;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getAltitude() {
		return altitude;
	}
	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}
	public Float getbLongitude() {
		return bLongitude;
	}
	public void setbLongitude(Float bLongitude) {
		this.bLongitude = bLongitude;
	}
	public Float getbLatitude() {
		return bLatitude;
	}
	public void setbLatitude(Float bLatitude) {
		this.bLatitude = bLatitude;
	}
}
