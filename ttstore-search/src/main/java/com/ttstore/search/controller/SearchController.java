package com.ttstore.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ttstore.search.bean.Item;
import com.ttstore.search.bean.SearchResult;
import com.ttstore.search.service.SearchService;

@Controller
public class SearchController {

	
	public static final Integer ROWS =32;
	@Autowired
	private SearchService searchService;
	
	
	/**
	 * 
	 * @Title: search   
	 * @Description: 内容搜索
	 * @param: @param keyWord 
	 * @param: @param page
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value="search",method=RequestMethod.GET)
	public ModelAndView search(@RequestParam("q") String keyWord,
			@RequestParam(value="page",defaultValue="1") Integer page){
		
		ModelAndView mv = new ModelAndView("search");
		
		
		//设置搜索关键字的编码 防止乱码问题
		try {
			keyWord = new String(keyWord.getBytes("ISO-8858-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			keyWord="";
		}
		
		//搜索关键字
		mv.addObject("query", keyWord);
		
	    //搜索的结果数据
		 SearchResult result;
		try {
			result = searchService.search(keyWord,page,ROWS);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new SearchResult(0L, null);
			
		}
		
		mv.addObject("itemList", result.getList());
		
		//页数
		mv.addObject("page", page);
		
		
		int total = result.getTotal().intValue();
		int pages = total%ROWS ==0?total/ROWS:total/ROWS+1;
		
		//总页数
		mv.addObject("pages", pages);

		return mv;
	}
	
}
