package com.springbatch.demonstrativoorcamentario.writer;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.demonstrativoorcamentario.dominio.DemostraticoOrcamentarioRodape;
import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

	@StepScope
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> multiDemostrativoOrcamentarioWriter(
			@Value("#{jobParameters['demostrativos']}") Resource demostrativos, 
			FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter){
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.name("multiDemostrativoOrcamentarioWriter")
				.resource(demostrativos)
				.delegate(demonstrativoOrcamentarioWriter)
				.resourceSuffixCreator(suffixCreator())
				.itemCountLimitPerResource(1)
				.build();
	}

	@StepScope
	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(
			@Value("#{jobParameters['demostrativo']}") Resource demostrativo, DemostraticoOrcamentarioRodape rodapeCallback) {
		return new FlatFileItemWriterBuilder<GrupoLancamento>().name("demonstrativoOrcamentarioWriter")
				.resource(demostrativo)
				.lineAggregator(lineAggregator())
				.headerCallback(cabecalhoCallback())
				.footerCallback(rodapeCallback)
				.build();
	}
	
	private ResourceSuffixCreator suffixCreator() {
		return index -> index + ".txt";
	}

	private FlatFileHeaderCallback cabecalhoCallback() {
		return writer -> {
			writer.append("\n");
			writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n",
					new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
			writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t\t HORA: %s\n",
					new SimpleDateFormat("HH:MM").format(new Date())));
			writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO\n"));
			writer.append(
					String.format("----------------------------------------------------------------------------\n"));
			writer.append(String.format("CODIGO NOME VALOR\n"));
			writer.append(String.format("\t Data Descricao Valor\n"));
			writer.append(
					String.format("----------------------------------------------------------------------------\n"));
		};
	}

	private LineAggregator<GrupoLancamento> lineAggregator() {
		return grupoLancamento -> {
			String formatGrupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(),
					grupoLancamento.getDescricaoNaturezaDespesa(),
					NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal()));
			StringBuilder tudo = new StringBuilder(formatGrupoLancamento);
			for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
				tudo.append(String.format("\t [%s] %s - %s\n",
						new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
						NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
			}
			return tudo.toString();
		};
	}
}
