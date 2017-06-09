package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.ItemCatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper; 
	
	@Override
	public ItemCatResult getItemCatList() {
		List itemCatList = getItemCatList(0l);
		ItemCatResult catResult = new ItemCatResult() ;
		catResult.setData(itemCatList); 
		return catResult;
	}
	
	private List getItemCatList(Long parentId){
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//根据parentId查询列表
		List<TbItemCat> list = itemCatMapper.selectByExample(example );
		List resultList = new ArrayList<>() ;
		for (TbItemCat tbItemCat : list) {
			
			//判断叶子节点
			if(tbItemCat.getIsParent()){ // 不是叶子节点
				ItemCatNode itemCatNode = new ItemCatNode() ;
				itemCatNode.setUrl("/products/"+tbItemCat.getId()+".html");
				//判断是否为一级节点
				if(tbItemCat.getParentId() == 0 ){
					itemCatNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else{
					itemCatNode.setName(tbItemCat.getName());
				}
				//递归掉用
				itemCatNode.setItems(getItemCatList(tbItemCat.getId()));
				resultList.add(itemCatNode);
			}else{// 是叶子节点
				String item = "/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName() ;
				resultList.add(item);
			}
		}
		
		return resultList ;
	}

	
}
