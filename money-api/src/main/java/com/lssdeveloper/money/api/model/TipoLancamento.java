package com.lssdeveloper.money.api.model;

public enum TipoLancamento {
	
	RECEITA("Receita"),
	DESPESA("Despesa");
	
	private String descricao;
	
	TipoLancamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
