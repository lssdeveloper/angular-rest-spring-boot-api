# Implementando o logout

**DICAS:**

*Quanto menor o tempo do access token melhor para a gente pois ele é manipulado pelo javascript, estando mais propenso a fraudes.*

*Já o refresh token deve ter um período de expiraçõ maior, por ser mais seguro, ele é um token que ficará em um cookie https em produção.*

*Para fazer a aplicação cliente, quando o usuário clicar em logout ele vai apagar o access token que o browser está usando, e vai chamar no servidor para remover o refresh token.*

**Solução:**

*Remover o refresh token do cookie, neste caso o spring não tem nada pronto neste sentido.*

**1º Criar uma classe TokenResource.java (.api.resource)**

```java

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); // TODO: Em producao sera true
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
}
```

**Como testar no Postman:**

*Selecione a consulta Novo access Token - client Mobile ou cliente Angular, o que preciso na verdade é identificar um refreshToken no cookie e perceber sua exclusão na consulta Tokens - Revoke - Remove o refresh token.*