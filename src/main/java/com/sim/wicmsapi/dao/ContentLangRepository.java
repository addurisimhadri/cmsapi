package com.sim.wicmsapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sim.wicmsapi.entity.ContentLang;
import com.sim.wicmsapi.entity.ContentLangId;

@Repository
public interface ContentLangRepository extends JpaRepository<ContentLang, ContentLangId> {
	@Query("select c from ContentLang c where active=1 ")
	public List<ContentLang> getActiveOnly();
}
