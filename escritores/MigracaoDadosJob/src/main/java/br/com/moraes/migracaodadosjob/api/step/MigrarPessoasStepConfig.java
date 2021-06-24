package br.com.moraes.migracaodadosjob.api.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.moraes.migracaodadosjob.api.model.Pessoa;

@Configuration
public class MigrarPessoasStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step migrarPessoasStep(
			ItemReader<Pessoa> arquivoPessoaReader,
			ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter,
			FlatFileItemWriter<Pessoa> arquivoPessoasInvalidaWriter) {
		return stepBuilderFactory.get("migrarPessoasStep")
				.<Pessoa, Pessoa> chunk(50)
				.reader(arquivoPessoaReader)
				.writer(pessoaClassifierWriter)
				.stream(arquivoPessoasInvalidaWriter)
				.build();
	}
}
