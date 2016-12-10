package com.ttstore.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

	
	/*@Value(value="IMAGE_PATH")
	public String IMAGE_PATH;
	@Value(value="IMAGE_BASE_URL")
	public String IMAGE_BASE_URL;
	*/
	
	@Value("${IMAGE_PATH}")
	public String IMAGE_PATH;
	
	@Value("${IMAGE_BASE_URL}")
	public String IMAGE_BASE_URL;
	
}
