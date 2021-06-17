package br.com.suleimanmoraes.demonstrativoorcamentariojob.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.suleimanmoraes.demonstrativoorcamentariojob.dominio.Lancamento;

@Configuration
public class JdbcPagingReaderStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step jdbcPagingReaderStep(ItemReader<Lancamento> jdbcPagingReader, ItemWriter<Lancamento> jdbcPagingWriter) {
		return stepBuilderFactory
				.get("jdbcPagingReaderStep")
				.<Lancamento, Lancamento>chunk(13)
				.reader(jdbcPagingReader)
				.writer(jdbcPagingWriter)
				.build();
	}
}
