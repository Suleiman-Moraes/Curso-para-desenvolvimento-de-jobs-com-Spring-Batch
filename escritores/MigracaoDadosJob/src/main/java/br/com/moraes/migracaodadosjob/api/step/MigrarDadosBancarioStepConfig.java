package br.com.moraes.migracaodadosjob.api.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.moraes.migracaodadosjob.api.model.DadosBancario;

@Configuration
public class MigrarDadosBancarioStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step migrarDadosBancarioStep(
			ItemReader<DadosBancario> arquivoDadosBancarioReader,
			ItemWriter<DadosBancario> bancoDadosBancarioWriter) {
		return stepBuilderFactory.get("migrarDadosBancarioStep")
				.<DadosBancario, DadosBancario> chunk(50)
				.reader(arquivoDadosBancarioReader)
				.writer(bancoDadosBancarioWriter)
				.build();
	}
}
