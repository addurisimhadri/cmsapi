package com.sim.wicmsapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.ContentDeviceRepository;
import com.sim.wicmsapi.entity.ContentDevice;
import com.sim.wicmsapi.service.ContentDeviceService;
@Service
public class ContentDeviceServiceImpl implements ContentDeviceService {

	@Autowired
	ContentDeviceRepository contentDeviceRepository;
	@Override
	public ContentDevice save(ContentDevice contentDevice) {
		return contentDeviceRepository.save(contentDevice);
	}

	@Override
	public List<ContentDevice> getContentDevices(int contId, int ctTypeId) {
		return contentDeviceRepository.getContentDevice(contId, ctTypeId);
	}

	@Override
	public ContentDevice getCD(int contId, int ctTypeId, String contFileName) {
		return contentDeviceRepository.getCD(contId, ctTypeId, contFileName);
	}

	@Override
	public void deleteContentDevice(int contId, int ctTypeId) {
		contentDeviceRepository.deleteContentDevice(contId, ctTypeId);
	}

}
