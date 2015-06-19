package org.cz.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.cz.project.service.FcstFineCityService;
import org.cz.project.service.TextLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.ChineseCalendar;

@Controller
public class OtController {
	
	@Autowired TextLocalService goodsService;
	@Autowired FcstFineCityService fcstFineCityService;
	
	@RequestMapping(value="/get_chinese_calendar", method=RequestMethod.GET)
	@ResponseBody
	public Map getChineseCalendar(){
		ChineseCalendar c=new ChineseCalendar();
		Map<String,Object> m =new HashMap<String,Object>();
		m.put("day_of_week", c.getChinese(ChineseCalendar.DAY_OF_WEEK));
		m.put("chinese_year", c.getChinese(ChineseCalendar.CHINESE_YEAR));
		m.put("chinese_month", c.getChinese(ChineseCalendar.CHINESE_MONTH));
		m.put("chinese_date", c.getChinese(ChineseCalendar.CHINESE_DATE));
		m.put("chinese_zodiac", c.getChinese(ChineseCalendar.CHINESE_ZODIAC));
		m.put("chinese_sectional_term_day", c.get(ChineseCalendar.CHINESE_SECTIONAL_TERM));
		m.put("chinese_sectional_term_name", c.getChinese(ChineseCalendar.CHINESE_SECTIONAL_TERM));
		m.put("chinese_principle_term_day", c.get(ChineseCalendar.CHINESE_PRINCIPLE_TERM));
		m.put("chinese_principle_term_name", c.getChinese(ChineseCalendar.CHINESE_PRINCIPLE_TERM));
		m.put("chinese_earthly_branch", c.getChinese(ChineseCalendar.CHINESE_EARTHLY_BRANCH));
		m.put("chinese_heavenly_stem", c.getChinese(ChineseCalendar.CHINESE_HEAVENLY_STEM));
		return m;
	}


}
