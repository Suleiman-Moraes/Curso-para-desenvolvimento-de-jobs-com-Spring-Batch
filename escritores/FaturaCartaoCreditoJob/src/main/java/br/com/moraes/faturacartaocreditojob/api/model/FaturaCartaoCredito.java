package br.com.moraes.faturacartaocreditojob.api.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class FaturaCartaoCredito {
	private Cliente cliente;
	
	private CartaoCredito cartaoCredito;
	
	private List<Transacao> transacoes = new LinkedList<>();

	public Double getTotal() {
		return transacoes.stream().mapToDouble(Transacao::getValor).reduce(0.0, Double::sum);
	}
}
