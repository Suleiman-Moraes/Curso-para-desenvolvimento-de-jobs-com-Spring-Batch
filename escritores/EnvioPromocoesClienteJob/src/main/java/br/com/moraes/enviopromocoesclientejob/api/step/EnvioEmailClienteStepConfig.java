package br.com.moraes.enviopromocoesclientejob.api.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

import br.com.moraes.enviopromocoesclientejob.api.model.InteresseProdutoCliente;

@Configuration
public class EnvioEmailClienteStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step envioEmailClienteStep(
			ItemStreamReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader,
			ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> processarEmailProdutoClienteProcessor,
			ItemWriter<SimpleMailMessage> enviarEmailProdutoClienteWriter) {
		return stepBuilderFactory.get("envioEmailClienteStep")
				.<InteresseProdutoCliente, SimpleMailMessage> chunk(1)
				.reader(lerInteresseProdutoClienteReader)
				.processor(processarEmailProdutoClienteProcessor)
				.writer(enviarEmailProdutoClienteWriter)
				.build();
	}
}
