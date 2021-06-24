package br.com.moraes.migracaodadosjob.api.model;

import java.util.Date;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
	private Integer id;

	private String nome;

	private String email;
	
	private Date dataNascimento;
	
	private Integer idade;

	public Boolean isValida() {
		return !Strings.isBlank(nome) && !Strings.isBlank(email) && dataNascimento != null;
	}
}
