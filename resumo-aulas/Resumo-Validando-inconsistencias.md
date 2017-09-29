# Validando inconsistências

**Cenário**

*Cliente informa uma categoria inexistente codigo = 51 (não existe)*

```json
{
    "descricao": "Prêmio Semestral",
    "dataVencimento": "2017-09-28",
    "valor": 10000,
    "tipo": "RECEITA",
    "categoria": {
        "codigo": 51
    },
    "pessoa": {
        "codigo": 2
        } 
}
```
**Solução** 

*Informar Status 400 Bad Request ao invés de 500 internal server*

*Exibir uma mensagem mais detalhada no body para o desenvolvedor*

```json
[
    {
        "mensagemUsuario": "Operação não permitida",
        "mensagemDesenvolvedor": "MySQLIntegrityConstraintViolationException: 
        Cannot add or update a child row: a foreign key constraint fails 
        (`lssdmoneyapi`.`lancamento`, CONSTRAINT `lancamento_ibfk_1` FOREIGN KEY 
        (`codigo_categoria`) REFERENCES `categoria` (`codigo`))"
    }
]
```
**1º pom.xml**

```xml
	<dependency>
		<groupId>org.apache.commons</groupId>
		<artifactId>commons-lang3</artifactId>
		<version>3.4</version>
	</dependency>
```

**2º LssdMoneyExceptionHandler.java**

```java
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> 
		handleDataIntegrityViolationException(
			DataIntegrityViolationException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage(
			"recurso.operacao-nao-permitida", null,
				LocaleContextHolder.getLocale());
		// exibe a causa raiz da exceção commons-lang3				
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, 
			mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), 
			HttpStatus.BAD_REQUEST, request);
	}
```