# Implementando a pesquisa de lançamento com Metamodel

**1º Alterado o método listar() renomeado para pesquisar(LancamentoFilter lancamentoFilter)**

```java
LancamentoResource.java
	@GetMapping
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter){
		return lancamentoRepository.filtrar(lancamentoFilter);
	}
```

**2º Propriedades de pesquisa do recurso LancamentoFilter.java no package repository.filter**

É aqui que criamos as propriedades dos recursos que queremos pesquisar.

Ex: 

localhost:8080/lancamento?dataVencimentoDe=2017-06-10

localhost:8080/lancamento?dataVencimentoDe=2017-06-10&dataVencimentoAte=2017-07-10

localhost:8080/lancamento?descricao=salario

informe a annotation @DateTimeFormat(pattern = "yyyy/MM/dd")
para usar a data conforme o padrão (2017-06-10)


```java
package com.lssdeveloper.money.api.repository.filter;

	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;
```


**3º Interface  LancamentoRepositoryQuery.java**

Obs: tem que ter o nome ClassRepositoryQuery para que possa ser interpretado corretamente pelo Spring

```java
package com.lssdeveloper.money.api.repository.lancamento;

import java.util.List;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.repository.filter.LancamentoFilter;

//Tem que ter esse nome RepositoryQuery para SpringDataJPA entender
public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}
```
**4º Implementando a pesquisa com o criteria JPA. (LancamentoRepositoryImpl.java)**

```java
package com.lssdeveloper.money.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.model.Lancamento_;
import com.lssdeveloper.money.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		// if (lancamentoFilter.getDescricao() != null) {
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDataVencimentoDe()));
		}
		if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
```

**5º LancamentoRepository extends LancamentoRepositoryQuery**

```java
package com.lssdeveloper.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lssdeveloper.money.api.model.Lancamento;
import com.lssdeveloper.money.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
```


**6º Utilizar o MetaModel**

Botão direito em cima do projeto => properties

Java Compiller => Annotations Processing
Selecionar : Enable project specific settings
Genereted source directory:
src/main/java

Depois, expandir a setinha de Annotation Processing e selecionar Factory Path

Marque: Enable project specific settings

Add Jars Externals

.m2/repository/org/hibernate/hibernate-jpamodelgen/5.0.12.Final/hibernate-jpamodelgen-5.0.12.Final.jar

No caso de não existir no seu repositório informe no pom.xml está dependência depois a mesma opde ser retirada, se desejar.

		<!-- inclusão do metamodel-->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-jpamodelgen</artifactId>
		<!-- 	<version>5.0.12.Final</version>  -->
		</dependency>

Ainda em Factory Path:

Desmarque org.eclipse.jst.ws.annotations.core

Marque /home/caminhoDoJarSelecionadoAcima...



