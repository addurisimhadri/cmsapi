package com.sim.wicmsapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sim.wicmsapi.entity.ContentDevice;
import com.sim.wicmsapi.entity.ContentDeviceId;

public interface ContentDeviceRepository extends JpaRepository<ContentDevice, ContentDeviceId> {
	
	@Query("delete ContentDevice where contId=:contId and ctTypeId=:ctTypeId ")
	void deleteContentDevice(int contId,int ctTypeId);
	@Query("select cd from ContentDevice cd where  contId=:contId and ctTypeId=:ctTypeId")
	List<ContentDevice> getContentDevice(int contId,int ctTypeId);
	@Query("select cd from ContentDevice cd where  contId=:contId and ctTypeId=:ctTypeId and contFileName=:contFileName")
	ContentDevice getCD(int contId,int ctTypeId,String contFileName);
	
	
}
