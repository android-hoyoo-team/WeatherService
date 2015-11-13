package org.cz.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cz.project.entity.bean.Result;
import org.cz.project.entity.table.ProbeAwsPerHour;
import org.cz.project.service.ProbeAwsPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.http.HttpUtil;
@Controller
public class ProbeAwsPerHourController {
	@Autowired ProbeAwsPerHourService probeAwsPerHourService;
	
	@RequestMapping(value="/get_lastest_probe_aws_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getLastestProbeAwsPerHour(HttpServletRequest request,HttpServletResponse response){
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
		Map<String,ProbeAwsPerHour> m=new HashMap<String, ProbeAwsPerHour>();
		m.put("latest_distance", probeAwsPerHourService.getLatestProbeAwsPerHour(param));
		m.put("latest_area", probeAwsPerHourService.getAreaLatestProbeAwsPerHour("58560"));
		result.setResult(m);
		return result;
		
	}
	@RequestMapping(value="/get_lastest_area_probe_aws_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getLastestAreaProbeAwsPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		result.setStatus("success");
		result.setResult(probeAwsPerHourService.getAreaLatestProbeAwsPerHour("58560"));
		return result;
	}
	@RequestMapping(value="/get_lastest_station_probe_aws_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getLastestStationProbeAwsPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		String station_num=params.get("station_num");
		if(station_num==null||station_num.trim().length()<=0)
		{
			station_num="58560";
		}
		else
			station_num=station_num.trim();
		result.setStatus("success");
		result.setResult(probeAwsPerHourService.getStationNumLatestProbeAwsPerHour(station_num));
		return result;
	}
	
}
