# Desafio: Cadastrando o primeiro lançamento

**1º LancamentoResource.java**

```java
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, 
			HttpServletResponse response){
		Lancamento lancamentoSalva = lancamentoRepository.save(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
	}
```