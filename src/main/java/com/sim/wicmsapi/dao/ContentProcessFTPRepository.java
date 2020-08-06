package com.sim.wicmsapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.ContentProcessFTPId;

@Repository
public interface ContentProcessFTPRepository extends JpaRepository<ContentProcessFTP, ContentProcessFTPId> {
	
	List<ContentProcessFTP> findByContentTypeId(int contentTypeId);
	
	@Query("select cpf  from ContentProcessFTP cpf where processStatus='In Queue' and submitStatus='Completed' order by lastUpdatedTimestamp" )
	List<ContentProcessFTP> findData();

}
