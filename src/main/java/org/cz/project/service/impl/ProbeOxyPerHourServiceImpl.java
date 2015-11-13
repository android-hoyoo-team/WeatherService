package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.ProbeOxyPerHour;
import org.cz.project.service.ProbeOxyPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProbeOxyPerHourServiceImpl implements ProbeOxyPerHourService {
	@Autowired BaseDao baseDao;

	@Override
	@Transactional
	public ProbeOxyPerHour getLatestProbeOxyPerHour(Map<String,Object> param) {
		System.out.println("params:"+param.get("longitude")+","+param.get("latitude"));
		String hql ="select  new map(fn_get_distance(t.longitude,t.latitude,:longitude,:latitude) as distance,t.stationNum as stationNum) from TStationOxygen t order by distance";
		List<Map> stations = baseDao.find(hql,param,0,1);
		System.out.println(stations);
		if(stations.size()>0) 
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("stationNum", stations.get(0).get("stationNum"));
			List find = baseDao.find("from ProbeOxyPerHour p where p.Stationnum=:stationNum and ext_concat(p.Date,p.Time) = (SELECT Max(ext_concat(p0.Date,p0.Time)) FROM ProbeOxyPerHour p0) order by p.id desc", map,0, 1);
			if(find!=null&&find.size()>0)
				return (ProbeOxyPerHour) find.get(0);
		}
		return null;
	}
	@Override
	@Transactional
	public ProbeOxyPerHour getAreaLatestProbeOxyPerHour(String areaCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("areaCode", areaCode);
		List find = baseDao.find("from ProbeOxyPerHour p where p.Areacode=:areaCode order by p.id desc", map,0, 1);
		return (ProbeOxyPerHour) find.get(0);
	}
	@Override
	@Transactional
	public Map getAvgLatestProbeOxyPerHour(String areaCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("areaCode", areaCode);
		//SELECT new Map(AVG(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode=:areaCode and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0) 
		List<Map> find = baseDao.find("SELECT new Map(Max(p.ID) as ID, Max(p.Date) as Date,Max(p.Time) as Time,Max(p.Areacode) as Areacode, AVG(p.MaxO2) as MaxO2,AVG(p.O2) as O2,AVG(p.MinO2) as MinO2 ,Max(p.Renewtime) as Renewtime) FROM ProbeOxyPerHour p where p.Areacode=:areaCode and ext_concat(p.Date,p.Time) = (SELECT Max(ext_concat(p0.Date,p0.Time)) FROM ProbeOxyPerHour p0)",map,0,1);// from ProbeOxyPerHour p where p.Areacode=:areaCode GROUP BY p.Date,p.Time order by ID desc", map,0, 1);
		return find.get(0);
	}
	@Override
	@Transactional
	public Map getAvgProvinceLatestProbeOxyPerHour() {
		Map<String,Object> map = new HashMap<String, Object>();
		//SELECT new Map(AVG(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode=:areaCode and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0) 
		List<Map> find = baseDao.find("SELECT new Map(Max(p.ID) as ID, Max(p.Date) as Date,Max(p.Time) as Time,Max(p.Areacode) as Areacode, AVG(p.MaxO2) as MaxO2,AVG(p.O2) as O2,AVG(p.MinO2) as MinO2 ,Max(p.Renewtime) as Renewtime) FROM ProbeOxyPerHour p where ext_concat(p.Date,p.Time) = (SELECT Max(ext_concat(p0.Date,p0.Time)) FROM ProbeOxyPerHour p0)",map,0,1);// from ProbeOxyPerHour p where p.Areacode=:areaCode GROUP BY p.Date,p.Time order by ID desc", map,0, 1);
		return find.get(0);
	}
	@Override
	@Transactional
	public List getLatestO2PerHourByStations(List stations) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("stations", stations);
		String sql=	"SELECT new Map(b.stationName as stationName,a.O2 as O2)"+  
						" FROM ProbeOxyPerHour a ,TStationOxygen b"+
						" where  a.Stationnum=b.stationNum and ext_concat(a.Date,a.Time) = (SELECT Max(ext_concat(p0.Date ,p0.Time)) from ProbeOxyPerHour p0)"+
						" and b.stationNum in (:stations)";
		//SELECT new Map(AVG(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode=:areaCode and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0) 
		List<Map> find = baseDao.find(sql,map,0,0);// from ProbeOxyPerHour p where p.Areacode=:areaCode GROUP BY p.Date,p.Time order by ID desc", map,0, 1);
		return find;
	}
}
