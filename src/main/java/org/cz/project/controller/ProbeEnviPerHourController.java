package org.cz.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.cz.project.entity.bean.Result;
import org.cz.project.service.ProbeAwsPerHourService;
import org.cz.project.service.ProbeEnviPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.http.HttpUtil;
@Controller
public class ProbeEnviPerHourController {
	@Autowired ProbeEnviPerHourService probeEnviPerHourService;
	
	@RequestMapping(value="/get_lastest_probe_envi_per_hour", method=RequestMethod.GET)
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
		result.setResult(probeEnviPerHourService.getLatestProbeEnviPerHour(param));
		return result;
		
	}
	@RequestMapping(value="/get_avg_station_num_probe_envi_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getAvgStationNumProbeEnviPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String stationNum=params.get("station_num");
		if(stationNum==null||stationNum.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("station_num不能为空");
			return result;
		}
		result.setStatus("success");
		result.setResult(probeEnviPerHourService.getAvgStationNumLatestProbeEnviPerHour(stationNum));
		return result;
		
	}
	@RequestMapping(value="/get_avg_lastest_probe_envi_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getAvgLastestProbeEnviPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String area_code=params.get("area_code");
		if(area_code==null||area_code.trim().length()<=0)
		{
			area_code="58560";
		}
		result.setStatus("success");
		result.setResult(probeEnviPerHourService.getAvgAreaLatestProbeEnviPerHour(area_code));
		return result;
		
	}
	@RequestMapping(value="/get_avg_province_probe_envi_per_hour", method=RequestMethod.GET)
	@ResponseBody
	public Result getAvgProvinceProbeEnviPerHour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		result.setStatus("success");
		result.setResult(probeEnviPerHourService.getAvgProvinceLatestProbeEnviPerHour());
		return result;
		
	}
	@RequestMapping(value="/get_avg_latest_probe_envi_per_hour_by_citys", method=RequestMethod.GET)
	@ResponseBody
	public Result getAvgLatestProbeEnviPerHourByCitys(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		Map<String, String> parameters = HttpUtil.getParameters(request, "utf-8");
		String citysStr = parameters.get("citys");
		if(citysStr==null||citysStr.trim().length()<=0)
		{
			citysStr="杭州";
		}
		citysStr=citysStr.replaceAll(",$|^,", "");
		
		String[] stationsArr = citysStr.split(",");
		if(stationsArr==null||stationsArr.length<=0)
		{
			
			result.setStatus("error");
			result.setMessage("城市["+citysStr+"]格式错误");
			return result;
		}
		result.setStatus("success");
		List<String> citys=Arrays.asList(stationsArr);
		result.setResult(probeEnviPerHourService.getAvgLatestProbeEnviPerHourByCitys(citys));
		return result;
		
	}
}
