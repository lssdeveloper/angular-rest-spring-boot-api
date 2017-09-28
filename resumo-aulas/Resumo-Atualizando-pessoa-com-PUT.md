# Atualizando pessoa com PUT


**1º criar o serviço**

```java
PessoaService.java

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
```
**2º Injeta o serviço**

```java
PessoaResource.java

	@Autowired
	private PessoaService pessoaService;

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(
			@PathVariable Long codigo, 
			@Valid @RequestBody Pessoa pessoa){

		Pessoa pessoaSalva = pessoaService.atualizar(pessoa, codigo);		
		return ResponseEntity.ok(pessoaSalva);
	}

```
