# Profiles do Spring

*Show View / Tasks - Exibe tudo que está como TODO*

*Profiles (Perfis) são configurações externas para dois ambientes (produção e desenvolvimento)*

*No Spring eu consigo trocar o profile dependendo do ambiente desejado*


```java
MoneyApiProperty.java


@ConfigurationProperties("money")
public class MoneyApiProperty {
	
	private String originPermitida = "http://localhost:8000";
	
	private final Seguranca seguranca = new Seguranca(); 
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public String getOriginPermitida() {
		return originPermitida;
	}
	
	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}
	
	public static class Seguranca {
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
		
	}

}
```
*Adicionar a dependência no pom.xml*

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
```	
*Incluir a api externa no main	do projeto*

```java
MoneyApiApplication.java

@SpringBootApplication
@EnableConfigurationProperties(MoneyApiProperty.class)
public class MoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyApiApplication.class, args);
	}
}
```
*Esta é a configuração de produção, este é o profile do Spring, quando estou subindo a aplicação, este será Habilidade quandoestiver rodando no Heroku, por exemplo*

```
application-prod.properties

money.seguranca.enable-https=true

```
*Alterando o TODO:*

```java
TokenResource.java 

public class TokenResource {
	
	@Autowired
	private MoneyApiProperty moneyProperty;

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(moneyProperty.getSeguranca().isEnableHttps()); 
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
}
```

```java
RefreshTokenPostProcessor.java

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
	
	@Autowired
	private MoneyApiProperty moneyProperty;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBody(token);
		
		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(moneyProperty.getSeguranca().isEnableHttps()); 
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(2592000); //30 dias
		resp.addCookie(refreshTokenCookie);
	}

}
```

```java
CorsFilter.java

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Autowired
	private MoneyApiProperty moneyApiProperty; 
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin", moneyApiProperty.getOriginPermitida());
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
		if ("OPTIONS".equals(request.getMethod()) && moneyApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        	response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        	response.setHeader("Access-Control-Max-Age", "3600");
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
		
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
```

