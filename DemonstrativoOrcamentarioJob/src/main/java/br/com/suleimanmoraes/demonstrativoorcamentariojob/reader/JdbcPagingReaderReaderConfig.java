package br.com.suleimanmoraes.demonstrativoorcamentariojob.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import br.com.suleimanmoraes.demonstrativoorcamentariojob.dominio.Lancamento;

@Configuration
public class JdbcPagingReaderReaderConfig {

	@Bean
	public JdbcPagingItemReader<Lancamento> jdbcPagingReader(@Qualifier("appDataSource") DataSource dataSource,
			PagingQueryProvider queryProvider) {
		return new JdbcPagingItemReaderBuilder<Lancamento>().name("jdbcPagingReader").dataSource(dataSource)
				.queryProvider(queryProvider).pageSize(12).rowMapper(new BeanPropertyRowMapper<Lancamento>(Lancamento.class))
				.build();
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("appDataSource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("SELECT *");
		queryProvider.setFromClause("FROM lancamento");
		queryProvider.setSortKey("descricaoNaturezaDespesa");
		return queryProvider;
	}
}
