package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.ProbeAwsPerHour;
import org.cz.project.service.ProbeEnviPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ProbeEnviPerHourServiceImpl implements ProbeEnviPerHourService {
	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public ProbeAwsPerHour getLatestProbeEnviPerHour(Map<String,Object> param) {
		System.out.println("params:"+param.get("longitude")+","+param.get("latitude"));
		String hql ="select  new map(fn_get_distance(t.longitude,t.latitude,:longitude,:latitude) as distance,t.stationNum as stationNum) from TStationArea t order by distance";
		List<Map> stations = baseDao.find(hql,param,0,1);
		System.out.println(stations);
		if(stations.size()>0)
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("stationNum", stations.get(0).get("stationNum"));
			List find = baseDao.find("from ProbeEnviPerHour p where p.stationNum=:stationNum order by p.id desc", map,0, 1);
			return (ProbeAwsPerHour) find.get(0);
		}
		return null;
	}
}
