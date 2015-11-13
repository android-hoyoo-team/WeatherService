package org.cz.project.service;

import java.util.List;
import java.util.Map;

import org.cz.project.entity.table.ProbeOxyPerHour;

public interface ProbeOxyPerHourService {
	ProbeOxyPerHour getLatestProbeOxyPerHour(Map<String,Object> jsonParam);

	ProbeOxyPerHour getAreaLatestProbeOxyPerHour(String areaCode);

	Map getAvgLatestProbeOxyPerHour(String areaCode);

	Map getAvgProvinceLatestProbeOxyPerHour();

	List getLatestO2PerHourByStations(List stations);
}
