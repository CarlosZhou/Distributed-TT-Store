package com.ttstore.web.mq.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttstore.common.service.RedisService;
import com.ttstore.web.service.ItemService;

public class ItemMQHandler {


    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void execute(String msg) {
      /*  // 删除缓存中的数据
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            String key = ItemService.REDIS_KEY + jsonNode.get("itemId").asLong();
            this.redisService.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
	
}
