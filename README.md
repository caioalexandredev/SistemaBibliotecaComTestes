#  Sistema de Gerenciamento de Biblioteca Online 📖

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x.x-green?style=for-the-badge&logo=spring)
![JUnit 5](https://img.shields.io/badge/JUnit_5-5.x-orange?style=for-the-badge&logo=junit5)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red?style=for-the-badge&logo=apache-maven)
![TDD](https://img.shields.io/badge/TDD-Ready-brightgreen?style=for-the-badge)

## 🎯 Sobre o Projeto

Este projeto é uma implementação de um backend para um **Sistema de Gerenciamento de Biblioteca Online**, desenvolvido com foco na qualidade de código e nas melhores práticas de engenharia de software. Ele serve como um exemplo prático de aplicação de conceitos avançados de desenvolvimento em um cenário do mundo real.

O principal diferencial deste projeto é sua construção inteiramente guiada pela metodologia de **Desenvolvimento Orientado a Testes (TDD)**, garantindo que cada funcionalidade seja robusta, confiável e coberta por uma suíte de testes automatizados.

---

## ✨ Funcionalidades Implementadas

O sistema possui as seguintes funcionalidades, todas validadas por testes unitários e de integração:

#### 👤 **Gestão de Usuários**
- Cadastro de novos usuários com nome, e-mail e endereço.
- Validação de e-mail único e formato válido.
- Modelagem de endereço como uma entidade separada (`@OneToOne`), demonstrando um design de domínio mais rico.

#### 📚 **Busca de Livros**
- Pesquisa flexível de livros por título, autor ou categoria.
- A busca é case-insensitive e busca por conteúdo parcial (ex: "senhor" encontra "O Senhor dos Anéis").
- Implementado com a poderosa API **Query by Example** do Spring Data JPA.

#### 📅 **Reserva de Livros**
- Permite que usuários cadastrados reservem livros.
- **Regra de Negócio:** Um livro só pode ser reservado se seu status for `DISPONIVEL`.
- Ao ser reservado, o status do livro é atualizado para `RESERVADO`.

#### 🔄 **Renovação de Empréstimos**
- Permite que usuários renovem empréstimos existentes.
- **Regra de Negócio 1:** A renovação só é permitida antes da data de vencimento.
- **Regra de Negócio 2:** Há um limite máximo de 2 renovações por empréstimo.

---

## 🧠 Conceitos e Padrões Aplicados

- **Test-Driven Development (TDD):** O ciclo "Vermelho-Verde-Refatora" foi seguido para cada funcionalidade, começando pelos testes para definir o comportamento esperado.
- **Arquitetura em Camadas:** O projeto é organizado nas camadas `Domain`, `Repository` e `Service`, separando as responsabilidades de forma clara.
- **Mapeamento Objeto-Relacional (ORM):** Uso intensivo do Spring Data JPA (Hibernate) para persistência de dados e mapeamento de entidades, incluindo relacionamentos como `@OneToOne` e `@ManyToOne`.
- **Injeção de Dependências:** O contêiner do Spring gerencia o ciclo de vida e a injeção de componentes como serviços e repositórios.
- **Validação de Dados:** Uso do `Jakarta Bean Validation` (`@Valid`, `@Email`, `@NotBlank`) para garantir a integridade dos dados na camada de serviço.

---

## 🛠️ Tecnologias Utilizadas

- **Backend:**
    - [Java 17](https://www.oracle.com/java/)
    - [Spring Boot 3](https://spring.io/projects/spring-boot)
    - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
    - [Lombok](https://projectlombok.org/)
- **Testes:**
    - [JUnit 5](https://junit.org/junit5/)
    - O `spring-boot-starter-test` inclui Mockito e AssertJ.
- **Banco de Dados:**
    - [H2 Database](https://www.h2database.com/): Banco de dados em memória, ideal para desenvolvimento e testes.
- **Build e Gerenciamento de Dependências:**
    - [Apache Maven](https://maven.apache.org/)

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar o projeto em seu ambiente local.

### Pré-requisitos

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior.
- [Apache Maven](https://maven.apache.org/download.cgi) instalado e configurado.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd gerenciamento-biblioteca
    ```

3.  **Execute a aplicação com o Maven:**
    ```bash
    mvn spring-boot:run
    ```
    A aplicação estará rodando. Como não criamos uma camada de API REST, a principal forma de interagir e verificar o funcionamento é através dos testes.

4.  **Execute a suíte de testes:**
    Para verificar se todas as funcionalidades estão operando conforme o esperado, rode todos os testes automatizados:
    ```bash
    mvn test
    ```
    Você deverá ver uma saída indicando que todos os testes passaram com sucesso.

---

## 🌐 Endpoints da API (Exemplo para Futuro Desenvolvimento)

Embora o foco deste projeto tenha sido na lógica de negócio e nos testes, uma camada de API REST (`@RestController`) seria o próximo passo natural. Abaixo, um exemplo de como os endpoints poderiam ser estruturados:

| Método | URI                | Descrição                                 |
| :----- | :----------------- | :---------------------------------------- |
| `POST` | `/api/usuarios`    | Cadastra um novo usuário.                 |
| `GET`  | `/api/livros`      | Busca livros por título, autor ou categoria. |
| `POST` | `/api/reservas`    | Cria uma nova reserva para um livro.      |
| `POST` | `/api/emprestimos/{id}/renovar` | Renova um empréstimo existente.      |

---

## 👨‍💻 Autor

Feito com ❤️ por 

- **Caio Alexandre de Sousa Ramos** - alexandre.caio.ramos@gmail.com.
- **Idelton Borges** - alexandre.caio.ramos@gmail.com.