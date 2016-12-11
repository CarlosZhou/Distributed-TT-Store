package com.ttstore.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.ttstore.manage.mapper.ItemParamItemMapper;
import com.ttstore.manage.pojo.ItemParamItem;
import com.ttstore.manage.service.base.BaseService;




@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

	
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;
	
	/**
	 * 
	 * @Title: updateItemParamItem   
	 * @Description: 更新商品规格数据
	 * @param: @param itemParams      
	 * @return: void      
	 * @throws
	 */
	public void updateItemParamItem(Long itemId ,String itemParams) {

		
		/**
		 * 更新的数据
		 */
		ItemParamItem itemParamItem= new ItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		
		/**
		 * 更新的条件
		 */
		Example example=new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		itemParamItemMapper.updateByExample(itemParamItem, example);
	}

}
