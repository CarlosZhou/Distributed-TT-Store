package com.ttstore.manage.controller.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttstore.common.bean.ItemCatResult;
import com.ttstore.manage.service.ItemCatService;


@RequestMapping("api/item/cat")
@Controller
public class ApiItemController {

	
	@Autowired
	private ItemCatService itemCatService;
	//jackson 的 ObjectMapper 可以把对象转成json
	private static final ObjectMapper  MAPPER = new ObjectMapper();
	
	/**
	 * 
	 * @Title: queryItemCatList   
	 * @Description: 对外提供接口数据 查询商城左侧菜单的商品类目
	 * @param: @return      
	 * @return: ResponseEntity<ItemCatResult>      
	 * @throws
	 */
/*	
 * 传来的参数涉及到一个回调的处理。jsonp的原理 因为跨域只能调用js 不能直接处理json数据 所以可以用回调，
	或者jQuery ajax直接指定dataType为jsonp格式,能直接去到data，起始jQuery的原理也是用了callback
	http://localhost:8080/rest/api/item/cat?callback=category.getDataService
	
	下面那个没有通用callback 每个写比较麻烦。所以才有方法2
*/	
	/*@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<String> queryItemCatList(@RequestParam(value="callback",defaultValue="",required=true)String callback){
		
		
		try {
			ItemCatResult resultData =  itemCatService.queryAllToTree();
			String json = MAPPER.writeValueAsString(resultData);
			if(StringUtils.isEmpty(callback)){
				//无需跨域支持
				return ResponseEntity.ok(json);

			}
			   //需跨域支持
				return ResponseEntity.ok("callback"+"("+json+")");		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}*/
	
	
	/**
	 * 
	 * @Title: queryItemCatList   
	 * @Description: 使用通用跨域支持的方法比较简便
	 * /ttstore-common/src/main/java/com/ttstore/common/spring/exetend/converter/json/CallbackMappingJackson2HttpMessageConverter.java
	 * @param: @return      
	 * @return: ResponseEntity<ItemCatResult>      
	 * @throws
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryItemCatList(){
		
		
		try {
			ItemCatResult resultData =  itemCatService.queryAllToTree();
 	 
 				return ResponseEntity.ok(resultData);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
}
