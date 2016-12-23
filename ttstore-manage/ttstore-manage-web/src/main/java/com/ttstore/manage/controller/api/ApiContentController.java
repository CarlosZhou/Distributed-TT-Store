package com.ttstore.manage.controller.api;

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
@RequestMapping("api/content")
public class ApiContentController {

	@Autowired
	private ContentService contentService;
	
	 
	
	/**
	 * 
	 * @Title: queryListByCategoryId   
	 * @Description: 更加分类id查询类目下的数据
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param categoryId
	 * @param: @return      
	 * @return: ResponseEntity<EasyUIResult>      
	 * @throws
	 */
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
