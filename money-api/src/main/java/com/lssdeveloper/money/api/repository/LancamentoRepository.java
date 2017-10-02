package com.lssdeveloper.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
