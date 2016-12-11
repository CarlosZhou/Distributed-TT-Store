package com.ttstore.manage.controller;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.pojo.ItemDesc;
import com.ttstore.manage.pojo.ItemParam;
import com.ttstore.manage.pojo.ItemParamItem;
import com.ttstore.manage.service.ItemParamItemService;
import com.ttstore.manage.service.ItemParamService;

/**
 * 
 * @ClassName:  ItemParam   
 * @Description:商品规格controller
 * @author: carlos-y
 * @date:   2016年12月11日 下午12:35:28   
 * @Copyright: 2016 carlos-y. All rights reserved.
 */

@Controller
@RequestMapping(value="item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	@Autowired
	private ItemParamItemService itemParamItemService;

	
	/**
	 * 
	 * @Title: queryByItemCatId   
	 * @Description: 根据商品归类ID查询是否有规格模板
	 * @param: @param itemCarId
	 * @param: @return      
	 * @return: ResponseEntity<ItemParam>      
	 * @throws
	 */
	@RequestMapping(value="{itemCarId}",method=RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCarId") Long itemCarId){

		try {
			ItemParam record = new ItemParam();
			record.setItemCatId(itemCarId);
			ItemParam itemParam =  itemParamService.queryOne(record);
			if(null==itemParam){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			return ResponseEntity.ok(itemParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}


 /**
  * 
  * @Title: saveItemParam   
  * @Description: 保存商品规格模板数据
  * @param: @param itemCarId
  * @param: @param paramData
  * @param: @return      
  * @return: ResponseEntity<Void>      
  * @throws
  */
	@RequestMapping(value="{itemCarId}",method=RequestMethod.POST)
	public ResponseEntity<Void>  saveItemParam(@PathVariable("itemCarId") Long itemCarId,@RequestParam("paramData") String paramData){
		
		try {

			
			if(TextUtils.isEmpty(paramData)){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);//参数错误
			}
			
			ItemParam record = new ItemParam();
			record.setItemCatId(itemCarId);
			record.setParamData(paramData);
			itemParamService.saveSelective(record);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();//修改成功但无返回内容

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
		
	}
	
	
	
	/**
	 * 
	 * @Title: queryByItemId   
	 * @Description: 根据商品ID查询该规格数据
	 * @param: @param itemId
	 * @param: @return      
	 * @return: ResponseEntity<ItemParamItem>      
	 * @throws
	 */
	@RequestMapping(value="item/{itemId}",method=RequestMethod.GET)
	public ResponseEntity<ItemParamItem>  queryByItemId(@PathVariable("itemId") Long itemId){
		
		try {

			
			if(TextUtils.isEmpty(itemId+"")){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);//参数错误
			}
			
			ItemParamItem record = new ItemParamItem();
			record.setItemId(itemId); 
 			
			return ResponseEntity.ok(itemParamItemService.queryOne(record)); 

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
		
	}

}
