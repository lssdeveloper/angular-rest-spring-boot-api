# Renovando o access token com o refresh token

```
.refreshTokenValiditySeconds(3600 * 24) => um dia

.reuseRefreshTokens(false) => para ele não reaproveitar 
o Token se não setar esta propriedade ele vai usar 
o Token por um dia até solicitarmos um novo Token 
pelo password flow.

Refresh Token => Serve para buscar um novo access Token 
sem ter que pedir usuaŕio e senha novamente do usuário.
```

**Alterações**

*AuthorizationServerConfig.java*

```java
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("@ngul@r0")
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(20)
			.refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}
```	

