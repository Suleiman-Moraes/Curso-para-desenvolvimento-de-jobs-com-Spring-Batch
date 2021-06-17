package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;

import com.springbatch.processadorclassifier.dominio.Transacao;

public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao>{

	@Override
	public Transacao process(Transacao transacao) throws Exception {
		System.out.println("\nAplicando regras de negócio na transação " + transacao.getId());
		return transacao;
	}
}
