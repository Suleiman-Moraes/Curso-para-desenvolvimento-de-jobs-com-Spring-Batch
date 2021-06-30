package br.com.moraes.faturacartaocreditojob.api.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import br.com.moraes.faturacartaocreditojob.api.model.CartaoCredito;
import br.com.moraes.faturacartaocreditojob.api.model.Cliente;
import br.com.moraes.faturacartaocreditojob.api.model.Transacao;

@Configuration
public class LerTransacoesReaderConfig {

	@Bean
	public JdbcCursorItemReader<Transacao> lerTransacoesReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Transacao>()
				.name("lerTransacoesReader")
				.dataSource(dataSource)
				.sql("SELECT * FROM transacao JOIN cartao_credito USING (numero_cartao_credito) ORDER BY numero_cartao_credito")
				.rowMapper(rowMapperTransacao())
				.build();
	}

	private RowMapper<Transacao> rowMapperTransacao() {
		return (rs, r) -> {
			CartaoCredito cartaoCredito = new CartaoCredito();
			cartaoCredito.setNumeroCartaoCredito(rs.getInt("numero_cartao_credito"));
			Cliente cliente = new Cliente();
			cliente.setId(rs.getInt("cliente"));
			cartaoCredito.setCliente(cliente);
			
			Transacao transacao = new Transacao();
			transacao.setId(rs.getInt("id"));
			transacao.setCartaoCredito(cartaoCredito);
			transacao.setData(rs.getDate("data"));
			transacao.setValor(rs.getDouble("valor"));
			transacao.setDescricao(rs.getString("descricao"));
			return transacao;
		};
	}
}
