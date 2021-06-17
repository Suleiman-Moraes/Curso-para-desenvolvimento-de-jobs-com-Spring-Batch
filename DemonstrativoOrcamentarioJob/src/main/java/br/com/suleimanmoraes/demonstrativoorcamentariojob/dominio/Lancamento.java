package br.com.suleimanmoraes.demonstrativoorcamentariojob.dominio;

import lombok.Data;

@Data
public class Lancamento {
	private Integer codigoNaturezaDespesa;
	private String descricaoNaturezaDespesa;
	private String descricaoLancamento;
	private String dataLancamento;
	private Double valorLancamento;
}
