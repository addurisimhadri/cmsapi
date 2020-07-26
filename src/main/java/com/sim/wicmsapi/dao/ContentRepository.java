package com.sim.wicmsapi.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sim.wicmsapi.entity.Content;
import com.sim.wicmsapi.entity.ContentId;

public interface ContentRepository extends JpaRepository<Content, ContentId> {

	@Query("select c from Content c where name=:name and cpId=:cpId and ctTypeId=:ctTypeId")
	Content findContent(@Param("name") String name, @Param("cpId") int cpId, @Param("ctTypeId") int ctTypeId);
	@Query("select c from Content c where ctTypeId=:ctTypeId and status=1")
	List<Content> findByCtTypeId(int ctTypeId,Pageable pageable);
	Content findByContIdAndCtTypeId(int contId, int ctTypeId);
	
	
}
