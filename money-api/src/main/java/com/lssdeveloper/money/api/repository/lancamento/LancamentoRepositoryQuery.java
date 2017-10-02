package com.lssdeveloper.money.api.repository.lancamento;

import java.util.List;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.repository.filter.LancamentoFilter;

//Tem que ter esse nome RepositoryQuery para SpringDataJPA entender
public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}
