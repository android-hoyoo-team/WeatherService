package org.cz.project.service;

import java.util.Map;

import org.cz.project.entity.table.ProbeAwsPerHour;

public interface ProbeAwsPerHourService {
			ProbeAwsPerHour getLatestProbeAwsPerHour(Map<String,Object> jsonParam);

			ProbeAwsPerHour getAreaLatestProbeAwsPerHour(String areaCode);

			ProbeAwsPerHour getStationNumLatestProbeAwsPerHour(String stationNum);
}
