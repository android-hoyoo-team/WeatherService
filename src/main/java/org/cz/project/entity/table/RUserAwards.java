package org.cz.project.entity.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "co_mobile.dbo.r_users_awards")
@NamedQueries({ @NamedQuery(name = "RUserAwards.findAll", query = "SELECT g FROM RUserAwards g")})
public class RUserAwards extends IdEntity {
	private Integer userId;
	private String awards;
	private String no_awards;
	private String  serialNumber;
	@Column(name="exchange",columnDefinition = "varchar(16) default 'false'")//sql server
	private String exchange;
	@Column(name="_type",columnDefinition = "varchar(16) default '1'")//sql server
	private String type;
	private Long expirationTime;//过期时间
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
	public String getType() {
		return type;
	}
	public Long getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Integer getUserId() {
		return userId;
	}
	public String getNo_awards() {
		return no_awards;
	}
	public void setNo_awards(String no_awards) {
		this.no_awards = no_awards;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
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
	
	public static void main(String[] args) {
		System.out.println(1*24*60*60*1000l);
	}
	
}
