package com.ttstore.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttstore.common.bean.EasyUIResult;
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

	
	/**
	 * 保存商品
	 * @Title: saveItem   
	 * @Description: 
	 * @param: @param item
	 * @param: @param desc      
	 * @return: void      
	 * @throws
	 */
	public void saveItem(Item item, String desc) {
		
		item.setStatus(1);
		save(item);
		
		
		ItemDesc itemdesc = new ItemDesc();
		itemdesc.setItemId(item.getId());
		itemdesc.setItemDesc(desc);		
		itemDescService.save(itemdesc);
			
	}

	/**
	 * 查询商品列表
	 * @Title: queryItemList   
	 * @Description: 
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return      
	 * @return: EasyUIResult      
	 * @throws
	 */
	public EasyUIResult queryItemList(Integer page, Integer rows) {
		
		 //配置分页参数
        PageHelper.startPage(page, rows);

        //加条件查询updated
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        List<Item> items = itemMapper.selectByExample(example);

        
        //获取分页后的信息
        PageInfo<Item> pageInfo= new PageInfo<Item>(items);
        
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
 
	}


 
}
