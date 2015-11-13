package org.cz.project.entity.table;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;


@Entity
@Table(name = "co_mobile.dbo.suggestion")
public class Suggestion extends  IdEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6730952034636819221L;
	private String contact;
//	@Column(name="info_value",columnDefinition = "varchar(1024) default '{}'")//sql server
	private String content;
	private Long addTime;
	private Long updateTime;
	private Long deadTime;
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
