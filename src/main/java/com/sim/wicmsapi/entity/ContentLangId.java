package com.sim.wicmsapi.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContentLangId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;

	
	@Override
    public int hashCode() {
        return Objects.hash(getId()+(getCode().length()));
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentLang)) return false;
        ContentLang contentLang = (ContentLang) o;
        return Objects.equals(getId()+getCode(), contentLang.getId()+contentLang.getCode());
    }
	
}
