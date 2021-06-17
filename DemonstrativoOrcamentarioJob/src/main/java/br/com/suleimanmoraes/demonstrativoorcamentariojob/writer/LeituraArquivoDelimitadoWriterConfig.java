package br.com.suleimanmoraes.demonstrativoorcamentariojob.writer;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.suleimanmoraes.demonstrativoorcamentariojob.dominio.Lancamento;

@Configuration
public class LeituraArquivoDelimitadoWriterConfig {

	private int id = 0;
	private Double valor = 0.0;
	private StringBuilder tudo = new StringBuilder();
	private NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	@Bean
	public ItemWriter<Lancamento> leituraArquivoDelimitadoWriter() {
		return items -> {
			items.forEach(i -> {
				if (id != i.getCodigoNaturezaDespesa().intValue()) {
					if (id != 0) {
						System.out.println(String.format(tudo.toString(), df.format(valor)));
						tudo = new StringBuilder();
					}
					id = i.getCodigoNaturezaDespesa();
					valor = 0.0;
					tudo.append("---- Demonstrativo orçamentário ----\n");
					tudo.append(String.format("[%s] %s", id, i.getDescricaoNaturezaDespesa()));
					tudo.append(" - R$ %s\n");
				}
				final String[] data = i.getDataLancamento().split("-");
				valor += i.getValorLancamento();
				tudo.append(String.format("\t[%s/%s/%s] %s - R$ %s\n", data[2], data[1], data[0],
						i.getDescricaoLancamento(), df.format(i.getValorLancamento())));
			});
			System.out.println(String.format(tudo.toString(), df.format(valor)));
		};
	}
}
