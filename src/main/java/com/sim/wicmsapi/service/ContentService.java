package com.sim.wicmsapi.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.vo.ContentDTO;

public interface ContentService {
	
	public Content save(Content content);
	public Content findContent(@Param("name") String name, @Param("cpId") int cpId, @Param("ctTypeId") int ctTypeId);
	public List<Content> getContentByCT(int ctTypeId);
	Content findContentCT(int contId,int ctTypeId);
	void updateStatus(List<ContentDTO> contentDTOs);
	public List<Content> getApprovedContentByCT(int ctTypeId);
	public void delete(List<ContentDTO> contentDTOs);

}
