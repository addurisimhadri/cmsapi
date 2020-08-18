package com.sim.wicmsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.wicmsapi.entity.GameContentId;
import com.sim.wicmsapi.entity.GameMeta;
@Repository
public interface GameMetaContentRepository extends JpaRepository<GameMeta, GameContentId> {
	
	GameMeta findByGmIdAndContentId(int gmId, int contentId);
}
