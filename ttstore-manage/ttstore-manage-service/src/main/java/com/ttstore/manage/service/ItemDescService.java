package com.ttstore.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.ttstore.manage.mapper.ItemCatMapper;
import com.ttstore.manage.mapper.ItemDescMapper;
import com.ttstore.manage.mapper.ItemMapper;
import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.pojo.ItemDesc;
import com.ttstore.manage.service.base.BaseService;

@Service
public class ItemDescService extends BaseService<ItemDesc>{

	@Autowired
	private ItemDescMapper itemDescMapper;


 
}
