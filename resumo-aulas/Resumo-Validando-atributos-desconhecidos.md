# Validando atributos desconhecidos

Imagine o seguinte cenário:

o cliente faz um POST com os seguintes atributos, sendo que somente "nome" é um atributo válido.

```jason
{
	"nome" : "Restaurantes" ,
	"descricao" : "Gastos em restaurantes",
	"outra" : "Outra observacao"
}
```
**application.properties**

```#deserialização, processo que transforma de um objeto jason para java
spring.jackson.deserialization.fail-on-unknown-properties=true 
```

se no caso acima estiver true a seguinte mensagem será exibida:

```jason
{
    "timestamp": 1505955976349,
    "status": 400,
    "error": "Bad Request",
    "exception": 
    "org.springframework.http.converter.HttpMessageNotReadableException",
    "message": "JSON parse error: 
    Unrecognized field \"descricao\" 
    (class com.lssdeveloper.money.api.model.Categoria), 
    not marked as ignorable; nested exception is com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: 
    Unrecognized field \"descricao\" 
    (class com.lssdeveloper.money.api.model.Categoria), 
    not marked as ignorable 
    (2 known properties: \"codigo\", \"nome\"])\n at 
    [Source: java.io.PushbackInputStream@269c81cf; line: 3, column: 17] 
    (through reference chain: com.lssdeveloper.money.api.model.Categoria[\"descricao\"])",
    "path": "/categorias"
}
```
**Veja que a mensagem de erro é bem clara quanto ao não reconhecimento deste campo:**

```
"message": "JSON parse error: 
    Unrecognized field \"descricao\" 
    (class com.lssdeveloper.money.api.model.Categoria), 
```    


**Status 400 Bad Request**

se na regra de negócio houver necessidade de controlar este erro use a forma acima, senão por default a deserialização é false.

```#deserialização, processo que transforma de um objeto jason para java
spring.jackson.deserialization.fail-on-unknown-properties=false
```

não sendo necessário informá-la.

Mas no caso será inserido no banco 	"nome" : "Restaurantes" 

*Havendo qualquer dúvida verifique as imagens salvas respectivo ao assunto.*