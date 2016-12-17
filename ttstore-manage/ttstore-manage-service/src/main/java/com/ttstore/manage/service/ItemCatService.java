package com.ttstore.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.common.bean.ItemCatData;
import com.ttstore.common.bean.ItemCatResult;
import com.ttstore.manage.mapper.ItemCatMapper;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.service.base.BaseService;

@Service
public class ItemCatService extends BaseService<ItemCat>{

	@Autowired
	private ItemCatMapper itemMapper;

	
	
	/**
	 * 全部查询，并且生成树状结构
	 * @return
	 */
	public ItemCatResult queryAllToTree() {
		ItemCatResult result = new ItemCatResult();
		// 全部查出，并且在内存中生成树形结构
		List<ItemCat> cats =queryAll();
		
		// 转为map存储，key为父节点ID，value为数据集合
		Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
		for (ItemCat itemCat : cats) {
			if(!itemCatMap.containsKey(itemCat.getParentId())){
				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
			}
			itemCatMap.get(itemCat.getParentId()).add(itemCat);
		}
		
		// 封装一级对象
		List<ItemCat> itemCatList1 = itemCatMap.get(0L);
		for (ItemCat itemCat : itemCatList1) {
			ItemCatData itemCatData = new ItemCatData();
			itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
			itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>"+itemCat.getName()+"</a>");
			result.getItemCats().add(itemCatData);
			if(!itemCat.getIsParent()){
				continue;
			}
			
			// 封装二级对象
			List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
			List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
			itemCatData.setItems(itemCatData2);
			for (ItemCat itemCat2 : itemCatList2) {
				ItemCatData id2 = new ItemCatData();
				id2.setName(itemCat2.getName());
				id2.setUrl("/products/" + itemCat2.getId() + ".html");
				itemCatData2.add(id2);
				if(itemCat2.getIsParent()){
					// 封装三级对象
					List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
					List<String> itemCatData3 = new ArrayList<String>();
					id2.setItems(itemCatData3);
					for (ItemCat itemCat3 : itemCatList3) {
						itemCatData3.add("/products/" + itemCat3.getId() + ".html|"+itemCat3.getName());
					}
				}
			}
			if(result.getItemCats().size() >= 14){
				break;
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	
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
