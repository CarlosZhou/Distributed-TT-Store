package com.ttstore.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.common.service.RedisService;
import com.ttstore.sso.mapper.UserMapper;
import com.ttstore.sso.pojo.User;

@Service
public class UserService {

	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Integer REDIS_TIME = 60*30;
	
	/**
	 * 
	 * @Title: check   
	 * @Description: 验证账号信息
	 * @param: @param param
	 * @param: @param type
	 * @param: @return      
	 * @return: Boolean      
	 * @throws
	 */
	public Boolean check(String param, Integer type) {
		
		User record = new User();
		switch (type) {
		case 1:
			record.setUsername(param);
			break;
		case 2:
			record.setPhone(param);

			break;
		case 3:
			record.setEmail(param);

			break;
		default:
			//参数错误
			return null;
		}
 
 
		return userMapper.selectOne(record) == null;
		
	}

	/**
	 * 
	 * @Title: doRegister   
	 * @Description: 注册
	 * @param: @param user
	 * @param: @return      
	 * @return: Boolean      
	 * @throws
	 */
	public Boolean doRegister(User user) {
		
		user.setId(null);
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
	
		//密码加密处理  MD5加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		return userMapper.insert(user)==1;
		
 	}

		/**
		 * 
		 * @Title: doLogin   
		 * @Description: 登录
		 * @param: @param username
		 * @param: @param password
		 * @param: @return      
		 * @return: String      
		 * @throws
		 */
		public String doLogin(String username, String password) throws Exception {
			User record = new User();
			record.setUsername(username);
			User user =  userMapper.selectOne(record);
			
			if(null==user){
				//用户不存在
				return null;
			}
			
			if(!StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())){
				//密码错误
				return null;
			}
			
				//登录成功，将用户的信息保存到redis中
			String token = DigestUtils.md5Hex(username+System.currentTimeMillis());
			
			//抹掉密码或者字段上面加上 @JsonIgnore
			user.setPassword(null);
			redisService.set("TOKEN_"+token, objectMapper.writeValueAsString(user), REDIS_TIME);
			
			return token;
		}

}
