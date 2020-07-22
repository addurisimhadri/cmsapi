package com.sim.wicmsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.wicmsapi.entity.ContentProvider;

public interface ContentProviderRepository extends JpaRepository<ContentProvider, Integer> {

	ContentProvider getByCpName(String cpName);	

}
