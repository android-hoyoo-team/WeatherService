/**
 * 
 */
package org.cz.project.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * @ClassName: StaticResourceInterceptor
 * @Description: TODO()
 * @author Huang.Jilong
 * @date 2015-7-10 10:51:12
 * 
 */
public class StaticResourceInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof ResourceHttpRequestHandler)
		{
//			System.out.println("@ResourceHttpRequestHandler");
		}
		else if(handler instanceof HandlerMethod)
		{
//			System.out.println("@HandlerMethod");
		}
		else 
		{
//			System.out.println(handler.getClass().getName());
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