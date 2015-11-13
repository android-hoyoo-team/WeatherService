package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.Users;
import org.cz.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import per.cz.util.StringUtil;
import per.cz.util.encryption.EncryptionUtil;
@Service
public class UserServiceImpl implements UserService {

	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public Users save(Users u) {
		u.setAddTime(new Date().getTime());
		u.setPassword(EncryptionUtil.getMD5Base64(u.getPassword().getBytes()));
		Serializable save = baseDao.save(u);
		if(save!=null)	
			return u;
		return null;
	}

	@Override
	@Transactional
	public void delete(Users u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(Users u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.update(u);
	}

	
	@Override
	@Transactional
	public Users findById(int id) {
		return (Users) baseDao.get(Users.class, id);
	}
	@Override
	@Transactional
	public Users findByName(String name) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("name", name);
		List<Users> find = baseDao.find("from Users u where u.name=:name", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}
	@Override
	@Transactional
	public Users findByPhoneNum(String phoneNum) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("phoneNum", phoneNum);
		List<Users> find = baseDao.find("from Users u where u.phoneNum=:phoneNum", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}
	@Override
	@Transactional
	public Users findByOauthToken(String oauthToken) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("oauthToken", oauthToken);
		List<Users> find = baseDao.find("from Users u where u.oauthToken=:oauthToken", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
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
	public Users findByName(String name, String password) {
		password=EncryptionUtil.getMD5Base64(password.getBytes());
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("name", name);
		m.put("password", password);
		List<Users> find =baseDao.find("from Users u where u.name=:name and u.password=:password", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public QueryResult<Users> findAll( int page,
			int rows, String sort,String order) {
		String hql="select new Users(id,name, phoneNum, persistLoginTimes,totalLoginTimes,  addTime,  updateTime, deadTime,  status) from Users u order by u."+sort+" "+order;
		//String f_hql = StringUtil.format(hql, param);
		QueryResult<Users> find2QueryResult = baseDao.find2QueryResultByPage(hql, null, page, rows);
		return find2QueryResult;
	}

	@Override
	@Transactional
	public void updataStatus(Users u, int status) {
		u.setStatus(status);
		this.update(u);
	}

}
