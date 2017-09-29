package com.lssdeveloper.money.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.model.Pessoa;
import com.lssdeveloper.money.api.repository.LancamentoRepository;
import com.lssdeveloper.money.api.repository.PessoaRepository;
import com.lssdeveloper.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento) ;
	} 
}
