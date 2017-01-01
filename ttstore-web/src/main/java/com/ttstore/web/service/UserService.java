package com.ttstore.web.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttstore.common.service.ApiService;
import com.ttstore.web.bean.User;

@Service
public class UserService {

	@Autowired
	private ApiService apiService;
 
	private static final ObjectMapper objectMapper = new ObjectMapper();

	
	/**
	 * 
	 * 
	 * @Title: queryByToekn   
	 * @Description: 根据token查询用户信息
	 * @param: @param token
	 * @param: @return      
	 * @return: User      
	 * @throws
	 */
	public User queryByToekn(String token){
	
		//查询sso系统提供的对外接口查询对应的用户
		String url = "http://127.0.0.1:8083/service/user/"+token;
		
		try {
			String jsonData = apiService.doGet(url);
			if(StringUtils.isEmpty(jsonData)){
				return null;
			}
			
			return objectMapper.readValue(jsonData, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
 	}
	
}
