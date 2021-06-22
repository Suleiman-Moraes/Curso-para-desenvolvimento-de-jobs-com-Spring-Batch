package com.springbatch.folhaponto.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.folhaponto.dominio.Funcionario;

@Configuration
public class FuncionarioReaderJdbcConfig {
	@Bean
	public JdbcCursorItemReader<Funcionario> funcionarioReaderJdbc(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Funcionario>()
				.name("funcionarioReaderJdbc")
				.dataSource(dataSource)
				.sql("SELECT *, MONTH(data) AS mes, YEAR(data) AS ano FROM funcionario LEFT JOIN registro_ponto ON (matricula = funcionario_id AND (data IS NULL OR MONTH(data)=MONTH(NOW()) AND YEAR(data)=YEAR(NOW()))) ORDER BY matricula")
				.beanRowMapper(Funcionario.class)
				.build();
	}
}
