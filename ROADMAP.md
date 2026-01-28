# Loja de Variedades - Backend (Template Spring Boot + MySQL)

Este repositório contém um template inicial de API REST usando Java Spring Boot conectado ao MySQL. Foi criado para servir como base para uma aplicação "Loja de Variedades".

## Objetivo
Fornecer um backend simples com CRUD de produtos e infra mínima para você expandir a loja.

## O que contém
- Estrutura Maven com dependências Spring Web, Spring Data JPA e MySQL
- Modelo `Product`, repositório, serviço e controller com endpoints CRUD
- `docker-compose.yml` para subir um container MySQL pronto
- `application.properties` com placeholders para configuração
 - Segurança básica com JWT e controle de roles (template)
 - `Dockerfile` e serviço `app` para orquestração via `docker-compose`

## Rodando localmente (rápido)
1. Inicie o MySQL via Docker Compose:

```bash
docker-compose up -d
```

2. Ajuste as credenciais em `src/main/resources/application.properties` se necessário.
3. Construa e rode:

```bash
mvn clean package
mvn spring-boot:run
```

API base: `http://localhost:8080/api/products`

-------------------------

Como este template está organizado
- `src/main/java/.../model` — entidades (`Product`, `User`, `Role`).
- `src/main/java/.../repository` — repositórios Spring Data JPA.
- `src/main/java/.../service` — serviços com a lógica de negócio.
- `src/main/java/.../controller` — controllers REST (`/api/**` e `/auth/**`).
- `src/main/resources` — `application-*.properties` (perfils `dev`/`prod`).
- `docker-compose.yml` — MySQL e (opcional) serviço `app` para orquestração.
- `Dockerfile` — imagem da aplicação.

Pré-requisitos
- Java 17
- Maven
- Docker & Docker Compose (opcional, recomendado para MySQL local)

Quickstart (modo rápido)
1. Subir o banco MySQL via Docker Compose:

```bash
docker-compose up -d
```

2. Construir a aplicação:

```bash
mvn -DskipTests package
```

3. Rodar localmente (perfil `dev` por padrão):

```bash
mvn spring-boot:run
# ou
java -jar target/lojadevariedades-0.0.1-SNAPSHOT.jar
```

4. Endpoints principais
- `POST /auth/login` — corpo JSON `{ "username": "admin", "password": "adminpass" }` retorna `{ token }`.
- `GET /api/products` — listar produtos (protegido; requer `Authorization: Bearer <token>`).
- `GET /api/products/{id}` — obter produto por id.
- `POST /api/products` — criar produto (ADMIN recomendado).
- `PUT /api/products/{id}` — atualizar produto (ADMIN recomendado).
- `DELETE /api/products/{id}` — deletar produto (ADMIN recomendado).

Exemplo de login e uso do token (curl):

```bash
# Login (gera token)
curl -X POST -H "Content-Type: application/json" -d '{"username":"admin","password":"adminpass"}' http://localhost:8080/auth/login

# Usar token para listar produtos
curl -H "Authorization: Bearer <TOKEN_AQUI>" http://localhost:8080/api/products
 
 # Exemplo de upload de imagem (usar token obtido no login)
 curl -H "Authorization: Bearer <TOKEN_AQUI>" -F "file=@/caminho/para/imagem.jpg" http://localhost:8080/api/products/1/image
```

Observações sobre autenticação
- O projeto já inclui um `DataInitializer` que cria dois papéis (`ROLE_ADMIN`, `ROLE_USER`) e um usuário `admin` com senha `adminpass` no primeiro start (perfil `dev`).
- Use `PasswordEncoder` (BCrypt) para senhas; em produção troque a senha padrão e o `jwt.secret` por variáveis de ambiente seguras.

Configuração por perfil
- `application.properties` — configurações comuns; `spring.profiles.active=dev` por padrão.
- `application-dev.properties` — usa `localhost` para MySQL e `ddl-auto=update`.
- `application-prod.properties` — usa `db` como host (quando a app roda dentro do `docker-compose`) e `ddl-auto=validate`.

Rodando tudo com Docker (app + db)
- Para orquestrar MySQL e a aplicação juntos, o `docker-compose.yml` inclui um serviço `app` que utiliza o `Dockerfile` do projeto.
- Build e subir:

```bash
docker-compose up --build -d
```

Banco de dados (credenciais padrão do compose)
- Database: `lojadevariedades`
- Username: `root`
- Password: `example`

Testes
- Rodar testes (unitários e integração, quando adicionados):

```bash
mvn test
```

Build e imagem Docker manual

```bash
mvn -DskipTests package
docker build -t lojadevariedades:latest .
docker run -e SPRING_PROFILES_ACTIVE=prod -p 8080:8080 --network host lojadevariedades:latest
```

CI/CD (exemplo mínimo GitHub Actions)
- Crie `.github/workflows/ci.yml` com um fluxo básico de build/test:

```yaml
name: CI
on: [push]
jobs:
   build:
      runs-on: ubuntu-latest
      steps:
         - uses: actions/checkout@v4
         - name: Set up JDK 17
            uses: actions/setup-java@v4
            with:
               java-version: '17'
         - name: Build with Maven
            run: mvn -B -DskipTests package
         - name: Run tests
            run: mvn test
```

