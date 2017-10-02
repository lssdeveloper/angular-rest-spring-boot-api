# Resumo-Desafio remover Lançamento

LancamentoResource.java

```java
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.delete(codigo);
	}
```	