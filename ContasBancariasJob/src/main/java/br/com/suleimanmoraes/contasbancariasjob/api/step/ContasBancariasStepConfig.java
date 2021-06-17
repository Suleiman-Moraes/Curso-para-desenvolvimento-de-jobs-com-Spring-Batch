package br.com.suleimanmoraes.contasbancariasjob.api.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.suleimanmoraes.contasbancariasjob.api.model.Cliente;

@Configuration
public class ContasBancariasStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step contasBancariasStep(ItemReader<Cliente> contasBancarias, 
			ItemProcessor<Cliente, Cliente> contasBancariasProcessor,
			ItemWriter<Cliente> jdbcPagingWriter) {
		return stepBuilderFactory
				.get("contasBancariasStep")
				.<Cliente, Cliente>chunk(5)
				.reader(contasBancarias)
				.processor(contasBancariasProcessor)
				.writer(jdbcPagingWriter)
				.build();
	}
}
