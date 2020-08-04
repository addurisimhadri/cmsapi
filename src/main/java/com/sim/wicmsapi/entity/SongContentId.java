package com.sim.wicmsapi.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SongContentId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int smId;
	private int contentTypeId;
	
	
	
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
