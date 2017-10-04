# Implementando segurança com OAuth 2 e Password Flow

**Alterações**

**pom.xml**

```xml
	<dependency>
		<groupId>org.springframework.security.oauth</groupId>
		<artifactId>spring-security-oauth2</artifactId>
	</dependency>
```
**ResourceServerConfig.java**

```java
@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ROLE");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/categorias").permitAll()
				.anyRequest().authenticated()
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
```
**AuthorizationServerConfig.java**

```java
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("@ngul@r0")
			.scopes("read", "write")
			.authorizedGrantTypes("password")
			.accessTokenValiditySeconds(1800);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
```
```java
@Bean
public Tokenstore tokenStore(){
    return new InMemoryTokenStore(); //Aqui poderia ser armazenado também em um banco de dados
}
```
```AuthorizationServerConfig.java

Configuração:

Clients.inMemory() => tem a opção para gravar em banco de dados também, podendo adicionar quantos clientes quiser.

.withClients("angular") => qual o nome do cliente, cliente no caso o front-end, poderia ser outro nome claro,mas não é o usuário do cliente.

.secret("@ngul@r0") => a senha do cliente

.scopes("read", "write") => define o escopo do cliente, limitando o acesso desse cliente, desta aplicação angular.

É possível definir escopos para clientes diferentes

.authorizedGrantTypes("password") => GrantTypes existem outros mas neste caso usaremos o passwordFlow, é o fluxo onde a aplicação recebe o usuário e senha do cliente e envia para pegar o access token.

Quando usar este caso? 
Quando a aplicação desenvolvida é de confiança sua, pois ele tem acesso ao usuário e senha.

.accessTokenValiditySeconds(1800) => quantos segundos este Token vai ficar ativo, 1800/60 = 30 minutos.


endpoints => é preciso armazenar este Token em algum lugar.

.tokenStore(tokenStore())

.authenticationManager(authemticationManager); => para poder validar

```


**No Postman fiz as seguintes requisições**

```
Lancamento-Novo access Token
Lancamneto-listar com Token
```