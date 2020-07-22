package com.sim.wicmsapi.service;

import java.util.List;
import java.util.Optional;

import com.sim.wicmsapi.entity.ContentType;

public interface ContentTypeService {

	public Optional<ContentType> getContentType(int contentId);
	public List<ContentType> getCTs();
	public ContentType save(ContentType contentType);
	public List<ContentType> getCTs(long ctTypeId,String active);
}
