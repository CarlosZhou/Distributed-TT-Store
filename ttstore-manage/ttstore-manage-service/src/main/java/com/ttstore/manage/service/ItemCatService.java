package com.ttstore.manage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.common.bean.ItemCatData;
import com.ttstore.common.bean.ItemCatResult;
import com.ttstore.manage.mapper.ItemCatMapper;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.service.base.BaseService;
import com.ttstore.manage.service.base.RedisService;

@Service
public class ItemCatService extends BaseService<ItemCat>{

	@Autowired
	private ItemCatMapper itemMapper;



	@Autowired
	private RedisService redisService;


	private static final ObjectMapper  MAPPER = new ObjectMapper();
	private static final  String REDIS_KEY ="TTSTORE_MANAGE_ITEM_CAT_API";		//规则：项目名_模块名_业务名
	private static final  Integer REDIS_TIME =3600;		//规则：项目名_模块名_业务名

	/**
	 * 全部查询，并且生成树状结构
	 * @return
	 */
	public ItemCatResult queryAllToTree() {
		ItemCatResult result = new ItemCatResult();

		//为了防止由于缓存报错而影响正常业务逻辑继续执行，所以把缓存早放进一个try cache里
		try {
			//先从redis缓存中命中，如果有数据直接返回 无需返回数据库
			String cacheData = redisService.get(REDIS_KEY);

			if(StringUtils.isNotEmpty(cacheData)){
				//命中
				System.out.println("取出");
				return MAPPER.readValue(cacheData, ItemCatResult.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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


		//将数据库查询结果集写入redis
		try {
			redisService.set(REDIS_KEY, MAPPER.writeValueAsString(result), REDIS_TIME);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
