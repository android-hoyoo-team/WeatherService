package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.Users;
import org.cz.project.entity.table.UsersLoginRecords;
import org.cz.project.service.UserLoginRecordsService;
import org.cz.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import per.cz.util.DateUtil;
import per.cz.util.StringUtil;
import per.cz.util.encryption.EncryptionUtil;
@Service
public class UserLoginRecordsServiceImpl implements UserLoginRecordsService {

	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public UsersLoginRecords save(UsersLoginRecords u) {
		u.setAddTime(new Date().getTime());
		//u.setPassword(EncryptionUtil.getMD5Base64(u.getPassword().getBytes()));
		Serializable save = baseDao.save(u);
		if(save!=null)	
			return u;
		return null;
	}

	@Override
	@Transactional
	public void delete(UsersLoginRecords u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(UsersLoginRecords u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.update(u);
	}

	
	@Override
	@Transactional
	public UsersLoginRecords findById(int id) {
		return (UsersLoginRecords) baseDao.get(UsersLoginRecords.class, id);
	}
	public QueryResult<Users> findUsers(Map<String, Object> m,int page, int size)
	{
		String hql = " from Users where :name: :password: :id:  order by :orderBy:";
		//1:deadTime=null;2:deadTime="null";3:deadTime="not null";
		//Map<String,Object> p = new HashMap<String,Object>();
		hql=hql.replaceAll(":name:", m.get("name")==null?"":" and name like :name");
		hql=hql.replaceAll(":password:", m.get("password")==null?"":" and password = :password");
		/*--->questionable*/
		hql = hql.replaceAll(":id:", m.get("id") == null?"":" and total in (:id)");
		hql = hql.replaceAll(":orderBy:", m.get("orderBy") == null?"id desc":m.get("OrderBy").toString());
		QueryResult<Users> find2QueryResult = baseDao.find2QueryResultByPage(hql, m, page, size);
		return find2QueryResult;
	}

	@Override
	@Transactional
	public QueryResult<UsersLoginRecords> findAll( int page,
			int rows, String sort,String order) {
		String hql="from UsersLoginRecords u order by u."+sort+" "+order;
		//String f_hql = StringUtil.format(hql, param);
		QueryResult<UsersLoginRecords> find2QueryResult = baseDao.find2QueryResultByPage(hql, null, page, rows);
		return find2QueryResult;
	}

	@Override
	@Transactional
	public UsersLoginRecords findLastByUserId(int userId) {
		List find = baseDao.find("from UsersLoginRecords u order by u.id desc", null);
		if(find!=null&&find.size()>0)
		{
			return (UsersLoginRecords) find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public UsersLoginRecords findLastYesterdayByUserId(int userId) {
		Map<String ,Object> param=new HashMap<String, Object>();
		param.put("loginDate", DateUtil.getDate4long(DateUtil.addDate(new Date(), -1)));
		List find = baseDao.find("from UsersLoginRecords u where u.loginDate=:loginDate and u.userId="+userId+" order by u.id desc", param);
		if(find!=null&&find.size()>0)
		{
			return (UsersLoginRecords) find.get(0);
		}
		return null;
	}
	@Override
	@Transactional
	public UsersLoginRecords findLastTodayByUserId(int userId) {
		Map<String ,Object> param=new HashMap<String, Object>();
		param.put("loginDate", DateUtil.getDate4long(new Date()));
		List find = baseDao.find("from UsersLoginRecords u where u.loginDate=:loginDate and u.userId="+userId+" order by u.id desc", param);
		if(find!=null&&find.size()>0)
		{
			return (UsersLoginRecords) find.get(0);
		}
		return null;
	}
}
