package org.cz.project.service;

import java.util.Map;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.Suggestion;


public interface SuggestionService {

	Suggestion saveOrUpdate( Suggestion u);
	void delete( Suggestion u);
	void update( Suggestion u);
	Suggestion findById(int id);
	QueryResult<Suggestion> findByParam(Map<String ,Object> param,int start,int length);
	QueryResult<Suggestion> findAll(int page, int rows, String sort,
			String order);
}
