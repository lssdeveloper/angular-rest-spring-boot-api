package com.lssdeveloper.money.api.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.lssdeveloper.money.api.model.Pessoa;
import com.lssdeveloper.money.api.repository.filter.PessoaFilter;
import com.lssdeveloper.money.api.repository.projection.ResumoPessoa;

public interface PessoaRepositoryQuery {
	
	public Page<Pessoa> filtrar(PessoaFilter PessoaFilter, Pageable pageable);
	public Page<ResumoPessoa> resumir(PessoaFilter PessoaFilter, Pageable pageable);

}
