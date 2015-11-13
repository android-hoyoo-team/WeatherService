package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.Suggestion;
import org.cz.project.entity.table.Users;
import org.cz.project.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public Suggestion saveOrUpdate(Suggestion u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.saveOrUpdate(u);
		return u;
	}

	@Override
	@Transactional
	public void delete(Suggestion u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(Suggestion u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.update(u);
	}

	
	@Override
	@Transactional
	public Suggestion findById(int id) {
		return (Suggestion) baseDao.get(Suggestion.class, id);
	}

	@Override
	@Transactional
	public QueryResult<Suggestion> findByParam(Map<String, Object> param, int start,
			int length) {
		String hql="from Suggestion u";
		//String f_hql = StringUtil.format(hql, param);
		QueryResult<Suggestion> find2QueryResult = baseDao.find2QueryResult(hql, param, start, length);
		return find2QueryResult;
	}
	@Override
	@Transactional
	public QueryResult<Suggestion> findAll( int page,
			int rows, String sort,String order) {
		String hql="from Suggestion u order by u."+sort+" "+order;
		//String f_hql = StringUtil.format(hql, param);
		QueryResult<Suggestion> find2QueryResult = baseDao.find2QueryResultByPage(hql, null, page, rows);
		return find2QueryResult;
	}
}
