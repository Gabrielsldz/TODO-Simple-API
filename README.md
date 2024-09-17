# TODO-Simple API

Esta é uma API RESTful para gerenciar tarefas (To-Do List) de usuários, desenvolvida utilizando Spring Boot, Maven, Docker e integrada com um banco de dados MySQL. A aplicação foi desenvolvida para ser modular, facilitando futuras alterações e manutenções.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.3**
  - Spring Data JPA
  - Spring Web
  - Spring Security (autenticação via JWT)
  - Spring Validation
- **MySQL** (via `mysql-connector-j`)
- **Lombok** (para simplificação de código)
- **Docker** (para containerização)
- **Maven** (para gerenciamento de dependências)
- **JWT** (para autenticação)

## Funcionalidades

- CRUD de usuários.
- CRUD de tarefas vinculadas a cada usuário.
- Autenticação e autorização utilizando tokens JWT.
- Validação de dados com Spring Validation.
- Integração com banco de dados MySQL.
- Arquitetura modular para facilitar manutenções e expansões futuras.

## Pré-requisitos

- **Java 17** ou superior instalado.
- **Docker** instalado.
- **Maven** instalado.

## Configuração e Execução

### 1. Clonar o Repositório

```bash
git clone https://github.com/Gabrielsldz/TODO-Simple-AP
cd todosimple
```

### 2. Compilar e Construir o Projeto

Compile e construa o projeto utilizando Maven:

```bash
mvn clean install
```

### 3. Executar com Docker

A aplicação foi configurada para rodar com Docker. O projeto já contém um `Dockerfile` e um arquivo `docker-compose.yml` que facilitam o deploy da aplicação juntamente com o banco de dados MySQL.

Para executar a aplicação com Docker, certifique-se de ter o Docker instalado e em execução, e use o comando:

```bash
docker-compose up --build
```

Isso iniciará os contêineres da aplicação e do banco de dados.

## Testes

Os testes unitários e de integração estão incluídos no projeto. Para executá-los, utilize o seguinte comando:

```bash
mvn test
```

## Estrutura Modular

A aplicação foi desenvolvida de forma modular, permitindo que cada componente (usuário, tarefa, autenticação, etc.) seja facilmente isolado e modificado. Isso facilita a escalabilidade e a manutenção do projeto, permitindo a implementação de novas funcionalidades com mínimo impacto no código existente.

---
