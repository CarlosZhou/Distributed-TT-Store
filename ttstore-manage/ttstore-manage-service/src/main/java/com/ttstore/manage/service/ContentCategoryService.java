package com.ttstore.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttstore.manage.mapper.ContentCategoryMapper;
import com.ttstore.manage.mapper.ContentMapper;
import com.ttstore.manage.pojo.Content;
import com.ttstore.manage.pojo.ContentCategory;
import com.ttstore.manage.service.base.BaseService;


@Service
public class ContentCategoryService  extends BaseService<ContentCategory>{

	
	
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	public void saveContentCateGory(ContentCategory contentCategory) {
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		save(contentCategory);
		
		//查询该节点的父节点是否为true，不是需要改为true
		ContentCategory parent = queryByid(contentCategory.getParentId());
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			update(parent);
		}
		
	}

	
	public void deleteAll(ContentCategory contentCategory) {
		
		
		List<Object> ids = new ArrayList<Object>();
		ids.add(contentCategory.getId());
		//递归查找下面的所有的子节点
		findAllSubNode(ids,contentCategory.getId());
		
		deleteByIds(ids, ContentCategory.class, "id");
		
		
		
		//判断该节点是否还有兄弟节点，如果没有，修改父节点isParent为false
		ContentCategory record = new ContentCategory();
		record.setParentId(contentCategory.getParentId());
		List<ContentCategory> lists = queryListByWhere(record);
		if(null==lists||lists.isEmpty()){
			ContentCategory parent = new ContentCategory();
			parent.setIsParent(false);
			parent.setId(contentCategory.getParentId());
			update(parent);
		}
	}
	
	private void findAllSubNode(List<Object> ids,Long pid){
		
		ContentCategory record = new ContentCategory();
		record.setParentId(pid);
		List<ContentCategory> lists =  queryListByWhere(record);
		for (int i = 0; i < lists.size(); i++) {
			ids.add(lists.get(i).getId());
			//判断该节点是否是父节点，如果是接续调用该方法找出子节点
			if(lists.get(i).getIsParent()){
				
				//开始递归
				findAllSubNode(ids,lists.get(i).getId());
				
			}
		}
		
	}
	
}
