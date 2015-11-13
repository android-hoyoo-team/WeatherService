package org.cz.project.service;

import java.util.Map;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.RUserAwards;


public interface RUserAwardsService {

	RUserAwards save( RUserAwards u);
	void delete( RUserAwards u);
	void update( RUserAwards u);
	RUserAwards findById(int id);
	QueryResult<RUserAwards> findByParam(Map<String ,Object> param,int start,int length);
	QueryResult<Map> findByParam4Page(Map<String ,Object> param,int page,int size);
	RUserAwards findByAwards(int userId, String awards);
	RUserAwards getLatestByUserId(int user_id);
}
