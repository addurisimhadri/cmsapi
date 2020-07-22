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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class ContentProcessFTPId implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  int cpfId;
	private  int contentTypeId;
	
	@Override
    public int hashCode() {
        return Objects.hash(getCpfId()+(getContentTypeId()));
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentProcessFTP)) return false;
        ContentProcessFTP contentProcessFTP = (ContentProcessFTP) o;
        return Objects.equals(getCpfId()+(getContentTypeId()), contentProcessFTP.getCpfId()+contentProcessFTP.getContentTypeId());
    }
	
	

}
