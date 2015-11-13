package org.cz.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cz.project.entity.bean.Result;
import org.cz.project.entity.bean.UserInfo;
import org.cz.project.entity.table.BaseSysInfo;
import org.cz.project.service.BaseSysInfoService;
import org.cz.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import per.cz.util.gson.GsonUtil;
import per.cz.util.http.HttpUtil;
@Controller
@RequestMapping("/apk")
public class ApkController {
	private String globalApkName="qingxinkongqi.apk";
	@Autowired UserService userService;
	@Autowired BaseSysInfoService baseSysInfoService;
	@RequestMapping(value="/version", method=RequestMethod.GET)
	@ResponseBody
	public Map login(HttpServletRequest request,HttpServletResponse response){
		BaseSysInfo baseSysInfo = baseSysInfoService.findByTypeAndKey("sys", "m_apk_version");
//		List res=new ArrayList<Map>();
		Map m=null;
		if(baseSysInfo==null)
		{
			m=new HashMap<String, Object>();
			m.put("version", "2.0.0.1");
//			res.add(m);	
		}
		else
		{
			String value = baseSysInfo.getValue();
			m = (Map<String, Object>) GsonUtil.jsonToMap(value);
		}
		System.out.println("User-Agent:\t"+request.getHeader("User-Agent"));
//		m.put("user-agent", request.getHeader("User-Agent"));
		return m;	
	}
	@RequestMapping(value="/get_latest_apk", method={RequestMethod.GET})
	@ResponseBody
	public Result getAPK(HttpServletRequest request,HttpServletResponse response){
		Result res=new Result<String>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			String remoteAddr = request.getRemoteAddr();
			String realPath = request.getSession().getServletContext().getRealPath("/");
			File fileOut = new File(realPath+ "project"+File.separator+"apk"+File.separator+globalApkName);
			//判断文件是否存在如果不存在就返回默认图标
			if(!(fileOut.exists() && fileOut.canRead())) {
				res.setMessage("apk不存在");
				res.setStatus("error");
				return res;
			}
			response.setHeader("content-disposition","attachment;filename="+globalApkName);
			response.setContentType("application/vnd.android.package-archive");
			response.setHeader("Content-Length", fileOut.length()+"");
			//request.getRequestDispatcher("/project/apk/"+globalApkName).forward(request, response);
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
			stream.flush();
			stream.close();
		} catch (Exception e) {
			res.setMessage(e.getMessage());
			res.setStatus("error");
			return res;
		}
		res.setStatus("success");
		return res;
	}
	@RequestMapping(value="/apk_upload", method={RequestMethod.POST})
	@ResponseBody
	public Result FileUpload(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result=new Result();
		String pathTag="";
		List<Object> list=new ArrayList<Object>();
		HttpSession session = request.getSession();
		Object _loginInfo = session.getAttribute("user_info");
		if(_loginInfo==null)
		{
			result.setMessage("没用权限，请登陆");
			result.setStatus("error");
			return result;
		}
		else
		{
			UserInfo u=(UserInfo) _loginInfo;
			if(u.getType().equals("user"))
			{
				result.setMessage("没用权限，请上传");
				result.setStatus("error");
				return result;
			}
			else if(u.getType().equals("manager"))
			{
				pathTag="apk";
			}
		}
		Map<String, String> param = HttpUtil.getParameters(request, "utf-8");
		String _code = param.get("code"); 
		int[] code=new int[4];
		if(_code==null||_code.trim().length()<=0)
		{
			result.setMessage("apk的版本名称或版本号不能为空");
			result.setStatus("error");
			return result;
		}
		try{
			String[] split = _code.trim().split("\\.");
			if(split==null||split.length!=4)
			{
				result.setMessage("apk的版本号:["+_code+"]格式错误");
				result.setStatus("error");
				return result;
			}
			for(int i=0;i<split.length;i++)
			{
				code[i]=Integer.parseInt(split[0]);
			}
		}catch(Exception ex){
			result.setMessage("apk的版本号:["+_code+"]格式错误");
			result.setStatus("error");
			return result;
		}
		BaseSysInfo baseSysInfo = baseSysInfoService.findByTypeAndKey("sys", "m_apk_version");
		if(baseSysInfo!=null&&baseSysInfo.getValue()!=null&&baseSysInfo.getValue().trim().length()>0)
		{
			String value = baseSysInfo.getValue();
			Map<String, Object> jsonToMap = (Map<String, Object>) GsonUtil.jsonToMap(value);
			if(jsonToMap!=null)
			{
//				Object verName = jsonToMap.get("verName");
				Object verCode = jsonToMap.get("version");
				if(verCode!=null||verCode.toString().trim().length()>0)
				{
					String[] split = verCode.toString().trim().split("\\.");
					int[] oldCode=new int[4];
					for(int i=0;i<split.length;i++)
					{
						oldCode[i]=Integer.parseInt(split[0]);
					}
					//float oldCode=Float.parseFloat(verCode.toString().trim());
					if(checkVersionCode(code,oldCode)<0)
					{
						result.setMessage("apk当前版本"+Arrays.toString(oldCode)+"比新上传的版本"+Arrays.toString(code)+"还要高");
						result.setStatus("error");
						return result;
					}
				}
			}
		}
		else
		{
			baseSysInfo=new BaseSysInfo();
			baseSysInfo.setKey("m_apk_version");
			baseSysInfo.setType("sys");
		}
		Map m=new HashMap<String, Object>();
		m.put("version", _code);
		list.add(m);	
		baseSysInfo.setValue(GsonUtil.objectToJson(m));
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
			String file_name=file_path+File.separator+globalApkName;
			String re_file_name="project/"+pathTag+"/"+globalApkName;
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
		baseSysInfoService.saveOrUpdate(baseSysInfo);
		result.setStatus("success");
		result.setResult(list);
		return result;
	}
	/**
	 * 新版-旧版
	 * @param currentVerCode2
	 * @param newVerCode2
	 * @return
	 */
	private int checkVersionCode(int[] currentVerCode2, int[] newVerCode2) {
		int currentVer=0;
		int newVer=0;
		for(int i=0;i<currentVerCode2.length;i++)
		{
			currentVer= (currentVerCode2[i]*((int)Math.pow(10,i)));
			newVer= (newVerCode2[i]*((int)Math.pow(10,i)));
		}
		return newVer-currentVer;
	}
}
