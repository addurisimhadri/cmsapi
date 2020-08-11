package com.sim.wicmsapi.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.wicmsapi.batch.processor.ContentProcessFTPProcessor;
import com.sim.wicmsapi.batch.reader.ContentProcessFTPReader;
import com.sim.wicmsapi.batch.writer.ContentProcessFTPWriter;
import com.sim.wicmsapi.entity.ContentProcessFTP;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private ContentProcessFTPProcessor processor;

	@Autowired
	private ContentProcessFTPReader reader;
	
	@Autowired
	private ContentProcessFTPWriter writer;

	@Autowired(required = true)
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job informUser(JobBuilderFactory jobBuilderFactory) {
		return this.jobBuilderFactory.get("informUser").start(fetchContFTP()).build();
	}

	private Step fetchContFTP() {
		return stepBuilderFactory.get("fetchTransaction").<ContentProcessFTP, ContentProcessFTP>chunk(10).reader(reader)
				.processor(processor).writer(writer).build();
	}
}
