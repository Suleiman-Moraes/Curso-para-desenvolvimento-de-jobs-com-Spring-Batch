package br.com.moraes.migracaodadosjob.api.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.moraes.migracaodadosjob.api.model.Pessoa;

@Configuration
public class PessoaClassifierWriterConfig {

	@Bean
	public ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter(
			JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
			FlatFileItemWriter<Pessoa> arquivoPessoasInvalidaWriter) {
		return new ClassifierCompositeItemWriterBuilder<Pessoa>()
				.classifier(classifier(bancoPessoaWriter, arquivoPessoasInvalidaWriter))
				.build();
	}

	private Classifier<Pessoa, ItemWriter<? super Pessoa>> classifier(JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
			FlatFileItemWriter<Pessoa> arquivoPessoasInvalidaWriter) {
		return pessoa -> {
			if(pessoa.isValida()) {
				return bancoPessoaWriter;
			}
			return arquivoPessoasInvalidaWriter;
		};
	}
}
