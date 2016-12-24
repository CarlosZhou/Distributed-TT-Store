package com.ttstore.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.service.ItemService;

@RequestMapping("api/item")
@Controller
public class ApiItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 
	 * @Title: queryById   
	 * @Description: 根据商品ID查询数据
	 * @param: @param itemId
	 * @param: @return      
	 * @return: ResponseEntity<Item>      
	 * @throws
	 */
	@RequestMapping(value="{itemId}",method=RequestMethod.GET)
	public ResponseEntity<Item> queryById(@PathVariable("itemId") Long itemId){
		
		try {
			Item item =  itemService.queryByid(itemId);
			
			if(null==item){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
}
