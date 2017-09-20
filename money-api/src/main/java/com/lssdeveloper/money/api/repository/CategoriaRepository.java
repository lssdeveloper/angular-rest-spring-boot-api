package com.lssdeveloper.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lssdeveloper.money.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
}
