package com.lssdeveloper.money.api.repository.projection;

public class ResumoPessoa {

	private Long codigo;
	private String nome;
	
	public ResumoPessoa(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
