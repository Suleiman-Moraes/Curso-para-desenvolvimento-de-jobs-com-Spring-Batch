package com.springbatch.skipexception.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	private String nome;
	private String sobrenome;
	private String idade;
	private String email;
}