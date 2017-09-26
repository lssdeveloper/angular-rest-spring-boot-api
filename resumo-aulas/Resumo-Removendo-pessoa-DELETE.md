# Removendo pessoa com DELETE

*Removendo um registro com o método (verbo) DELETE*

```java
PessoaResource.java

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
```
```java
CategoriaResource.java

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.delete(codigo);
	}
```

**Tratamento do Status**

*Uma vez deletado o registro se executarmos novamente o método com o respectivo código receberemos 
o status 500, como este não é um erro interno de servidor, abaixo resolvemos o Status com o código 404 NOT_FOUND*

##### Neste caso o Status 404 Not Found é mais conveniente
###### Neste exemplo não exibe mensagem no body, somente o status 404 Not NOT_FOUND
```java
LssdMoneyExceptionHandler.java

	@ExceptionHandler({EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException() {
		
	}
```
###### Neste caso exibe uma mensagem no body
```java
LssdMoneyExceptionHandler.java

	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> 
		handleEmptyResultDataAccessException(
		EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage(
			"recurso.nao.encontrado", null, LocaleContextHolder.getLocale());
		//não precisa do getCause() pois o erro é a causa	
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(
			new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), 
					HttpStatus.NOT_FOUND, request);
	}
```
###### Incluindo a mensagem
```
messages.properties

recurso.nao.encontrado=Recurso n\u00E3o encontrado
```