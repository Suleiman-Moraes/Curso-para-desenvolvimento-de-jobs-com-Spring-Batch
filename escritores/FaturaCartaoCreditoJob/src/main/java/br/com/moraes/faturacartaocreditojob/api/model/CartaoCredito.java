package br.com.moraes.faturacartaocreditojob.api.model;

import lombok.Data;

@Data
public class CartaoCredito {
	private Integer numeroCartaoCredito;
	
	private Cliente cliente;
}
