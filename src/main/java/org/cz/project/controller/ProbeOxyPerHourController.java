package org.cz.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cz.project.entity.bean.Result;
import org.cz.project.entity.table.ProbeAwsPerHour;
import org.cz.project.entity.table.ProbeOxyPerHour;
import org.cz.project.service.ProbeAwsPerHourService;
import org.cz.project.service.ProbeOxyPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.http.HttpUtil;
@Controller
@RequestMapping("/oxy")
public class ProbeOxyPerHourController {
	@Autowired ProbeOxyPerHourService probeOxyPerHourService;
	
	@RequestMapping(value="/get_lastest_probe_oxy_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getLastestProbeOxyPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _longitude=params.get("longitude");
		String _latitude=params.get("latitude");
		if(_longitude==null||_latitude==null||_latitude.trim().length()<=0||_longitude.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("longitude或者latitude不能为空");
			return result;
		}
		float latitude;
		float longitude;
		try{
			longitude = Float.parseFloat(_longitude);
			latitude = Float.parseFloat(_latitude);
		}catch(Exception ex)
		{
			result.setStatus("error");
			result.setMessage("longitude或者latitude参数错误");
			return result;
		}
		result.setStatus("success");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("longitude", longitude);
		param.put("latitude", latitude);
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("latest_distance", probeOxyPerHourService.getLatestProbeOxyPerHour(param));
		m.put("latest_area", probeOxyPerHourService.getAreaLatestProbeOxyPerHour("58560"));
		m.put("latest_area_avg", probeOxyPerHourService.getAvgLatestProbeOxyPerHour("58560"));
		result.setResult(m);
		return result;
	}
	@RequestMapping(value="/get_avg_province_lastest_probe_oxy_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getProvinceLastestProbeOxyPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		result.setStatus("success");
		result.setResult(probeOxyPerHourService.getAvgProvinceLatestProbeOxyPerHour());
		return result;
	}
	@RequestMapping(value="/get_latest_o2_per_hour_by_stations", method=RequestMethod.GET)
	@ResponseBody
	public Result getLatestO2PerHourByStations(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		Map<String, String> parameters = HttpUtil.getParameters(request, "utf-8");
		String stations = parameters.get("stations");
		if(stations==null||stations.trim().length()<=0)
		{
			stations="K1862,K1868";
		}
		stations=stations.replaceAll(",$|^,", "");
		
		String[] stationsArr = stations.split(",");
		if(stationsArr==null||stationsArr.length<=0)
		{
			
			result.setStatus("error");
			result.setMessage("站点["+stations+"]格式错误");
			return result;
		}
		result.setStatus("success");
		List<String> stationsList=Arrays.asList(stationsArr);
		result.setResult(probeOxyPerHourService.getLatestO2PerHourByStations(stationsList));
		return result;
	}
}
