package br.com.suleimanmoraes.demonstrativoorcamentariojob.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import br.com.suleimanmoraes.demonstrativoorcamentariojob.dominio.Lancamento;

@Configuration
public class LeituraArquivoDelimitadoReaderConfig {

	@StepScope
	@Bean
	public FlatFileItemReader<Lancamento> leituraArquivoDelimitadoReader(
			@Value("#{jobParameters['arquivo']}") Resource arquivoClientes) {
		return new FlatFileItemReaderBuilder<Lancamento>().name("leituraArquivoDelimitadoOrcamentoReader")
				.resource(arquivoClientes).delimited().names(new String[] { "codigoNaturezaDespesa",
						"descricaoNaturezaDespesa", "descricaoLancamento", "dataLancamento", "valorLancamento" })
				.targetType(Lancamento.class).build();
	}
}
