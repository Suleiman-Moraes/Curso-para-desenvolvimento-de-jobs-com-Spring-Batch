package br.com.moraes.faturacartaocreditojob.api.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.moraes.faturacartaocreditojob.api.model.FaturaCartaoCredito;
import br.com.moraes.faturacartaocreditojob.api.model.Transacao;
import br.com.moraes.faturacartaocreditojob.api.reader.FaturaCartaoCreditoReader;
import br.com.moraes.faturacartaocreditojob.api.writer.TotalTransacoesFooterCallback;

@Configuration
public class FaturaCartaoCreditoStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemStreamReader<Transacao> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCreditoWriter,
			TotalTransacoesFooterCallback totalTransacoesFooterCallback) {
		return stepBuilderFactory.get("faturaCartaoCreditoStep")
				.<FaturaCartaoCredito, FaturaCartaoCredito> chunk(1)
				.reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
				.processor(carregarDadosClienteProcessor)
				.writer(escreverFaturaCartaoCreditoWriter)
				.listener(totalTransacoesFooterCallback)
				.build();
	}
}
