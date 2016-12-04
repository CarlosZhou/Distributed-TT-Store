package com.ttstore.manage.service.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttstore.manage.pojo.BasePojo;

/**
 * @ClassName:  BaseService   
 * @Description: 对CRUD进行了一次封装
 * 	
 * 1、queryById
 * 2、queryAll
 * 3、queryOne
 * 4、queryListByWhere
 * 5、queryPageListByWhere
 * 6、save
 * 7、saveSelective
 * 8、update
 * 9、updateSelective
 * 10、deleteById
 * 11、deleteByIds
 * 12、deleteByWhere
 * 
 * @author: carlos-y
 * @date:   2016年12月4日 上午10:51:44   
 * @Copyright: 2016 carlos-y. All rights reserved.
 */
public abstract class BaseService<T extends BasePojo> {

	
	/**
	 * Spring 4.0特性 泛型注入
	 */
	@Autowired
	private Mapper<T> mapper;
		
	/**
	 * 在Srpring 4.x中 不需要这么麻烦来获取Mapper对象，可以用4.0新特性 注解注入
	 * @Title: getMapper   
	 * @Description: 子类负责传入具体的Mapper对象
	 * @param: @return      
	 * @return: Mapper<T>      
	 * @throws
	 
	public abstract Mapper<T> mapper;
	
	*/
	
	/**
	 * c
	 * @Title: queryByid   
	 * @Description: 更具ID查询数据（主键查询）
	 * @param: @param id
	 * @return: T      
	 * @throws
	 */
	public T queryByid(Long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 
	 * @Title: queryAll   
	 * @Description: 查询所有数据
	 * @param: @return      
	 * @return: List<T>      
	 * @throws
	 */
	public List<T> queryAll(){
		
		return mapper.select(null);
	}
	
	/**
	 * 
	 * @Title: queryOne   
	 * @Description: 查询一条数据（条件查询）
	 * @param: @return      
	 * @return: List<T>      
	 * @throws
	 */
	public T queryOne(T record){
		
		return mapper.selectOne(record);
	}
	
	
	/**
	 * 
	 * @Title: queryListByWhere   
	 * @Description: 根据条件查询多条数据
	 * @param: @param record
	 * @param: @return      
	 * @return: List<T>      
	 * @throws
	 */
	public List<T> queryListByWhere(T record){
		return mapper.select(record);

	}
	
	/**
	 * 
	 * @Title: queryPageListByWhere   
	 * @Description: 条件查询 分页
	 * @param: @param record
	 * @param: @param page 页数
	 * @param: @param rows 每页的行数
	 * @param: @return      
	 * @return: PageInfo<T>      
	 * @throws
	 */
	public  PageInfo<T> queryPageListByWhere(T record,Integer page,Integer rows){
		
		//设置分页参数
		PageHelper.startPage(page, rows);
		
		//排序操作无法封装 需要子类自己定义
		List<T> list = mapper.select(record);
		
		return new PageInfo<T>(list);
			
	}
	
	/**
	 * 
	 * @Title: save   
	 * @Description: 插入数据(insert就把所有值插入,但是要注意加入数据库字段有default,default是不会起作用的)
	 * @param: @param record
	 * @param: @return      
	 * @return: Integer  影响条数       
	 * @throws
	 */
	public  Integer save(T t){		
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		return mapper.insert(t);
	}

	
	/**
	 * 
	 * @Title: save   
	 * @Description: 插入数据(如果为null就不插入，insertSelective不会忽略default，null的不操作)
	 * @param: @param record
	 * @param: @return      
	 * @return: Integer  影响条数       
	 * @throws
	 */
	public  Integer saveSelective(T t){
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		return mapper.insertSelective(t);
	}
	
	
	/**
	 * 
	 * @Title: update   
	 * @Description: 更新（会将为空的字段在数据库中置为NULL。）
	 * @param: @param record
	 * @param: @return      
	 * @return: Integer  影响条数     
	 * @throws
	 */
	public Integer update(T t){
		t.setUpdated(new Date());
		return mapper.updateByPrimaryKey(t);
	}

	/**
	 * 
	 * @Title: updateSelective   
	 * @Description:  更新（只是更新新的model中不为空的字段。null的不操作）
	 * @param: @param record
	 * @param: @return      
	 * @return: Integer 影响条数        
	 * @throws
	 */
	public Integer updateSelective(T t){
		t.setUpdated(new Date());
		t.setCreated(null);//防止被更新创建时间，理论上创建时间是不能被更新的
		return mapper.updateByPrimaryKeySelective(t);
	}
	
	
	/**
	 * 
	 * @Title: deleteById   
	 * @Description: 根据主键ID删除数据 （物理删除，逻辑删除调用update即可）
	 * @param: @param id
	 * @param: @return      
	 * @return: Interger  影响条数   
	 * @throws
	 */
	public Integer deleteById(Long id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 
	 * @Title: deleteByIds   
	 * @Description: 根据主键ID删除数据
	 * @param: @param ids 主键id
	 * @param: @param clazz Pojo实体对象
	 * @param: @param property 主机id对应的数据库字段名称
	 * @param: @return   
	 * @return: Integer  影响条数       
	 * @throws
	 */
	public Integer deleteByIds(List<Object> ids,Class<T> clazz,String property){
		 Example example = new Example(clazz);
		 //设置条件
		 example.createCriteria().andIn(property, ids);		 
		 return mapper.deleteByExample(example);
	}
	
	
	/**
	 * 
	 * @Title: deleteByWhere   
	 * @Description: 
	 * @param: @param record 条件
	 * @param: @return      
	 * @return: Integer   影响条数     
	 * @throws
	 */
	public Integer deleteByWhere(T record){
		
		return mapper.delete(record);
	}
	
}
