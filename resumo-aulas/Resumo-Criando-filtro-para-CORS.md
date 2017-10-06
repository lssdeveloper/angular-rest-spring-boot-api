# Criando filtro para CORS

**Solução**

*No pacote cors criar a classe CorsFilter*

Anotar uma ordem de prioridade bem alta logo no início

@Order(Ordered.HIGHEST_PRECEDENCE)

OBS: Tirar o HEADER1 pois ele não configurou no código então não será necessário.

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
	req.send();
}
</script>

</body>
</html>
```

```html
<html>
<body>

<button onclick="enviar()">Buscar access token</button>

<script>
function printResponse() {
  console.log(this.responseText);
}
function enviar() {
	var req = new XMLHttpRequest();
	req.addEventListener("load", printResponse);
	req.open("POST", "http://localhost:8091/oauth/token");
	req.setRequestHeader("Authorization", "Basic YW5ndWxhcjpAbmd1bEByMA==");
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("client_id=angular&username=admin&password=admin&grant_type=password");
}
</script>

</body>
</html>
```

**Sobe o servidor HTTP python**

```
leandro@leandro-S500CA ~ $ python -m SimpleHTTPServer 8000

```

**Testar no browser**

```
http://localhost:8000/get-javascript.html
http://localhost:8000/get-token-access.html
```

