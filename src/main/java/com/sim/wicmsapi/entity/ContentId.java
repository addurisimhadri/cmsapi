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
public class ContentId  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int contId;
	private int ctTypeId;
	
	@Override
    public int hashCode() {
        return Objects.hash(getContId()+(getCtTypeId()));
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;
        Content content = (Content) o;
        return Objects.equals(getContId()+(getCtTypeId()), content.getContId()+content.getCtTypeId());
    }

}
