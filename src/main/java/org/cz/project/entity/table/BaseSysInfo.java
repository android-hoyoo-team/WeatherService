package org.cz.project.entity.table;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;


@Entity
@Table(name = "co_mobile.dbo.base_sys_info")
public class BaseSysInfo extends  IdEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6730952034636819221L;
	@Column(name="info_key")  
	private String key;
	//@Basic(fetch = FetchType.LAZY)
//	@Column(columnDefinition = "MEDIUMTEXT")//mysql
//	@Column(name="info_value",columnDefinition = "varchar(256) default 'x'",length=1024)//sql server
	@Column(name="info_value",columnDefinition = "varchar(8000) default '{}'")//sql server
	private String value;
	@Column(name="info_type")  
	private String type;
	private Long addTime;
	private Long updateTime;
	private Long deadTime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
