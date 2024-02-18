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

## SOLUÇÃO

### Arquitetura da Aplicação

Minha aplicação segue o padrão MVC (Model-View-Controller), com uma clara separação de responsabilidades entre as diferentes camadas:

- **Camada de Apresentação (Controller):** Aqui estão os controllers da aplicação, como UserController e CarController, responsáveis por receber as requisições HTTP dos clientes, rotear essas requisições para os serviços apropriados e retornar as respostas adequadas.

- **Camada de Serviço (Service):** Contém a lógica de negócios da aplicação. Nesta camada, estão as interfaces IUserService e ICarService, juntamente com suas implementações UserService e CarService. Os serviços lidam com as operações relacionadas aos usuários, carros e autenticação, coordenando a interação entre os controllers e os repositórios.

- **Camada de Persistência (Repository):** Responsável pelo acesso aos dados e interação com o banco de dados. Aqui estão as interfaces UserRepository e CarRepository, que estendem a interface CrudRepository do Spring Data. Os repositórios fornecem métodos para realizar operações CRUD no banco de dados de forma eficiente.

As camadas se comunicam entre si de forma organizada, seguindo o fluxo de controle típico do padrão MVC.

### Tecnologias Utilizadas

As principais tecnologias, frameworks e bibliotecas utilizadas na implementação do projeto são:

- Spring Boot
- Spring Security
- Spring Data JPA
- Junit 5
- Lombok
- Mapstruct
- JWT
- Swagger

Essas tecnologias foram escolhidas por sua robustez, facilidade de uso e suporte à construção de APIs RESTful escaláveis e seguras.

### Decisões de Design

Durante o desenvolvimento da aplicação, foram tomadas as seguintes decisões de design:

- Uso de DTOs e Mappers
- Tratamento de Exceções Personalizado

Essas decisões foram tomadas com base nos requisitos do projeto, nas melhores práticas de desenvolvimento e nas necessidades de escalabilidade e manutenibilidade da aplicação.

### Testes Unitários

Os testes unitários são uma parte fundamental do processo de desenvolvimento de software, garantindo que cada componente da aplicação funcione conforme o esperado de forma isolada. Para isso, foram elaborados testes unitários utilizando o framework JUnit 5.

Os testes unitários abrangem diversas partes da aplicação, incluindo os serviços, controllers, mappers e outras classes que contenham lógica de negócio. O objetivo é verificar se cada unidade de código se comporta conforme o esperado em diferentes cenários.

### Executando o Projeto

Para executar o projeto localmente, siga as instruções abaixo:

1. Clone este repositório: `git clone https://github.com/nylbert/carUsersApi.git`
2. Navegue até o diretório do projeto: `cd carusersapi`
3. Compile o projeto: `mvn clean package`
4. Execute o projeto: `java -jar target/api-0.0.1-SNAPSHOT.jar`
5. O servidor estará rodando em `http://localhost:8080`

É necessário ter as seguintes tecnologias instaladas para executar a API localmente:

- Java 17
- Maven

Após a execução, você pode acessar a documentação da API utilizando o Swagger em: `http://localhost:8080/swagger-ui.html`

---
