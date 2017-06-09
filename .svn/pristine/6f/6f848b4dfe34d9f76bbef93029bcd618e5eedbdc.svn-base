package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY ;
	
	@Autowired
	private TbContentMapper contentMapper; 
	
	@Override
	public List<TbContent> getContentList(Long cid) {
		// 这里应该查询redis缓存,有则从缓存中取
		try {
			String json = jedisClient.hget(REDIS_CONTENT_KEY, cid + "");
			if (!StringUtils.isBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 正常查询数据库逻辑
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example );
		// 将查询到的数据放入redis缓存
		try {
			jedisClient.hset(REDIS_CONTENT_KEY, cid + "", JsonUtils.object2Json(list));
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return list;
	}

	@Override
	public TaotaoResult syncContent(Long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid + "") ;
		return TaotaoResult.ok();
	}

}
