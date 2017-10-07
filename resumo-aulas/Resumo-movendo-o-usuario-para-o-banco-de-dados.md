# Movendo o usuário para o banco de dados

ResourceServerConfig.java

*É preciso passar um userDetailsService como objeto, sendo necessário injetá-lo*

```java
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//aqui recebemos a senha que será encodada
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	//aqui é realizado o encoded
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

```
*Mas para usar este userDetailsService precisamor de uma implementação AppUserDetailsService.java*

```
AppUserDetailsService.java

@Service é importante esta annotation está como um serviço do springframework

pois senão recebemos o seguinte erro:

***************************
APPLICATION FAILED TO START
***************************

Description:

Field userDetailsService in 
com.lssdeveloper.money.api.config.ResourceServerConfig 
required a bean of type 'org.springframework.security.core.userdetails.UserDetailsService' 
that could not be found.


Action:

Consider defining a bean of type 'org.springframework.security.core.userdetails.UserDetailsService' 
in your configuration.

Segue outras considerações para esta classe:

//lista de permissões, são as permissões
Set<SimpleGrantedAuthority> authorities = new HashSet<>(); 

ResourceServerConfig.java

// retorna um password passwordEncoder
.passwordEncoder(passwordEncoder()); 

//ele quem consegue ler a senha encodada
BCryptPasswordEncoder(); 
```

```java

import org.springframework.stereotype.Service;

import com.lssdeveloper.money.api.model.Usuario;
import com.lssdeveloper.money.api.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) 
		throws UsernameNotFoundException {
		
		//na hora de logar buscar por e-mail
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		//expressão lambda
		Usuario usuario = 
		usuarioOptional.orElseThrow(() -> 
		new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new User(email, usuario.getSenha(), getPermissoes(usuario));
	}
	//lista as permissões do usuario
	private Collection<? extends GrantedAuthority> 
	getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		//cada uma das permissõesé passada nesse set authorities (java 8)
		usuario.getPermissoes().forEach(p -> authorities.add(
			new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
}

```

*Classe para gerar senha GeradorSenha.java, simplesmente executar Run Application Java e copiar a senha encodada no console*

*Para testar basta ir no Postman Novo Access Token e passar esta senha encodada para o respectivo usuário do banco*

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
	//no caso admin é a senha ser encodada
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}
	
}
```
*Em UsuarioRepository.java Optional<Usuario> usa uma abordagem orientada a objetos, é semelhante se usuario.equals(null), mais ou menos isso.*

```java

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmail(String email);	
}
```






