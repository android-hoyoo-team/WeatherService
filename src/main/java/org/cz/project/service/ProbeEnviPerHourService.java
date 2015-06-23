package org.cz.project.service;

import java.util.Map;

import org.cz.project.entity.table.ProbeAwsPerHour;

public interface ProbeEnviPerHourService {

	ProbeAwsPerHour getLatestProbeEnviPerHour(Map<String, Object> param);

}
