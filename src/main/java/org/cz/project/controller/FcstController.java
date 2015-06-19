package org.cz.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.cz.project.entity.bean.Result;
import org.cz.project.service.FcstFineCityService;
import org.cz.project.service.TextLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.ChineseCalendar;

@Controller
public class FcstController {
	
	@Autowired TextLocalService goodsService;
	@Autowired FcstFineCityService fcstFineCityService;
	
	@RequestMapping(value="/xx", method=RequestMethod.GET)
	@ResponseBody
	public String index(){
		return "";
	}
	@RequestMapping(value="/get_fcst_text_local_by_stationnum", method=RequestMethod.GET)
	@ResponseBody
	public Result getFcstTextLocal(){
		System.out.println("get_fcst_text_local_by_stationnum");
		Result result=new Result();
		result.setStatus("success");
		result.setResult(goodsService.getFcstTextLocalByStationnum("58560"));
		return result;
	}
	@RequestMapping(value="/get_fcst_fine_city_by_stationnum", method=RequestMethod.GET)
	@ResponseBody
	public Result getFcstFineCity(){
		System.out.println("get_fcst_fine_city_by_stationcode");
		Result result=new Result();
		result.setStatus("success");
		result.setResult(fcstFineCityService.getFcstFineCityByStationCode("58560"));
		return result;
	}
	@RequestMapping(value="/get_weather_desc_type", method=RequestMethod.GET)
	@ResponseBody
	public Map getWeatherDescType(){
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
	@RequestMapping(value="/get_wind_type", method=RequestMethod.GET)
	@ResponseBody
	public Map getWindType(){
//		Result result=new Result();
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("0", "2-3级");
		m.put("1", "东北风，3-4级");
		m.put("2", "东风，4-5级");
		m.put("3", "东南风，5-6级");
		m.put("4", "南风，6-7级");
		m.put("5", "西南风，7-8级");
		m.put("6", "西风，8-9级");
		m.put("7", "西北风，9-10级");
		m.put("8", "北风，10-11级");
		m.put("9", "旋转风，11-12级");
		return m;
	}
}
