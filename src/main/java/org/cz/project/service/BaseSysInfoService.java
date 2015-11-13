package org.cz.project.service;

import java.util.List;
import java.util.Map;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.BaseSysInfo;


public interface BaseSysInfoService {

	BaseSysInfo saveOrUpdate( BaseSysInfo u);
	void delete( BaseSysInfo u);
	void update( BaseSysInfo u);
	BaseSysInfo findById(int id);
	QueryResult<BaseSysInfo> findByParam(Map<String ,Object> param,int start,int length);
	BaseSysInfo findByTypeAndKey(String type, String key);
	List<BaseSysInfo> findByType(String type);
}
