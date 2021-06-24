package br.com.moraes.migracaodadosjob.api.reader;

import java.util.Date;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import br.com.moraes.migracaodadosjob.api.model.Pessoa;

@Configuration
public class ArquivoPessoaReaderConfig {

	@Bean
	public FlatFileItemReader<Pessoa> arquivoPessoaReader(){
		return new FlatFileItemReaderBuilder<Pessoa>().name("arquivoPessoaReader")
				.resource(new FileSystemResource("files/pessoas.csv"))
				.delimited()
				.names("nome", "email", "dataNasciemento", "idade", "id")
				.addComment("--")
				.fieldSetMapper(fieldSetMapper())
				.build();
	}

	private FieldSetMapper<Pessoa> fieldSetMapper() {
		return fieldSet -> new Pessoa(fieldSet.readInt("id"), 
				fieldSet.readString("nome"), 
				fieldSet.readString("email"), 
				new Date(fieldSet.readDate("dataNasciemento", "yyyy-MM-dd HH:mm:ss").getTime()), 
				fieldSet.readInt("idade"));
	}
}
