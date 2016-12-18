package com.ttstore.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.ttstore.manage.pojo.Content;

public interface ContentMapper extends Mapper<Content>{

	
	/**
	 * 
	 * @Title: queryContentList   
	 * @Description: 根据categoryId查询内容列表，并且按照更新时间进行排序
	 * @param: @param categoryId
	 * @param: @return      
	 * @return: List<Content>      
	 * @throws
	 */
	public List<Content> queryContentList(Long categoryId);
	
}
