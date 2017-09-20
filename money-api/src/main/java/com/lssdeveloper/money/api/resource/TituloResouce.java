package com.lssdeveloper.money.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lssdeveloper.money.api.model.Titulo;
import com.lssdeveloper.money.api.repository.TituloRepository;

@RestController
@RequestMapping("/titulos")
public class TituloResouce {
	
	@Autowired
	private TituloRepository tituloRepository;
	
	@GetMapping
	public List<Titulo> listar(){
		return tituloRepository.findAll();
	}

}