Segurança e boas práticas
- Nunca commit `jwt.secret` em repositórios públicos. Use variáveis de ambiente no CI/CD e em produção.
- Troque a senha `adminpass` por algo forte e crie um fluxo de gestão de usuários.

Roadmap e checklist (estado atual resumido)
- Modelagem básica: `Product` (OK)
- Perfis `dev`/`prod`: (OK)
- CRUD `Product`: (OK)
- Autenticação JWT e roles: (OK)
- Dockerfile e `docker-compose` com `app`: (OK)
 - Paginação/ordenacão (OK)
 - Upload de imagens (OK)
- `Category` (pendente)
- `Order` e carrinho (pendente)
- Testes unitários/integr. (pendente)
- CI/CD (template básico adicionado acima)

Contribuição
- Abra issues e pull requests. Mantenha as mudanças pequenas e documentadas.

Contato
- Se precisar, posso:
   - Implementar paginação em `GET /api/products`.
   - Adicionar upload de imagens com armazenamento local/S3.
   - Criar endpoints de `Category` e `Order`.

Licença
- Este template é fornecido sem licença específica; adicione uma se necessário.

## Perfis (ambientes)

- O projeto já vem com dois perfis configurados: `dev` e `prod`.

- `application.properties`: configurações comuns e perfil padrão (`dev`).
- `application-dev.properties`: configurações para desenvolvimento (usa `localhost` como host do MySQL).
- `application-prod.properties`: configurações para produção (usa `db` como host — útil quando a app roda em container numa mesma rede do `docker-compose`).

Como executar com um perfil específico:

```bash
# Usando Maven (ex.: perfil prod)
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Usando o JAR (ex.: perfil prod)
java -Dspring.profiles.active=prod -jar target/lojadevariedades-0.0.1-SNAPSHOT.jar
```

Se for executar a aplicação em um container junto com o MySQL via `docker-compose`, defina a variável de ambiente `SPRING_PROFILES_ACTIVE=prod` no serviço da aplicação no `docker-compose.yml` (eu posso adicionar essa configuração se quiser dockerizar a app também).
## Roadmap (passos sugeridos)
1. Modelagem básica
   - [x] `Product` (nome, descrição, preço, estoque)
2. Infra e desenvolvimento local
   - [x] `docker-compose` com MySQL
   - [x] Configurar perfis (`dev`, `prod`)
3. Funcionalidades CRUD
   - [x] Endpoints CRUD para `Product`
   - [x] Paginação, ordenação e filtros
     - [ ] Implementar `Pageable` em `ProductRepository`/`ProductController`
   - [x] Upload e gerenciamento de imagens dos produtos (stub)
      - [x] Endpoint de upload (`POST /api/products/{id}/image`)
      - [x] Armazenamento local em `uploads/` (configurável via `upload.dir`)

4. Domínio e fluxos da loja
   - [ ] `Category` (categorias de produto)
     - [ ] Entidade `Category` e relação com `Product`
     - [ ] CRUD para `Category`
   - [x] `Customer` / autenticação (JWT)
   - [ ] `Order` e gerenciamento de carrinho
     - [ ] Entidades `Order` e `OrderItem`
     - [ ] Endpoints para criar/consultar pedidos
   - [ ] Pagamento (integração stub/real)

5. Segurança e permissões
   - [x] Autenticação com JWT
   - [x] Roles (USER, ADMIN) e controle de acesso
   - [ ] Proteção de endpoints administrativos
     - [ ] Restringir CRUD de produtos/categorias para `ROLE_ADMIN`

6. Infra e deploy
   - [x] Dockerizar aplicação (`Dockerfile`) e serviço `app` no `docker-compose`
   - [ ] CI/CD (GitHub Actions) - adicionar workflow de build/test/deploy
   - [ ] Deploy em cloud (Heroku, AWS, GCP, etc.)

7. Qualidade
   - [ ] Testes unitários e de integração
     - [ ] Testes para `ProductService` e `ProductController`
   - [ ] Monitoramento e logs (Prometheus / ELK)

Próximas subtarefas curtas (prioridade sugerida)
- Implementar paginação/ordenacão em `GET /api/products`
- Adicionar upload simples de imagem (salvar em `uploads/` local)
- Criar CRUD de `Category` e relacionar a `Product`
- Implementar endpoints de `Order` básicos
- Escrever 5 testes de unidade e 2 de integração


## Endpoints básicos (exemplos)
- `GET /api/products` - listar todos
- `GET /api/products/{id}` - obter por id
- `POST /api/products` - criar
- `PUT /api/products/{id}` - atualizar
- `DELETE /api/products/{id}` - deletar

## Próximos passos recomendados para você
- Adicionar endpoints de categorias e relacionamentos
- Criar frontend simples (React/Vue) consumindo a API
- Adicionar testes automatizados (unitários e integração)

Se quiser, eu posso:
- Rodar e testar a aplicação localmente
- Implementar paginação em `GET /api/products`
- Adicionar upload de imagens e CRUD de `Category`/`Order`

Boa sorte — diga qual tarefa prefere que eu implemente a seguir.