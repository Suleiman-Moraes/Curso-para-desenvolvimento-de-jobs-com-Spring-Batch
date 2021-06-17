package br.com.suleimanmoraes.contasbancariasjob.api.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import br.com.suleimanmoraes.contasbancariasjob.api.model.Cliente;

@Configuration
public class ContasBancariasReaderConfig {

	@Bean
	public JdbcPagingItemReader<Cliente> contasBancariasReader(@Qualifier("appDataSource") DataSource dataSource,
			PagingQueryProvider queryProvider) {
		return new JdbcPagingItemReaderBuilder<Cliente>().name("contasBancariasReader")
				.dataSource(dataSource)
				.queryProvider(queryProvider)
				.pageSize(10)
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
				.build();
	}
	
	@Bean
	public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("appDataSource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("SELECT cliente.*");
		queryProvider.setFromClause("FROM cliente LEFT JOIN conta ON conta.cliente_id = cliente.id");
		queryProvider.setWhereClause("conta.cliente_id IS NULL");
		queryProvider.setSortKey("cliente.id");
		return queryProvider;
	}
}
