package org.cz.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.bytecode.analysis.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.bean.Awards;
import org.cz.project.entity.bean.Result;
import org.cz.project.entity.bean.UserInfo;
import org.cz.project.entity.table.BaseSysInfo;
import org.cz.project.entity.table.ManagerUsers;
import org.cz.project.entity.table.RUserAwards;
import org.cz.project.entity.table.Users;
import org.cz.project.service.BaseSysInfoService;
import org.cz.project.service.RUserAwardsService;
import org.cz.project.service.RUsersImagesService;
import org.cz.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.DateUtil;
import per.cz.util.bean.BeanUtils;
import per.cz.util.gson.GsonUtil;
import per.cz.util.http.HttpUtil;
@Controller
public class AwardsController {
	@Autowired UserService userService;
	@Autowired RUsersImagesService rUsersImagesService; 
	@Autowired RUserAwardsService rUserAwardsService; 
	@Autowired BaseSysInfoService baseSysInfoService;
	@RequestMapping(value="/get_user_awards", method=RequestMethod.GET)
	@ResponseBody
	public Result getRUsersAwards(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Map<String, Object> param=new HashMap<String, Object>();
		Result<Object> result=new Result<Object>();
		String _page=params.get("page");
		String _size=params.get("size");
		String _order=params.get("order");
		String _sort=params.get("sort");
		
		//null,illegal,all
		String _awards=params.get("awards");
		String _user_id=params.get("user_id");
		String user_name=params.get("user_name");
		String serial_number=params.get("serial_number");
		String _out_of_date =params.get("out_of_date");
		String _exchange =params.get("exchange");
		
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		if(_loginInfo!=null)
		{
			UserInfo u=(UserInfo) _loginInfo;
			params.put("user_info", u.getType());
			if(u.getType().equals("manager"))
			{
				ManagerUsers m_user=(ManagerUsers) u.getUsers();
				String tag=m_user.getTag();
				if(!"0".equals(tag))
				{
					param.put("tag", tag);
				}
			}
		}
		
//		UserInfo u=(UserInfo) _loginInfo;
//		else
//		{
//			
//		}
		
		
		
//		boolean out_of_date=false;
		int user_id=0;
		if(_out_of_date==null||_out_of_date.trim().length()<=0)
		{
			_out_of_date="all";
		}
		if(_awards==null||_awards.trim().length()<=0)
		{
			_awards=null;
		}
		if(_user_id==null||_user_id.trim().length()<=0)
		{
			_user_id="0";
		}
		if(_exchange==null||(!_exchange.trim().toLowerCase().equals("false")&&!_exchange.trim().toLowerCase().equals("true")))
		{
			_exchange=null;
		}
		else
		{
			_exchange=_exchange.trim().toLowerCase();
		}
		int page=0,size=0;
		if(StringUtils.isEmpty(_size)||StringUtils.isEmpty(_page))
		{
			result.setStatus("error");
			result.setMessage("页码和每页的大小需要指定.");
			return result;
		}
		try{
			user_id=Integer.parseInt(_user_id);
		}catch(Exception ex)
		{
			result.setStatus("error");
			result.setMessage("user_id参数错误:"+_user_id);
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
		try{
			
			param.put("awards", _awards);
			param.put("no_awards", "false");
			param.put("out_of_date", _out_of_date);
			param.put("exchange", _exchange);
			if(user_name!=null&&user_name.trim().length()>0)
				param.put("user_name", user_name.trim());
			if(serial_number!=null&&serial_number.trim().length()>0)
				param.put("serial_number", serial_number.trim());
			if(user_id>0)
				param.put("user_id", user_id);
			if(_order!=null&&_order.trim().length()>=0)
				param.put("order", _order.trim());
			if(_sort!=null&&_sort.trim().length()>=0)
				param.put("sort", _sort);

			QueryResult<Map> findByParam = rUserAwardsService.findByParam4Page(param, page, size);
			if(findByParam!=null)
			{
				result.setStatus("success");
				result.setResult(findByParam);
				return result;
			}
			result.setStatus("error");
			result.setMessage("没有获取到用户获奖信息");
			return result;
		}catch(Exception e)
		{
			e.printStackTrace();
			result.setStatus("error");
			result.setMessage("系统错误:"+e.getMessage());
			return result;
		}
	}
	@RequestMapping(value="/get_user_today_awards", method=RequestMethod.GET)
	@ResponseBody
	public Result getUserTodayAwards(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _userId=params.get("user_id");
		int userId=0;
		if(StringUtils.isEmpty(_userId))
		{
			result.setStatus("error");
			result.setMessage("用户id必须制定[user_id]");
			return result;
		}
		try{
			userId=Integer.parseInt(_userId);
		}catch(Exception ex)
		{
			result.setStatus("error");
			result.setMessage("指定的用户的id格式不正确:"+_userId);
			return result;
		}
		Users findById = userService.findById(userId);
		if(findById==null)
		{
			result.setMessage(userId+"用户不存在");
			result.setStatus("error");
			return result;
		}
		RUserAwards latestByUserId = rUserAwardsService.getLatestByUserId(userId);
		if(latestByUserId!=null)
		{
			Date latestAddTime=new Date(latestByUserId.getAddTime());
			if(DateUtil.isToday(latestAddTime))
			{
				result.setStatus("success");
				result.setResult(latestByUserId);
				return result;
			}
		}
		result.setStatus("error");
		result.setMessage("今天还没有抽取");
		result.setResult(latestByUserId);
		return result;
	}
	@RequestMapping(value="/exchange", method=RequestMethod.GET)
	@ResponseBody
	public Result exchange(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		if(_loginInfo==null)
		{
			result.setMessage("请登陆后进行操作。");
			result.setStatus("error");
			return result;
		}
		UserInfo u=(UserInfo) _loginInfo;
		if(!u.getType().equals("manager"))
		{
			result.setMessage("请登陆后进行操作。");
			result.setStatus("error");
			return result;
		}
		ManagerUsers m_user=(ManagerUsers) u.getUsers();
		String tag=m_user.getTag();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		
		String user_name=params.get("user_name");
		String phone_num=params.get("phone_num");
		String serial_num=params.get("serial_num");
		String _exchange=params.get("exchange");
		boolean exchange=true;
		if(_exchange!=null&&_exchange.trim().toLowerCase().equals("false"))
		{
			exchange=false;
		}
		if(serial_num==null||serial_num.trim().length()<=0)
		{
			result.setMessage("获奖序列号不能为空");
			result.setStatus("error");
			return result;
		}
		Users user;
		if(user_name!=null&&user_name.trim().length()>0)
		{
			user = userService.findByName(user_name.trim());
		}
		else if(phone_num!=null&&phone_num.trim().length()>0)
		{
			user = userService.findByPhoneNum(phone_num);
		}
		else
		{
			result.setMessage("用户名或是手机号必须提供一个");
			result.setStatus("error");
			return result;
		}
		if(user==null)
		{
			result.setMessage("用户不存在");
			result.setStatus("error");
			return result;
		}
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("user_id", user.getId());
		m.put("serial_num", serial_num);
		
		  QueryResult<RUserAwards> findByParam = rUserAwardsService.findByParam(m, 0, 0);
		if(findByParam==null||findByParam.getTotal()==0)
		{
			result.setMessage("没有此中奖信息");
			result.setStatus("error");
			return result;
		}
		RUserAwards rUserAwards = findByParam.getResult().get(0);
		if(!tag.equals(rUserAwards.getTag()))
		{
			result.setMessage("没有权限兑换");
			result.setStatus("error");
			return result;
		}
		if(rUserAwards.getNo_awards().equals("true"))
		{
			result.setMessage("并没有中奖");
			result.setStatus("error");
			return result;
		}
		if(exchange)
		{
			if(rUserAwards==null||rUserAwards.getExchange().equals("true"))
			{
				result.setMessage("已经兑奖");
				result.setStatus("error");
				return result;
			}
		}
		else
		{
			if(rUserAwards==null||rUserAwards.getExchange().equals("false"))
			{
				result.setMessage("并没有兑奖");
				result.setStatus("error");
				return result;
			}
			
		}

		if(rUserAwards==null||rUserAwards.getExpirationTime()<=new Date().getTime())
		{
			result.setMessage("奖项已经过期");
			result.setStatus("error");
			return result;
		}
		rUserAwards.setExchange(exchange+"");
		rUserAwardsService.update(rUserAwards);
		result.setMessage("成功");
		result.setStatus("success");
		return result;
	}
	@RequestMapping(value="/draw_lottery", method=RequestMethod.GET)
	@ResponseBody
	public Result getRUsersAward(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		String oauth_token;
		Users user=null;
		if(_loginInfo==null||!((UserInfo) _loginInfo).getType().equals("user"))
		{
			oauth_token = params.get("oauth_token");
			if(oauth_token==null||oauth_token.trim().length()<=0)
			{
				result.setMessage("请登陆或者提供oauth_token进行操作。");
				result.setStatus("error");
				return result;
			}
			else
			{
				user = userService.findByOauthToken(oauth_token.trim());
				if(user==null)
				{
					result.setMessage("oauth_token：["+oauth_token+"]不合法");
					result.setStatus("error");
					return result;
				}
			}
			
		}
		else
		{
			user=(Users) ((UserInfo) _loginInfo).getUsers();
		}
		Integer user_id = user.getId();
		 String key = null;
		BaseSysInfo findByTypeAndKey = baseSysInfoService.findByTypeAndKey("sys", "m_awards_info");
		if(findByTypeAndKey==null||findByTypeAndKey.getValue()==null||findByTypeAndKey.getValue().trim().length()<=0)
		{
			result.setMessage("目前还没有奖品，还不能抽奖");
			result.setStatus("error");
			return result;
		}
		BaseSysInfo m_awards_open_info = baseSysInfoService.findByTypeAndKey("sys", "m_awards_open");
		if(m_awards_open_info==null||m_awards_open_info.getValue()==null||m_awards_open_info.getValue().trim().length()<=0||m_awards_open_info.getValue().trim().toLowerCase().equals("false"))
		{
			result.setMessage("抽奖功能还未开发,敬请期待！");
			result.setStatus("error");
			return result;
		}
		RUserAwards latestByUserId = rUserAwardsService.getLatestByUserId(user_id);
		if(latestByUserId!=null)
		{
			Date latestAddTime=new Date(latestByUserId.getAddTime());
			if(DateUtil.isToday(latestAddTime))
			{
				result.setMessage("您已经抽过奖,请明天再来");
				result.setStatus("error");
				return result;
			}
		}
		Map<String, Object> jsonToMap = (Map<String, Object>) GsonUtil.jsonToMap(findByTypeAndKey.getValue());
		List<Map> list_aws = (List<Map>) jsonToMap.get("awards");
//		List<Awards> list_aws = (List<Awards>) GsonUtil.jsonToList((String) jsonToMap.get("awards"));
		
		double ran_all=0;
		List<Awards> list_aw=new ArrayList<Awards>();
		for (int i=0;i<list_aws.size();i++) {
			Map awards_m=list_aws.get(i);
			awards_m.put("ran",Double.parseDouble(awards_m.get("ran").toString()));
			Awards awards = BeanUtils.transMap2Bean(awards_m, Awards.class);
			awards.setIndex(i);
			list_aw.add(awards);
			if(awards.getRan()<0)
				awards.setRan(0);
			ran_all+=awards.getRan();
		}
		double random = Math.random();
		double r=ran_all*random;
		Awards g_awards=null;
		System.out.println(ran_all);
		System.out.println(r);
		ran_all=0;
		for (Awards awards : list_aw) {
			if(awards.getRan()<0)
				awards.setRan(0);
			
			if(r>=ran_all&&r<(ran_all+awards.getRan()))
			{
				g_awards=awards;
				break;
			}
			ran_all+=awards.getRan();
		}
		RUserAwards rUserAwards=new RUserAwards();
		rUserAwards.setAwards(g_awards.getRes());
		if(g_awards.isNo_aw())
		{
			rUserAwards.setNo_awards("true");
		}
		else
		{
			rUserAwards.setNo_awards("false");
		}
		rUserAwards.setTag(g_awards.getTag());
		rUserAwards.setUserId(user_id);
		long time=24*60*90;//90天
		BaseSysInfo keepTimeInfo = baseSysInfoService.findByTypeAndKey("sys", "awards_keep_time");
		if(keepTimeInfo!=null&&keepTimeInfo.getValue()!=null&&keepTimeInfo.getValue().trim().length()>0)
		{
			try{
				time=Long.parseLong(keepTimeInfo.getValue().trim());
			}catch(Exception ex)
			{
				
			}
		}
		rUserAwards.setExchange("false");
		rUserAwards.setType("1");//用户抽奖
		rUserAwards.setExpirationTime(new Date().getTime()+time);
//		rUserAwards.setDeadTime();
		String serial=(DateUtil.date2Str(new Date(), "yyyyMMddHHmmss")+(Math.random()*100)).replaceAll("\\..*$", "");
		rUserAwards.setSerialNumber(serial);
		//RUserImages rUserImages=new RUserImages();
		RUserAwards save = rUserAwardsService.save(rUserAwards);
		if(save!=null)
		{
			result.setResult(g_awards);
			result.setStatus("success");
			return result;
		}else
		{
			result.setStatus("error");
			result.setMessage("保存失败");
			return result;
		}
		
		
	}
	@RequestMapping(value="/save_user_awards", method=RequestMethod.GET)
	@ResponseBody
	public Result saveRUsersAwards(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Object> result=new Result<Object>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		String _user_id = params.get("user_id");
		int user_id=0;
		String res = params.get("res");
		String _expiration_time = params.get("expiration_time");
		if(res==null||res.trim().length()<=0)
		{
			result.setMessage("参数:res不能为空");
			result.setStatus("error");
			return result;
		}
		if(_user_id==null||_user_id.trim().length()<=0)
		{
			result.setMessage("参数:user_id不能为空");
			result.setStatus("error");
			return result;
		}
		else 
		{
			try{
				user_id=Integer.parseInt(_user_id);
			}
			catch (Exception e) {
				result.setMessage("参数:user_id不正确，必须为正整数");
				result.setStatus("error");
				return result;
			}
			Users findById = userService.findById(user_id);
			if(findById==null)
			{
				result.setMessage("用户号为:["+user_id+"]的用户不存在");
				result.setStatus("error");
				return result;
			}
		}
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		String oauth_token;
		ManagerUsers user=null;
		if(_loginInfo==null||!((UserInfo) _loginInfo).getType().equals("manager"))
		{
			result.setMessage("对不起，您没有权限");
			result.setStatus("error");
			return result;
			
		}
		else
		{
			user=(ManagerUsers) ((UserInfo) _loginInfo).getUsers();
		}
//		Integer user_id = user.getId();
		String key = null;
		BaseSysInfo findByTypeAndKey = baseSysInfoService.findByTypeAndKey("sys", "m_awards_info");
		if(findByTypeAndKey==null||findByTypeAndKey.getValue()==null||findByTypeAndKey.getValue().trim().length()<=0)
		{
			result.setMessage("目前还没有奖品");
			result.setStatus("error");
			return result;
		}
		Map<String, Object> jsonToMap = (Map<String, Object>) GsonUtil.jsonToMap(findByTypeAndKey.getValue());
		List<Map> list_aws = (List<Map>) jsonToMap.get("awards");
//		List<Awards> list_aws = (List<Awards>) GsonUtil.jsonToList((String) jsonToMap.get("awards"));
		
		//double ran_all=0;
		List<Awards> list_aw=new ArrayList<Awards>();
		for (int i=0;i<list_aws.size();i++) {
			Map awards_m=list_aws.get(i);
			awards_m.put("ran",Double.parseDouble(awards_m.get("ran").toString()));
			Awards awards = BeanUtils.transMap2Bean(awards_m, Awards.class);
			awards.setIndex(i);
			list_aw.add(awards);
			if(awards.getRan()<0)
				awards.setRan(0);
			//ran_all+=awards.getRan();
		}
		
		Awards g_awards=null;
		for (Awards awards : list_aw) {
			if(awards.getRes().equals(res.trim()))
			{
//				if(awards.isNo_aw())
//				{
//					result.setMessage("目前还没有奖品，还不能抽奖");
//					result.setStatus("error");
//					return result;
//				}
				g_awards=awards;
				break;
			}
		}
		if(g_awards==null)
		{
			result.setMessage("奖项:["+res+"]不存在");
			result.setStatus("error");
			return result;
		}
		RUserAwards rUserAwards=new RUserAwards();
		rUserAwards.setAwards(g_awards.getRes());
		if(g_awards.isNo_aw())
		{
			rUserAwards.setNo_awards("true");
		}
		else
		{
			rUserAwards.setNo_awards("false");
		}
		rUserAwards.setUserId(user_id);
		long time=30*24*60*60*1000;//90天
		BaseSysInfo keepTimeInfo = baseSysInfoService.findByTypeAndKey("sys", "awards_keep_time");
		if(keepTimeInfo!=null&&keepTimeInfo.getValue()!=null&&keepTimeInfo.getValue().trim().length()>0)
		{
			try{
				time=Long.parseLong(keepTimeInfo.getValue().trim());
			}catch(Exception ex)
			{
				
			}
		}
		if(_expiration_time!=null&&_expiration_time.trim().length()>0)
		{
			try{
				time=Long.parseLong(_expiration_time);
			}catch(Exception ex)
			{
				result.setMessage("expiration_time:["+_expiration_time+"]参数错误。");
				result.setStatus("error");
				return result;
			}
		}
		rUserAwards.setExchange("false");
		rUserAwards.setType("2");//管理员发奖
		rUserAwards.setExpirationTime(new Date().getTime()+time);
		rUserAwards.setTag(g_awards.getTag());
//		rUserAwards.setDeadTime();
		String serial=(DateUtil.date2Str(new Date(), "yyyyMMddHHmmss")+(Math.random()*100)).replaceAll("\\..*$", "");
		rUserAwards.setSerialNumber(serial);
		//RUserImages rUserImages=new RUserImages();
		RUserAwards save = rUserAwardsService.save(rUserAwards);
		if(save!=null)
		{
			result.setResult(g_awards);
			result.setStatus("success");
			return result;
		}else
		{
			result.setStatus("error");
			result.setMessage("保存失败");
			return result;
		}
	}
	

}
