package com.taotao.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class TbItemCatController {

	@Autowired
	private ItemCatService itemCatService ;
	// 使用jsonp的两种方法
	//第一种:返回string
	/*@RequestMapping(value="/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback){
		ItemCatResult itemCatList = itemCatService.getItemCatList();
		if(StringUtils.isBlank(callback)){
			return JsonUtils.object2Json(itemCatList);
		}
		String json = JsonUtils.object2Json(itemCatList);
		json = callback + "(" + json  + ");" ;
		//注意，直接返回字符串会有乱码，因为浏览器会对字符串解析成json
		return json;
	}*/
	
	//第二种方法:使用MappingJacksonValue进行包装
	@RequestMapping(value="/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		ItemCatResult itemCatList = itemCatService.getItemCatList();
		if(StringUtils.isBlank(callback)){
			return itemCatList;
		}
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(itemCatList);
		mappingJacksonValue.setJsonpFunction(callback);
		//注意，直接返回字符串会有乱码，因为浏览器会对字符串解析成json
		return mappingJacksonValue;
	}
}
