package com.sim.wicmsapi.utility;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.wicmsapi.entity.PhysicalFolder;
import com.sim.wicmsapi.vo.PhysicalFolderDTO;

public class PhysicalFolderUtility {
	private static final Logger logger = LoggerFactory.getLogger(PhysicalFolderUtility.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	public static void convertListEtoVo(List<PhysicalFolder> physicalFolders, List<PhysicalFolderDTO> physicalFolderDTOs) {
		
		try {
			Iterator<PhysicalFolder> it=physicalFolders.iterator();
			while (it.hasNext()) {
				PhysicalFolder physicalFolder =  it.next();
				PhysicalFolderDTO physicalFolderDTO=convertEToObj(physicalFolder, new TypeReference<PhysicalFolderDTO>(){});
				physicalFolderDTOs.add(physicalFolderDTO);				
			}
			
		} catch (Exception e) {
			logger.error(myMarker, " {} ",e.getMessage());
		}
		
	}
	public static PhysicalFolder convertObjToE(Object o, TypeReference<PhysicalFolder> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
	public static PhysicalFolderDTO convertEToObj(Object o, TypeReference<PhysicalFolderDTO> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
}
