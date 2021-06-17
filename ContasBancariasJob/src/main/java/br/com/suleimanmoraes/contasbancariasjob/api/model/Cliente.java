package br.com.suleimanmoraes.contasbancariasjob.api.model;

import lombok.Data;

@Data
public class Cliente {
	
	private Integer id;
	private String nome;
	private Double faixaSalarial;
	private Integer idade;
	private String email;
}
