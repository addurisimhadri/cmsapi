package com.sim.wicmsapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sim.wicmsapi.entity.ContentType;

@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
	
	 @Query("SELECT ct FROM ContentType ct WHERE active=:active and ct.contentId > :ctTypeId and ct.contentId!=41 and ct.contentId < 90")
	  List<ContentType> findByContentIdGreaterThanQuery(@Param("ctTypeId") long ctTypeId,@Param("active") String active);
	 
	 @Query("SELECT ct FROM ContentType ct WHERE active='Y' and ct.contentId < 90")
	  List<ContentType> getContentTypes();
	 
	 
}
