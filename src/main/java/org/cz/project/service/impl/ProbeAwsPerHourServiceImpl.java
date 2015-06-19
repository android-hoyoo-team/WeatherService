package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.ProbeAwsPerHour;
import org.cz.project.entity.table.TStationArea;
import org.cz.project.service.ProbeAwsPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProbeAwsPerHourServiceImpl implements ProbeAwsPerHourService {
	@Autowired BaseDao baseDao;

	@Override
	@Transactional
	public ProbeAwsPerHour getLatestProbeAwsPerHour(Map<String,Object> jsonParam) {
		// TODO Auto-generated method stub
		//baseDao.find(hql, params, start, length);
//		List find2 = baseDao.find("from TStationArea", 0, 1);
//		System.out.println(find2);
		System.out.println("params:"+jsonParam.get("longitude")+","+jsonParam.get("latitude"));
//		String hql ="select fnGetDistance(120.35,80.0,120.35,29.91)  as f from TStationArea t";
		String hql ="select fnGetDistance(longitude)  as f from TStationArea t";
		//String hql = "select Max(longitude) from TStationArea";
		List stations = baseDao.find(hql);
		//List stations = baseDao.find(hql, jsonParam, 0, 1);
		System.out.println("ppp:"+stations.get(0));
//		TStationArea station = null;
//		if(stations!=null){
//			station = (TStationArea) stations.get(0);
//		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("stationNum", "K6079");
		List find = baseDao.find("from ProbeAwsPerHour p where p.stationNum=:stationNum", map,0, 1);
		 return (ProbeAwsPerHour) find.get(0);
	}
}
