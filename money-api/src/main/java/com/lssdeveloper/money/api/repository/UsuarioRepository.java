package com.lssdeveloper.money.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lssdeveloper.money.api.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmail(String email);
	
}
