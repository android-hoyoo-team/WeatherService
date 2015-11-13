package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.BaseSysInfo;
import org.cz.project.service.BaseSysInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CopyOfUserServiceImpl implements BaseSysInfoService {

	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public BaseSysInfo saveOrUpdate(BaseSysInfo u) {
		if(u.getAddTime()==null)
			u.setAddTime(new Date().getTime());
		u.setUpdateTime(new Date().getTime());
		baseDao.saveOrUpdate(u);
		return u;
	}

	@Override
	@Transactional
	public void delete(BaseSysInfo u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(BaseSysInfo u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.update(u);
	}

	
	@Override
	@Transactional
	public BaseSysInfo findById(int id) {
		return (BaseSysInfo) baseDao.get(BaseSysInfo.class, id);
	}
	@Override
	@Transactional
	public BaseSysInfo findByTypeAndKey(String type,String key) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("type", type);
		m.put("key", key);
		List<BaseSysInfo> find = baseDao.find("from BaseSysInfo u where u.type=:type and u.key=:key", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public List<BaseSysInfo>  findByType(String type) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("type", type);
		List<BaseSysInfo> find =baseDao.find("from BaseSysInfo u where u.type=:type", m);
		return find;
	}

	@Override
	@Transactional
	public QueryResult<BaseSysInfo> findByParam(Map<String, Object> param, int start,
			int length) {
		String hql="from BaseSysInfo u where u.type=:type and u.key=:key";
		//String f_hql = StringUtil.format(hql, param);
		QueryResult<BaseSysInfo> find2QueryResult = baseDao.find2QueryResult(hql, param, start, length);
		return find2QueryResult;
	}

}
