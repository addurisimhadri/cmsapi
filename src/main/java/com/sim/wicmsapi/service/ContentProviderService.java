package com.sim.wicmsapi.service;

import java.util.List;

import com.sim.wicmsapi.entity.ContentProvider;

public interface ContentProviderService {
	public ContentProvider getContentProvider(int cpId);
	public ContentProvider getContentProvider(String cpName);
	public List<ContentProvider> getCPs();

}
