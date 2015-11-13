package org.cz.project.service.impl;

import java.util.List;
import java.util.Map;

import org.cz.project.dao.BaseDao;
import org.cz.project.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TownServiceImpl implements TownService {
	@Autowired BaseDao baseDao;
	@Override
	@Transactional
	public List<Map<String,Object>> getTown(Map<String,Object> param) {
		
//		String hql ="select new map(a.fcstTime as fcstTime,a.stid as stid,b.stname as stname,b.stlat as stlat,b.stlon as stlon,a.fh as fh,a.ww03 as ww03,a.ww06 as ww06,a.ww12 as ww12,a.t as t,a.tmax12 as tmax12,a.tmin12 as tmin12,a.pr03 as pro03,a.pr06 as pro06,a.pr12 as pr12,a.pr24 as pr24,a.rh as fcstTime,a.wd as wd,a.ws as ws,a.cloud as cloud,a.vv as vv,a.slp as slp)  from TownFine a ,TownInfo b where  a.stid = b.stid  and a.fcstTime = (select max(a1.fcstTime) from TownFine a1) and a.fh=:fh";
		String hql ="select new map(a.fcstTime as fcstTime,a.stid as stid,b.stname as stname,b.stlat as stlat,b.stlon as stlon,a.fh as fh,a.ww03 as ww03,a.ww06 as ww06,a.ww12 as ww12,a.t as t,a.tmax12 as tmax12,a.tmin12 as tmin12,a.pr03 as pro03,a.pr06 as pro06,a.pr12 as pr12,a.pr24 as pr24,a.rh as fcstTime,a.wd as wd,a.ws as ws,a.cloud as cloud,a.vv as vv,a.slp as slp)  from TownFine a ,TownInfo b where  a.stid = b.stid  and a.fcstTime = (select max(a1.fcstTime) from TownFine a1) and a.fh=:fh";
		List<Map<String,Object>> stations = baseDao.find(hql,param,0,0);
		return stations;
	}
	@Override
	@Transactional
	public List<Map<String, Object>> getTownTour(Map<String, Object> param) {
		String hql ="select new map(a.fcstTime as fcstTime,a.stid as stid,b.stname as stname,b.stlat as stlat,b.stlon as stlon,a.fh as fh,a.ww03 as ww03,a.ww06 as ww06,a.ww12 as ww12,a.t as t,a.tmax12 as tmax12,a.tmin12 as tmin12,a.pr03 as pro03,a.pr06 as pro06,a.pr12 as pr12,a.pr24 as pr24,a.rh as fcstTime,a.wd as wd,a.ws as ws,a.cloud as cloud,a.vv as vv,a.slp as slp)  from TownFine a ,TStationTour b where  a.stid = b.stid  and a.fcstTime = (select max(a1.fcstTime) from TownFine a1) and a.fh=:fh";
		List<Map<String,Object>> stations = baseDao.find(hql,param,0,0);
		return stations;
	}

}
