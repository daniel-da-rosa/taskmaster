# 🛡️ Taskmaster API

> Uma API REST robusta para gestão educacional, focada em Domain-Driven Design (DDD), Clean Code e Blindagem de Domínio.

O **Taskmaster** não é apenas um sistema de gerenciamento; é um exercício de arquitetura de software moderna utilizando o ecossistema Spring Boot. O projeto prioriza a integridade dos dados através de **Value Objects**, **Converters JPA** e tratamento de erros padronizado (RFC 7807).

---

## 🚀 Tecnologias & Arquitetura

* **Java 17+**
* **Spring Boot 4** (Web, Data JPA, Security, Validation)
* **PostgreSQL** (Hospedado no Supabase)
* **Maven**
* **Lombok**

### Destaques de Engenharia
* **Rich Domain Model:** Uso de *Value Objects* para regras de negócio (CNPJ, Email) em vez de tipos primitivos.
* **JPA Converters:** Mapeamento transparente entre Objetos de Valor e colunas do banco de dados.
* **Tratamento de Erros Global:** Respostas de erro padronizadas usando `ProblemDetail` (RFC 7807).
* **Segurança:** Configuração de Spring Security para controle de acesso (atualmente em modo dev/permit-all).

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior
* Maven
* Uma instância PostgreSQL (ou contêiner Docker)

### 1. Configuração do Banco de Dados
O projeto espera as seguintes variáveis de ambiente ou configuração no `application.yml`. 
Para rodar localmente, você pode definir nas variáveis de ambiente da sua IDE:

```properties
DB_URL=jdbc:postgresql://seu-host:5432/postgres?sslmode=require
DB_USERNAME=postgres
DB_PASSWORD=sua_senha_secreta
