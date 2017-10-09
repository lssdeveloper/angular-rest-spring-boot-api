 **1º V06__insert_role_remover_categoria.sql

 ```sq
INSERT INTO permissao (codigo, descricao) values (9, 'ROLE_REMOVER_CATEGORIA');
-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);
 ```

 **2º CategoriaResource.java**

```java
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.delete(codigo);
	}
```

**3º PessoaResource.java**

*Incluído os @PreAuthorize*

```java
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")

	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
```	

**4º LancamentoResource.java**

```java
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")

	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
```