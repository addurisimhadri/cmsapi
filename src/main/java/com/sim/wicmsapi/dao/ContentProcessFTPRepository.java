package com.sim.wicmsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.wicmsapi.entity.ContentProcessFTP;
import com.sim.wicmsapi.entity.ContentProcessFTPId;

@Repository
public interface ContentProcessFTPRepository extends JpaRepository<ContentProcessFTP, ContentProcessFTPId> {

}
