package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.RUserImages;
import org.cz.project.entity.table.Users;
import org.cz.project.service.RUsersImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import per.cz.util.encryption.EncryptionUtil;
@Service
public class RUsersImagesServiceImpl implements RUsersImagesService {
	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public RUserImages save(RUserImages u) {
		u.setAddTime(new Date().getTime());
		Serializable save = baseDao.save(u);
		if(save!=null)	
			return u;
		return null;
	}

	@Override
	@Transactional
	public void delete(RUserImages u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(RUserImages u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.update(u);
	}

	@Override
	@Transactional
	public RUserImages findById(int id) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("id", id);
		List<RUserImages> find = baseDao.find("from RUserImages u where u.id=:id", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public RUserImages findByName(int userId, int id) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("userId", userId);
		m.put("id", id);
		List<RUserImages> find =baseDao.find("from RUserImages u where u.userId=:userId and u.id=:id", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public QueryResult<RUserImages> findByParam(Map<String, Object> param,
			int start, int length) {
			String hql="from RUserImages u where 1=1 order by u.id desc";
			QueryResult<RUserImages> find2QueryResult = baseDao.find2QueryResult(hql, param, start, length);
			return find2QueryResult;
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
	public QueryResult<Map> findByParam4Page(Map<String, Object> param,
			int page, int size) {
		if(param==null)
			param=new HashMap<String, Object>();
		String hql="select new map(r.id as id, r.comment as comment,r.status as status,r.url as url,r.userId as userId ,u.name as name,r.addTime as addTime)  from RUserImages r ,Users u where  r.userId = u.id :userId: :status: order by r.id desc";
		if(param.get("status")==null||param.get("status").toString().trim().length()<=0||param.get("status").toString().trim().toLowerCase().equals("all"))
			hql=hql.replace(":status:", "");
		else if(param.get("status").toString().trim().toLowerCase().equals("null"))
			hql=hql.replace(":status:", " and r.status is null ");
		else hql=hql.replace(":status:", " and r.status =:status");
		hql=hql.replace(":userId:",param.get("user_id")==null?"":"and userId=:user_id ");
		//String hql="from RUserImages u where 1=1 order by u.id desc";
		System.out.println(hql);
		System.out.println(param);
		QueryResult<Map> find2QueryResult = baseDao.find2QueryResultByPage(hql, param, page, size);
		return find2QueryResult;
	}

}
