# O que é SOFEA?

### Service-Oriented Front-End Architecture

### Arquitetura de Front-end Orientada a Serviços

#### Idéia básica:
```
Remover toda a lógica de apresentação do servidor e levar para o cliente.
ou seja:
Parar de renderizar as solicitações do cliente no servidor, essa solicitação agora é renderizada na
máquina do cliente.
```
#### Vantagens:
```
 - Desenvolvimento assíncrono do front-end e back-end
 - Escalabilidade, o back-end fica stateless (sem estado), significando facilidade na escalabilidade.
 - Interoperabilidade, podendo ser consumido em qualquer API, Android, IOs, Windows, Linux e etc, por
 qualquer linguagem de programaçao que suporte REST.
 - Melhor experiência do usuário e baixa latência, buscando somente dados (JSON), o front-end 
(html+css+javascript) já foi baixado para a máquina.
```
#### Desvantagens:
```
 - Esta é uma arquitetura muito mais trabalhosa de ser alcançada, ainda é mais fácil construir 
aplicações da forma tradicional.
```
#### Considerações:
###### Angular X JSF
```
Fora as questões óbvias (como, por exemplo, a linguagem base de cada framework - Typescript e Java).
A grande diferença é que uma aplicação feita com JSF é mais simples de ser desenvolvida, pois, você 
trabalha com um só projeto, praticamente uma só linguagem que é o Java (apesar de ter JavaScript 
envolvido, não precisamos conhecê-lo), enquanto que para que uma aplicação Angular funcione é preciso 
que uma outra aplicação, em uma outra linguagem (em nosso caso, o Java) seja feita para complementá-la.

Quando o JSF busca o HTML através do ajax o mesmo já vem junto com os dados. Contrário a isso, 
o Angular busca o HTML uma vez só e então depois busca os dados e faz o processamento do HTML com 
os dados.

Como disse, o JSF deixa as coisas mais simples... Por outro lado, o Angular simplifica os casos em 
que precisamos, no frontend, consumir várias APIs de uma vez só.
```
###### Exemplo de escalabilidade real:
```
Com o backend stateless (sem estado) nós podemos ter o seguinte cenário...

Podemos usar um serviço como o Heroku, por exemplo, publicar a aplicação em um dyno (pense em um 
dyno como uma máquina) e depois somente dar um comando para levantar mais 5 dynos 
(ou máquinas servidoras). E um mesmo usuário vai poder ter sua requisição atendida em qualquer um dos
 5 dynos que ele nem percebe.

Em uma aplicação com estado, nós teríamos que compartilhar a sessão entre os dynos (servidores) ou 
sempre atender a uma mesmo usuário com o mesmo dyno.
```
#### Questões interessantes:

##### Cenário 1
Como funciona escalar uma aplicação em vários servidores, no sentido de configuração, precisa realizar
muita configuração, ou é transparente? precisando apenas chamar o serviço? Pensando na arquitetura SOFEA.

**Resposta:**

Se você tiver uma aplicação que não guarda estados (a não ser no banco de dados que, nesse caso, deve 
ser compartilhado por todas as instâncias) então é bem mais fácil.

Primeiro você publica sua aplicação em várias máquinas na rede e, ainda nessa rede, você separa uma 
máquina nova para ser o balanceador de carga. O balanceador é a máquina que vai receber todas as 
requisições e decidir quem está menos sobrecarregado para atendê-la.

Veja aqui uma imagem que representa isso que falei: 
<http://www.serverlab.ca/wp-content/uploads/2014/03/51-figure-02c.png>

A boa notícia é que temos muito bons serviços hoje que nos dão esses balanceadores. No Heroku, por 
exemplo, basta você dizer que quer uma instância a mais da sua aplicação (lá eles chamam de dynos) 
que a carga já será balanceada.

##### Cenário 2
Em uma configuração de load Balancer manual. Uma aplicação web recebendo requisições e distribuindo as 
requisições para as máquinas "web services" com menos requisições. 
Ex: "Tenho 3 web service e um load Balancer, totalizando 4 maquinas, certo? Então recebi 10 requisições, 
essas requisições chegam no load Balancer e ele distribui "4 requisições para um web service" e 
"3 requisições para cada um dos outros dois web services",e esses "web services" entregam as respostas
para seus clientes, é isso?
As máquinas precisam estar na mesma rede?

**Resposta:**

Sim. Só não precisamos desenvolver o load balance... Somente realizar as configurações necessárias.

[Veja um exemplo:] <https://www.digitalocean.com/community/tutorials/how-to-set-up-nginx-load-balancing>.




