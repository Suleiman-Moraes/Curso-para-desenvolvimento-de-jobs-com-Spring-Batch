package br.com.suleimanmoraes.contasbancariasjob.api.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import br.com.suleimanmoraes.contasbancariasjob.api.enums.TipoContaEnum;
import br.com.suleimanmoraes.contasbancariasjob.api.model.Cliente;
import br.com.suleimanmoraes.contasbancariasjob.api.model.Conta;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ContasBancariasProcessorConfig {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public ItemProcessor<Cliente, Cliente> contasBancariasProcessor() throws Exception {
		return new CompositeItemProcessorBuilder<Cliente, Cliente>().delegates(validatingProcessor()).build();
	}

	private ValidatingItemProcessor<Cliente> validatingProcessor() {
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<>();
		processor.setValidator(validator());
		processor.setFilter(Boolean.TRUE);
		return processor;
	}

	private Validator<Cliente> validator() {
		return cliente -> {
			final TipoContaEnum tipo = TipoContaEnum.get(cliente.getFaixaSalarial());
			Conta conta = Conta.builder().tipo(tipo.toString()).limite(tipo.getLimite()).clienteId(cliente.getId())
					.build();
			insert(conta);
		};
	}

	@Transactional
	private void insert(Conta conta) {
		try {
			jdbcTemplate.update("INSERT INTO conta (tipo, limite, cliente_id) VALUES (?, ?, ?)", conta.getTipo(),
					conta.getLimite(), conta.getClienteId());
		} catch (Exception e) {
			log.warn("insert " + e.getMessage());
			throw e;
		}
	}
}
