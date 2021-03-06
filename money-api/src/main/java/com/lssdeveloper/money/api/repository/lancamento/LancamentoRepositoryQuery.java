package com.lssdeveloper.money.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.repository.filter.LancamentoFilter;
import com.lssdeveloper.money.api.repository.projection.ResumoLancamento;

//Tem que ter esse nome RepositoryQuery para SpringDataJPA entender
public interface LancamentoRepositoryQuery {
	

public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
