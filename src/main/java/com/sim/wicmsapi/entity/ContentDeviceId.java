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
public class ContentDeviceId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int contId;
	private int ctTypeId;
	private String contFileName;
	@Override
    public int hashCode() {
        return Objects.hash(getContId()+getCtTypeId()+getContFileName());
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentDevice)) return false;
        ContentDevice contentDevice = (ContentDevice) o;
        return Objects.equals(getContId()+getCtTypeId()+getContFileName(), contentDevice.getContId()+contentDevice.getCtTypeId()+contentDevice.getContFileName());
    }

}
