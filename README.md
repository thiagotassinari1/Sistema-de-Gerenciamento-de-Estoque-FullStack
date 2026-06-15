# 📦 Sistema de Gerenciamento de Estoque

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-orange)

Projeto acadêmico desenvolvido como parte do **Trabalho Prático do Grau A (Backend)** e **Trabalho Prático do Grau B (Frontend)** da disciplina de **Implementação de Software**, no curso de Análise e Desenvolvimento de Sistemas da Universidade do Vale do Rio dos Sinos (**Unisinos**).

A solução foi pensada como um sistema de controle de estoque com API REST no backend e interface web no frontend. O objetivo é apoiar o cadastro de produtos, categorias, fornecedores e pedidos, reduzindo erros no processo de reabastecimento e deixando a atualização do inventário mais consistente.

---

## Visão Geral

O repositório está organizado em duas partes:

* **Backend (`estoque/`)**: API REST desenvolvida com **Java 17**, **Spring Boot 3.5**, **Spring Data JPA**, **Bean Validation** e **PostgreSQL**.
* **Frontend (`estoque-front/`)**: aplicação web em **Vue 3** com **Vite**, **Vue Router** e **Axios**, consumindo a API do backend.

Essa separação permite evoluir a interface e as regras de negócio de forma independente, mantendo o backend responsável pela integridade dos dados e o frontend focado na experiência de uso.

---

## Arquitetura

O backend segue uma organização em camadas, com responsabilidades bem definidas:

* **Controller:** expõe os endpoints HTTP.
* **Service:** concentra a lógica de negócio e validações de fluxo.
* **Repository:** faz o acesso ao banco de dados.
* **Entity:** representa as tabelas e relações do domínio.
* **Exception Handler:** centraliza o tratamento de erros da API.

No frontend, a interface está estruturada com componentes reutilizáveis, composables para reaproveitamento de lógica e rotas dedicadas para listagem e formulário de produtos.

---

## Entidades

O sistema trabalha com quatro entidades principais:

1. **Produto:** item central do estoque, com nome, descrição, preço, quantidade e categoria.
2. **Categoria:** classifica os produtos do catálogo.
3. **Fornecedor:** representa os parceiros comerciais responsáveis pelo abastecimento.
4. **Pedido:** registra a solicitação de compra e o vínculo entre produto e fornecedor.

---

## Backend

O backend expõe uma API REST para operações de cadastro, consulta, atualização e remoção. Entre os recursos já presentes no projeto estão:

* CRUD de **produtos**.
* CRUD de **categorias**.
* CRUD de **fornecedores**.
* Operações de **pedidos** com atualização de estoque.
* Documentação da API via **Swagger/OpenAPI**.

### Principais rotas

#### Produtos
| Método | URL | Finalidade |
| :--- | :--- | :--- |
| `GET` | `/produtos` | Listar produtos com suporte a paginação e filtros. |
| `GET` | `/produtos/{id}` | Buscar um produto específico. |
| `POST` | `/produtos` | Cadastrar um novo produto. |
| `PUT` | `/produtos/{id}` | Atualizar um produto existente. |
| `DELETE` | `/produtos/{id}` | Remover um produto. |

#### Categorias
| Método | URL | Finalidade |
| :--- | :--- | :--- |
| `GET` | `/categorias/todas` | Listar todas as categorias disponíveis. |

#### Pedidos
| Método | URL | Finalidade |
| :--- | :--- | :--- |
| `POST` | `/pedidos` | Registrar pedido e atualizar o estoque do produto. |
| `DELETE` | `/pedidos/{id}` | Cancelar um pedido e reverter a quantidade no estoque. |

---

## Frontend

A interface web foi construída em **Vue 3** com **Vite** e consome a API via **Axios**. Nesta etapa do projeto, o frontend está focado no fluxo de produtos e categorias.

### Funcionalidades atuais

* Listagem paginada de produtos.
* Filtro por nome na listagem.
* Cadastro e edição de produtos.
* Exclusão de produtos.
* Seleção de categoria no formulário.
* Roteamento para tela de listagem e formulário.

### Rotas do frontend

* `/produtos` - listagem de produtos.
* `/produtos/novo` - cadastro de produto.
* `/produtos/:id/editar` - edição de produto.

### Tecnologias do frontend

* **Vue 3**
* **Vite**
* **Vue Router**
* **Axios**

---

## Backend Individual

Caso tenha interesse no código backend individualmente, acesse [este repositório](https://github.com/thiagotassinari1/Sistema-de-Gerenciamento-de-Estoque-CRUD.git).

---

## Tecnologias Utilizadas

* **Java 17** e **Spring Boot** no backend.
* **JPA/Hibernate** para persistência.
* **PostgreSQL** como banco relacional.
* **Swagger/OpenAPI** para documentação da API.
* **Vue 3** e **Vite** no frontend.
* **Axios** para consumo da API.

---

## Como Executar

### Backend

**Pré-requisitos:** Java 17, Maven e PostgreSQL instalados e rodando localmente.

1. Acesse a pasta do backend:
   ```bash
   cd estoque
   ```
2. Copie o arquivo de exemplo e configure suas credenciais:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
3. Edite o `application.properties` com o usuário e senha do seu PostgreSQL local e certifique-se de que o banco de dados informado existe.
4. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
   A API estará disponível em `http://localhost:8080`.

### Frontend

**Pré-requisitos:** Node.js 18 ou superior.

1. Acesse a pasta do frontend:
   ```bash
   cd estoque-front
   ```
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Copie o arquivo de variáveis de ambiente:
   ```bash
   cp .env.example .env
   ```
4. Defina a URL do backend no `.env`:
   ```
   VITE_API_URL=http://localhost:8080
   ```
5. Inicie a aplicação:
   ```bash
   npm run dev
   ```
   O frontend estará disponível em `http://localhost:5173`.

---

## Variável de Ambiente

| Variável | Descrição | Valor padrão |
| :--- | :--- | :--- |
| `VITE_API_URL` | URL base da API REST do backend | `http://localhost:8080` |

---

## Rotas do Frontend

| Rota | Finalidade |
| :--- | :--- |
| `/produtos` | Listagem paginada de produtos com filtro por nome e categoria |
| `/produtos/novo` | Formulário de cadastro de produto |
| `/produtos/:id/editar` | Formulário de edição de produto existente |
| `/categorias` | Listagem de todas as categorias cadastradas |
| `/categorias/nova` | Formulário de cadastro de categoria |
| `/categorias/:id/editar` | Formulário de edição de categoria existente |
| `/fornecedores` | Listagem de todos os fornecedores cadastrados |
| `/fornecedores/novo` | Formulário de cadastro de fornecedor |
| `/fornecedores/:id/editar` | Formulário de edição de fornecedor existente |
| `/pedidos` | Listagem de pedidos com opção de cancelamento |
| `/pedidos/novo` | Formulário de criação de novo pedido |

---

## Bibliotecas Adicionais do Frontend

| Biblioteca | Justificativa |
| :--- | :--- |
| **Vue Router** | Gerenciamento de rotas e navegação entre as telas da aplicação |
| **Axios** | Realização de requisições HTTP à API REST, centralizada na camada de services |

---

## Vídeo de Apresentação

Link: https://drive.google.com/file/d/1Koo_gYpI1SUTm4MEW9WOgwecNzL8WbZc/view?usp=sharing

---

## Integrantes do Grupo

* Luiz Otávio
* Nicolas Kauer Toldo
* Thiago Tassinari
