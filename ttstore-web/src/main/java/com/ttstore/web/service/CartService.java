package com.ttstore.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
 import com.ttstore.common.service.ApiService;
import com.ttstore.web.threadlocal.UserThreadLocal;

@Service
public class CartService {

   /* @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${TAOTAO_CART_URL}")
    private String TAOTAO_CART_URL;

    *//**
     * 
     * @Title: queryCartListByUser   
     * @Description: 查询购物车系统 获取用户的购物车数据，并且需要在购物车系统 加个接口供前台访问
     * @param: @return      
     * @return: List<Cart>      
     * @throws
     *//*
    public List<Cart> queryCartListByUser() {
        try {
            String url = TAOTAO_CART_URL + "/service/api/cart/" + UserThreadLocal.get().getId();
            String jsonData = this.apiService.doGet(url);
            if (null == jsonData) {
                return null;
            }
            return MAPPER.readValue(jsonData,
                    MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
