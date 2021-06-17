package com.springbatch.arquivomultiplosformatos.dominio;

import lombok.Data;

@Data
public class Transacao {
	public String id;
	public String descricao;
	public Double valor;
}
