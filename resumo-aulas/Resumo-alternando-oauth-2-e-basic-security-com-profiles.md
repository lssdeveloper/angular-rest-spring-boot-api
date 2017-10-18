# Alternando OAuth 2 e BAsic Security com profiles

*Adicionando o BAsic Security para facilitar o desenvolvimento do front end.*

*Neste caso podemos contar com o auxílio dos profiles do Spring*

*Com a anotação @Profile("basic-security") eu consigo colocar um nome de um profile que quero que seja ativo quando tiver subindo a aplicação*

*Se quiser um profile basic-config o objeto BasicSecurityConfig será carregado*

```java

@Profile("basic-security")
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

```

*Se quiser um profile oauth-security utilizar a anotação @Profile("oauth-security") em 

```java
AuthorizationServerConfig.java

@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

```

```java
ResourceServerConfig.java

@Profile("oauth-security")
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

```

*Em application.properties defino a propriedade profile que será trabalhada, na produção no caso sempre será oauth-security*

```xml
#seleciono o profile que quero que fique ativo
#spring.profiles.active=basic-security
spring.profiles.active=oauth-security
```
*Definindo o basic-security*
```xml
spring.profiles.active=basic-security

```
*No Postman criar uma nova consulta 