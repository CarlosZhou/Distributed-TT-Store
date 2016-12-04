package com.ttstore.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.ttstore.manage.mapper.ItemCatMapper;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.service.base.BaseService;

@Service
public class ItemCatService extends BaseService<ItemCat>{

	@Autowired
	private ItemCatMapper itemMapper;

	/**
	 * 查询商品类型 列表
	 * @param pid
	 * @return
	 *//*
	public List<ItemCat> queryItemListByParent(long pid) {
		
		ItemCat record  = new ItemCat();
		record.setParentId(pid);
		
		return itemMapper.select(record);
	}*/

	
/*	在Srpring 4.x中 不需要这么麻烦来获取Mapper对象，可以用4.0新特性 注解注入
 *  @Override
	public Mapper<ItemCat> getMapper() {
		return itemMapper;
	}*/
	
 
}
