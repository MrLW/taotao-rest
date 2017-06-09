package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY ;
	
	@Value("${REDIS_ITEM_BASE_INFO_KEY}")
	private String REDIS_ITEM_BASE_INFO_KEY ;
	@Value("${REDIS_ITEM_DESC_KEY}")
	private String REDIS_ITEM_DESC_KEY ;
	@Value("${REDIS_ITEM_PARAM_KEY}")
	private String REDIS_ITEM_PARAM_KEY ;
	
	@Value("${REDIS_ITEM_EXPIRE_SECOND}")
	private Integer REDIS_ITEM_EXPIRE_SECOND ;
	
	@Override
	public TbItem getItemById(Long itemId) {
		
		try {
			// 从缓存中查找
			String json = jedisClient.get(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_BASE_INFO_KEY);
			if (StringUtils.isNotBlank(json)) { // 不為空
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			} 
		} catch (Exception e) {
			// 不影响正常的业务逻辑
			e.printStackTrace(); 
		}
		// 缓存中没有
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		
		try {
			// 添加缓存
			jedisClient.set(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_BASE_INFO_KEY,
					JsonUtils.object2Json(tbItem));
			// 设置过期时间
			jedisClient.expire(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_BASE_INFO_KEY, REDIS_ITEM_EXPIRE_SECOND) ;
		} catch (Exception e) {
			// 不影响正常的业务逻辑
			e.printStackTrace(); 
		}
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		try {
			// 从缓存中查找
			String json = jedisClient.get(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_DESC_KEY);
			if (StringUtils.isNotBlank(json)) { // 不為空
				TbItemDesc tbDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbDesc;
			} 
		} catch (Exception e) {
			// 不影响正常的业务逻辑
			e.printStackTrace(); 
		}
		// 缓存中没有
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			// 添加缓存
			jedisClient.set(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_DESC_KEY,
					JsonUtils.object2Json(itemDesc));
			// 设置过期时间
			jedisClient.expire(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_DESC_KEY, REDIS_ITEM_EXPIRE_SECOND) ;
		} catch (Exception e) {
			// 不影响正常的业务逻辑
			e.printStackTrace(); 
		}
		return itemDesc;
	}

	@Override
	public TbItemParamItem getItemParamById(Long itemId) {
		try {
			// 从缓存中查找
			String json = jedisClient.get(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_PARAM_KEY);
			if (StringUtils.isNotBlank(json)) { // 不為空
				TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return tbItemParamItem;
			} 
		} catch (Exception e) {
			// 不影响正常的业务逻辑
			e.printStackTrace(); 
		}
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		// 缓存中没有====查询数据库
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		TbItemParamItem itemParamItem = null ;
		if(list != null && list.size() > 0 ){
			itemParamItem = list.get(0) ;
		}
		
		try {
			if(itemParamItem != null ){// 只有在itemParamItem不为null时才设置缓存
				// 添加缓存
				jedisClient.set(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_PARAM_KEY,
						JsonUtils.object2Json(itemParamItem));
				// 设置过期时间
				jedisClient.expire(itemId + ":" + REDIS_ITEM_KEY + ":" + REDIS_ITEM_PARAM_KEY, REDIS_ITEM_EXPIRE_SECOND) ;
			}
		} catch (Exception e) {
			// 不影响正常的业务逻辑
			e.printStackTrace(); 
		}
		return itemParamItem;
	}

}
