package com.ttstore.manage.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.ParseException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttstore.common.bean.EasyUIResult;
import com.ttstore.common.service.ApiService;
import com.ttstore.manage.mapper.ItemCatMapper;
import com.ttstore.manage.mapper.ItemMapper;
import com.ttstore.manage.pojo.Item;
import com.ttstore.manage.pojo.ItemCat;
import com.ttstore.manage.pojo.ItemDesc;
import com.ttstore.manage.service.base.BaseService;
 
@Service
public class ItemService extends BaseService<Item>{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescService itemDescService;

	
/*	private RabbitTemplate rwm*/
	
	@Autowired
	private ApiService apiService;
	
	/**
	 * 保存商品
	 * @Title: saveItem   
	 * @Description: 
	 * @param: @param item
	 * @param: @param desc      
	 * @return: void      
	 * @throws
	 */
	public void saveItem(Item item, String desc) {
		
		item.setStatus(1);
		save(item);
		
		
		ItemDesc itemdesc = new ItemDesc();
		itemdesc.setItemId(item.getId());
		itemdesc.setItemDesc(desc);		
		itemDescService.save(itemdesc);
			
	}

	/**
	 * 查询商品列表
	 * @Title: queryItemList   
	 * @Description: 
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return      
	 * @return: EasyUIResult      
	 * @throws
	 */
	public EasyUIResult queryItemList(Integer page, Integer rows) {
		
		 //配置分页参数
        PageHelper.startPage(page, rows);

        //加条件查询updated
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        List<Item> items = itemMapper.selectByExample(example);

        
        //获取分页后的信息
        PageInfo<Item> pageInfo= new PageInfo<Item>(items);
        
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
 
	}

	/**
	 * 
	 * @Title: updateItem   
	 * @Description: 更新数据需要通知前台删除缓存
	 * @param: @param id
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
		public boolean updateItem(Item id){
		//方式一	
		/*	更新数据之前通知前台吧换存数据删除掉来保持数据同步
		 * //前台系统公开的接收接口
					  try {
						apiService.doPost("url");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 	 这里需要try cache  理论上service不能try cache，但是通知和本身业务无关 不能影响事物继续执行，所以要捕获一次
		 *
		 */		
			
			
			//方式2 rabbitMQ
			/*  try {
		            // 发送消息通知其它系统，该商品已经更新
		            Map<String, Object> msg = new HashMap<String, Object>();
		            msg.put("itemId", itemId);
		            msg.put("type", type);
		            msg.put("data", System.currentTimeMillis());
		            this.rabbitTemplate.convertAndSend("item." + type, MAPPER.writeValueAsString(msg));
		        } catch (Exception e) {
		            e.printStackTrace();	
		        }*/
			
			return true;
		}
 
}
