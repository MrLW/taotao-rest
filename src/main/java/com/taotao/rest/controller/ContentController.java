package com.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService ;
	// 获取内容列表
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable Long cid){
		
		try {
			List<TbContent> list = contentService.getContentList(cid);
			return TaotaoResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace() ;
//			保存错误信息
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e)) ;
		}
	}
	
	// 同步缓存
	@RequestMapping("/content/sync/{cid}")
	public TaotaoResult syncContent(@PathVariable Long cid){
		try {
			System.out.println("cid:" + cid); 
			TaotaoResult result = contentService.syncContent(cid);
			return result ;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
	
}
