package br.com.moraes.migracaodadosjob.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosBancario {
	private Integer id;
	
	private Integer pessoaId;
	
	private Integer agencia;
	
	private Integer conta;
	
	private Integer banco;
}
