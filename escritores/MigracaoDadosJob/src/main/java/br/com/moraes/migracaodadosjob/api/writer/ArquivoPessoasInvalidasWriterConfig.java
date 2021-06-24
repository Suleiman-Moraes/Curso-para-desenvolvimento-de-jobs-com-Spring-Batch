package br.com.moraes.migracaodadosjob.api.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import br.com.moraes.migracaodadosjob.api.model.Pessoa;

@Configuration
public class ArquivoPessoasInvalidasWriterConfig {

	@Bean
	public FlatFileItemWriter<Pessoa> arquivoPessoasInvalidaWriter(){
		return new FlatFileItemWriterBuilder<Pessoa>().name("arquivoPessoasInvalidaWriter")
				.resource(new FileSystemResource("files/pessoas_invalidas.csv"))
				.delimited()
				.names("id")
				.build();
	}
}
