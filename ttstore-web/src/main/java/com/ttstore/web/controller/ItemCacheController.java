package com.ttstore.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttstore.common.service.RedisService;
 

/**
 * 
 * @ClassName:  ItemCacheController   
 * @Description: 前台系统暴露给 后台系统的 数据变更通知接口，
 * 				后台数据发生变化，调用此接口通知前台数据发生变化，做相应操作
 * @author: carlos-y
 * @date:   2016年12月24日 下午9:23:04   
 * @Copyright: 2016 carlos-y. All rights reserved.
 */
@RequestMapping("item/cache")
@Controller
public class ItemCacheController {

	@Autowired
	private RedisService redisService;
	
	@Autowired
	//private ItemService itemService;
	
	
	@RequestMapping(value="${itemId}",method=RequestMethod.POST)
	public ResponseEntity<Void> deleteCache(@PathVariable("itemId") Long itemId){
		
		/*
		 * 
		 * 1 从service层中获取redis操作用的key
		 * 2 用redisservice删除这个key
		 * 3 返回相应状态码 204，出现异常相应500
		 * 4 接下开开发后台逻辑
		 * 
		 * */
		
		
		
	}
}
