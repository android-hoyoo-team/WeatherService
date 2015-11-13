package org.cz.project.service;

import java.util.Map;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.ManagerUsers;


public interface ManagerUserService {

	ManagerUsers save( ManagerUsers u);
	void delete( ManagerUsers u);
	void update( ManagerUsers u);
	ManagerUsers findByName(String name);
	ManagerUsers findById(int id);
	ManagerUsers findByName(String name,String password);
	QueryResult<ManagerUsers> findByParam(Map<String ,Object> param,int start,int length);
}
