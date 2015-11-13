package org.cz.project.entity.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "co_mobile.dbo.users_login_records")
@NamedQueries({ @NamedQuery(name = "UsersLoginRecords.findAll", query = "SELECT g FROM UsersLoginRecords g")})
public class UsersLoginRecords extends IdEntity {
	private Integer userId;
	private Long loginDate;
	private Long loginTime;
	private String address;//地址
//	private String oauthToken;//获取的token
	@Column(nullable=true)
	private Float longitude;//经度
	@Column(nullable=true)
	private Float latitude;//纬度
	private Long addTime;
	private Long updateTime;
	private Long deadTime;
	private String tag;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Long getAddTime() {
		return addTime;
	}
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(Long deadTime) {
		this.deadTime = deadTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Long loginDate) {
		this.loginDate = loginDate;
	}
	public Long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
}
