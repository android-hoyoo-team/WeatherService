package org.cz.project.service;

import java.util.List;

import org.cz.project.entity.table.FcstTextLocal;

public interface FcstFineCityService {
	List<FcstTextLocal> getFcstFineCityByStationCode(String stationnum);
}
