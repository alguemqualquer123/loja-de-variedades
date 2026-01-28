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
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Endpoints da API](#-endpoints-da-api)
- [Autenticação](#-autenticação)
- [Estrutura do Projeto](#-estrutura-do-projeto)

---

## 🌟 Visão Geral

**Loja de Variedades** é um backend robusto para e-commerce desenvolvido com as melhores práticas de desenvolvimento Java e Spring Boot. O projeto oferece uma base sólida e escalável para criação de aplicações de comércio eletrônico.

---

## ✨ Características

### Funcionalidades Implementadas

- ✅ **CRUD Completo de Produtos**
  - Criação, leitura, atualização e exclusão de produtos
  - Busca por nome, código e código de barras
  - Paginação e ordenação
  
- ✅ **Gerenciamento de Imagens**
  - Upload de imagens de produtos
  - Armazenamento local

- ✅ **Autenticação e Autorização**
  - JWT (JSON Web Tokens)
  - Sistema de roles (ADMIN e USER)

- ✅ **Dados Iniciais**
  - População automática do banco com produtos padrão
  - Usuário admin padrão

---

## 🛠 Tecnologias

- **Spring Boot 3.1.6**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security + JWT**
- **MySQL 8.0**
- **Docker & Docker Compose**

---

## 📋 Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker & Docker Compose

---

## 🚀 Instalação e Configuração

### 1. Clonar o repositório

```bash
git clone https://github.com/alguemqualquer123/loja-de-variedades.git
cd loja-de-variedades
```

### 2. Subir o Banco de Dados

Utilize o Docker Compose para iniciar o MySQL:

```bash
docker compose up -d db
```

O banco de dados será criado automaticamente na primeira execução.

### 3. Executar a Aplicação

Você pode executar a aplicação usando o Maven:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

### 🔐 Credenciais Padrão

Na primeira execução, um usuário administrador é criado automaticamente:

- **Username:** `admin`
- **Password:** `adminpass`

---

## 📡 Endpoints da API

### Rota Raiz
- `GET /` - Status do serviço e metadados públicos

### Autenticação
- `POST /auth/login` - Login e obtenção do token JWT
- `POST /auth/refresh` - Atualizar token
- `POST /auth/register` - Criar conta de usuário
- `POST /auth/verify` - Verificar conta via token
- `POST /auth/request-reset` - Solicitar reset de senha
- `POST /auth/reset` - Confirmar reset de senha

### Produtos
- `GET /api/products` - Listar produtos (paginado)
- `GET /api/products/{id}` - Detalhes do produto
- `POST /api/products` - Criar produto (Requer autenticação)
- `PUT /api/products/{id}` - Atualizar produto (Requer autenticação)
- `DELETE /api/products/{id}` - Remover produto (Requer autenticação)

### Categorias
- `GET /api/categories` - Listar categorias
- `GET /api/categories/{id}` - Detalhes da categoria
- `POST /api/categories` - Criar categoria (Requer ADMIN)
- `PUT /api/categories/{id}` - Atualizar categoria (Requer ADMIN)
- `DELETE /api/categories/{id}` - Remover categoria (Requer ADMIN)

### Usuários
- `GET /api/users` - Listar usuários (Requer ADMIN)
- `GET /api/users/{id}` - Detalhes do usuário (Requer ADMIN)
- `POST /api/users` - Criar usuário (Requer ADMIN)
- `PUT /api/users/{id}` - Atualizar usuário (Requer ADMIN)
- `DELETE /api/users/{id}` - Remover usuário (Requer ADMIN)

---

## 📂 Estrutura do Projeto

```
src/main/java/com/example/lojadevariedades/
├── config/          # Configurações (Security, Data Init)
├── controller/      # Controladores REST
├── dto/             # Data Transfer Objects
├── model/           # Entidades JPA
├── repository/      # Repositórios Spring Data
├── security/        # Filtros e Utilitários JWT
├── service/         # Regras de Negócio
└── utils/           # Utilitários Gerais
```

---

## 🧭 Perfis de Ambiente
- Dev: usa `localhost` e cria/atualiza schema automaticamente, ver [application-dev.properties](src/main/resources/application-dev.properties)
- Prod: usa host `db` do docker e cria/atualiza schema automaticamente, ver [application-prod.properties](src/main/resources/application-prod.properties)

---

## 🐳 Deploy com Docker
Build da imagem e subida dos serviços:
```bash
docker compose build app
docker compose up -d
```
- Healthcheck garante que o app só inicia após o MySQL estar pronto
- Volume para uploads: `./uploads` mapeado para `/app/uploads`
- Porta exposta: `8080` (host) → `8080` (container)
- Variáveis já definidas em Compose:
  - `SPRING_PROFILES_ACTIVE=prod`
  - `SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/lojadevariedades?createDatabaseIfNotExist=true...`

Arquivos relevantes:
- Dockerfile: multi-stage, build + runtime
- docker-compose.yml: serviços `db` e `app`, com healthcheck e volumes

---

## 🔐 Autenticação (JWT)
Credenciais padrão (criadas automaticamente), ver [DataInitializer.java](src/main/java/com/example/lojadevariedades/config/DataInitializer.java):
- Username: `admin`
- Password: `adminpass`

Segredo e expirações configurados em:
- Dev: [application-dev.properties](src/main/resources/application-dev.properties#L10-L12)  
- Prod: [application-prod.properties](src/main/resources/application-prod.properties#L10-L12)

---

## ✅ Validação e Erros
- Validações com Jakarta Bean Validation em DTOs e entidades:
  - DTOs: [LoginRequest.java](src/main/java/com/example/lojadevariedades/dto/LoginRequest.java), [RegisterRequest.java](src/main/java/com/example/lojadevariedades/dto/RegisterRequest.java), [RefreshRequest.java](src/main/java/com/example/lojadevariedades/dto/RefreshRequest.java), [PasswordResetRequest.java](src/main/java/com/example/lojadevariedades/dto/PasswordResetRequest.java), [PasswordResetConfirmRequest.java](src/main/java/com/example/lojadevariedades/dto/PasswordResetConfirmRequest.java), [VerifyRequest.java](src/main/java/com/example/lojadevariedades/dto/VerifyRequest.java)
  - Entidades: [Product.java](src/main/java/com/example/lojadevariedades/model/Product.java), [Category.java](src/main/java/com/example/lojadevariedades/model/Category.java), [User.java](src/main/java/com/example/lojadevariedades/model/User.java), [Role.java](src/main/java/com/example/lojadevariedades/model/Role.java), [RefreshToken.java](src/main/java/com/example/lojadevariedades/model/RefreshToken.java)
- Controladores com validação de entrada:
  - [AuthController.java](src/main/java/com/example/lojadevariedades/controller/AuthController.java) usa `@Valid` nos DTOs
  - [ProductController.java](src/main/java/com/example/lojadevariedades/controller/ProductController.java) e [CategoryController.java](src/main/java/com/example/lojadevariedades/controller/CategoryController.java) usam `@Validated` nas rotas de criação/edição
  - [UserController.java](src/main/java/com/example/lojadevariedades/controller/UserController.java) usa `@Valid` para criar/atualizar e `@Validated` na classe
- Tratamento global de erros: [GlobalExceptionHandler.java](src/main/java/com/example/lojadevariedades/exception/GlobalExceptionHandler.java)
- Resposta padronizada: [ResponseJson.java](src/main/java/com/example/lojadevariedades/utils/ResponseJson.java)

---

## 💻 Exemplos de Uso (cURL)
Login:
```bash
curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"adminpass"}'
```

Listar produtos (com token):
```bash
curl -s http://localhost:8080/api/products \
  -H "Authorization: Bearer <ACCESS_TOKEN>"
```

Upload de imagem:
```bash
curl -s -X POST http://localhost:8080/api/products/1/image \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  -F "file=@/caminho/arquivo.png"
```

Renovar tokens:
```bash
curl -s -X POST http://localhost:8080/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"<REFRESH_TOKEN>"}'
```

---

## 🧪 Troubleshooting
- Porta 8080 ocupada (Windows):
  ```bash
  netstat -ano | findstr :8080
  taskkill /PID <PID> /F
  ```
- Banco não encontrado: garantido via `createDatabaseIfNotExist=true` em dev/prod
- Subida do app antes do DB: healthcheck previne (compose)
- Problemas com uploads em Windows: ver volume `./uploads:/app/uploads`

---

## 📄 Licença
MIT
