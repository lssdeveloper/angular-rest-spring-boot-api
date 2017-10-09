# Adicionando Permissões de acesso

**Configuração**

*1º ResourceServerConfig.java*

```java
habilita a segurança nos métodos

@EnableGlobalMethodSecurity(prePostEnabled = true)

E para que isto funcione, necessita adicionar um novo Bean.

	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}

```

*2º CategoriaResource.java*

Admin tem todas as Permissões

Maria tem somente duas 

```java
Configuração dos métodos

	@GetMapping	
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> listar(){
		//findAll() = Select * from categoria
		return categoriaRepository.findAll();
		
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}
	//recebendo uma nova categoria
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}	
```

*3º ResourceServerConfig.java, aqui é somente uma dica pois não há alterações no código*

**OBS: No código abaixo não irá fazer sentido quando for configurado o @PreAuthorize, pois este sobreescreve o código abaixo: 
*.antMatchers("/categorias").permitAll()*.**

*DICA: 
Se for necessário autenticar use sempre o @PreAuthorize, senão o código abaixo é suficiente.*

```java
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/categorias").permitAll()
				.anyRequest().authenticated()
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.csrf().disable();
	}
```	

**Teste no Postman (imagens)**

**Realizando o teste de acesso ao Token com usuário Maria**

*maria-authorization-body.png*

*maria-authorization-headers.png (com autorização)*

*maria-sem-authorization-headers.png (sem autorização)*

**Testando a Permissões de Maria**

*maria-adicionar-categoria-acesso-negado.png => Maria criando uma categoria com acesso negado*

*maria-listando-categorias-authorization.png => Maria listando as categorias autorizado*


**Configurando um Escopo**

*4º AuthorizationServerConfig.java*

*Cenário: Um novo cliente foi adicionado (mobile), este com escopo de somente leitura*

```java
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("@ngul@r0")
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24)
		.and()
			.withClient("mobile")
			.secret("m0b1l30")
			.scopes("read")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24);
	}
```
*Mas para que o escopo funcione, coloque em @PreAuthorize o respectivo escopo.*

```java
CategoriaResource.java

	@GetMapping	
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> listar(){
		//findAll() = Select * from categoria
		return categoriaRepository.findAll();
		
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}
	//recebendo uma nova categoria
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
```
**DICA:**

*hasAuthority('ROLE_PESQUISAR_CATEGORIA') é autorização do usuário*

*#oauth2.hasScope('read') é escopo do cliente*

*Então, autorização é respectivo ao usuário e escopo ao cliente*

**Exemplo:**

*O Admin tem autorização para todas as ROLES mas o escopo do cliente "mobile" é somente de leitura.*

Então fica assim a resposta (Status):
```json
{
    "error": "insufficient_scope",
    "error_description": "Insufficient scope for this resource",
    "scope": "write"

```
*Testando o Escopo do cliente mobile com o usuário admin*

*Veja no Postman a consulta:

criar-authorization-admin-scope-mobile.png*