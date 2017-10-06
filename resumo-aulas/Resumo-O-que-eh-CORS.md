# O que é CORS?

Nesta aula temos um exemplo de uma requisição de outra ORIGN e nesta caso o Browser por segurança bloqueisa requisições javascript.

```
Host localhost:8091

Origin http://localhost:8000
```
**Arquivo de teste**

```html
<html>
<body>

<button onclick="buscar()">Buscar categorias</button>

<script>
function printResponse() {
  console.log(this.responseText);
}
function buscar() {
	var req = new XMLHttpRequest();
	req.addEventListener("load", printResponse);
	req.open("GET", "http://localhost:8091/categorias");
	req.setRequestHeader('HEADER1', 'VALOR');
	req.send();
}
</script>

</body>
</html>

```

**Como testar**

*Com o python instalado na máquina execute este comando para levantar este servidor Http*

*Salve o código html acima adaptando o recurso que deseja testar, abra um ternminal e execute o comando abaixo*

```
leandro@leandro-S500CA ~ $ python -m SimpleHTTPServer 8000

```

*Abra um Browser e digite*

```
http://localhost:8000/get-javascript.html
```
**Solução**

*maxAge = 10 => é o tempo que uma nova requisição pode ser feita com sucesso, no caso 10 segundos*

```
CategoriaResource.java

	@CrossOrigin(maxAge = 10, origins = {"http://localhost:8000"})
	@GetMapping	
	public List<Categoria> listar(){
		//findAll() = Select * from categoria
		return categoriaRepository.findAll();
		
	}
```
Obs: O Spring ainda não suporta CORS para o OAuth2, ocorrendo alguns bugs, precisando realizar um filtro para o mesmo, que será feito na próxima aula.
