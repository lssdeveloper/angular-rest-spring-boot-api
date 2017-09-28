package com.lssdeveloper.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lssdeveloper.money.api.model.Pessoa;
import com.lssdeveloper.money.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Pessoa pessoa, Long codigo) {
		//1º buscar a pessoa 
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if (pessoaSalva==null) {
			throw new EmptyResultDataAccessException(1);
		}	
		//copiar as propriedades dessa pessoa tirando o código
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaRepository.save(pessoaSalva);
	}
}
