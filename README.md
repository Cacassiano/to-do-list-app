
# To-Do List Application

Este repositório contém uma aplicação de lista de tarefas (To-Do List) dividida em frontend e backend.

## Visão geral

- Frontend: aplicação construída com Vite + React (arquivos no diretório `frontend/`).
- Backend: API REST construída com Spring Boot (Java), expondo endpoints para gerenciamento de usuários e tarefas (arquivos no diretório `backend/`).
- Persistência: PostgreSQL com migrações de banco de dados versionadas.
- Build e execução: Maven para o backend; Node/npm para o frontend. Docker e Docker Compose estão disponíveis para orquestrar os serviços.

## Principais Tecnologias

- Java + Spring Boot: aplicação web REST com controllers, services e repositories.
- Spring Security: autenticação/autorizações básicas usando tokens JWT.
- Spring Data JPA: acesso a dados e repositórios.
- Flyway: migrações de esquemas do banco de dados.
- PostgreSQL: banco de dados relacional usado em produção.
- Maven: gerenciamento de dependências e ciclo de vida de build.
- Testes: JUnit + Spring Test e H2 Database para simular banco de dados.

## Estrutura importante do projeto

- `backend/` - código-fonte do backend (Spring Boot)
	- `src/main/java/dev/cassiano/to_do_api/` - controllers, services, entities, repositories
	- `src/main/resources/` - propriedades de configuração e migrações (`db/migration`)
	- `src/test/java/...` - testes unitários e de integração
- `frontend/` - código do cliente (Vite + React)
- `docker-compose.yml` - orquestração dos containers: `db` (postgres), `backend`, `frontend`

## Variáveis de ambiente relevantes

O `docker-compose.yml` já define algumas variáveis usadas pelo backend. Valores podem ser sobrescritos via ambiente ou `.env`:

- `DB_URL` 
- `DB_USERNAME` 
- `DB_PASSWORD`

> Observação: a aplicação usa tokens para autenticação. Se houver propriedades relacionadas a JWT (secret, expiration) em `application.properties`, ajuste conforme necessário em ambiente de produção.

## Como rodar (opção A) — Usando Docker Compose (mais rápido)

1. Na raiz do projeto, execute:

```bash
docker compose up --build
```

2. Serviços disponíveis após o build:
- PostgreSQL: porta `5432` (container `db`)
- Backend: porta exposta conforme `docker-compose.yml` (ex.: `8080`)
- Frontend: porta `80`

3. A aplicação backend aplica as migrações do banco automaticamente na inicialização (Flyway), criando as tabelas previstas em `backend/src/main/resources/db/migration/`.

## Como rodar (opção B) — Sem Docker (executar localmente)

Pré-requisitos:
- Java (JDK) compatível com o projeto (use o JDK indicado no `pom.xml` — o wrapper Maven (`mvnw`) garante consistência do build)
- Maven (opcional, você pode usar o wrapper `./mvnw`)
- Node.js + npm (para executar o frontend)
- Um PostgreSQL rodando localmente ou em outro host acessível

Passos:

1. Configure um banco PostgreSQL (local ou via Docker):

```bash
docker run --name todolist-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=todolist -p 5432:5432 -d postgres:14
```

2. Ajuste as variáveis de ambiente para apontar para o banco (export ou arquivo `.env`). Exemplo mínimo:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/todolist
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

3. Rodar o backend com o Maven wrapper:

```bash
cd backend
./mvnw spring-boot:run
```

Ou empacotar e executar o jar:

```bash
cd backend
./mvnw -DskipTests package
java -jar target/*.jar
```

4. Rodar o frontend (se desejar rodar localmente em modo desenvolvimento):

```bash
cd frontend
npm i
npm run dev
```

Observação: ajuste no `frontend/src/services/AxiosService.js` a URL base da API se necessário (ex.: apontar para `http://localhost:8080/api` ou `http://localhost:9000/api` dependendo da configuração do backend — ver `application.properties`).

## Testes

Para executar a suíte de testes do backend:

```bash
cd backend
./mvnw test
```

Para rodar uma classe de teste específica (ex.: `TaskControllerTests`):

```bash
./mvnw -Dtest=dev.cassiano.to_do_api.controllers.TaskControllerTests test
```

## Endpoints (sumário)

O backend expõe endpoints REST para gestão de usuários e tarefas. Exemplos (verificar controllers para detalhes e payloads):

- `POST /api/users` - criar usuário
- `POST /api/login` - autenticar e obter token
- `GET /api/tasks` - listar tarefas do usuário autenticado
- `GET /api/tasks/{id}` - obter tarefa por id
- `POST /api/tasks` - criar tarefa
- `PUT /api/tasks/{id}` - atualizar tarefa
- `DELETE /api/tasks/{id}` - deletar tarefa

Consulte os controllers em `backend/src/main/java/dev/cassiano/to_do_api/controllers/` para contratos exatos de request/response (DTOs em `dtos/`). Os testes em `src/test` também mostram exemplos de uso das rotas.

## Observações

- Mantenha as variáveis sensíveis (JWT secret, DB password) fora do repositório — use variáveis de ambiente ou secret manager.
- Em produção, configure um banco gerenciado ou um container Postgres separado e faça backup das migrações e dados.
- Validar CORS e configuração de segurança caso o frontend esteja servido de outro domínio.

## Depuração / Problemas comuns

- Erro de conexão com o banco: verifique `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e se o Postgres está aceitando conexões na porta correta.
- Migrações não aplicadas: verifique logs do backend na inicialização — Flyway normalmente aplica migrações automaticamente.
- Portas em uso: caso as portas 80/8080/5432/9000 estejam ocupadas, ajuste `server.port` no `application.properties` ou as portas do `docker-compose.yml`.

## Contribuição

1. Faça um fork e crie uma branch com o seu feature/bugfix.
2. Mantenha os commits pequenos e claros.
3. Rode os testes localmente antes de abrir um PR.
