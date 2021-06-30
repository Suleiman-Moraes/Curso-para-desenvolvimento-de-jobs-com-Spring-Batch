package br.com.moraes.faturacartaocreditojob.api.model;

import lombok.Data;

@Data
public class Cliente {
	private Integer id;
	
	private String nome;
	
	private String endereco;
}
