package br.com.moraes.faturacartaocreditojob.api.model;

import java.util.Date;

import lombok.Data;

@Data
public class Transacao {
	private Integer id;
	
	private CartaoCredito cartaoCredito;
	
	private String descricao;
	
	private Double valor;
	
	private Date data;
}
