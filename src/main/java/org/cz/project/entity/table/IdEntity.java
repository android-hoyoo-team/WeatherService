package org.cz.project.entity.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class IdEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8983717485676331879L;

	
	@Id
	@GeneratedValue
	private Integer id;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
