package com.ttstore.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ttstore.manage.pojo.Item;
import com.ttstore.web.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {

	
	@Autowired
	private ItemService itemService;
	
	
	//既有页面 又有 模型对象  就用ModelAndView 作为返回值
	@RequestMapping(value="{itemId}",method=RequestMethod.GET)
	public ModelAndView itemDetail(@PathVariable("itemId") Long itemId){
 		ModelAndView modelAndView = new ModelAndView("item");
		//设置模型数据
 		Item item = itemService.queryByid(itemId);
		modelAndView.addObject("item",item);
		return modelAndView;
 	}
	
}
