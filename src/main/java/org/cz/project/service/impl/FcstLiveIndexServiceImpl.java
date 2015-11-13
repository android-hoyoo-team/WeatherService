package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.FcstLiveIndex;
import org.cz.project.service.FcstLiveIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FcstLiveIndexServiceImpl implements FcstLiveIndexService{
	@Autowired BaseDao baseDao;

	@Override
	@Transactional
	public FcstLiveIndex getLastestFcstLiveIndexByType(String fcst_type) {
		Map<String ,Object> p=new HashMap<String, Object>();
		p.put("fcst_type", fcst_type);
		List find = baseDao.find("from FcstLiveIndex f where f.fcst_type=:fcst_type order by f.id desc", p,0,1);
		if(find.size()>0)
			return (FcstLiveIndex) find.get(0);
		return null;
	}
	

}
