package com.ttstore.web.bean;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.ttstore.manage.pojo.Item {

	
	public String[] getImages(){

		return StringUtils.split(getImage(),',');
		
	}
}
