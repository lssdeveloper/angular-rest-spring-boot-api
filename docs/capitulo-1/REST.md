## REST

É uma forma de integrarmos sistemas.

### Traduzindo, Cenário

Dois sistemas diferentes e precisa integrá-los
Faremso isso usando o REST

Um back-end que fornece serviços REST (feito com Java)
O outro é um front-en consumindo serviços REST (feito com Angular)

São aplicações diferentes interagindo com REST

*REST é uma arquitetura, te orientando através de algumas regras e princípios.*

*ful implementação usando conceitos do REST*

*API REST aplicações que proporcionam serviços externos baseados em REST*




### Ambiente de Desenvolvimento REST (Neste Curso)

Spring Boot
Spring Tool Suite (IDE)
Postman
MySQL

### Testando APIs com Postman

www.mocky.io


### Introdução ao Protocolo HTTP

*Basicamente o HTPP é uma pergunta e uma resposta.
O HTTP é baseado em uma requisição e uma resposta.*

HTTP Request  (Ex: Postman, Browser, Angular)
HTTP Response (Ex: Mocky.io)

Toda requisição HTTP tem um verbo, um método (GET, POST, PUT, DELETE ...)

Toda resposta HTTP tem um código (Code: 200 OK, 404, 500), Headers: Content-Type, etc

###### Exemplo de Resposta:
```JSON
[
	{
		"codigo": "1", 
		"nome": "Zé das Couves"
	}
]
```

### O que é um recurso?
É tudo aquilo que tem informações que pode ser identificado por um Id.

*URI = Recurso*

###### Exemplos de Recurso (URI)

*Retornando um identificador*

`http://barcelona.com/jogadores/10`

*10* identifica um dos jogadores do Barcelona

**Uma URI é a parte da URL que identifica um recurso.**

*Retornando uma lista de recusos*

Com esta URI consigo recuperar a lista de jogadores do Barcelona

`http://barcelona.com/jogadores`


*** Representações de um recurso

**Cuidado! Não confundir recurso com a representação de um recurso.**

*Existem várias representações possíveis para um recurso.*

*Por exemplo:*
`http://barcelona.com/jogadores/10`

Quero buscar o jogador do barcelona que é o 10
Eu posso pedir uma imagem, ou uma ficha mais detalhada

```JSON
{
    "nome" : "Leonel Messi",
    "apelido" : "La Pulga",
    "poisção" : "Atacante", 
    "idade" : "30",
    "altura" : "1,69",
    "nacionalidade" : "Argentina""
}
```
Ou seja, é o mesmo recurso, mas com representações diferentes:
Podendo ser, imagem, JSON, XML ...

*E como é feita essa distinção?*
Através de Headers HTTP


### Modelo de maturidade Richardson - Nível 0
                        Glória do REST
                   Nível 3:Você consegue dizer no retorno da requisição como fazer futuras ações
                Nível 2: Já pode considerar como REST (Utiliza os verbos HTTP mais eficiente)
            Nível 1: Ainda não é REST (Noção de recurso)
        Nível 0: Não é REST (HTTP só para Transporte)  

###### Nível 0 = Semelhante ao SOAP baseia-se em chamadas de funções 
**Exemplo:**

    POST buscarMedicos HTTP/1.1

`<buscarHorariosDisponiveis data = "2017-10-02" medico = "pereira"/>`

    Resposta:

    POST /horarios HTTP/1.1 

###### Nível 1 = Começa introduzir conceitos de recursos.
**Exemplo:**

    POST /medicos/pereira HTTP/1.1

`<buscarHorariosDisponiveis data = "2017-10-02"/>`

    Resposta:

    POST /horarios/1234 HTTP/1.1

###### Nível 2 = Utiliza os verbos corretamente, mas clareza e mais recursos utilizados (Aqui já é sufieciente).
**Exemplo:**

    GET /medicos/pereira/horarios?data=2017-10-02&status=disponivel HTTP/1.1

    Resposta:

    POST /horarios/1234 HTTP/1.1

    ou

    PUT  /horarios/1234 HTTP/1.1

###### Nível 3 = REST completo (demanda mais implementações, tornando o processo demorado complexo demais, proporcionando maior acoplamento do que será retornado)
**Exemplo:**

    GET /medicos/pereira/horarios?data=2017-10-02&status=disponivel HTTP/1.1

    Resposta:(Aqui é que muda, tornando-se complexo demais)

    POST /horarios/1234 HTTP/1.1

    ou

    PUT  /horarios/1234 HTTP/1.1

### HATEOAS

**Hypertext As The Engine of application State**

*Hipertexto como o motor de estado da aplicação*

##### Formas de escrever HATEOAS

### HAL

<http://stateless.co/hal_specification.html>

<https://en.wikipedia.org/wiki/Hypertext_Application_Language>







