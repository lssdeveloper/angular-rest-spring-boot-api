# Desafio: Lista e busca de lançamentos

**1º incluir dependências no pom.xml**

```xml
	<!-- Hibernate Java 8 Support -->
	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-java8</artifactId>
	</dependency>

	<!-- Suporte do Jackson para as datas do Java 8 -->
	<dependency>
		<groupId>com.fasterxml.jackson.datatype</groupId>
		<artifactId>jackson-datatype-jsr310</artifactId>
	</dependency>
```
**2º application.properties**

```
	spring.jackson.date-format=yyyy-MM-dd
```

**3º LancamentoRepository.java**

```java
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}

```	

**4º LancamentoResource.java**

```java
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@GetMapping
	public List<Lancamento> listar(){
		return lancamentoRepository.findAll();
	}
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		
		return lancamento != null ? 
		ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}		
}
```
