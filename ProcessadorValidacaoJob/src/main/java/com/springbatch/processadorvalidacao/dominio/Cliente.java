package com.springbatch.processadorvalidacao.dominio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class Cliente {

	@NotNull
	@Size(min = 1, max = 100)
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Nome deve ser alfabético")
	private String nome;

	@NotNull
	@Range(min = 18, max = 200)
	private Integer idade;

	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "^[a-zA-Z._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,6}$", message = "Email inválido")
	private String email;
}
