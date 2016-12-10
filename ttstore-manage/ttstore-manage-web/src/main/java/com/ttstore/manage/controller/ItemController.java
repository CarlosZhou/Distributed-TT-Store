package com.ttstore.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.manage.mapper.ItemMapper;
import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.pojo.ItemDesc;
import com.ttstore.manage.service.ItemCatService;
import com.ttstore.manage.service.ItemDescService;
import com.ttstore.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
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
	public ResponseEntity<List<ItemCat>> saveItem(@RequestParam(value="desc") String desc,Item item){

		
		try {
			item.setStatus(1);
			itemService.save(item);
			
			
			ItemDesc itemdesc = new ItemDesc();
			itemdesc.setItemId(item.getId());
			itemdesc.setItemDesc(desc);		
			itemDescService.save(itemdesc);
			
			 ResponseEntity.status(HttpStatus.CREATED).build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
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
	

}
