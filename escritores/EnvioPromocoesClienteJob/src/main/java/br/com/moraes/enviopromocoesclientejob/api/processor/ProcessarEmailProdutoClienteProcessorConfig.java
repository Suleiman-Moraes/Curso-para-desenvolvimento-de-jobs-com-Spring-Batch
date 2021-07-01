package br.com.moraes.enviopromocoesclientejob.api.processor;

import java.text.NumberFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import br.com.moraes.enviopromocoesclientejob.api.model.InteresseProdutoCliente;

@Component
public class ProcessarEmailProdutoClienteProcessorConfig
		implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> {

	@Override
	public SimpleMailMessage process(InteresseProdutoCliente item) throws Exception {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("xpto@no-reply.com");
		email.setTo(item.getCliente().getEmail());
		email.setSubject("Promoção Imperdível!!!");
		email.setText(gerarTextoPromocao(item));
		Thread.sleep(2000);
		return email;
	}

	private String gerarTextoPromocao(InteresseProdutoCliente item) {
		StringBuilder tudo = new StringBuilder(String.format("Olá, %s!\n\n", item.getCliente().getNome()));
		tudo.append("Essa promoção pode ser do seu interesse:\n\n");
		tudo.append(String.format("%s - %s", item.getProduto().getNome(), item.getProduto().getDescricao()));
		tudo.append(String.format("Por apenas: %s!",
				NumberFormat.getCurrencyInstance().format(item.getProduto().getPreco())));
		return tudo.toString();
	}

}
