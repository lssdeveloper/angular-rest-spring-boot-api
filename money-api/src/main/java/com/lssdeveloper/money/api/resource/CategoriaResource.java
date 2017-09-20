package com.lssdeveloper.money.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lssdeveloper.money.api.model.Categoria;
import com.lssdeveloper.money.api.repository.CategoriaRepository;

/*
 * Esta é a classe que vai expor todos os "RECURSOS" relacionado a categoria
 */

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping	
	public List<Categoria> listar(){
		//findAll() = Select * from categoria
		return categoriaRepository.findAll();
		
	}
	
}
