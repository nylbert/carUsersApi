# API de Gerenciamento de Usuários e Carros

## ESTÓRIAS DE USUÁRIO

1. **AT-01:** Criar aplicação Spring Boot.
2. **AT-02:** Criar entidades da aplicação.
3. **AT-03:** Criar repositórios.
4. **AT-04:** Criar services.
5. **AT-05:** Criar DTOs.
6. **AT-06:** Criar Mappers.
7. **AT-07:** Implementar CRUD de Usuários.
8. **AT-08:** Implementar Login com Spring Security.
9. **AT-09:** Implementar CRUD de Carros.
10. **AT-10:** Implementar Swagger.
11. **AT-11:** Implementar endpoint de informações do usuário logado.
12. **AT-12:** Implementar Testes unitarios
13. **AT-13:** Implementar Requisitos desejaveis
14. **AT-14:** Implementar Requisitos extras

## SOLUÇÃO

### Arquitetura da Aplicação

Minha aplicação segue o padrão MVC (Model-View-Controller), com uma clara separação de responsabilidades entre as diferentes camadas:

- **Camada de Apresentação (Controller):** Aqui estão os controllers da aplicação, como UserController e CarController, responsáveis por receber as requisições HTTP dos clientes, rotear essas requisições para os serviços apropriados e retornar as respostas adequadas.

- **Camada de Serviço (Service):** Contém a lógica de negócios da aplicação. Nesta camada, estão as interfaces IUserService e ICarService, juntamente com suas implementações UserService e CarService. Os serviços lidam com as operações relacionadas aos usuários, carros e autenticação, coordenando a interação entre os controllers e os repositórios.

- **Camada de Persistência (Repository):** Responsável pelo acesso aos dados e interação com o banco de dados. Aqui estão as interfaces UserRepository e CarRepository, que estendem a interface CrudRepository do Spring Data. Os repositórios fornecem métodos para realizar operações CRUD no banco de dados de forma eficiente.

As camadas se comunicam entre si de forma organizada, seguindo o fluxo de controle típico do padrão MVC.

### Tecnologias Utilizadas

As principais tecnologias, frameworks e bibliotecas utilizadas na implementação do projeto são:

- Spring Boot 3
- Spring Security
- Spring Data JPA
- Junit 5
- Lombok
- Mapstruct
- JWT
- Swagger

Essas tecnologias foram escolhidas por sua robustez, facilidade de uso e suporte à construção de APIs RESTful escaláveis e seguras.

### Testes Unitários

Os testes unitários são uma parte fundamental do processo de desenvolvimento de software, garantindo que cada componente da aplicação funcione conforme o esperado de forma isolada. Para isso utilizei o framework JUnit 5.

Implementei os testes unitarios focando nos controllers e nos services.

A cobertura ficou em cerca de 87%

### Executando o Projeto

Para executar o projeto localmente, siga as instruções abaixo:

1. Clone este repositório: `git clone https://github.com/nylbert/carUsersApi.git`
2. Navegue até o diretório do projeto: `cd carUsersApi`
3. Compile o projeto: `mvn clean package`
4. Execute o projeto: `java -jar target/api-0.0.1-SNAPSHOT.jar`
5. O servidor estará rodando em `http://localhost:8080`

É necessário ter as seguintes tecnologias instaladas para executar a API localmente:
- Java 17
- Maven

Após a execução, você pode acessar a documentação da API utilizando o Swagger em: `http://localhost:8080/swagger-ui.html`

### Executando os Testes Unitários

Utilize o comando `mvn test`.

### URL da aplicação no Heroku

O repositório escolhido para deploy da aplicação foi o Heroku:

`https://user-car-api-2197b529f599.herokuapp.com/api/`

### Postman

Na pasta raiz do projeto encontra-se o arquivo `REST API- CRUD.postman_collection.json` que contem requests para todos os endpoints da API.

### Javadocs

Adicionei a documentação javadoc apenas nas interfaces dos services: IUserService e ICarService por serem as classes focadas nas regras de negócio.

### Upload de imagens

Realizar o upload de imagens através dos endpoints:
`/api/user/{id}/image` e `/api/cars/{id}/image`

No arquivo postman contem um exemplo de como deve ser feito o request

### Scheduling

Para esse requisito criei um agendamento bem simples o `CarScheduler` que efetua a exclusão de todos os carros cadastrados uma vez por dia às 00:00. 

### Front

O front foi construído com angular 15 e node 18. 

Devido ao tempo limitado foi construído visando `práticidade` então não se assustem rsrs.

Para mais informações de como rodar o front ler o README.md do projeto em: `https://github.com/nylbert/carsUsersFront` 

---
