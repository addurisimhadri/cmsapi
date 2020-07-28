package com.sim.wicmsapi.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.service.ContentService;
import com.sim.wicmsapi.vo.ApiResponse;
import com.sim.wicmsapi.vo.ContentDTO;


@CrossOrigin
@RestController
@RequestMapping(value = "/cont")
public class ContentController {
	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
	@Autowired
	ContentService contentService;
	
	@GetMapping(value="/approvable/{ctId}/getAll")
	public Iterable<ContentDTO> getAppContents(@PathVariable("ctId") int ctId){
		List<ContentDTO> contentDTOs=new ArrayList<>();
		List<Content> contents= contentService.getContentByCT(ctId);
		logger.info("============="+contents.size());
		Iterator<Content> it=contents.iterator();
		while (it.hasNext()) {
			Content content =  it.next();
			ContentDTO contentDTO=convertObjToX(content, new TypeReference<ContentDTO>(){});
			contentDTOs.add(contentDTO);
		}
		logger.info("====contentDTOs========="+contentDTOs.size());
		return contentDTOs;
	}
	@GetMapping(value="/approved/{ctId}/getAll")
	public Iterable<ContentDTO> getContents(@PathVariable("ctId") int ctId){
		List<ContentDTO> contentDTOs=new ArrayList<>();
		List<Content> contents= contentService.getApprovedContentByCT(ctId);
		logger.info("============="+contents.size());
		Iterator<Content> it=contents.iterator();
		while (it.hasNext()) {
			Content content =  it.next();
			ContentDTO contentDTO=convertObjToX(content, new TypeReference<ContentDTO>(){});
			contentDTOs.add(contentDTO);
		}
		logger.info("====contentDTOs========="+contentDTOs.size());
		return contentDTOs;
	}
	
	
	@PostMapping(value = "/updatestatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<Void> updateStatus(@RequestBody Map payload) {
		JSONObject root = new JSONObject( payload);
		List<ContentDTO> contentDTOs=new ArrayList<ContentDTO>();
        JSONArray dataArray = root.getJSONArray("contents");
        for (int t=0; t<dataArray.length(); t++) {
            JSONObject jsonObj = dataArray.getJSONObject(t);
           ContentDTO contentDTO=convertJtoObj(jsonObj.toString());
           contentDTOs.add(contentDTO);
        }
        contentService.updateStatus(contentDTOs);
        //logger.info(""+contentDTOs);
        return new ApiResponse<>(HttpStatus.OK.value(),"Employee update Status successfully.",null);
	}
	
	public  static ContentDTO convertObjToX(Object o, TypeReference<ContentDTO> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
	
	public static ContentDTO convertJtoObj(String jsonString) {
		 Gson gson = new Gson(); 
		return gson.fromJson(jsonString, ContentDTO.class);
	}

}
