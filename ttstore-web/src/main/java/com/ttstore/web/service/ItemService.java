package com.ttstore.web.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttstore.common.service.ApiService;
import com.ttstore.manage.pojo.Item;

 
@Service
public class ItemService {

	//ApiService  后台系统中接口调用的服务类（通用）
	@Autowired
	private ApiService apiService;
	
	@Value("${TAOTAOMANAGE_URL}")
	private String TAOTAOMANAGE_URL;
	
	
	//jackson 的 ObjectMapper 可以把对象转成json
	private static final ObjectMapper  MAPPER = new ObjectMapper();
	/**
	 * 
	 * @Title: queryByid   
	 * @Description: 根据商品ID查询商品数据，通过后台接口提供的服务进行查询
	 * @param: @return      
	 * @return: Ttem      
	 * @throws
	 */
	public Item queryByid(Long itemId) {
		
		String url =TAOTAOMANAGE_URL+ "/rest/api/item"+itemId;
		try {
			String jsonData = apiService.doGet(url);
			if(null == jsonData){
				return null;
			}
			//将json数据返序列化为Item对象
			return MAPPER.readValue(jsonData, Item.class);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
