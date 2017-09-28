# Implementando atualização parcial com PUT

**1º criar o serviço**

```java
PessoaService.java

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);	
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);		
	}
```

**2º criar o recurso**

```java
PessoaResource.java

	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
```

**3º passo foi preciso fazer um ajuste em LssMoneyExceptionHandler.java**

```
	ex.getCause() != null ?
				ex.getCause().toString() : ex.toString();
```				

```java
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable
		(HttpMessageNotReadableException ex,
		HttpHeaders headers, 
		HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage(
			"mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ?
				ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, 
			mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, 
			HttpStatus.BAD_REQUEST, request);
	}
```


