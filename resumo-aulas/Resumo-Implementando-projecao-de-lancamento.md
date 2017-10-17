# Implementando projeção de lançamento

**Cenário:**

*Este é tabém conhecido como projeção, ou seja, retornar somente aquilo que precisamos no momento.*

*Retorna uma consulta resumida do Lançamento, neste exemplo veremos como exibir somente a descrição da pessoa e da categoria ao invés de todo o objeto.*

*1º ResumoLancamento.java*

**OBS:**
*A pessoa e a categoria estão como String, ou seja, será retornado a descrição destas propriedades e não o objeto num todo.*

```java
public class ResumoLancamento {

	private Long codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	public ResumoLancamento(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

    //getters e setters .....

}
```
*2º LancamentoRespositoryQuery.java*

```java
//Tem que ter esse nome RepositoryQuery para SpringDataJPA entender
public interface LancamentoRepositoryQuery {
	

public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
```
*3º LancamentoRepositoryImpl.java*

```java
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(Lancamento_.codigo), root.get(Lancamento_.descricao)
				, root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento)
				, root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
				, root.get(Lancamento_.categoria).get(Categoria_.nome)
				, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
```
```java
	//exatamente aqui que retornamo a descrição de categoria e pessoa
	, root.get(Lancamento_.categoria).get(Categoria_.nome)
	, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

	//Aqui foi preciso colocar um tipo genérico

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable)
```				

*4º adicionar no recurso este resumo (LancamentoResource.java)*

*Ao passar a string resumo na URL com o método GET será chamado o recurso resumir*

```java
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
```	
*5º Teste no Postman*

```
Solicitar um Token (Lancamento - Novo acces Token - com email)

E Solicitar a consulta (Lancamento - Projeção Resumir)

É possível em cima do resumo realizar um filtro também

(Lancamento - Projeção Resumir - Filtro)
```
