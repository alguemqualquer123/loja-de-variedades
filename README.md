# 🛒 Loja de Variedades - Backend REST API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

> Uma API REST completa e moderna para gestão de loja de variedades, construída com Spring Boot e MySQL. Inclui autenticação JWT, controle de acesso baseado em roles (RBAC) e suporte completo a Docker.

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Características](#-características)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Uso](#-uso)
- [Endpoints da API](#-endpoints-da-api)
- [Autenticação e Autorização](#-autenticação-e-autorização)
- [Perfis de Ambiente](#-perfis-de-ambiente)
- [Deploy com Docker](#-deploy-com-docker)
- [Testes](#-testes)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Roadmap](#-roadmap)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

---

## 🌟 Visão Geral

**Loja de Variedades** é um backend robusto para e-commerce desenvolvido com as melhores práticas de desenvolvimento Java e Spring Boot. O projeto oferece uma base sólida e escalável para criação de aplicações de comércio eletrônico, com foco em segurança, performance e facilidade de manutenção.

### Objetivos do Projeto

- Fornecer uma API REST completa e bem documentada
- Implementar autenticação e autorização seguras com JWT
- Suportar múltiplos ambientes (desenvolvimento e produção)
- Facilitar o deploy através de containers Docker
- Servir como template para novos projetos de e-commerce

---

## ✨ Características

### Funcionalidades Implementadas

- ✅ **CRUD Completo de Produtos**
  - Criação, leitura, atualização e exclusão de produtos
  - Busca por nome, código e código de barras
  - Paginação e ordenação de resultados
  
- ✅ **Gerenciamento de Imagens**
  - Upload de imagens de produtos
  - Armazenamento local configurável
  - Endpoint dedicado para upload

- ✅ **Autenticação e Autorização**
  - JWT (JSON Web Tokens) para autenticação stateless
  - Refresh tokens com expiração configurável
  - Sistema de roles (ADMIN e USER)
  - Controle de acesso baseado em permissões

- ✅ **Gerenciamento de Usuários**
  - Cadastro automático de usuário administrador
  - Criptografia de senhas com BCrypt
  - Validação de credenciais

- ✅ **Containerização**
  - Dockerfile otimizado para produção
  - Docker Compose para orquestração de serviços
  - Suporte a múltiplos perfis de ambiente

- ✅ **Dados Iniciais**
  - População automática do banco com 20 produtos padrão
  - Criação automática de roles e usuário admin
  - Configuração pronta para uso imediato

---

## 🛠 Tecnologias

### Backend Framework
- **Spring Boot 3.1.6** - Framework principal
- **Spring Web** - Construção de APIs REST
- **Spring Data JPA** - Camada de persistência
- **Spring Security** - Segurança e autenticação

### Banco de Dados
- **MySQL 8.0** - Banco de dados relacional
- **Hibernate** - ORM (Object-Relational Mapping)

### Segurança
- **JWT (JJWT 0.11.5)** - Geração e validação de tokens
- **BCrypt** - Criptografia de senhas

### Build e Deploy
- **Maven** - Gerenciamento de dependências e build
- **Docker & Docker Compose** - Containerização
- **Java 17** - Versão LTS do Java

### Utilitários
- **Validation API** - Validação de dados
- **SLF4J** - Logging

---

## 🏗 Arquitetura

O projeto segue uma arquitetura em camadas (Layered Architecture) com separação clara de responsabilidades:

```
┌─────────────────────────────────────┐
│        Controllers Layer            │
│   (REST API Endpoints - @RestController)
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Service Layer               │
│   (Business Logic - @Service)       │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│       Repository Layer              │
│   (Data Access - @Repository)       │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Database Layer              │
│          (MySQL 8.0)                │
└─────────────────────────────────────┘
```

### Camadas Principais

1. **Controllers** - Recebem requisições HTTP e retornam respostas
2. **Services** - Implementam a lógica de negócio
3. **Repositories** - Gerenciam a persistência de dados
4. **Models** - Entidades JPA que mapeiam as tabelas do banco
5. **Security** - Filtros JWT, configuração de segurança, UserDetailsService
6. **DTOs** - Objetos de transferência de dados para requests/responses

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java JDK 17** ou superior
  ```bash
  java -version
  ```

- **Maven 3.6+**
  ```bash
  mvn -version
  ```

- **Docker & Docker Compose** (opcional, mas recomendado)
  ```bash
  docker --version
  docker-compose --version
  ```

- **Git**
  ```bash
  git --version
  ```

---

## 🚀 Instalação e Configuração

### 1. Clone o Repositório

```bash
git clone https://github.com/alguemqualquer123/loja-de-variedades.git
cd loja-de-variedades
```

### 2. Inicie o Banco de Dados MySQL

**Opção A: Usando Docker Compose (Recomendado)**

```bash
docker-compose up -d db
```

**Opção B: MySQL Local**

Se você já tem MySQL instalado localmente, crie o banco de dados:

```sql
CREATE DATABASE lojadevariedades CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configure as Credenciais

As configurações padrão estão em `src/main/resources/application-dev.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lojadevariedades
spring.datasource.username=root
spring.datasource.password=example
```

Ajuste conforme sua instalação do MySQL.

### 4. Build do Projeto

```bash
mvn clean package -DskipTests
```

### 5. Execute a Aplicação

```bash
mvn spring-boot:run
```

Ou execute o JAR diretamente:

```bash
java -jar target/lojadevariedades-0.0.1-SNAPSHOT.jar
```

A API estará disponível em: **http://localhost:8080**

---

## 💡 Uso

### Credenciais Padrão

Após o primeiro start, um usuário administrador é criado automaticamente:

- **Username:** `admin`
- **Password:** `adminpass`

> ⚠️ **IMPORTANTE:** Em ambiente de produção, troque essas credenciais imediatamente!

### Fluxo Básico de Uso

1. **Fazer login** e obter um token JWT
2. **Usar o token** no header `Authorization: Bearer <token>` para acessar endpoints protegidos
3. **Gerenciar produtos** através dos endpoints CRUD
4. **Upload de imagens** para produtos específicos

---

## 🔌 Endpoints da API

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/auth/login` | Fazer login e obter token JWT | ❌ Não |
| POST | `/auth/refresh` | Renovar token usando refresh token | ❌ Não |

**Exemplo de Login:**

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "adminpass"
  }'
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6",
  "expiresIn": 3600000
}
```

### Produtos

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/products` | Listar todos os produtos | ✅ Sim |
| GET | `/api/products/{id}` | Obter produto por ID | ✅ Sim |
| GET | `/api/products/search?name={nome}` | Buscar produtos por nome | ✅ Sim |
| POST | `/api/products` | Criar novo produto | ✅ Sim (ADMIN) |
| PUT | `/api/products/{id}` | Atualizar produto | ✅ Sim (ADMIN) |
| DELETE | `/api/products/{id}` | Deletar produto | ✅ Sim (ADMIN) |
| POST | `/api/products/{id}/image` | Upload de imagem | ✅ Sim (ADMIN) |

**Exemplo - Listar Produtos:**

```bash
curl -H "Authorization: Bearer <SEU_TOKEN>" \
  http://localhost:8080/api/products
```

**Exemplo - Criar Produto:**

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Caneta Azul",
    "price": 2.50,
    "stock": 100,
    "code": "PEN001",
    "barcode": "7891234567890"
  }'
```

**Exemplo - Upload de Imagem:**

```bash
curl -X POST http://localhost:8080/api/products/1/image \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -F "file=@/caminho/para/imagem.jpg"
```

---

## 🔐 Autenticação e Autorização

### Sistema de Tokens JWT

O projeto implementa autenticação stateless usando JSON Web Tokens (JWT):

1. **Access Token:** Token de curta duração (1 hora padrão) usado para acessar recursos
2. **Refresh Token:** Token de longa duração (7 dias padrão) usado para renovar o access token

### Fluxo de Autenticação

```
┌──────────┐          ┌──────────┐          ┌──────────┐
│  Client  │          │   API    │          │ Database │
└────┬─────┘          └────┬─────┘          └────┬─────┘
     │                     │                     │
     │  1. POST /auth/login│                     │
     │────────────────────>│                     │
     │  (username/password)│                     │
     │                     │  2. Validate User   │
     │                     │────────────────────>│
     │                     │<────────────────────│
     │                     │  3. Generate Tokens │
     │  4. Return Tokens   │                     │
     │<────────────────────│                     │
     │                     │                     │
     │  5. API Request     │                     │
     │  + Bearer Token     │                     │
     │────────────────────>│                     │
     │                     │  6. Validate Token  │
     │                     │  7. Process Request │
     │  8. Response        │                     │
     │<────────────────────│                     │
```

### Roles e Permissões

O sistema possui dois níveis de acesso:

- **ROLE_USER:** Acesso de leitura aos produtos
- **ROLE_ADMIN:** Acesso total (CRUD de produtos, upload de imagens)

### Configuração de Segurança

Os seguintes endpoints são públicos (não requerem autenticação):
- `/auth/login`
- `/auth/refresh`
- `/actuator/**`

Todos os outros endpoints requerem um token JWT válido.

### Configurações JWT

Edite as propriedades em `application-dev.properties` ou `application-prod.properties`:

```properties
jwt.secret=verysecuresecretverysecuresecret1234
jwt.expirationMs=3600000
jwt.refreshExpirationMs=604800000
```

> ⚠️ **SEGURANÇA:** Em produção, **SEMPRE** use variáveis de ambiente para armazenar o `jwt.secret`:
> ```bash
> export JWT_SECRET="sua-chave-secreta-muito-segura-aqui"
> ```

---

## 🌍 Perfis de Ambiente

O projeto suporta múltiplos perfis de ambiente para facilitar o desenvolvimento e deploy.

### Perfil Dev (Desenvolvimento)

Ativado por padrão. Configurações em `application-dev.properties`:

- Conecta ao MySQL em `localhost:3306`
- `ddl-auto=update` - Atualiza o schema automaticamente
- `show-sql=true` - Exibe queries SQL no console
- Logging detalhado (DEBUG)

**Ativar perfil dev:**

```bash
mvn spring-boot:run
```

ou

```bash
java -jar target/lojadevariedades-0.0.1-SNAPSHOT.jar
```

### Perfil Prod (Produção)

Configurações em `application-prod.properties`:

- Conecta ao MySQL via hostname `db` (para Docker)
- `ddl-auto=validate` - Apenas valida o schema
- `show-sql=false` - Não exibe queries SQL
- Logging reduzido (INFO)

**Ativar perfil prod:**

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

ou

```bash
java -Dspring.profiles.active=prod -jar target/lojadevariedades-0.0.1-SNAPSHOT.jar
```

ou via variável de ambiente:

```bash
export SPRING_PROFILES_ACTIVE=prod
java -jar target/lojadevariedades-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Deploy com Docker

### Deploy Completo (App + Database)

O projeto inclui configuração Docker Compose pronta para uso:

**1. Build e start de todos os serviços:**

```bash
docker-compose up --build -d
```

Isso irá:
- Construir a imagem da aplicação
- Iniciar o container MySQL
- Iniciar o container da aplicação
- Configurar a rede entre os containers

**2. Verificar os logs:**

```bash
docker-compose logs -f app
```

**3. Parar os serviços:**

```bash
docker-compose down
```

**4. Parar e remover volumes (cuidado: apaga os dados!):**

```bash
docker-compose down -v
```

### Deploy Apenas do Banco de Dados

Se você quer rodar a aplicação localmente, mas o banco no Docker:

```bash
docker-compose up -d db
mvn spring-boot:run
```

### Build Manual da Imagem

```bash
mvn clean package -DskipTests
docker build -t lojadevariedades:latest .
docker run -e SPRING_PROFILES_ACTIVE=prod -p 8080:8080 lojadevariedades:latest
```

### Configuração Docker

**Dockerfile:**

```dockerfile
FROM eclipse-temurin:17-jdk-jammy

ARG JAR_FILE=target/lojadevariedades-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

**docker-compose.yml:**

```yaml
version: '3.8'
services:
  db:
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: lojadevariedades
      MYSQL_ROOT_PASSWORD: example
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql
      
  app:
    build: .
    depends_on:
      - db
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    ports:
      - "8080:8080"

volumes:
  db_data:
```

---

## 🧪 Testes

### Executar Testes

```bash
mvn test
```

### Executar Testes com Coverage

```bash
mvn clean test jacoco:report
```

O relatório de cobertura estará disponível em `target/site/jacoco/index.html`.

### Testes de Integração

```bash
mvn verify
```

> ℹ️ **Nota:** A suite de testes está em desenvolvimento. Consulte o [Roadmap](#-roadmap) para mais detalhes.

---

## 📁 Estrutura do Projeto

```
loja-de-variedades/
├── src/
│   ├── main/
│   │   ├── java/com/example/lojadevariedades/
│   │   │   ├── config/
│   │   │   │   ├── CreateDefaultItems.java
│   │   │   │   ├── DataInitializer.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── TestController.java
│   │   │   ├── dto/
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RefreshRequest.java
│   │   │   │   └── RefreshResponse.java
│   │   │   ├── model/
│   │   │   │   ├── Product.java
│   │   │   │   ├── RefreshToken.java
│   │   │   │   ├── Role.java
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── RefreshTokenRepository.java
│   │   │   │   ├── RoleRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── security/
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── JwtUtil.java
│   │   │   ├── service/
│   │   │   │   ├── FileStorageService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   └── RefreshTokenService.java
│   │   │   ├── utils/
│   │   │   │   ├── LoadJson.java
│   │   │   │   └── Logger.java
│   │   │   └── LojaDeVariedadesApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/example/lojadevariedades/
├── target/
├── uploads/
├── .gitignore
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
└── ROADMAP.md
```

### Descrição das Pastas

- **config/** - Configurações e inicializadores (segurança, dados padrão)
- **controller/** - Endpoints REST (@RestController)
- **dto/** - Data Transfer Objects para requests e responses
- **model/** - Entidades JPA (tabelas do banco)
- **repository/** - Interfaces Spring Data JPA
- **security/** - Filtros JWT, utilitários de segurança, UserDetailsService
- **service/** - Lógica de negócio
- **utils/** - Classes utilitárias (logging, JSON parsing)
- **resources/** - Arquivos de configuração (.properties)

---

## 🗺 Roadmap

Consulte o arquivo [ROADMAP.md](./ROADMAP.md) para ver o planejamento detalhado do projeto.

### Próximas Funcionalidades

- [ ] **Categorias de Produtos**
  - Entidade Category
  - Relacionamento com Product
  - CRUD de categorias

- [ ] **Sistema de Pedidos**
  - Entidades Order e OrderItem
  - Carrinho de compras
  - Histórico de pedidos

- [ ] **Integração de Pagamento**
  - Gateway de pagamento (Stripe/PayPal)
  - Webhooks para confirmação
  - Status de pagamento

- [ ] **Testes Automatizados**
  - Testes unitários (JUnit 5)
  - Testes de integração (TestContainers)
  - Coverage > 80%

- [ ] **CI/CD**
  - GitHub Actions
  - Deploy automático
  - Análise de código (SonarQube)

- [ ] **Documentação da API**
  - Integração com Swagger/OpenAPI
  - Geração automática de docs
  - Exemplos interativos

- [ ] **Monitoramento**
  - Spring Boot Actuator
  - Prometheus + Grafana
  - Logs centralizados

---

## 🤝 Contribuição

Contribuições são bem-vindas! Siga estas etapas:

### Como Contribuir

1. **Fork** o repositório
2. **Clone** seu fork
   ```bash
   git clone https://github.com/alguemqualquer123/loja-de-variedades.git
   ```
3. **Crie uma branch** para sua feature
   ```bash
   git checkout -b feature/minha-nova-feature
   ```
4. **Commit** suas mudanças
   ```bash
   git commit -m "Add: nova funcionalidade X"
   ```
5. **Push** para a branch
   ```bash
   git push origin feature/minha-nova-feature
   ```
6. Abra um **Pull Request**

### Diretrizes

- Mantenha o código limpo e bem documentado
- Siga as convenções de código do projeto
- Adicione testes para novas funcionalidades
- Atualize a documentação quando necessário
- Faça commits pequenos e descritivos

### Padrão de Commits

Usamos [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` - Nova funcionalidade
- `fix:` - Correção de bug
- `docs:` - Apenas documentação
- `style:` - Formatação de código
- `refactor:` - Refatoração
- `test:` - Adição de testes
- `chore:` - Tarefas de manutenção

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 📞 Suporte e Contato

- **Issues:** [GitHub Issues](https://github.com/alguemqualquer123/loja-de-variedades/issues)
- **Discussões:** [GitHub Discussions](https://github.com/alguemqualquer123/loja-de-variedades/discussions)

---

## 🙏 Agradecimentos

- [Spring Boot](https://spring.io/projects/spring-boot) - Framework incrível
- [MySQL](https://www.mysql.com/) - Banco de dados confiável
- [Docker](https://www.docker.com/) - Containerização simplificada
- Comunidade Open Source - Por todo o conhecimento compartilhado

---

## 📊 Status do Projeto

![Status](https://img.shields.io/badge/status-active-success.svg)
![Maintenance](https://img.shields.io/badge/maintained-yes-green.svg)

**Última atualização:** Janeiro 2026

---

<div align="center">

Desenvolvido com ❤️ usando Spring Boot

[⬆ Voltar ao topo](#-loja-de-variedades---backend-rest-api)

</div>
