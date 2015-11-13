package org.cz.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cz.project.entity.bean.Result;
import org.cz.project.service.FcstFineCityService;
import org.cz.project.service.FcstLiveIndexService;
import org.cz.project.service.TextLocalService;
import org.cz.project.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.ChineseCalendar;
import per.cz.util.http.HttpUtil;

@Controller
public class FcstController {
	
	@Autowired TextLocalService goodsService;
	@Autowired FcstFineCityService fcstFineCityService;
	@Autowired TownService townService;
	@Autowired FcstLiveIndexService fcstLiveIndexService;
	
	
	@RequestMapping(value="/xx", method=RequestMethod.GET)
	@ResponseBody
	public String index(){
		return "";
	}
	@RequestMapping(value="/get_fcst_live_index_by_type", method=RequestMethod.GET)
	@ResponseBody
	public Result getFcstLiveIndexByType(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String type=params.get("type");
		if(type==null||type.toString().length()<=0)
		{
			result.setMessage("type 不能为空");
			result.setStatus("error");
			return result;
		}
//		Result result=new Result();
		result.setStatus("success");
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("fcst_type", type);
		result.setResult(fcstLiveIndexService.getLastestFcstLiveIndexByType(type));
		return result;
	}
	@RequestMapping(value="/get_town_info", method=RequestMethod.GET)
	@ResponseBody
	public Result getTownInfo(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _fh=params.get("fh");
		Short fh;
		if(_fh==null||_fh.length()<=0)
		{
			result.setMessage("fh 不能为空");
			result.setStatus("error");
			return result;
		}
		else 
		{
			try{
				fh = Short.parseShort(_fh);
			}catch(Exception e)
			{
				result.setMessage("fh:"+_fh+"格式错误");
				result.setStatus("error");
				return result;
			}
		}
//		Result result=new Result();
		result.setStatus("success");
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("fh", fh);
		result.setResult(townService.getTown(m));
		return result;
	}
	@RequestMapping(value="/get_town_tour", method=RequestMethod.GET)
	@ResponseBody
	public Result getTownTour(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _fh=params.get("fh");
		Short fh;
		if(_fh==null||_fh.length()<=0)
		{
			result.setMessage("fh 不能为空");
			result.setStatus("error");
			return result;
		}
		else 
		{
			try{
				fh = Short.parseShort(_fh);
			}catch(Exception e)
			{
				result.setMessage("fh:"+_fh+"格式错误");
				result.setStatus("error");
				return result;
			}
		}
//		Result result=new Result();
		result.setStatus("success");
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("fh", fh);
		result.setResult(townService.getTownTour(m));
		return result;
	}
	@RequestMapping(value="/get_fcst_text_local_by_stationnum", method=RequestMethod.GET)
	@ResponseBody
	public Result getFcstTextLocal(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("get_fcst_text_local_by_stationnum");
		Result result=new Result();
		result.setStatus("success");
		result.setResult(goodsService.getFcstTextLocalByStationnum("58560"));
		return result;
	}
	@RequestMapping(value="/get_fcst_fine_city_by_stationnum", method=RequestMethod.GET)
	@ResponseBody
	public Result getFcstFineCity(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("get_fcst_fine_city_by_stationcode");
		Result result=new Result();
		result.setStatus("success");
		result.setResult(fcstFineCityService.getFcstFineCityByStationCode("58560"));
		return result;
	}
	@RequestMapping(value="/get_weather_desc_type", method=RequestMethod.GET)
	@ResponseBody
	public Map getWeatherDescType(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
//		Result result=new Result();
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("0", "晴");m.put("1", "多云");m.put("2", "阴");m.put("3", "阵雨");
		m.put("4", "雷阵雨");m.put("5", "雷阵雨并伴有冰雹");m.put("6", "雨夹雪");
		m.put("7", "小雨");m.put("8", "中雨");m.put("9", "大雨");m.put("10", "暴雨");
		m.put("11", "大暴雨");m.put("12", "特大暴雨");m.put("13", "阵雪");m.put("14", "小雪");
		m.put("15", "中雪");m.put("16", "大雪");m.put("17", "暴雪");m.put("18", "雾");
		m.put("19", "冻雨");m.put("20", "沙城暴");m.put("21", "小雨到中雨");m.put("22", "中雨到大雨");
		m.put("23", "大雨到暴雨");m.put("24", "暴雨到大暴雨");m.put("25", "大暴雨到特大暴雨");m.put("26", "小到中雪");
		m.put("27", "中到大雪");m.put("28", "大到暴雪");m.put("29", "浮尘");m.put("30", "扬沙");
		m.put("31", "强沙尘暴");m.put("32", "强雾");
		return m;
	}
	@RequestMapping(value="/get_wind_d_type", method=RequestMethod.GET)
	@ResponseBody
	public Map getWindDType(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
//		Result result=new Result();
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("0", "");
		m.put("1", "东北风");
		m.put("2", "东风");
		m.put("3", "东南风");
		m.put("4", "南风");
		m.put("5", "西南风");
		m.put("6", "西风");
		m.put("7", "西北风");
		m.put("8", "北风");
		m.put("9", "旋转风");
		return m;
	}
	@RequestMapping(value="/get_wind_s_type", method=RequestMethod.GET)
	@ResponseBody
	public Map getWindSType(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
//		Result result=new Result();
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("0", "2-3级");
		m.put("1", "3-4级");
		m.put("2", "4-5级");
		m.put("3", "5-6级");
		m.put("4", "6-7级");
		m.put("5", "7-8级");
		m.put("6", "8-9级");
		m.put("7", "9-10级");
		m.put("8", "10-11级");
		m.put("9", "11-12级");
		return m;
	}
}
