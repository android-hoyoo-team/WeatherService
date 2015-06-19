package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.FcstTextLocal;
import org.cz.project.service.TextLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TextLocalServiceImpl implements TextLocalService{
	@Autowired BaseDao baseDao;

	@Override
	@Transactional
	public List<FcstTextLocal> getFcstTextLocalByStationnum(String stationnum) {
		Map<String ,Object> p=new HashMap<String, Object>();
		p.put("stationnum", stationnum);
		List find = baseDao.find("from FcstTextLocal f where f.stationnum=:stationnum and f.id in (select MAX(id) from FcstTextLocal f2 where f2.stationnum=:stationnum group by f2.fcst_type)", p);
		return find;
	}
	

}
