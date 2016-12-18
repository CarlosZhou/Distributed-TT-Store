package com.ttstore.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.manage.mapper.ContentMapper;
import com.ttstore.manage.pojo.Content;
import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.service.base.BaseService;

@Service
public class ContentService extends BaseService<Content> {

	
	@Autowired
	private ContentMapper contentMapper;

	public EasyUIResult queryListByCategoryId(Integer page, Integer rows, Long categoryId) {
		 
		
		 //配置分页参数
        PageHelper.startPage(page, rows);

        //不用通用mapper来解决，用xml的配置方式解决
        List<Content> lists = contentMapper.queryContentList(categoryId);
             
        //获取分页后的信息
        PageInfo<Content> pageInfo= new PageInfo<Content>(lists);
        
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
		
	}
}
