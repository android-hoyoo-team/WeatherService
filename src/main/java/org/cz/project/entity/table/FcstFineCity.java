package org.cz.project.entity.table;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "JhPanWeather.dbo.Fcst_Fine_City")
@IdClass(FcstFineCityID.class)  
@NamedQueries({ @NamedQuery(name = "FcstFineCity.findAll", query = "SELECT g FROM FcstFineCity g")})
public class FcstFineCity implements Serializable{
	@Id
	private String stationCode;
	@Id
	private String dateHour;
	private Integer fcstType;
	private String refineTime;
	private Date renewTime;
	@Column(name="Temp24H") 
	private Float temp24H;
	private Float temp24L;
	private Float temp48H;
	private Float temp48L;
	private Float temp72H;
	private Float temp72L;
	private Float temp96H;
	private Float temp96L;
	private Float temp120H;
	private Float temp120L;
	private Float temp144H;
	private Float temp144L;
	private Float temp168H;
	private Float temp168L;
	
	private Integer weaDesc12=0;
	private Integer weaDesc24=0;
	private Integer weaDesc36=0;
	private Integer weaDesc48;
	private Integer weaDesc60;
	private Integer weaDesc72;
	private Integer weaDesc84;
	private Integer weaDesc96;
	private Integer weaDesc108;
	private Integer weaDesc120;
	private Integer weaDesc132;
	private Integer weaDesc144;
	private Integer weaDesc156;
	private Integer weaDesc168;
	
	private Integer windD12;
	private Integer windD24;
	private Integer windD36;
	private Integer windD48;
	private Integer windD60;
	private Integer windD72;
	private Integer windD84;
	private Integer windD96;
	private Integer windD108;
	private Integer windD120;
	private Integer windD132;
	private Integer windD144;
	private Integer windD156;
	private Integer windD168;
	
