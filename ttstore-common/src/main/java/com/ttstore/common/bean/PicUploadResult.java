package com.ttstore.common.bean;

/**
 * 
 * @ClassName:  PicUploadResult   
 * @Description: 文件上传通用 bean 上传的结果数据封装
 * @author: carlos-y
 * @date:   2016年12月10日 上午9:28:18   
 * @Copyright: 2016 carlos-y. All rights reserved.
 */
public class PicUploadResult {
    
    private Integer error;
    
    private String url;
    
    private String width;
    
    private String height;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    
    

}
