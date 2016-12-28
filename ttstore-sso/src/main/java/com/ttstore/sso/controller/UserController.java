
package com.ttstore.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ttstore.common.utils.CookieUtils;
import com.ttstore.sso.pojo.User;
import com.ttstore.sso.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @Title: register   
	 * @Description:  用户注册界面跳转
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register(){
		
		return "register";
	}
	
	/**
	 * 
	 * @Title: login   
	 * @Description: 登录
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
		
		return "login";
	}
	
	
	
	/**
	 * 
	 * @Title: check   
	 * @Description: 用户检查
	 * @param: @param param
	 * @param: @param type
	 * @param: @return      
	 * @return: ResponseEntity<Boolean>      
	 * @throws
	 */
	@RequestMapping(value="{param}/{type}",method=RequestMethod.GET)
	public ResponseEntity<Boolean> check(@PathVariable("param") String param,@PathVariable("type") Integer type){
		
		try {
			Boolean bool =  userService.check(param,type);
			if(bool == null){
				//参数错误
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
 			return ResponseEntity.ok(!bool);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 
	 * @Title: doRegister   
	 * @Description:  账号注册
	 * @param: @param user
	 * @param: @param bindingResult
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@RequestMapping(value="doRegister",method=RequestMethod.POST)
	@ResponseBody
/*	@responsebody表示该方法的返回结果直接写入HTTP response body中
*/
	public Map<String,Object> doRegister(@Valid User user,BindingResult bindingResult){//校验是否符合规则  并把对象封装进BindingResult
		
		Map<String,Object>  result = new HashMap<String, Object>();
		
		if(bindingResult.hasErrors()){
			//没有通过校验
			result.put("status", "400");
			List<String> errors = new ArrayList<String>();		
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				errors.add(objectError.getDefaultMessage());
			}
						
			result.put("data", "校验失败"+StringUtils.join(errors,"|"));
			return result;
 		}
		
		
		try {
			
			Boolean bool = userService.doRegister(user);
			
			if(bool){
				result.put("status", "200");
			}else{
				result.put("status", "500");
				result.put("data", "失败、、、、");

			}
			
			return result;

		} catch (Exception e) {
			result.put("status", "500");
			result.put("data", "失败、、、、");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @Title: doLogin   
	 * @Description: 用户登录逻辑
	 * @param: @param user
	 * @param: @param bindingResult
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@RequestMapping(value="doLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doLogin(User user,HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object>  result = new HashMap<String, Object>();

		try {
			String token =  userService.doLogin(user.getUsername(),user.getPassword());
 
			if(token==null){
				//登录失败
				result.put("status", 500);
				return result;
				
			}
			    //登录成功，保存token到cookie中
				result.put("status", "200");
								
				CookieUtils.setCookie(request, response, "TT_TOKEN", token);
				
		} catch (Exception e) {
			//登录失败
			result.put("status", 500);
			e.printStackTrace();
		}

		
		return result;
	}
	
	
	/**
	 * 
	 * @Title: queryUserByToken   
	 * @Description: 根据TOKEN 查询用户信息
	 * @param: @param token
	 * @param: @return      
	 * @return: ResponseEntity<User>      
	 * @throws
	 */
	@RequestMapping(value="{token}",method=RequestMethod.GET)
	public ResponseEntity<User> queryUserByToken(@PathVariable("token") String token){
		
		try {
			User user = userService.queryUserByToken(token);
			
			if(null==user){
				
				//资源不存在
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				
			}
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}

}
