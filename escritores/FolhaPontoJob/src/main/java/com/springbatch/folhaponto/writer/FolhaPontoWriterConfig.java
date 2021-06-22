package com.springbatch.folhaponto.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.folhaponto.dominio.FolhaPonto;

@Configuration
public class FolhaPontoWriterConfig {
	@Bean
	public FlatFileItemWriter<FolhaPonto> imprimeFolhaPontoWriter(
			CabecalhoCallback cabecalhoCallback,
			RodapeCallback rodapeCallback) {
		return new FlatFileItemWriterBuilder<FolhaPonto>().name("imprimeFolhaPontoWriter")
				.resource(new FileSystemResource("./files/folha_ponto.txt"))
				.lineAggregator(lineAggregator())
				.headerCallback(cabecalhoCallback)
				.footerCallback(rodapeCallback)
				.build();
	}

	private LineAggregator<FolhaPonto> lineAggregator() {
		return folhaPonto -> {
			StringBuilder writer = new StringBuilder();
			writer.append(String.format("----------------------------------------------------------------------------\n"));
			writer.append(String.format("NOME:%s\n", folhaPonto.getNome()));
			writer.append(String.format("MATRICULA:%s\n", folhaPonto.getMatricula()));
			writer.append(String.format("----------------------------------------------------------------------------\n"));
			writer.append(String.format("%10s%10s%10s%10s%10s", "DATA", "ENTRADA", "SAIDA", "ENTRADA", "SAIDA"));

			for (String dataRegistroPonto : folhaPonto.getRegistrosPontos().keySet()) {
				writer.append(String.format("\n%s", dataRegistroPonto));

				for (String registro : folhaPonto.getRegistrosPontos().get(dataRegistroPonto)) {
					writer.append(String.format("%10s", registro));
				}
			}
			return writer.toString();
		};
	}
}
