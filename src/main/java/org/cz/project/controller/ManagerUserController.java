package org.cz.project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cz.project.entity.bean.Result;
import org.cz.project.entity.bean.UserInfo;
import org.cz.project.entity.table.ManagerUsers;
import org.cz.project.entity.table.Users;
import org.cz.project.service.ManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.cz.util.http.HttpUtil;
@Controller
@RequestMapping("/admin")
public class ManagerUserController {
	@Autowired ManagerUserService managerUserService;

	@RequestMapping(value="/save_user", method=RequestMethod.GET)
	@ResponseBody
	public Result saveUser(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _name=params.get("name");
		String _password=params.get("password");
		if(_name==null||_password==null||_name.trim().length()<=0||_password.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("用户名或者密码不能为空");
			return result;
		}
		ManagerUsers findByName = managerUserService.findByName(_name);
		if(findByName!=null)
		{
			result.setMessage(_name+"用户已经存在");
			result.setStatus("error");
			return result;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		ManagerUsers u=new ManagerUsers();
		u.setName(_name);
		u.setPassword(_password);
		u.setAddTime(new Date().getTime());
		ManagerUsers save = managerUserService.save(u);
		if(save!=null)
		{
			save.setPassword(null);
			result.setResult(save);
			result.setStatus("success");
			return result;
		}else
		{
			result.setStatus("error");
			result.setMessage("保存失败");
			return result;
		}
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	@ResponseBody
	public Result login(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _name=params.get("name");
		String _password=params.get("password");
		if(_name==null||_password==null||_name.trim().length()<=0||_password.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("用户名或者密码不能为空");
			return result;
		}
		ManagerUsers findByName = managerUserService.findByName(_name,_password);
		if(findByName==null)
		{
			result.setMessage("用户名或密码错误");
			result.setStatus("error");
			return result;
		}
		HttpSession session = request.getSession();
		UserInfo<ManagerUsers> userInfo = new UserInfo<ManagerUsers>("manager",findByName);
		session.setAttribute("user_info", userInfo);
		findByName.setPassword(null);
		result.setResult(findByName);
		result.setMessage("登录成功");
		result.setStatus("success");
		return result;		
	}
	@RequestMapping(value="/login_out", method=RequestMethod.GET)
	@ResponseBody
	public Result loginOut(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		HttpSession session = request.getSession();
		session.removeAttribute("user_info");
		result.setMessage("登出成功");
		result.setStatus("success");
		return result;		
	}
	@RequestMapping(value="/change_password", method=RequestMethod.GET)
	@ResponseBody
	public Result changePassword(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _old_password=params.get("old_password");
		String _password=params.get("password");
		if(_old_password==null||_password==null||_old_password.trim().length()<=0||_password.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("用户名或者密码不能为空");
			return result;
		}
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
		ManagerUsers findByName = managerUserService.findByName(m_user.getName(),_old_password);
		if(findByName==null)
		{
			result.setMessage("原始密码密码错误");
			result.setStatus("error");
			return result;
		}
		findByName.setPassword(_password);
		managerUserService.update(findByName);
		UserInfo<ManagerUsers> userInfo = new UserInfo<ManagerUsers>("manager",findByName);
		session.setAttribute("user_info", userInfo);
		findByName.setPassword(null);
		result.setResult(findByName);
		result.setMessage("密码修改成功");
		result.setStatus("success");
		return result;		
	}
}
