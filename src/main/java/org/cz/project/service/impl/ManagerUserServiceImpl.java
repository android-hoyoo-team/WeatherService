package org.cz.project.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.dao.QueryResult;
import org.cz.project.entity.table.ManagerUsers;
import org.cz.project.service.ManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import per.cz.util.encryption.EncryptionUtil;
@Service
public class ManagerUserServiceImpl implements ManagerUserService {

	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public ManagerUsers save(ManagerUsers u) {
		u.setAddTime(new Date().getTime());
		u.setPassword(EncryptionUtil.getMD5Base64(u.getPassword().getBytes()));
		Serializable save = baseDao.save(u);
		if(save!=null)	
			return u;
		return null;
	}

	@Override
	@Transactional
	public void delete(ManagerUsers u) {
		baseDao.delete(u);
	}

	@Override
	@Transactional
	public void update(ManagerUsers u) {
		u.setUpdateTime(new Date().getTime());
		u.setPassword(EncryptionUtil.getMD5Base64(u.getPassword().getBytes()));
		baseDao.update(u);
	}

	
	@Override
	@Transactional
	public ManagerUsers findById(int id) {
		return (ManagerUsers) baseDao.get(ManagerUsers.class, id);
	}
	@Override
	@Transactional
	public ManagerUsers findByName(String name) {
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("name", name);
		List<ManagerUsers> find = baseDao.find("from ManagerUsers u where u.name=:name", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public ManagerUsers findByName(String name, String password) {
		password=EncryptionUtil.getMD5Base64(password.getBytes());
		Map<String ,Object> m=new HashMap<String, Object>();
		m.put("name", name);
		m.put("password", password);
		List<ManagerUsers> find =baseDao.find("from ManagerUsers u where u.name=:name and u.password=:password", m);
		if(find!=null&&find.size()>0)
		{
			return find.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public QueryResult<ManagerUsers> findByParam(Map<String, Object> param, int start,
			int length) {
		String hql="from ManagerUsers u where u.name=:name and u.password=:password";
		//String f_hql = StringUtil.format(hql, param);
		QueryResult<ManagerUsers> find2QueryResult = baseDao.find2QueryResult(hql, param, start, length);
		return find2QueryResult;
	}
public static void main(String[] args) {
	System.out.println(EncryptionUtil.getMD5Base64("admin@panan.com".getBytes()));
}
}