	private Integer windS12;
	private Integer windS24;
	private Integer windS36;
	private Integer windS48;
	private Integer windS60;
	private Integer windS72;
	private Integer windS84;
	private Integer windS96;
	private Integer windS108;
	private Integer windS120;
	private Integer windS132;
	private Integer windS144;
	private Integer windS156;
	private Integer windS168;
	public String getDateHour() {
		return dateHour;
	}
	public void setDateHour(String dateHour) {
		dateHour = dateHour;
	}
	public Integer getFcstType() {
		return fcstType;
	}
	public void setFcstType(Integer fcstType) {
		fcstType = fcstType;
	}
	public String getRefineTime() {
		return refineTime;
	}
	public void setRefineTime(String refineTime) {
		refineTime = refineTime;
	}
	public Date getRenewTime() {
		return renewTime;
	}
	public void setRenewTime(Date renewTime) {
		renewTime = renewTime;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		stationCode = stationCode;
	}
	public Float getTemp24H() {
		return temp24H;
	}
	public void setTemp24H(Float temp24h) {
		temp24H = temp24h;
	}
	public Float getTemp24L() {
		return temp24L;
	}
	public void setTemp24L(Float temp24l) {
		temp24L = temp24l;
	}
	public Float getTemp48H() {
		return temp48H;
	}
	public void setTemp48H(Float temp48h) {
		temp48H = temp48h;
	}
	public Float getTemp48L() {
		return temp48L;
	}
	public void setTemp48L(Float temp48l) {
		temp48L = temp48l;
	}
	public Float getTemp72H() {
		return temp72H;
	}
	public void setTemp72H(Float temp72h) {
		temp72H = temp72h;
	}
	public Float getTemp72L() {
		return temp72L;
	}
	public void setTemp72L(Float temp72l) {
		temp72L = temp72l;
	}
	public Float getTemp96H() {
		return temp96H;
	}
	public void setTemp96H(Float temp96h) {
		temp96H = temp96h;
	}
	public Float getTemp96L() {
		return temp96L;
	}
	public void setTemp96L(Float temp96l) {
		temp96L = temp96l;
	}
	public Float getTemp120H() {
		return temp120H;
	}
	public void setTemp120H(Float temp120h) {
		temp120H = temp120h;
	}
	public Float getTemp120L() {
		return temp120L;
	}
	public void setTemp120L(Float temp120l) {
		temp120L = temp120l;
	}
	public Float getTemp144H() {
		return temp144H;
	}
	public void setTemp144H(Float temp144h) {
		temp144H = temp144h;
	}
	public Float getTemp144L() {
		return temp144L;
	}
	public void setTemp144L(Float temp144l) {
		temp144L = temp144l;
	}
	public Float getTemp168H() {
		return temp168H;
	}
	public void setTemp168H(Float temp168h) {
		temp168H = temp168h;
	}
	public Float getTemp168L() {
		return temp168L;
	}
	public void setTemp168L(Float temp168l) {
		temp168L = temp168l;
	}
	public Integer getWeaDesc12() {
		return weaDesc12;
	}
	public void setWeaDesc12(Integer weaDesc12) {
		weaDesc12 = weaDesc12;
	}
	public Integer getWeaDesc24() {
		return weaDesc24;
	}
	public void setWeaDesc24(Integer weaDesc24) {
		weaDesc24 = weaDesc24;
	}
	public Integer getWeaDesc36() {
		return weaDesc36;
	}
	public void setWeaDesc36(Integer weaDesc36) {
		weaDesc36 = weaDesc36;
	}
	public Integer getWeaDesc48() {
		return weaDesc48;
	}
	public void setWeaDesc48(Integer weaDesc48) {
		weaDesc48 = weaDesc48;
	}
	public Integer getWeaDesc60() {
		return weaDesc60;
	}
	public void setWeaDesc60(Integer weaDesc60) {
		weaDesc60 = weaDesc60;
	}
	public Integer getWeaDesc72() {
		return weaDesc72;
	}
	public void setWeaDesc72(Integer weaDesc72) {
		weaDesc72 = weaDesc72;
	}
	public Integer getWeaDesc84() {
		return weaDesc84;
	}
	public void setWeaDesc84(Integer weaDesc84) {
		weaDesc84 = weaDesc84;
	}
	public Integer getWeaDesc96() {
		return weaDesc96;
	}
	public void setWeaDesc96(Integer weaDesc96) {
		weaDesc96 = weaDesc96;
	}
	public Integer getWeaDesc108() {
		return weaDesc108;
	}
	public void setWeaDesc108(Integer weaDesc108) {
		weaDesc108 = weaDesc108;
	}
	public Integer getWeaDesc120() {
		return weaDesc120;
	}
	public void setWeaDesc120(Integer weaDesc120) {
		weaDesc120 = weaDesc120;
	}
	public Integer getWeaDesc132() {
		return weaDesc132;
	}
	public void setWeaDesc132(Integer weaDesc132) {
		weaDesc132 = weaDesc132;
	}
	public Integer getWeaDesc144() {
		return weaDesc144;
	}
	public void setWeaDesc144(Integer weaDesc144) {
		weaDesc144 = weaDesc144;
	}
	public Integer getWeaDesc156() {
		return weaDesc156;
	}
	public void setWeaDesc156(Integer weaDesc156) {
		weaDesc156 = weaDesc156;
	}
	public Integer getWeaDesc168() {
		return weaDesc168;
	}
	public void setWeaDesc168(Integer weaDesc168) {
		weaDesc168 = weaDesc168;
	}
	public Integer getWindD12() {
		return windD12;
	}
	public void setWindD12(Integer windD12) {
		windD12 = windD12;
	}
	public Integer getWindD24() {
		return windD24;
	}
	public void setWindD24(Integer windD24) {
		windD24 = windD24;
	}
	public Integer getWindD36() {
		return windD36;
	}
	public void setWindD36(Integer windD36) {
		windD36 = windD36;
	}
	public Integer getWindD48() {
		return windD48;
	}
	public void setWindD48(Integer windD48) {
		windD48 = windD48;
	}
	public Integer getWindD60() {
		return windD60;
	}
	public void setWindD60(Integer windD60) {
		windD60 = windD60;
	}
	public Integer getWindD72() {
		return windD72;
	}
	public void setWindD72(Integer windD72) {
		windD72 = windD72;
	}
	public Integer getWindD84() {
		return windD84;
	}
	public void setWindD84(Integer windD84) {
		windD84 = windD84;
	}
	public Integer getWindD96() {
		return windD96;
	}
	public void setWindD96(Integer windD96) {
		windD96 = windD96;
	}
	public Integer getWindD108() {
		return windD108;
	}
	public void setWindD108(Integer windD108) {
		windD108 = windD108;
	}
	public Integer getWindD120() {
		return windD120;
	}
	public void setWindD120(Integer windD120) {
		windD120 = windD120;
	}
	public Integer getWindD132() {
		return windD132;
	}
	public void setWindD132(Integer windD132) {
		windD132 = windD132;
	}
	public Integer getWindD144() {
		return windD144;
	}
	public void setWindD144(Integer windD144) {
		windD144 = windD144;
	}
	public Integer getWindD156() {
		return windD156;
	}
	public void setWindD156(Integer windD156) {
		windD156 = windD156;
	}
	public Integer getWindD168() {
		return windD168;
	}
	public void setWindD168(Integer windD168) {
		windD168 = windD168;
	}
	public Integer getWindS12() {
		return windS12;
	}
	public void setWindS12(Integer windS12) {
		windS12 = windS12;
	}
	public Integer getWindS24() {
		return windS24;
	}
	public void setWindS24(Integer windS24) {
		windS24 = windS24;
	}
	public Integer getWindS36() {
		return windS36;
	}
	public void setWindS36(Integer windS36) {
		windS36 = windS36;
	}
	public Integer getWindS48() {
		return windS48;
	}
	public void setWindS48(Integer windS48) {
		windS48 = windS48;
	}
	public Integer getWindS60() {
		return windS60;
	}
	public void setWindS60(Integer windS60) {
		windS60 = windS60;
	}
	public Integer getWindS72() {
		return windS72;
	}
	public void setWindS72(Integer windS72) {
		windS72 = windS72;
	}
	public Integer getWindS84() {
		return windS84;
	}
	public void setWindS84(Integer windS84) {
		windS84 = windS84;
	}
	public Integer getWindS96() {
		return windS96;
	}
	public void setWindS96(Integer windS96) {
		windS96 = windS96;
	}
	public Integer getWindS108() {
		return windS108;
	}
	public void setWindS108(Integer windS108) {
		windS108 = windS108;
	}
	public Integer getWindS120() {
		return windS120;
	}
	public void setWindS120(Integer windS120) {
		windS120 = windS120;
	}
	public Integer getWindS132() {
		return windS132;
	}
	public void setWindS132(Integer windS132) {
		windS132 = windS132;
	}
	public Integer getWindS144() {
		return windS144;
	}
	public void setWindS144(Integer windS144) {
		windS144 = windS144;
	}
	public Integer getWindS156() {
		return windS156;
	}
	public void setWindS156(Integer windS156) {
		windS156 = windS156;
	}
	public Integer getWindS168() {
		return windS168;
	}
	public void setWindS168(Integer windS168) {
		windS168 = windS168;
	}
	
}
