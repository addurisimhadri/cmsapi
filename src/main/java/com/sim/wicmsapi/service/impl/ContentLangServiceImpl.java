package com.sim.wicmsapi.service.impl;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentLangRepository;
import com.sim.wicmsapi.entity.ContentLang;
import com.sim.wicmsapi.service.ContentLangService;

@Service
public class ContentLangServiceImpl implements ContentLangService {
	@Autowired
	ContentLangRepository contentLangRepository;
	
	@Override
	public Map<String,String> getLangMap() {
		Map<String,String> contentLangMap = new Hashtable<String,String>();
		List<ContentLang> list= contentLangRepository.getActiveOnly();
		Iterator<ContentLang> it=list.iterator();		
		while (it.hasNext()) {
			ContentLang contentLang = (ContentLang) it.next();	
			contentLangMap.put( contentLang.getCode(),contentLang.getName());
		}			
		return contentLangMap;
	}

}
