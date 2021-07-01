package br.com.moraes.enviopromocoesclientejob.api.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import br.com.moraes.enviopromocoesclientejob.api.model.Cliente;
import br.com.moraes.enviopromocoesclientejob.api.model.InteresseProdutoCliente;
import br.com.moraes.enviopromocoesclientejob.api.model.Produto;

@Configuration
public class LerInteresseProdutoClienteReaderConfig {

	@Bean
	public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
				.name("lerInteresseProdutoClienteReader")
				.dataSource(dataSource)
				.sql(getSql())
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<InteresseProdutoCliente> rowMapper() {
		return (rs, rowNumber) -> {
			Cliente cliente = new Cliente();
			cliente.setId(rs.getInt("id"));
			cliente.setNome(rs.getString("nome"));
			cliente.setEmail(rs.getString("email"));
			
			Produto produto = new Produto();
			produto.setId(rs.getInt(6));
			produto.setNome(rs.getString(7));
			produto.setDescricao(rs.getString("descricao"));
			produto.setPreco(rs.getDouble("preco"));
			
			final InteresseProdutoCliente interesseProdutoCliente = new InteresseProdutoCliente(cliente, produto);
			return interesseProdutoCliente;
		};
	}

	private String getSql() {
		StringBuilder sql = new StringBuilder("SELECT * FROM interesse_produto_cliente ipc");
		sql.append(" JOIN cliente ON (cliente = cliente.id)");
		sql.append(" JOIN produto ON (produto = produto.id)");
		return sql.toString();
	}
}
