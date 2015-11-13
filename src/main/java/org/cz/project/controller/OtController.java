package org.cz.project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.bean.Result;
import org.cz.project.entity.table.BaseSysInfo;
import org.cz.project.entity.table.Suggestion;
import org.cz.project.entity.table.Users;
import org.cz.project.service.BaseSysInfoService;
import org.cz.project.service.FcstFineCityService;
import org.cz.project.service.SuggestionService;
import org.cz.project.service.TextLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.upload.UploadProperty;
import per.cz.util.ChineseCalendar;
import per.cz.util.StringUtil;
import per.cz.util.http.HttpUtil;

@Controller
public class OtController {
	
	@Autowired TextLocalService goodsService;
	@Autowired FcstFineCityService fcstFineCityService;
	@Autowired BaseSysInfoService baseSysInfoService;
	@Autowired SuggestionService suggestionService;
	@RequestMapping(value="/error", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result index(HttpServletRequest request,HttpServletResponse response){
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//Config.class.getClass().getResource("upload.properties").getPath();
		//response.setStatus(403);
		Result result=new Result();
		result.setMessage("没有权限，请登录或联系管理员");
		result.setStatus("error");
		response.setHeader("Location", "/");
		response.setHeader("Refresh","2;URL=/");
		return result;
	}
	@RequestMapping(value="/get_sys_key_value", method=RequestMethod.GET)
	@ResponseBody
	public Result getSysKeyValue (HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		//Config.class.getClass().getResource("upload.properties").getPath();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result result=new Result();
		String key = params.get("key");
		if(key==null||key.trim().length()<=0)
		{
			List<BaseSysInfo> baseSysInfoList = baseSysInfoService.findByType("sys");
			result.setResult(baseSysInfoList);
			result.setStatus("success");
		}
		else
		{
			key=key.trim();
			BaseSysInfo baseSysInfo = baseSysInfoService.findByTypeAndKey("sys", key);
			result.setResult(baseSysInfo);
			result.setStatus("success");
		}
		return result;
	}
	@RequestMapping(value="/save_sys_key_value", method=RequestMethod.GET)
	@ResponseBody
	public Result saveSysKeyValue (HttpServletRequest request,HttpServletResponse response){
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//Config.class.getClass().getResource("upload.properties").getPath();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result result=new Result();
		String key = params.get("key");
		String value = params.get("value");
		if(key==null||key.trim().length()<=0||value==null||value.trim().length()<=0)
		{
			result.setMessage("key和value都不能为空");
			result.setStatus("success");
			return result;
		}
		else
		{
			key=key.trim();
			value=value.trim();
			BaseSysInfo findByTypeAndKey = baseSysInfoService.findByTypeAndKey("sys", key);
			BaseSysInfo baseSysInfo=null;
			if(findByTypeAndKey==null)
				baseSysInfo=new BaseSysInfo();
			else
			{
				baseSysInfo=findByTypeAndKey;
			}
			baseSysInfo.setKey(key);
			baseSysInfo.setType("sys");
			baseSysInfo.setValue(value);
			
			BaseSysInfo save = baseSysInfoService.saveOrUpdate(baseSysInfo);
			result.setResult(save);
			result.setStatus("success");
		}
		return result;
	}
	@RequestMapping(value="/get_chinese_calendar", method=RequestMethod.GET, headers="Accept=*")
	@ResponseBody
	public Map getChineseCalendar(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		ChineseCalendar c=new ChineseCalendar();
		Map<String,Object> m =new HashMap<String,Object>();
		m.put("simple_gregorian_date", c.getSimpleGregorianDateString());
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
	@RequestMapping(value="/get_all_suggestion", method=RequestMethod.GET)
	@ResponseBody
	public Result findAllSuggestion(HttpServletRequest request,HttpServletResponse response){
		Result<Object> result=new Result<Object>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		String _page = params.get("page");
		String _size = params.get("size");
		String sort = params.get("sort");
		String order = params.get("order");
		int page=0,size=0;
		if(StringUtils.isEmpty(_size)||StringUtils.isEmpty(_page))
		{
			result.setStatus("error");
			result.setMessage("页码和每页的大小需要指定.");
			return result;
		}
		try{
			page=Integer.parseInt(_page);
			size=Integer.parseInt(_size);
		}catch(Exception e)
		{
			result.setStatus("error");
			result.setMessage("页码和每页指定的数据格式不正确");
			return result;
		}
		QueryResult<Suggestion> findAll = suggestionService.findAll(page, size,sort,order);
		if(findAll!=null)
		{
			result.setStatus("success");
			result.setResult(findAll);
			return result;
		}
		else
		{
			result.setStatus("error");
			result.setMessage("获取用户错误");
			return result;
		}
	}
	@RequestMapping(value="/save_suggestion", method=RequestMethod.GET)
	@ResponseBody
	public Result saveSuggestion (HttpServletRequest request,HttpServletResponse response){
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//Config.class.getClass().getResource("upload.properties").getPath();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result result=new Result();
		String content = params.get("content");
		String contact = params.get("contact");
		if(content==null||content.trim().length()<=0||contact==null||contact.trim().length()<=0)
		{
			result.setMessage("建议和联系方式都不能为空");
			result.setStatus("success");
			return result;
		}
		else
		{
			if(contact.matches("1[0-9]{10}")||StringUtil.isEmail(contact))
			{
				content=content.trim();
				contact=contact.trim();
				Suggestion s=new Suggestion();
				s.setContact(contact);
				s.setContent(content);
				s.setAddTime(new Date().getTime());
				Suggestion saveOrUpdate = suggestionService.saveOrUpdate(s);
				result.setResult(saveOrUpdate);
				result.setStatus("success");
				return result;
			}
			else
			{
				result.setMessage(contact+"不是email也不是手机号");
				result.setStatus("success");
				return result;
			}
		}
	}
//	@RequestMapping(value="/file_upload", method={RequestMethod.GET})
//	@ResponseBody
//	public Result FileUploadGet(HttpServletRequest request,HttpServletResponse response){
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		//Config.class.getClass().getResource("upload.properties").getPath();
//		Result result=new Result();
//			result.setResult(UploadProperty.getProp4XML());
//			result.setStatus("success");
//			return result;
//	}

}
