package com.ttstore.common.spring.exetend.converter.json;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @ClassName:  CallbackMappingJackson2HttpMessageConverter   
 * @Description: 通用jsonp支持类，还需要在xml中配置 applicationContext-servlet.xml中的注解驱动中
 * @author: carlos-y
 * @date:   2016年12月17日 上午10:59:06   
 * @Copyright: 2016 carlos-y. All rights reserved.
 * 
 * 
 * <!-- 注解驱动 -->
	<mvc:annotation-driven>
		<mvc:message-converters register-defaults="true">
		
		<!--自定义String的字符串的消息转换器  -->
			
			<bean class="com.ttstore.common.spring.exetend.converter.json.CallbackMappingJackson2HttpMessageConverter">
				<property name="callbackName" value="callback"/>
			</bean>
		</mvc:message-converters>
	</mvc:annotation-driven>
 */
public class CallbackMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	// 做jsonp的支持的标识，在请求参数中加该参数
	private String callbackName;

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// 从threadLocal中获取当前的Request对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String callbackParam = request.getParameter(callbackName);
		if(StringUtils.isEmpty(callbackParam)){
			// 没有找到callback参数，直接返回json数据
			super.writeInternal(object, outputMessage);
		}else{
			JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
			try {
				String result =callbackParam+"("+super.getObjectMapper().writeValueAsString(object)+");";
				IOUtils.write(result, outputMessage.getBody(),encoding.getJavaName());
			}
			catch (JsonProcessingException ex) {
				throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
			}
		}
		
	}

	public String getCallbackName() {
		return callbackName;
	}

	public void setCallbackName(String callbackName) {
		this.callbackName = callbackName;
	}

}
