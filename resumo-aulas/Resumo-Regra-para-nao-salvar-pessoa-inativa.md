# Regra para não salvar pessoa inativa

**1º Criar o serviço para lançamento LancamentoService.java**

```java
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
```
**2º PessoaService.java**

```java
de private para public

	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if (pessoaSalva==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}

```
**3º Pessoa.java inclusão do método isInativo()**

```java
	//Assim hibernate e o Jackson não identificam como uma propriedade do model 
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
	}
```
**4º PessoaInexistenteOuInativaException.java**

```java
public class PessoaInexistenteOuInativaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
```
**5º LancamentoResource.java**

```java
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> 
		handlePessoaInexistenteOuInativaException(
					PessoaInexistenteOuInativaException ex){
		String mensagemUsuario = messageSource.getMessage("
					pessoa.inexistente-ou-inativa", null, 
									LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(
							mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
```
**6º messages.properties**

```
pessoa.inexistente-ou-inativa=Pessoa inexistente ou inativa para incluir no lan\u00E7amento
```

