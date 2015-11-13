/**
 * 
 */
package org.cz.project.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: GlobalInterceptor
 * @Description: TODO()
 * @author Huang.Jilong
 * @date 2013-11-12 10:51:12
 * 
 */
//@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {
//	@Autowired
//	private ICookieService cookieService;
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
			"StopWatch-StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		response.setHeader("Access-Control-Allow-Origin", "*");
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		System.out.println("QueryString:"+request.getQueryString());
		System.out.println("URI:"+request.getRequestURI());
		System.out.println("ContextPath:"+request.getContextPath());
		System.out.println("ServletPath:"+request.getServletPath());
		System.out.println("PathInfo:"+request.getPathInfo());
		
		return true;// 继续流程
	}

	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		//response.setHeader("Access-Control-Allow-Origin", "*");
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		System.out.println(String.format("%s consume %d millis",request.getRequestURI(), consumeTime));
		if (consumeTime > 500) {// 此处认为处理时间超过500毫秒的请求为慢请求
			// TODO 记录到日志文件
//			System.out.println(String.format("%s consume %d millis",
//					request.getRequestURI(), consumeTime));
		}
	}
}