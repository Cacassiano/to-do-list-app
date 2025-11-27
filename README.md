# To-Do List Application

Esta aplicação é um sistema completo de gerenciamento de tarefas, composto por um backend desenvolvido em Spring Boot e um frontend construído em React. Toda a solução foi conteinerizada utilizando Docker e orquestrada via Docker Compose.

## Tecnologias Utilizadas

### Backend (Spring Boot)
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT para autenticação
- Flyway para controle de migrações
- Testes automatizados com JUnit, Mockito e bibliotecas de teste do Spring

### Frontend (React)
- React
- Tailwind CSS
- Axios para comunicação com o backend

### Infraestrutura
- Docker com Dockerfiles separados para backend e frontend
- Docker Compose com arquivo .yml na raiz do projeto
- Acesso ao frontend via: http://localhost:80

## Descrição da Aplicação

A aplicação permite o gerenciamento completo de tarefas, oferecendo funcionalidades de criação, listagem, atualização e exclusão.  
A autenticação é baseada em JWT, garantindo controle de acesso seguro às rotas protegidas.  
O backend expõe uma API REST consumida pelo frontend, enquanto o PostgreSQL é utilizado como banco de dados relacional, com versionamento de estrutura realizado via Flyway.

A arquitetura foi projetada para ser modular e de fácil execução, com todos os serviços configurados para subir automaticamente através do Docker Compose.

## Execução

Na raiz do projeto, execute:

```

docker compose up

```

Após a inicialização, o frontend estará disponível em:

```

[http://localhost:80](http://localhost:80)

```

O backend e o banco de dados serão iniciados automaticamente conforme configurado no Docker Compose.
