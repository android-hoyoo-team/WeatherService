/**
 * 
 */
package org.cz.project.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import per.cz.util.http.AjaxUtils;

/**
 * @ClassName: AdminPrivilegeInterceptor
 * @Description: TODO()
 * @author Huang.Jilong
 * @date 2015-7-10 10:51:12
 * 
 */
public class AdminPrivilegeInterceptor extends HandlerInterceptorAdapter {
	//private static Map<String,String> forbidMap=new HashMap<String,String>();
	private static List<String> forbidList=new ArrayList<String>();
	static{
		//forbidList.add("/save_user");
		forbidList.add("/file_upload");//get\post\..
		forbidList.add("/save_user_images");
		forbidList.add("/update_user_images_status");
		//forbidList.add("/save_user");
		//forbidList.add("/admin/save_user");
		//forbidList.add("/admin/login");
		forbidList.add("/save_sys_key_value");
		forbidList.add("/get_all_users");
		//forbidList.add("/save_user_awards");//user登录
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String loginUrl="/login";
		String servletPath = request.getServletPath();
//		System.out.println("*****AdminPrivilegeInterceptor*****");
		if(handler instanceof ResourceHttpRequestHandler)//静态资源放行
			return true;
		if(servletPath.startsWith(loginUrl)) {  
			return true;
		}
		boolean forbidTag=false;
		for (String f : forbidList) {
			if(f.equals(servletPath))
			{
				forbidTag=true;
				break;
			}
		}
		if(forbidTag)
		{
			HttpSession session = request.getSession();
			Object _loginInfo = session.getAttribute("user_info");
			if(_loginInfo==null)
			{
				if(AjaxUtils.isAjaxRequest(request))
				{
					request.getRequestDispatcher("/error").forward(request, response);
					return false;
				}
				else
				{
					request.getRequestDispatcher("/error").forward(request, response);
					//response.sendRedirect("/page/login.jsp");
					return false;
				}
				/*尽量通过重定向来实现*/
//			request.getRequestDispatcher("/page/login.jsp").forward(request, response);
//			return false;
			}
		}
		return true;// 继续流程
	}


	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
					throws Exception {
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}