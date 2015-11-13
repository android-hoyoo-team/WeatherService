package org.cz.project.entity.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "co_mobile.dbo.users")
@NamedQueries({ @NamedQuery(name = "Users.findAll", query = "SELECT g FROM Users g")})
public class Users extends IdEntity {
	public Users()
	{super();}
	public Users(Integer id,String name, String phoneNum, Integer persistLoginTimes,
			Integer totalLoginTimes, Long addTime, Long updateTime,
			Long deadTime, Integer status) {
		super();
		
		this.setId(id);
		this.name = name;
		this.phoneNum = phoneNum;
		this.persistLoginTimes = persistLoginTimes;
		this.totalLoginTimes = totalLoginTimes;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.deadTime = deadTime;
		this.status = status;
	}
	private String name;
	private String password;
	@Column(nullable=true)
	private String phoneNum;
	private String oauthToken; 
//	private Long lastLoginTime;//最后登陆时间
//	private Long yesterdayLastLoginTime;//昨天最后登陆时间
//	private Long currentLoginTime;//这次登陆时间
	private Integer persistLoginTimes;//持续登陆
	private Integer totalLoginTimes;//总共登陆
	
	private Long addTime;
	private Long updateTime;
	private Long deadTime;
	//@Column(columnDefinition="")
	
	public String getOauthToken() {
		return oauthToken;
	}
	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}
	private Integer status=0;
	public String getName() {
		return name;
	}
	public Integer getPersistLoginTimes() {
		return persistLoginTimes;
	}
	public void setPersistLoginTimes(Integer persistLoginTimes) {
		this.persistLoginTimes = persistLoginTimes;
	}
	public Integer getTotalLoginTimes() {
		return totalLoginTimes;
	}
	public void setTotalLoginTimes(Integer totalLoginTimes) {
		this.totalLoginTimes = totalLoginTimes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	
}
