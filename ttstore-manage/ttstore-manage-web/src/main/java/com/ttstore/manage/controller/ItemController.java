package com.ttstore.manage.controller;

import java.util.List;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.manage.mapper.ItemMapper;
import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.pojo.ItemDesc;
import com.ttstore.manage.pojo.ItemParam;
import com.ttstore.manage.pojo.ItemParamItem;
import com.ttstore.manage.service.ItemCatService;
import com.ttstore.manage.service.ItemDescService;
import com.ttstore.manage.service.ItemParamItemService;
import com.ttstore.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	
	
	@Autowired
	private ItemDescService itemDescService;
	
	
	/**
	 *  
	 * @Title: saveItem   
	 * @Description: 保存商品
	 * @param: @param desc
	 * @param: @param item
	 * @param: @return      
	 * @return: ResponseEntity<List<ItemCat>>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<List<ItemCat>> saveItem(@RequestParam(value="desc") String desc,Item item,
			@RequestParam(value="itemParams") String itemParams ){

		
		try {
			
			//保存商品数据
			item.setStatus(1);
			itemService.save(item);
			
			
			//保存商品描述数据
			ItemDesc itemdesc = new ItemDesc();
			itemdesc.setItemId(item.getId());
			itemdesc.setItemDesc(desc);		
			itemDescService.save(itemdesc);
			
			
			//保存商品规格模板数据
			ItemParamItem itemParamItem = new ItemParamItem();
			itemParamItem.setParamData(itemParams);
			itemParamItem.setItemId(item.getId());
			itemParamItemService.saveSelective(itemParamItem);
			
			 ResponseEntity.status(HttpStatus.CREATED).build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
	/**
	 * 
	 * @Title: queryItemList   
	 * @Description: 查询商品列表
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return      
	 * @return: ResponseEntity<EasyUIResult>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page",defaultValue="1") Integer page,
													  @RequestParam(value = "rows",defaultValue="30") Integer rows){
		
	 
		try {
			return ResponseEntity.ok(itemService.queryItemList(page,rows));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	

	/**
	 * 
	 * @Title: queryItemDesc   
	 * @Description: 查询商品描述
	 * @param: @param itemId
	 * @param: @return      
	 * @return: RequestEntity<ItemDesc>      
	 * @throws
	 */
	@RequestMapping(value="desc/{itemId}",method=RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDesc(@PathVariable("itemId") Long itemId){
		
		 try {
			ItemDesc itemDesc =  itemDescService.queryByid(itemId);
			if(null==itemDesc){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		 
	}
	
	/**
	 * 
	 * @Title: editItem   
	 * @Description: 更新商品信息
	 * @param: @param desc
	 * @param: @param item
	 * @param: @return      
	 * @return: ResponseEntity<Void>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void>  editItem(@RequestParam("desc") String desc,Item item,@RequestParam(value="itemParams") String itemParams){
		
		try {
			
			if(TextUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);//参数错误
			}
			
		    itemService.updateSelective(item);			
			ItemDesc itemdesc = new ItemDesc();
			itemdesc.setItemId(item.getId());
			itemdesc.setItemDesc(desc);		
			itemDescService.save(itemdesc);
			/*
			ItemParamItem itemParamItem= new ItemParamItem();
			itemParamItem.setItemId(item.getId());
			itemParamItem.setParamData(itemParams);
			
			不能更新，因为没有主键，需要单独在写方法
			
			itemParamItemService.update(itemParamItem);*/
			
			itemParamItemService.updateItemParamItem(item.getId(),itemParams);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//修改成功但无返回内容

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
		
	}
	
	
}
