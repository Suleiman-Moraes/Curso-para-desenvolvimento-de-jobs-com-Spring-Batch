package br.com.moraes.migracaodadosjob.api.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@EnableBatchProcessing
@Configuration
public class MigracaoDadosJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean
	public Job migracaoDadosJob(@Qualifier("migrarPessoasStep") Step migrarPessoasStep,
			@Qualifier("migrarDadosBancarioStep") Step migrarDadosBancarioStep) {
		return jobBuilderFactory.get("migracaoDadosJob")
				.start(stepsParalelos(migrarPessoasStep, migrarDadosBancarioStep))
				.end()
				.incrementer(new RunIdIncrementer())
				.build();
	}

	private Flow stepsParalelos(Step migrarPessoasStep, Step migrarDadosBancarioStep) {
		Flow migrarDadosBancarioFlow = new FlowBuilder<Flow>("migrarDadosBancarioFlow")
				.start(migrarDadosBancarioStep)
				.build();
		return new FlowBuilder<Flow>("stepsParalelosFlow")
				.start(migrarPessoasStep)
				.split(new SimpleAsyncTaskExecutor())
				.add(migrarDadosBancarioFlow)
				.build();
	}
}
