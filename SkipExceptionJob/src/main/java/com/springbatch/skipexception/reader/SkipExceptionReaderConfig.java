package com.springbatch.skipexception.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.springbatch.skipexception.dominio.Cliente;

@Configuration
public class SkipExceptionReaderConfig {
	@Bean
	public ItemReader<Cliente> skipExceptionReader(@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("skipExceptionReader")
				.dataSource(dataSource)
				.sql("SELECT * FROM cliente")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<Cliente> rowMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				if (resultSet.getRow() == 11) {
					throw new SQLException(
							String.format("Encerrando a execução - Cliente inválido %s", resultSet.getString("email")));
				} else {
					return clienteRowMapper(resultSet);
				}
			}

			private Cliente clienteRowMapper(ResultSet resultSet) throws SQLException {
				return new Cliente(resultSet.getString("nome"), resultSet.getString("sobrenome"),
						resultSet.getString("idade"), resultSet.getString("email"));
			}
		};
	}
}
