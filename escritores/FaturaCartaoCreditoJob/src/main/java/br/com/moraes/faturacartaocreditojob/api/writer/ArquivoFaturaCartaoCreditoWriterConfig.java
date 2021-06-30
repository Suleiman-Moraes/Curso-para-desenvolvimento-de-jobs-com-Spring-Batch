package br.com.moraes.faturacartaocreditojob.api.writer;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import br.com.moraes.faturacartaocreditojob.api.model.FaturaCartaoCredito;

@Configuration
public class ArquivoFaturaCartaoCreditoWriterConfig {

	@Bean
	public MultiResourceItemWriter<FaturaCartaoCredito> arquivosFaturaCartaoCredito(){
		return new MultiResourceItemWriterBuilder<FaturaCartaoCredito>()
				.name("arquivoFaturaCartaoCreditoWriter")
				.resource(new FileSystemResource("files/fatura"))
				.itemCountLimitPerResource(1)
				.resourceSuffixCreator(index -> index + ".txt")
				.delegate(arquivoFaturaCartaoCredito())
				.build();
	}

	@Bean
	public FlatFileFooterCallback footerCallback() {
		return new TotalTransacoesFooterCallback();
	}

	private FlatFileItemWriter<FaturaCartaoCredito> arquivoFaturaCartaoCredito() {
		return new FlatFileItemWriterBuilder<FaturaCartaoCredito>()
				.name("arquivoFaturaCartaoCredito")
				.resource(new FileSystemResource("files/fatura.txt"))
				.lineAggregator(lineAggregator())
				.headerCallback(headerCallback())
				.footerCallback(footerCallback())
				.build();
	}

	private FlatFileHeaderCallback headerCallback() {
		return writer -> {
			writer.append(String.format("%121s\n", "Cartão XPTO"));
			writer.append(String.format("%121s\n\n", "Rua Vergueiro, 131"));
		};
	}

	private LineAggregator<FaturaCartaoCredito> lineAggregator() {
		return faturaCartaoCredito -> {
			StringBuilder writer = new StringBuilder(
					String.format("Nome: %s\n", faturaCartaoCredito.getCliente().getNome()));
			writer.append(String.format("Endereço: %s\n\n\n", faturaCartaoCredito.getCliente().getEndereco()));
			writer.append(String.format("Fatura completa do cartão %d\n", faturaCartaoCredito.getCartaoCredito().getNumeroCartaoCredito()));
			writer.append("-----------------------------------------------------------------------------------------------------------------------------\n");
			writer.append("DATA DESCRICAO VAROR\n");
			writer.append("-----------------------------------------------------------------------------------------------------------------------------\n");
			faturaCartaoCredito.getTransacoes().forEach(t -> writer.append(String.format("\n[%10s] %-80s - %s", 
					new SimpleDateFormat("dd/MM/yyyy").format(t.getData()),
					t.getDescricao(),
					NumberFormat.getCurrencyInstance().format(t.getValor())
					)));
			return writer.toString();
		};
	}
}
