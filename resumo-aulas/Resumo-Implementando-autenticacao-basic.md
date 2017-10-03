# Implementando autenicação Basic

```
.anyRequest() = qualquer requisição precisa estar autenticado, usuário e senha validado.
.anyMatchers = se quiser deixar alguma requisição abaerta, sem autenticar.

tipo de autenticação = httpBasic()
desabilita sessão = sessionManagement()
desabilita cross site request foregin
```

**Solução**

*1º Incluir no pom.xml*

```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

*2º SecurityConfig.java*

```java
package com.lssdeveloper.money.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ROLE");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/categorias").permitAll()
				.anyRequest().authenticated()
				.and()
			.httpBasic().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.csrf().disable();
	}
}
```

*Testes no Postman:*
```
1º Logar sem passar usuário e senha para lançamentos
Status 401 Unauthorized - não autorizado

2º Usuário e senhas inválidos
Status 401 Unauthorized - não autorizado

3º Usuário e senhas válidos
Status 200 

3º Lista categorias (não precisa autenticar) antMatchers("/categorias").permitAll()
Status 200 
```