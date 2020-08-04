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
public class GameContentId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gmId;
	private int contentId;
	
	
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
