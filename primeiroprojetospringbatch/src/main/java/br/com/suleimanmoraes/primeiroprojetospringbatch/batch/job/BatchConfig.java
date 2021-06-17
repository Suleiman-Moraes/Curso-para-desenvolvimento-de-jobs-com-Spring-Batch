package br.com.suleimanmoraes.primeiroprojetospringbatch.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean
	public Job imprimirOlaJob(Step imprimeOlaStep){
		return jobBuilderFactory.get("imprimirOlaJob").start(imprimeOlaStep).incrementer(new RunIdIncrementer()).build();
	}
}
