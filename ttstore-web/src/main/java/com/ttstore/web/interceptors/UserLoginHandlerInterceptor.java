package com.ttstore.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ttstore.common.utils.CookieUtils;
import com.ttstore.web.bean.User;
import com.ttstore.web.service.UserService;

/**
 * 
 * @ClassName:  UserLoginHandlerInterceptor   
 * @Description:登录验证拦截  拦截器需要在Spring mvc配置文件中配置（ttstore-web-servlet.xml）
 * @author: carlos-y
 * @date:   2017年1月1日 下午3:01:13   
 * @Copyright: 2017 carlos-y. All rights reserved.
 */
public class UserLoginHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userSerivce;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if(token==null){
			//未登录 重定向到登录界面
			response.sendRedirect("http://127.0.0.1:8083/user/login.html");
			return false;
		}
			
			//再判断登录是否超时
	   User user	= userSerivce.queryByToekn(token);
	   if(null== user){
		   //登录超时
			response.sendRedirect("http://127.0.0.1:8083/user/login.html");

	   }
		
	   //登录成功
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
