package com.ttstore.common.bean;


/**
 * 
 * @ClassName:  HttpResult   
 * @Description:HttpClient javaBean
 * @author: carlos-y
 * @date:   2016年12月24日 下午9:44:46   
 * @Copyright: 2016 carlos-y. All rights reserved.
 */
public class HttpResult {
    
    private int statusCode;
    
    private String data;
    
    public HttpResult(){
        
    }
    
    public HttpResult(int statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    

}
