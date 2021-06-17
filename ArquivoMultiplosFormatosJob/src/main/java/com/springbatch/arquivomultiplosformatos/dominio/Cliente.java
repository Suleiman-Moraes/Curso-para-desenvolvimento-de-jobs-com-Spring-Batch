package com.springbatch.arquivomultiplosformatos.dominio;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class Cliente {
	private String nome;
	private String sobrenome;
	private String idade;
	private String email;
	private List<Transacao> transacoes = new LinkedList<>();
	
	public void setAddTransacoes(Transacao transacao) {
		getTransacoes().add(transacao);
	}
}
