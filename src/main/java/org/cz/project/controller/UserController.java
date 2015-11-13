package org.cz.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cz.project.dao.QueryResult;
import org.cz.project.entity.bean.Result;
import org.cz.project.entity.bean.UserInfo;
import org.cz.project.entity.table.BaseSysInfo;
import org.cz.project.entity.table.RUserAwards;
import org.cz.project.entity.table.RUserImages;
import org.cz.project.entity.table.Users;
import org.cz.project.entity.table.UsersLoginRecords;
import org.cz.project.service.BaseSysInfoService;
import org.cz.project.service.RUserAwardsService;
import org.cz.project.service.RUsersImagesService;
import org.cz.project.service.UserLoginRecordsService;
import org.cz.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import per.cz.util.DateUtil;
import per.cz.util.encryption.EncryptionUtil;
import per.cz.util.http.HttpUtil;
@Controller
public class UserController {
	@Autowired UserService userService;
	@Autowired RUsersImagesService rUsersImagesService; 
//	@Autowired RUserAwardsService rUserAwardsService; 
	@Autowired BaseSysInfoService baseSysInfoService;
	@Autowired UserLoginRecordsService userLoginRecordsService;
	@RequestMapping(value="/get_all_users", method=RequestMethod.GET)
	@ResponseBody
	public Result findAllUsers(HttpServletRequest request,HttpServletResponse response){
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
		QueryResult<Users> findAll = userService.findAll(page, size,sort,order);
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
	@RequestMapping(value="/update_user_status", method=RequestMethod.GET)
	@ResponseBody
	public Result updateUserStatus(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _user_id = params.get("user_id");
		String _status=params.get("status");
		if(_user_id==null||_user_id.trim().length()<=0)
		{
			result.setMessage("user_id不能为空");
			result.setStatus("error");
			return result;
		}
		if(_status==null||_status.trim().length()<=0)
		{
			_status="0";
		}
		int user_id=0;
		int status=0;
		try{
			user_id=Integer.parseInt(_user_id);
			status=Integer.parseInt(_status);
		}catch(Exception e)
		{
			result.setMessage("user_id或status格式错误,必须为数字");
			result.setStatus("error");
			return result;
		}
		Users findById = userService.findById(user_id);
		if(findById==null)
		{
			result.setMessage("用户不存在");
			result.setStatus("error");
			return result;
		}
		userService.updataStatus(findById, status);
		result.setStatus("success");
		return result;
	}
	@RequestMapping(value="/get_user_images", method=RequestMethod.GET)
	@ResponseBody
	public Result getRUsersImages(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _page=params.get("page");
		String _size=params.get("size");
		//null,illegal,all
		String _status=params.get("status");
		String _user_id=params.get("user_id");
		int user_id=0;
		if(_status==null||_status.trim().length()<=0)
		{
			_status="null";
		}
		if(_status.trim().equals("all"))
		{
			HttpSession session = request.getSession();
			Object _loginInfo = session.getAttribute("user_info");
			if(_loginInfo==null)
			{
				result.setMessage("没用权限访问");
				result.setStatus("error");
				return result;
			}
			else
			{
				UserInfo u=(UserInfo) _loginInfo;
				if(u.getType().equals("user"))
				{
					result.setMessage("没用权限访问");
					result.setStatus("error");
					return result;
				}
				else if(u.getType().equals("manager"))
				{
					
				}
			}
		}
		if(_user_id==null||_user_id.trim().length()<=0)
		{
			_user_id="0";
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
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("status", _status);
			if(user_id>0)
				param.put("user_id", user_id);
			QueryResult<Map> findByParam = rUsersImagesService.findByParam4Page(param, page, size);
			if(findByParam!=null)
			{
				result.setStatus("success");
				result.setResult(findByParam);
				return result;
			}
			result.setStatus("error");
			result.setMessage("没有获取到用户图片信息");
			return result;
		}catch(Exception e)
		{
			result.setStatus("error");
			result.setMessage("获取用户图片信息错误:"+e.getMessage());
			return result;
		}
	}
	
	@RequestMapping(value="/save_user_images", method=RequestMethod.GET)
	@ResponseBody
	public Result saveRUsersImages(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _comment=params.get("comment");
		String _url=params.get("url");
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
		if(_comment==null||_url==null||_comment.trim().length()<=0||_url.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("图片或是评论不能为空");
			return result;
		}
		Users findById = userService.findById(userId);
		if(findById==null)
		{
			result.setMessage(userId+"用户不存在");
			result.setStatus("error");
			return result;
		}
		RUserImages rUserImages=new RUserImages();
		rUserImages.setAddress(params.get("address"));
		String _lon=params.get("longitude");
		String _lat=params.get("latitude");
		Float lat=null;
		Float lon=null;
		if(_lat!=null&&_lat.trim().length()>=0)
		{
			try{
				lat=Float.parseFloat(_lat.trim());
			}catch(Exception ex)
			{
				result.setMessage("latitude格式不正确["+_lat+"]");
				result.setStatus("error");
				return result;
			}
		}
		if(_lon!=null&&_lon.trim().length()>=0)
		{
			try{
				lon=Float.parseFloat(_lon.trim());
			}catch(Exception ex)
			{
				result.setMessage("longitude格式不正确["+_lon+"]");
				result.setStatus("error");
				return result;
			}
		}
		rUserImages.setLatitude(lat);
		rUserImages.setLongitude(lon);
		rUserImages.setUrl(_url);
		rUserImages.setComment(_comment);
		rUserImages.setUserId(userId);
		
		Map<String,Object> param = new HashMap<String,Object>();
		RUserImages save = rUsersImagesService.save(rUserImages);
		if(save!=null)
		{
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
	@RequestMapping(value="/update_user_images_status", method=RequestMethod.GET)
	@ResponseBody
	public Result updateRUsersImagesStatus(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		
		String _id=params.get("id");
		int id=0;
		String _status=params.get("status");
		if(_id==null||_id.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("需要指定要修改的id");
			return result;
		}
		if(_status==null||_status.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("需要指定修改的状态");
			return result;
		}
		try{
			id=Integer.parseInt(_id);
		}catch(Exception ex)
		{
			result.setStatus("error");
			result.setMessage("指定的id:["+_id+"]格式错误");
			return result;
		}
		RUserImages findById = rUsersImagesService.findById(id);
		if(findById==null)
		{
			result.setMessage(_id+"发布资源不存在");
			result.setStatus("error");
			return result;
		}
		if(_status.trim().toLowerCase().equals("null"))
		{
			findById.setStatus(null);
		}
		if(_status.trim().toLowerCase().equals("illegal"))
		{
			findById.setStatus("illegal");
		}
		try{
			rUsersImagesService.update(findById);
		}catch(Exception ex)
		{
			result.setStatus("error");
			result.setMessage("修改失败");
			return result;
		}
		result.setResult(findById);
		result.setStatus("success");
		return result;
	}
	@RequestMapping(value="/save_user", method=RequestMethod.GET)
	@ResponseBody
	public Result saveUser(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		Result<Object> result=new Result<Object>();
		String _name=params.get("name");
		String _password=params.get("password");
		String _phone_num=params.get("phone_num");
		if(_name==null||_password==null||_name.trim().length()<=0||_password.trim().length()<=0)
		{
			result.setStatus("error");
			result.setMessage("用户名或者密码不能为空");
			return result;
		}
		if(_phone_num==null||_phone_num==null)
		{
			result.setStatus("error");
			result.setMessage("号码不能为空");
			return result;
		}
		if(!_phone_num.matches("1[0-9]{10}"))
		{
			result.setStatus("error");
			result.setMessage("不是正确的11位号码");
			return result;
		}
		if(_name.length()<1)
		{
			result.setStatus("error");
			result.setMessage("用户名不能为空");
			return result;
		}
		if(_password.length()<6)
		{
			result.setStatus("error");
			result.setMessage("密码不能少于6位");
			return result;
		}
		Users findByName = userService.findByName(_name);
		if(findByName!=null)
		{
			result.setMessage(_name+"用户已经存在");
			result.setStatus("error");
			return result;
		}
		Users findByPhoneNum = userService.findByPhoneNum(_phone_num.trim());
		if(findByPhoneNum!=null)
		{
			result.setMessage(_phone_num+"号码已经被占用");
			result.setStatus("error");
			return result;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		Users u=new Users();
		u.setName(_name);
		u.setPassword(_password);
		u.setPhoneNum(_phone_num);
		u.setAddTime(new Date().getTime());
		Users save = userService.save(u);
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
	@RequestMapping(value="/login_out", method=RequestMethod.GET)
	@ResponseBody
	public Result loginOut(HttpServletRequest request,HttpServletResponse response){
		Result result=new Result();
		List<String> list=new ArrayList<String>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		String oauth_token;
		Users user=null;
		if(_loginInfo==null||(!((UserInfo) _loginInfo).getType().equals("user")&&!((UserInfo) _loginInfo).getType().equals("manager")))
		{
			
			oauth_token = params.get("oauth_token");
			if(oauth_token==null||oauth_token.trim().length()<=0)
			{
				result.setMessage("请提供oauth_token进行操作。");
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
		else{
			UserInfo u=(UserInfo) _loginInfo;
			if(u.getType().equals("user"))
			{
				user=(Users)u.getUsers();
				//pathTag="upload_imgs";
			}
			else if(u.getType().equals("manager"))
			{
				result.setMessage("管理员暂时不需要退出");
				result.setStatus("error");
				return result;
				//pathTag="manager_upload_imgs";
			}
		}
		
		
		
		UsersLoginRecords ulr=new UsersLoginRecords();
//		ulr.setAddTime(new Date().getTime());
		ulr.setTag("login_out");
		ulr.setAddress(params.get("address"));
		String _lon=params.get("longitude");
		String _lat=params.get("latitude");
		Float lat=null;
		Float lon=null;
		if(_lat!=null&&_lat.trim().length()>=0)
		{
			try{
				lat=Float.parseFloat(_lat.trim());
			}catch(Exception ex)
			{
				result.setMessage("latitude格式不正确["+_lat+"]");
				result.setStatus("error");
				return result;
			}
		}
		if(_lon!=null&&_lon.trim().length()>=0)
		{
			try{
				lon=Float.parseFloat(_lon.trim());
			}catch(Exception ex)
			{
				result.setMessage("longitude格式不正确["+_lon+"]");
				result.setStatus("error");
				return result;
			}
		}
		user.setOauthToken(null);
		userService.update(user);
		ulr.setLatitude(lat);
		ulr.setLongitude(lon);
		ulr.setLoginTime(new Date().getTime()-DateUtil.getDate4long(new Date()));
		ulr.setUserId(user.getId());
		ulr.setLoginDate(DateUtil.getDate4long(new Date()));
		UsersLoginRecords save = userLoginRecordsService.save(ulr);
		result.setStatus("success");
		return result;		
	}
	@RequestMapping(value="/sign", method=RequestMethod.GET)
	@ResponseBody
	public Result sign(HttpServletRequest request,HttpServletResponse response){
		Result result=new Result();
		List<String> list=new ArrayList<String>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		String oauth_token;
		Users user=null;
		if(_loginInfo==null||(!((UserInfo) _loginInfo).getType().equals("user")&&!((UserInfo) _loginInfo).getType().equals("manager")))
		{
			
			oauth_token = params.get("oauth_token");
			if(oauth_token==null||oauth_token.trim().length()<=0)
			{
				result.setMessage("请提供oauth_token进行操作。");
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
		else{
			UserInfo u=(UserInfo) _loginInfo;
			if(u.getType().equals("user"))
			{
				user=(Users)u.getUsers();
				//pathTag="upload_imgs";
			}
			else if(u.getType().equals("manager"))
			{
				result.setMessage("管理员暂时不需要签到");
				result.setStatus("error");
				return result;
				//pathTag="manager_upload_imgs";
			}
		}
		
		
		
		UsersLoginRecords ulr=new UsersLoginRecords();
//		ulr.setAddTime(new Date().getTime());
		ulr.setTag("login");
		ulr.setAddress(params.get("address"));
		String _lon=params.get("longitude");
		String _lat=params.get("latitude");
		Float lat=null;
		Float lon=null;
		if(_lat!=null&&_lat.trim().length()>=0)
		{
			try{
				lat=Float.parseFloat(_lat.trim());
			}catch(Exception ex)
			{
				result.setMessage("latitude格式不正确["+_lat+"]");
				result.setStatus("error");
				return result;
			}
		}
		if(_lon!=null&&_lon.trim().length()>=0)
		{
			try{
				lon=Float.parseFloat(_lon.trim());
			}catch(Exception ex)
			{
				result.setMessage("longitude格式不正确["+_lon+"]");
				result.setStatus("error");
				return result;
			}
		}
		ulr.setLatitude(lat);
		ulr.setLongitude(lon);
		ulr.setLoginTime(new Date().getTime()-DateUtil.getDate4long(new Date()));
		ulr.setUserId(user.getId());
		ulr.setLoginDate(DateUtil.getDate4long(new Date()));
		UsersLoginRecords save = userLoginRecordsService.save(ulr);
		result.setStatus("success");
		return result;
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
		Users findByName = userService.findByName(_name,_password);
		if(findByName==null)
		{
			result.setMessage("用户名或密码错误");
			result.setStatus("error");
			return result;
		}
		if(findByName.getStatus()!=0)
		{
			result.setMessage("用户账户已经被禁用，请联系管理员");
			result.setStatus("error");
			return result;
		}
		HttpSession session = request.getSession();
		UserInfo<Users> userInfo = new UserInfo<Users>("user",findByName);
		String k=new Date().getTime()+""+findByName.getName();
		findByName.setOauthToken(EncryptionUtil.getMD5Base64(k.getBytes()));
		findByName.setTotalLoginTimes(findByName.getTotalLoginTimes()==null?1:findByName.getTotalLoginTimes()+1);
		UsersLoginRecords findLastYesterdayByUserId = userLoginRecordsService.findLastYesterdayByUserId(findByName.getId());
		UsersLoginRecords findLastTodayByUserId = userLoginRecordsService.findLastTodayByUserId(findByName.getId());
		if(findLastTodayByUserId==null)
		{
			if(findLastYesterdayByUserId==null)
			{
				findByName.setPersistLoginTimes(1);
			}
			else
			{
				findByName.setPersistLoginTimes(findByName.getPersistLoginTimes()==null?1:findByName.getPersistLoginTimes()+1);
			}
		}
		
		userService.update(findByName);
		
		UsersLoginRecords ulr=new UsersLoginRecords();
//		ulr.setAddTime(new Date().getTime());
		ulr.setTag("login");
		ulr.setAddress(params.get("address"));
		String _lon=params.get("longitude");
		String _lat=params.get("latitude");
		Float lat=null;
		Float lon=null;
		if(_lat!=null&&_lat.trim().length()>=0)
		{
			try{
				lat=Float.parseFloat(_lat.trim());
			}catch(Exception ex)
			{
				result.setMessage("latitude格式不正确["+_lat+"]");
				result.setStatus("error");
				return result;
			}
		}
		if(_lon!=null&&_lon.trim().length()>=0)
		{
			try{
				lon=Float.parseFloat(_lon.trim());
			}catch(Exception ex)
			{
				result.setMessage("longitude格式不正确["+_lon+"]");
				result.setStatus("error");
				return result;
			}
		}
		ulr.setLatitude(lat);
		ulr.setLongitude(lon);
		ulr.setLoginTime(new Date().getTime()-DateUtil.getDate4long(new Date()));
		ulr.setUserId(findByName.getId());
		ulr.setLoginDate(DateUtil.getDate4long(new Date()));
		UsersLoginRecords save = userLoginRecordsService.save(ulr);
		
		session.setAttribute("user_info", userInfo);
		findByName.setPassword(null);
		result.setResult(findByName);
		result.setMessage("登录成功");
		result.setStatus("success");
		return result;		
	}
	private String defaultFile="NA.png";
	@RequestMapping(value="/project/{pathTag}/{file:.*}", method={RequestMethod.GET})
	@ResponseBody
	public Result getFile(HttpServletRequest request,HttpServletResponse response,@PathVariable("pathTag") String pathTag,@PathVariable("file") String file){
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
			Result<Object> result=new Result<Object>();
			String file_q = params.get("file");
			if(!StringUtils.isEmpty(file_q))
				file=file_q;
			if(StringUtils.isEmpty(file)) {
				file = defaultFile;
			}
			System.out.println(file);
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String fileName = realPath
					+ "project"+File.separator+pathTag+File.separator
					+ file.toUpperCase().trim();

			File fileOut = new File(fileName);

			//判断文件是否存在如果不存在就返回默认图标
			if(!(fileOut.exists() && fileOut.canRead())) {
				fileOut = new File(realPath	+ "project"+File.separator+"upload_imgs"+File.separator +defaultFile);
			}
			BufferedInputStream bfi=new BufferedInputStream(new FileInputStream(fileOut));
//			FileInputStream inputStream= new FileInputStream(fileOut);
			OutputStream stream = response.getOutputStream();
			byte[] data = new byte[4096];
			int l=0;
			while((l=bfi.read(data))>0)
			{
				stream.write(data, 0, l);
			}
			bfi.close();
			response.setContentType("image/png");
			stream.flush();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/file_upload", method={RequestMethod.POST})
	@ResponseBody
	public Result FileUpload(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result=new Result();
		String pathTag="";
		List<String> list=new ArrayList<String>();
		Map<String, String> params = HttpUtil.getParameters(request, "utf-8");
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		String oauth_token;
		Users user=null;
		if(_loginInfo==null||(!((UserInfo) _loginInfo).getType().equals("user")&&!((UserInfo) _loginInfo).getType().equals("manager")))
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
				}else
				{
					pathTag="upload_imgs";
				}
			}
		}
		else{
			UserInfo u=(UserInfo) _loginInfo;
			if(u.getType().equals("user"))
			{
				pathTag="upload_imgs";
			}
			else if(u.getType().equals("manager"))
			{
				pathTag="manager_upload_imgs";
			}
		}
		String realPath = request.getSession().getServletContext().getRealPath("/");
		Map<String, MultipartFile> fileMap = ((DefaultMultipartHttpServletRequest)request).getFileMap();
		Iterator<Entry<String, MultipartFile>> iterator = fileMap.entrySet().iterator();
		for (; iterator.hasNext(); ) {
			MultipartFile file = iterator.next().getValue();
			String name = file.getName();
			String file_path="project"+File.separator+pathTag;
			File file2 = new File(realPath+File.separator+file_path);
			if(!file2.isDirectory()){
				file2.mkdirs();
			}
			String file_name=file_path+File.separator+DateUtil.date2Str(new Date(),"yyyy-MM-dd_HH-mm-ss")+".jpg";
			String re_file_name="project/"+pathTag+"/"+DateUtil.date2Str(new Date(),"yyyy-MM-dd_HH-mm-ss")+".jpg";
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(new File(realPath+File.separator+file_name)));
					stream.write(bytes);
					stream.close();
					list.add(re_file_name);
				} catch (Exception e) {
					result.setMessage(e.getMessage());
					result.setStatus("error");
					return result;
				}
			} else {
				result.setMessage("You failed to upload " + name + " because the file was empty.");
				result.setStatus("error");
				return result;
			}
		}
		//		RUserImages rUserImages = new RUserImages();
		//		rUserImages.s
		//		rUsersImagesService.save(u)
		result.setStatus("success");
		result.setResult(list);
		return result;
	}

}
