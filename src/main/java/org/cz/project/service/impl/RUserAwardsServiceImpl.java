package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.RUserAwards;
import org.cz.project.entity.table.Users;
import org.cz.project.service.RUserAwardsService;
import org.cz.project.service.RUsersImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import per.cz.util.encryption.EncryptionUtil;
@Service
public class RUserAwardsServiceImpl implements RUserAwardsService {
	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public RUserAwards save(RUserAwards u) {
		u.setAddTime(new Date().getTime());
		Serializable save = baseDao.save(u);
		if(save!=null)	
			return u;
		return null;
	}
	@Override
	@Transactional
	public RUserAwards getLatestByUserId(int user_id) {
		List<RUserAwards> find = baseDao.find("from RUserAwards r where r.userId="+user_id+" order by r.id desc",0,1);
		if(find==null||find.size()==0)
			return null;
		else
			return find.get(0);
	}

	@Override
	@Transactional
	public void delete(RUserAwards u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(RUserAwards u) {
		u.setUpdateTime(new Date().getTime());
		baseDao.update(u);
	}

	@Override
	@Transactional
	public RUserAwards findById(int id) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("id", id);
		List<RUserAwards> find = baseDao.find("from RUserAwards u where u.id=:id", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public RUserAwards findByAwards(int userId, String awards) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("userId", userId);
		m.put("awards", awards);
		List<RUserAwards> find =baseDao.find("from RUserAwards u where u.userId=:userId and u.awards=:awards", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public QueryResult<RUserAwards> findByParam(Map<String, Object> param,
			int start, int length) {
			String hql="from RUserAwards u where 1=1 :userId: :serialNumber: order by u.id desc";
			if(param==null)
				param=new HashMap<String, Object>();
			hql=hql.replace(":userId:",param.get("user_id")==null?"":"and u.userId in (:user_id) ");
			hql=hql.replace(":serialNumber:",param.get("serial_num")==null?"":"and u.serialNumber in (:serial_num) ");
			
			QueryResult<RUserAwards> find2QueryResult = baseDao.find2QueryResult(hql, param, start, length);
			return find2QueryResult;
	}
	@Override
	@Transactional
	public QueryResult<Map> findByParam4Page(Map<String, Object> param,
			int page, int size) {
		if(param==null)
			param=new HashMap<String, Object>();
		String hql="select new map(r.exchange as exchange,r.serialNumber as :serialNumber:,r.no_awards as no_awards,r.type as type,r.expirationTime as expirationTime,r.id as id, r.awards as awards,r.userId as userId ,u.phoneNum as phoneNum,u.name as name,r.addTime as addTime)  from RUserAwards r ,Users u where  r.userId = u.id :userId: :serial_number: :name: :awards: :tag: :no_awards: :out_of_date: :exchange: order by :sort: :order:";
		hql=hql.replace(":userId:",param.get("user_id")==null?"":"and userId=:user_id ");
		hql=hql.replace(":awards:",param.get("awards")==null?"":"and awards=:awards ");
		hql=hql.replace(":name:",param.get("user_name")==null?"":"and name like '%"+param.get("user_name")+"%' ");
		hql=hql.replace(":serialNumber:","manager".equals(param.get("user_info"))?"0":"serialNumber ");
		
		hql=hql.replace(":serial_number:",param.get("serial_number")==null?"":"and serialNumber =:serial_number ");
		hql=hql.replace(":no_awards:",param.get("no_awards")==null?"":"and no_awards=:no_awards");
		hql=hql.replace(":tag:",param.get("tag")==null?"":"and tag=:tag");
		String _out_of_date="";
		if(param.get("out_of_date")==null||param.get("out_of_date").toString().trim().equals("all"))
		{
			_out_of_date="and r.expirationTime is not null";
		}
		else if(param.get("out_of_date").toString().trim().toLowerCase().equals("false"))//没有过期的
		{
			_out_of_date="and r.expirationTime is not null and r.expirationTime>"+new Date().getTime();
		}
		else if(param.get("out_of_date").toString().trim().toLowerCase().equals("true"))
		{
			_out_of_date="and r.expirationTime is not null and r.expirationTime<="+new Date().getTime();
		}
		hql=hql.replace(":out_of_date:",_out_of_date);
		String exchange="";
		if(param.get("exchange")==null||param.get("exchange").toString().trim().equals("all"))
		{
			exchange="";
		}
		else if(param.get("exchange").toString().trim().toLowerCase().equals("false"))//没有过期的
		{
			exchange="and r.exchange ='false'";
		}
		else if(param.get("exchange").toString().trim().toLowerCase().equals("true"))
		{
			exchange="and r.exchange ='true'";
		}
		hql=hql.replace(":exchange:",exchange);
		hql = hql.replaceAll(":sort:", param.get("sort") == null?"id":param.get("sort").toString());
		hql = hql.replaceAll(":order:", param.get("order") == null?"desc":param.get("order").toString());
		//String hql="from RUserAwards u where 1=1 order by u.id desc";
		QueryResult<Map> find2QueryResult = baseDao.find2QueryResultByPage(hql, param, page, size);
		return find2QueryResult;
	}

}
