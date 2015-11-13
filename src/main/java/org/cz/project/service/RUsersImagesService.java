package org.cz.project.service;

import java.util.Map;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.RUserImages;
import org.cz.project.entity.table.Users;


public interface RUsersImagesService {

	RUserImages save( RUserImages u);
	void delete( RUserImages u);
	void update( RUserImages u);
	RUserImages findById(int id);
	QueryResult<RUserImages> findByParam(Map<String ,Object> param,int start,int length);
	QueryResult<Map> findByParam4Page(Map<String ,Object> param,int page,int size);
	RUserImages findByName(int userId, int id);
}
