package br.com.moraes.migracaodadosjob.api.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.moraes.migracaodadosjob.api.model.DadosBancario;

@Configuration
public class BancoDadosBancarioWriterConfig {

	@Bean
	public JdbcBatchItemWriter<DadosBancario> bancoDadosBancarioWriter(
			@Qualifier("appDataSource") DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<DadosBancario>()
				.dataSource(dataSource)
				.sql("INSERT INTO dados_bancarios (id, pessoa_id, agencia, conta, banco) "
						+ "VALUES (:id, :pessoaId, :agencia, :conta, :banco)")
				.beanMapped()
				.build();
	}
}
