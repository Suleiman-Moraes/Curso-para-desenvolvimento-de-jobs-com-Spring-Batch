package br.com.suleimanmoraes.demonstrativoorcamentariojob.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import br.com.suleimanmoraes.demonstrativoorcamentariojob.dominio.Lancamento;

@Configuration
public class LeituraMultiplosArquivosDelimitadoReaderConfig {

	@StepScope
	@Bean
	public MultiResourceItemReader<Lancamento> multiplosArquivosReader(
			@Value("#{jobParameters['arquivos']}") Resource[] arquivoClientes,
			FlatFileItemReader<Lancamento> leituraArquivoMultiplosFormatosBuilder) {
		return new MultiResourceItemReaderBuilder<Lancamento>().name("multiplosArquivosOrcamentoReader")
				.resources(arquivoClientes)
				.delegate(leituraArquivoMultiplosFormatosBuilder)
				.build();
	}
}
