package br.com.suleimanmoraes.contasbancariasjob.api.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.suleimanmoraes.contasbancariasjob.api.model.Cliente;

@Configuration
public class ContasBancariasWriterConfig {

	@Bean
	public ItemWriter<Cliente> contasBancariasWriterWriter() {
		return clientes -> clientes.forEach(System.out::println);
	}
}
