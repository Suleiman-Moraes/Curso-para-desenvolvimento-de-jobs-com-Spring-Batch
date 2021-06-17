package br.com.suleimanmoraes.contasbancariasjob.api.enums;

import lombok.Getter;

@Getter
public enum TipoContaEnum {
	PRATA(500.0),
	OURO(1000.0),
	PLATINA(2500.0),
	DIAMANTE(5000.0);
	
	private Double limite;
	
	private TipoContaEnum(Double limite) {
		this.limite = limite;
	}
	
	public static TipoContaEnum get(Double salario) {
		if(salario == null || salario <= 3000) {
			return PRATA;
		}
		else if(salario <= 5000) {
			return OURO;
		}
		else if(salario <= 10000) {
			return PLATINA;
		}
		return DIAMANTE;
	}
}
