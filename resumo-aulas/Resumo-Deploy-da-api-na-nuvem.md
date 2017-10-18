# Deploy da api na nuvem	

*Ao subir a aplicação no Heroku, ocorreu um erro que a solução foi retirar do pom.xml a dependência abaixo:*

```xml
pom.xml	

	<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-jpamodelgen</artifactId>
			<!-- <version>5.0.12.Final</version> -->
	</dependency>
```

*Em seguida realizar os passos novamente:*

```
git add .
git commit -m "Comentario"
git push heroku master  
```

**Configura o mysql**

```

O comando abaixo passa a string que será configurada as variáveis de conexão

heroku config:get JAWSDB_URL

username (antes :)
senha (depois :)
url (depois do @)

mysql://username:password@url


```

```
Para verificar as variáveis de configuração

heroku config

=== lssd-money-api Config Vars
JAWSDB_URL:             
JDBC_DATABASE_PASSWORD: 
JDBC_DATABASE_URL:      
JDBC_DATABASE_USERNAME: 

```

**Comando para verficar se a aplicação subiu com sucesso no heroku**

```
heroku logs --tail

```