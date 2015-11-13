package org.cz.project.service;

import java.util.List;
import java.util.Map;

import org.cz.project.entity.table.ProbeAwsPerHour;
import org.cz.project.entity.table.ProbeEnviPerHour;

public interface ProbeEnviPerHourService {

	ProbeEnviPerHour getLatestProbeEnviPerHour(Map<String, Object> param);

	Map getAvgAreaLatestProbeEnviPerHour(String areaCode);

	Map getAvgProvinceLatestProbeEnviPerHour();

	List getAvgLatestProbeEnviPerHourByCitys(List citys);

	/**
	 * 按照站点查询最近时间的平均值
	 * @param stationNum
	 * @return
	 */
	Map getAvgStationNumLatestProbeEnviPerHour(String stationNum);

}
