package com.ttstore.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.manage.pojo.Content;
import com.ttstore.manage.service.ContentCategoryService;
import com.ttstore.manage.service.ContentService;

@Controller
@RequestMapping("content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	
	/**
	 * 
	 * @Title: saveContent   
	 * @Description: 新增内容
	 * @param: @param content
	 * @param: @return      
	 * @return: ResponseEntity<Void>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content){

		try {
			content.setId(null);
			
			contentService.save(content);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryListByCategoryId(@RequestParam(value = "page",defaultValue="1") Integer page,
			  @RequestParam(value = "rows",defaultValue="30") Integer rows,
			  @RequestParam(value = "categoryId") Long categoryId ){

		try {
			EasyUIResult easyUIResult = contentService.queryListByCategoryId(page,rows,categoryId);
			
			return ResponseEntity.ok(easyUIResult);

 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
	
}
