package com.sim.wicmsapi.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.sim.wicmsapi.vo.PropertiesBean;


@Configuration
@ComponentScan(basePackages = "com.sim.wicmsapi")
@PropertySource("classpath:/resizeimage.properties")
public class PropertiesConfig {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	
	@Bean
	@Qualifier("propertiesBean")
	public PropertiesBean getPropertiesBean( Environment env) {
		PropertiesBean propertiesBean=new PropertiesBean();
		propertiesBean.setExPath(env.getProperty("IMAGEMAGIC_PATH"));
		propertiesBean.setWeh(env.getProperty("RES_WIDTH_EQUAL_TO_HEIGHT"));
		propertiesBean.setWgh(env.getProperty("RES_WIDTH_GREATERTHAN_HEIGHT"));
		propertiesBean.setWlh(env.getProperty("RES_WIDTH_LESSTHAN_HEIGHT"));
		propertiesBean.setW5h(env.getProperty("reSizes960x570"));
		propertiesBean.setW7h(env.getProperty("reSizes960x760"));
		logger.info(myMarker, "propertiesBean ========================================== {} ",propertiesBean);	
		return propertiesBean;
	}
	
	

}
