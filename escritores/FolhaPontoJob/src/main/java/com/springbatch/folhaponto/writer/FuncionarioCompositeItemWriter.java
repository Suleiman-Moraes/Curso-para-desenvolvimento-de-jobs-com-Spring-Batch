package com.springbatch.folhaponto.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.folhaponto.dominio.FolhaPonto;

@Configuration
public class FuncionarioCompositeItemWriter {
	
	@Bean
	public ClassifierCompositeItemWriter<FolhaPonto> classifierFuncionarioWriter(
			@Qualifier("funcionarioSemPontoWriter") FlatFileItemWriter<FolhaPonto> funcionarioSemPontoWriter,
			@Qualifier("imprimeFolhaPontoWriter") FlatFileItemWriter<FolhaPonto> imprimeFolhaPontoWriter) {
		return new ClassifierCompositeItemWriterBuilder<FolhaPonto>()
				.classifier(classifier(funcionarioSemPontoWriter, imprimeFolhaPontoWriter)).build();
	}

	private Classifier<FolhaPonto, ItemWriter<? super FolhaPonto>> classifier(
			FlatFileItemWriter<FolhaPonto> funcionarioSemPontoWriter, 
			FlatFileItemWriter<FolhaPonto> imprimeFolhaPontoWriter) {
		return f -> {
			if (f.getNome() != null) {
				return imprimeFolhaPontoWriter;
			}
			return funcionarioSemPontoWriter;
		};
	}
}
