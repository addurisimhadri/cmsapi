package com.sim.wicmsapi.entity;

import java.io.Serializable;
import java.util.Objects;

public class GameContentId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gmId;
	private int contentId;
	public int getGmId() {
		return gmId;
	}
	public void setGmId(int gmId) {
		this.gmId = gmId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(getGmId()+(getContentId()));
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameMeta)) return false;
        GameMeta gameMeta = (GameMeta) o;
        return Objects.equals(getGmId()+(getContentId()), gameMeta.getGmId()+gameMeta.getContentId());
    }
}
