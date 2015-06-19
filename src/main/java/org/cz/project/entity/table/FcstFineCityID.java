package org.cz.project.entity.table;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

public class FcstFineCityID implements Serializable{
	private String dateHour;
	private String stationCode;
	public FcstFineCityID() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateHour == null) ? 0 : dateHour.hashCode());
		result = prime * result
				+ ((stationCode == null) ? 0 : stationCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FcstFineCityID other = (FcstFineCityID) obj;
		if (dateHour == null) {
			if (other.dateHour != null)
				return false;
		} else if (!dateHour.equals(other.dateHour))
			return false;
		if (stationCode == null) {
			if (other.stationCode != null)
				return false;
		} else if (!stationCode.equals(other.stationCode))
			return false;
		return true;
	}
	
	
}
