package com.lssdeveloper.money.api.model;

public enum Regiao {
	
/*	NORTE("Norte"),
	NORDESTE("Nordeste"),
	CENTROOESTE("Centro-Oeste"),*/
	SUL("---Sul---"),
	SUDESTE("Sudeste");
	
	private String descricao;
	
	Regiao(String descricao ) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}

}
