package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.FcstTextLocal;
import org.cz.project.service.FcstFineCityService;
import org.cz.project.service.TextLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FcstFineCityServiceImpl implements FcstFineCityService{
	@Autowired BaseDao baseDao;

	@Override
	@Transactional
	public List<FcstTextLocal> getFcstFineCityByStationCode(String stationCode) {
		Map<String ,Object> p=new HashMap<String, Object>();
		p.put("stationCode", stationCode);
		List find = baseDao.find("from FcstFineCity f where f.stationCode=:stationCode order by renewTime desc", p,0,1);
		return find;
	}
	

}
