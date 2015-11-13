package org.cz.project.service;

import java.util.List;
import java.util.Map;


public interface TownService {

	List<Map<String, Object>> getTown(Map<String, Object> param);
	List<Map<String, Object>> getTownTour(Map<String, Object> param);
}
