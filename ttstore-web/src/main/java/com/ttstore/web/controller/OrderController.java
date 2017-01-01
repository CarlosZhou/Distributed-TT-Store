package com.ttstore.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ttstore.web.bean.Item;
import com.ttstore.web.service.ItemService;

@RequestMapping("order")
@Controller
public class OrderController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="{itemId}",method=RequestMethod.GET)
	public ModelAndView toOrder(@PathVariable("itemId") Long itemId){
	
		ModelAndView mv = new ModelAndView("order");
		Item item = new Item();
		mv.addObject("item", itemService.queryByid(itemId));
		
		return mv;
	}
	
}
