package com.ttstore.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.mapper.Mapper;
import com.ttstore.manage.mapper.ItemCatMapper;
import com.ttstore.manage.mapper.ItemMapper;
import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.pojo.ItemDesc;
import com.ttstore.manage.service.base.BaseService;
 
@Service
public class ItemService extends BaseService<Item>{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescService itemDescService;
	
	public void saveItem(Item item, String desc) {
		
		item.setStatus(1);
		save(item);
		
		
		ItemDesc itemdesc = new ItemDesc();
		itemdesc.setItemId(item.getId());
		itemdesc.setItemDesc(desc);		
		itemDescService.save(itemdesc);
			
	}


 
}
