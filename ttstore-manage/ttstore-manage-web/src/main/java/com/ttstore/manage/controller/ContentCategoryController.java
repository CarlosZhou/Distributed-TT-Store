package com.ttstore.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttstore.manage.pojo.ContentCategory;
import com.ttstore.manage.service.ContentCategoryService;

@Controller
@RequestMapping("content/category")
 public class ContentCategoryController {

	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 
	 * @Title: queryListByParentId   
	 * @Description: 查询内容分类
	 * @param: @param pid
	 * @param: @return      
	 * @return: ResponseEntity<List<ContentCategory>>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryListByParentId(@RequestParam(value="id",defaultValue="0") Long pid){
		
		try {
			ContentCategory record = new ContentCategory();
			record.setParentId(pid);
			
			List<ContentCategory> list = contentCategoryService.queryListByWhere(record);
			
			if(null==list||list.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
	/**
	 * 
	 * @Title: saveContentCategory   
	 * @Description: 新增分类节点
	 * @param: @param contentCategory
	 * @param: @return      
	 * @return: ResponseEntity<ContentCategory>      
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
		
		try {
			

			contentCategoryService.saveContentCateGory(contentCategory);	
			return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);//对象保存后数据库就会自动返回id
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	/**
	 * 
	 * @Title: rename   
	 * @Description: 节点重命名
	 * @param: @param id
	 * @param: @param name
	 * @param: @return      
	 * @return: ResponseEntity<Void>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> rename(@RequestParam("id") Long id,@RequestParam("name")String name ){
		
		try {
			ContentCategory record = new ContentCategory();
			record.setId(id);
			record.setName(name);
			contentCategoryService.updateSelective(record);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
	/**
	 * 
	 * @Title: delete   
	 * @Description: 删除节点
	 * @param: @param contentCategory
	 * @param: @return      
	 * @return: ResponseEntity<Void>      
	 * @throws
	 * 
	 * 注意delete的方式的ajax请求方式有所不同  加参数,"_method":"DELETE"},
	 * $.ajax({
     			   type: "POST",
     			   url: "/rest/content/category",
     			   data : {parentId:node.parentId,id:node.id,"_method":"DELETE"},
     			   success: function(msg){
     				   //$.messager.alert('提示','新增商品成功!');
     				  tree.tree("remove",node.target);
     			   },
     			   error: function(){
     				   $.messager.alert('提示','删除失败!');
     			   }
     			});
	 * 
	 */
	@RequestMapping(method=RequestMethod.DELETE)
	public  ResponseEntity<Void> delete(ContentCategory contentCategory){
		
		try {
			//需要递归删除并包括下面的子节点
			contentCategoryService.deleteAll(contentCategory);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
}
