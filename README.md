# LiterAlura - Book Catalog with Gutendex API


Este projeto é uma aplicação Java com Spring Boot que consome a API pública [Gutendex](https://gutendex.com) para buscar, salvar e listar livros no banco de dados.

### Funcionalidades

- Buscar livro por título
- Listar livros salvos
- Listar autores salvos
- Listar autores vivos em determinado ano
- Listar livros por idioma

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL ou outro banco de dados compatível com Spring Data JPA

### Como executar

1. Clone este repositório
2. Configure o `application.properties` com as credenciais do seu banco de dados
3. Rode o projeto via IDE ou com o Maven:

```bash
mvn spring-boot:run

1- Buscar livro pelo título
2- Listar livros registrados
3- Listar autores registrados
4- Listar autores vivos em um determinado ano
5- Listar livros em um determinado idioma
0- Sair
```
## Buscar livro por título
```bash


1- Dom Casmurro

```
## Buscar livro por idioma
```bash

pt  - Português
en  - Inglês
fr  - Francês
es  - Espanhol

```

