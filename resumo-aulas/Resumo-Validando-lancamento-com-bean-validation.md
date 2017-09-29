# Validando lançamento com bean validation

*Inserindo @NotNull nos campos obrigatórios*

**Cenário**

*Body com campo obrigatórios não informados*

```json
{
    "descricao": "Prêmio Semestral",
    "dataVencimento": "2017-09-28",
    "valor": 10000,

    "pessoa": {
        "codigo": 2
        }
}
```

**1º Lancamento.java**

```java
	@NotNull
	private String descricao;

	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@NotNull
	private BigDecimal valor;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;
```
**2º messages.properties**
*(coloca os campos em maiúsculos)*

```
lancamento.descricao=Descri\u00E7\u00E3o
lancamento.dataVencimento=Data de vencimento
lancamento.valor=Valor
lancamento.tipo=Tipo
lancamento.categoria=Categoria
lancamento.pessoa=Pessoa
```

**Resultado**

```json
[
    {
        "mensagemUsuario": "Tipo é obrigatório(a)",
        "mensagemDesenvolvedor": "Field error in object 'lancamento' on field 'tipo': 
        rejected value [null]; 
        codes [NotNull.lancamento.tipo,NotNull.tipo,
        NotNull.com.lssdeveloper.money.api.model.TipoLancamento,NotNull]; 
        arguments [org.springframework.context.support.DefaultMessageSourceResolvable: 
        codes [lancamento.tipo,tipo]; arguments []; default message [tipo]]; 
        default message [{0} é obrigatório(a)]"
    },
    {
        "mensagemUsuario": "Categoria é obrigatório(a)",
        "mensagemDesenvolvedor": "Field error in object 'lancamento' 
        on field 'categoria': rejected value [null]; 
        codes [NotNull.lancamento.categoria,
        NotNull.categoria,NotNull.com.lssdeveloper.money.api.model.Categoria,NotNull]; 
        arguments [org.springframework.context.support.DefaultMessageSourceResolvable: 
        codes [lancamento.categoria,categoria]; arguments []; 
        default message [categoria]]; default message [{0} é obrigatório(a)]"
    }
]
```