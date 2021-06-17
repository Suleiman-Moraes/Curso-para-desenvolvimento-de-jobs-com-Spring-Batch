package br.com.suleimanmoraes.demonstrativoorcamentariojob.step;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeituraArquivoDelimitadoStepConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

//	@Bean
//	public Step leituraArquivoDelimitadoStep(MultiResourceItemReader<Lancamento> multiResourceItemReader,
//			ItemWriter<Lancamento> leituraArquivoDelimitadoWriter) {
//		return stepBuilderFactory
//				.get("leituraArquivoDelimitadoOrcamentoStep")
//				.<Lancamento, Lancamento>chunk(50)
//				.reader(multiResourceItemReader)
//				.writer(leituraArquivoDelimitadoWriter)
//				.build();
//	}
}
