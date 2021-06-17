package br.com.suleimanmoraes.contasbancariasjob.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Conta {

	private Integer id;
	private String tipo;
	private Double limite;
	private Integer clienteId;
}
