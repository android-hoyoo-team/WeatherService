package org.cz.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.entity.table.ProbeAwsPerHour;
import org.cz.project.entity.table.ProbeEnviPerHour;
import org.cz.project.service.ProbeEnviPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProbeEnviPerHourServiceImpl implements ProbeEnviPerHourService {
	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public ProbeEnviPerHour getLatestProbeEnviPerHour(Map<String,Object> param) {
		System.out.println("params:"+param.get("longitude")+","+param.get("latitude"));
		String hql ="select new map(fn_get_distance(t.longitude,t.latitude,:longitude,:latitude) as distance,t.stationNum as stationNum) from TStationEnvi t where t.stationNum in (select p.stationNum from ProbeEnviPerHour p) order by distance";
		List<Map> stations = baseDao.find(hql,param,0,1);
		System.out.println(stations);
		if(stations.size()>0)
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("stationNum", stations.get(0).get("stationNum"));
			List find = baseDao.find("from ProbeEnviPerHour p where p.stationNum=:stationNum and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0) order by p.id desc", map,0, 1);
			return (ProbeEnviPerHour) find.get(0);
		}
		return null;
	}
	@Override
	@Transactional
	public Map getAvgStationNumLatestProbeEnviPerHour(String stationNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("stationNum", stationNum);
		List find = baseDao.find("SELECT new Map(AVG(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.stationNum=:stationNum and  p.pm25 > 0 and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0) ", map,0, 1);
		return (Map) find.get(0);
	}
	@Override
	@Transactional
	public Map getAvgAreaLatestProbeEnviPerHour(String areaCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("areaCode", areaCode);
		//String hql="SELECT new Map(AVG(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode=:areaCode and  p.pm25 > 0 and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0)";
		//if(areaCode.equals("58560"))
		//{
			//hql="SELECT cast(avg(Pm25) as decimal(10,1)) as Pm25 FROM Probe_Envi_PerHour where Areacode = '58560' and Pm25 between 0 and 200 and Pm25 <> 50  and cast(Date as varchar(8))+Time = (SELECT max(cast(Date as varchar(8))+Time) from Probe_Envi_PerHour where Areacode = '58560')";
		String hql="SELECT new Map(cast_avg_decimal(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode=:areaCode and  p.pm25 >= 0 and p.pm25 <= 200 and p.pm25 <> 50 and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0 where p0.areaCode=:areaCode)";
		//}
		List find = baseDao.find(hql, map,0, 1);
		return (Map) find.get(0);
	}
	@Override
	@Transactional
	public Map getAvgProvinceLatestProbeEnviPerHour() {
		Map<String,Object> map = new HashMap<String, Object>();
//		List find = baseDao.find("SELECT new Map(cast_avg_decimal(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode='00000' and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0 where p0.areaCode='00000') ", map,0, 1);
		List find = baseDao.find("SELECT new Map(cast_avg_decimal(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.areaCode='00000' and p.pm25>0 and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0 ) ", map,0, 1);
//		List find = baseDao.find("SELECT new Map(cast_avg_decimal(p.pm25) as pm25) FROM ProbeEnviPerHour p where p.stationNum='58457' and p.pm25>0 and ext_concat(p.date,p.time) = (SELECT Max(ext_concat(p0.date,p0.time)) FROM ProbeEnviPerHour p0 ) ", map,0, 1);
		return (Map) find.get(0);
	}
	@Override
	@Transactional
	public List getAvgLatestProbeEnviPerHourByCitys(List citys) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("citys", citys);
		String sql="SELECT new Map(b.city as city,cast_avg_decimal(a.pm25) as pm25) "+
				" FROM ProbeEnviPerHour a , TStationEnvi b"+
				" where  a.stationNum=b.stationNum and ext_concat(a.date, a.time) = (SELECT max(ext_concat(p0.date, p0.time)) from ProbeEnviPerHour p0)"+
				" and a.pm25>0 and b.city in (:citys) GROUP BY b.city";
		List find = baseDao.find(sql, map,0, 0);
		return find;
	}
}
