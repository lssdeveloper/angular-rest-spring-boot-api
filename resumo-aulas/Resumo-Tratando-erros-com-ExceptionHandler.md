# Tratando erros com ExceptionHandler

```java
LssdMoneyExceptionHandler.java

@ControllerAdvice
public class LssdMoneyExceptionHandler extends ResponseEntityExceptionHandler {
```

**messages.properties**

```
mensagem.invalida=Mensagem inv\u00E1lida
```
*Mensagem exibida no Postman*

```jason
{
    "mensagemUsuario": "Mensagem inválida",
    "mensagemDesenvolvedor": 
    "com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: 
    Unrecognized field \"descricao\" 
    (class com.lssdeveloper.money.api.model.Categoria), 
    not marked as ignorable (2 known properties: 
    \"codigo\", \"nome\"])\n at 
    [Source: java.io.PushbackInputStream@a40dd15; line: 3, column: 17] 
    (through reference chain: com.lssdeveloper.money.api.model.Categoria[\"descricao\"])"
}
```