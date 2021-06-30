package br.com.moraes.faturacartaocreditojob.api.processor;

import javax.xml.bind.ValidationException;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.moraes.faturacartaocreditojob.api.model.Cliente;
import br.com.moraes.faturacartaocreditojob.api.model.FaturaCartaoCredito;

@Component
public class CarregarDadosClienteProcessor implements ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public FaturaCartaoCredito process(FaturaCartaoCredito item) throws Exception {
		final String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d",
				item.getCliente().getId());
		ResponseEntity<Cliente> response = restTemplate.getForEntity(uri, Cliente.class);
		
		if(response.getStatusCode() != HttpStatus.OK) {
			throw new ValidationException("Cliente não encontrado!");
		}
		item.setCliente(response.getBody());
		return item;
	}

}
