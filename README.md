# 📦 Sistema de Gerenciamento de Estoque (CRUD)

![Status](https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen)

Este projeto é uma API RESTful completa desenvolvida como o **Trabalho Prático do Grau A** para a disciplina de **Implementação de Software** do curso de Análise e Desenvolvimento de Sistemas da Universidade do Vale do Rio dos Sinos (**Unisinos**).

O sistema atua como uma barreira de integridade para o controle de pedidos de compra e reabastecimento de mercadorias, automatizando a validação de produtos e a atualização dinâmica do inventário para evitar falhas humanas e inconsistências no estoque.

---

## 🏗️ Arquitetura e Conceitos

O projeto foi construído seguindo os princípios da arquitetura **RESTful** e a separação de responsabilidades em camadas (**MVC**):

* **Controller:** Responsável por expor os endpoints e gerenciar as requisições HTTP.
* **Service:** Onde reside a lógica de negócio, incluindo o uso da anotação `@Transactional` para garantir a integridade das operações de banco de dados.
* **Entity/Model:** Representação das tabelas do banco de dados relacional e objetos de domínio.
* **Persistência:** Utiliza um banco de dados relacional com o suporte de um ORM para o mapeamento das entidades.

---

## 🗂️ Entidades e Relacionamentos

O sistema gerencia quatro entidades principais que garantem o fluxo de reabastecimento:

1.  **Produto:** Entidade central que armazena nome, descrição, quantidade em estoque e preço.
2.  **Categoria:** Utilizada para classificar e agrupar os produtos do catálogo.
3.  **Fornecedor:** Representa os parceiros comerciais (nome e CNPJ) responsáveis pelo fornecimento dos itens.
4.  **Pedido:** Ordem de compra que vincula um produto a um fornecedor, registrando a data e a quantidade solicitada.

**Cardinalidade:**
* **Categoria (1:N) Produto:** Uma categoria agrupa muitos produtos.
* **Fornecedor (1:N) Pedido:** Um fornecedor pode receber diversos pedidos.
* **Produto (1:N) Pedido:** Um item pode ser reabastecido através de múltiplos pedidos ao longo do tempo.

---

## 🛣️ Rotas da API (Endpoints)

Abaixo estão listadas as principais rotas implementadas no sistema:

### Produtos
| Método | URL | Finalidade |
| :--- | :--- | :--- |
| `POST` | `/produtos` | Registrar novos produtos no estoque. |
| `GET` | `/produtos/todos` | Listar todos os produtos cadastrados. |
| `PUT` | `/produtos/{id}` | Atualizar informações de um produto específico. |
| `DELETE` | `/produtos/{id}` | Remover um produto do sistema. |

### Pedidos (Lógica de Negócio)
| Método | URL | Finalidade |
| :--- | :--- | :--- |
| `POST` | `/pedidos` | Registrar pedido e **atualizar automaticamente** o estoque do produto. |
| `DELETE` | `/pedidos/{id}` | Cancelar um pedido e reverter a quantidade do estoque. |

> **Nota:** Rotas similares de CRUD completo estão disponíveis para **Categorias** e **Fornecedores**.

---

## 🛠️ Tecnologias Utilizadas

* **Java / Spring Boot** (Back-end RESTful).
* **Banco de Dados Relacional** (PostgreSQL).
* **Swagger / Postman:** Para documentação e testes das chamadas da API.
* **ORM (JPA/Hibernate):** Para persistência e mapeamento de dados.

---

## 👥 Integrantes do Grupo

* Luiz Otávio
* Nicolas Toldo
* Thiago Tassinari
