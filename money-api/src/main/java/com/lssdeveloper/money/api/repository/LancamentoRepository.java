package com.lssdeveloper.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lssdeveloper.money.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
