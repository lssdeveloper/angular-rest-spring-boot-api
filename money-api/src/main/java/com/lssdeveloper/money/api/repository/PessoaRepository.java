package com.lssdeveloper.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lssdeveloper.money.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
