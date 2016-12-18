package com.ttstore.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttstore.manage.mapper.ContentMapper;
import com.ttstore.manage.pojo.Content;
import com.ttstore.manage.service.base.BaseService;

@Service
public class ContentService extends BaseService<Content> {

	
	@Autowired
	private ContentMapper contentMapper;
}
