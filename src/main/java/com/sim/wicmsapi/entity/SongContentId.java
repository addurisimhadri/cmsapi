package com.sim.wicmsapi.entity;

import java.io.Serializable;
import java.util.Objects;

public class SongContentId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int smId;
	private int contentTypeId;
	
	public int getSmId() {
		return smId;
	}
	public void setSmId(int smId) {
		this.smId = smId;
	}
	public int getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	
	
	@Override
    public int hashCode() {
        return Objects.hash(getSmId()+(getContentTypeId()));
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongMeta)) return false;
        SongMeta songMeta = (SongMeta) o;
        return Objects.equals(getSmId()+(getContentTypeId()), songMeta.getSmId()+songMeta.getContentTypeId());
    }


}
