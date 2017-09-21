#  Validando valores inválidos com Bean Validation

*Cenário 1:*
Tentando inserir um valor no campo not null

*Cenário 2:*
Tamanho máximo de caracteres min = 3 e max = 20

Onde receberíamos o seguinte status:
Status 500 Internal Server Error

**Solução:**

```
 - Enviar uma mensagem amigável para o usuário e o desenvolvedor, criando Lista de erros.
 - Tratar o Status de 500 para 400 pois é um erro do cliente informando null num campo not null.
 - Traduzindo as mensagens do Bean Validations para todo o sistema (ValidationMessages.properties)
```

```java
Categoria.java

	 @NotNull
	 @Size(min = 3, max = 20)
	 private String nome;
```	

```java
CategoriaResource.java
    response.setHeader("Location", uri.toASCIIString());
```

```java
LssdMoneyExceptionHandler.java
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null,
		 LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, 
				LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}
```
```
messages.properties

mensagem.invalida=Mensagem inv\u00E1lida

categoria.nome=Nome
```
```
ValidationMessages.properties

javax.validation.constraints.NotNull.message={0} \u00e9 obrigat\u00f3rio(a)

javax.validation.constraints.Size.message={0} deve ter o tamanho entre {min} e {max}
```



