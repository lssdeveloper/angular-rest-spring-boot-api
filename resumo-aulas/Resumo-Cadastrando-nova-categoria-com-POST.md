# Cadastrando nova categoria com POST

*Código para criar uma categoria*

**CategoriaResource.java**

```java
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		//desta forma já passo o ststus 201 created
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
```
*Buscando uma categoria pelo código*
```java
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
		return categoriaRepository.findOne(codigo);
	}
```

### Veja no Postman os exemplos que estão nas imagens:

categoria-buscar-pelo-codigo.png

listar-categorias.png

response-entity-categoria-criar.png


