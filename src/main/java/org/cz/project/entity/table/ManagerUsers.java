package org.cz.project.entity.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "co_mobile.dbo.manager_users")
@NamedQueries({ @NamedQuery(name = "ManagerUsers.findAll", query = "SELECT g FROM ManagerUsers g")})
public class ManagerUsers extends IdEntity {
	private String name;
	private String password;
	private Long addTime;
	private Long updateTime;
	private Long deadTime;
	@Column(name="tag",columnDefinition = "varchar(16) default '0'")//sql server 
	private String tag;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
