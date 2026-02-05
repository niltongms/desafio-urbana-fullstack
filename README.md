# Desafio Técnico Analista 2025 - Gestão de Usuários e Cartões de Transporte

Este projeto é uma solução **Fullstack** desenvolvida como parte do desafio técnico para a vaga de Analista. A aplicação consiste em um sistema robusto para o gerenciamento de usuários e seus respectivos cartões de transporte, implementando operações CRUD completas e regras de negócio específicas com foco em segurança, escalabilidade e qualidade de software.

## Objetivo do Projeto

Apresentar habilidades avançadas de codificação e os valores aplicados na engenharia de software, priorizando um código limpo, estruturado em camadas e bem testado.

## Tecnologias e Requisitos Obrigatórios Atendidos

* **Backend:** Java 17 (superando o requisito mínimo Java 8) utilizando o framework **Spring Boot**.
* **Build & Dependências:** Processo de build e gerenciamento de bibliotecas via **Maven**.
* **Persistência:** Uso de **JPA / Hibernate** para interação com banco de dados relacional **PostgreSQL**.
* **Arquitetura:** Estruturação rigorosa em camadas (**Controller, Service, Repository, DTO, Entity**).
* **Frontend:** **Angular 17** com Standalone Components (superando o requisito mínimo Angular 4).
* **Versionamento:** Código disponibilizado em repositório Git online.

## Diferenciais Implementados

Para demonstrar domínio técnico superior, foram incluídos os seguintes diferenciais solicitados:

* **Autenticação & Segurança:** Sistema de **Login** com **JWT** (JSON Web Token) e criação de **Perfis com Permissão** (ADMIN e COMUM).
* **Documentação:** API totalmente documentada e testável via **Swagger (OpenAPI 3)**.
* **Qualidade de Software (JUnit 5):** Implementação de **Testes Unitários** utilizando JUnit e Mockito para garantir a integridade das regras de negócio.
* **Padrões de Projeto:** Utilização estratégica de **Padrão DTO** para tráfego de dados e **Design Patterns** de mercado.
* **Desenvolvimento Limpo:** Foco em legibilidade, evitando o uso de bibliotecas como Lombok (conforme solicitado) e utilizando **nativeQuery** para otimização de consultas.
* **UX/UI Profissional:** Design **Responsivo** (Bootstrap 5) e uso da biblioteca **SweetAlert2** para modais interativos de recarga e confirmações.
* **Infraestrutura:** Ambiente preparado para execução via **Docker** e **Docker Compose**.

## Funcionalidades Implementadas

### Gestão de Usuários

* **CRUD Completo:** Consulta (lista todos), Inclusão, Alteração e Remoção.
* **Estrutura:** Nome, Email, Senha e Lista de Cartões associados.

### Gestão de Cartões

* **Associação:** Vínculo de múltiplos cartões (0 a N) por usuário.
* **Controle:** Consulta de todos os cartões, remoção e alteração de status (Ativar/Inativar).
* **Tipos de Cartão:** COMUM, ESTUDANTE e TRABALHADOR.
* **Sistema de Recarga:** Funcionalidade estendida para gestão de saldo via interface intuitiva.

##  Qualidade, Maven e Testes (JUnit)

O projeto utiliza o **Apache Maven** para orquestrar o ciclo de vida da aplicação e gerenciar dependências como *Spring Security* e *OpenAPI*. Seguindo a premissa de que "bom código é código testado", a aplicação possui cobertura de **Testes Unitários** com **JUnit 5** e **Mockito**, validando comportamentos críticos como lógica de ativação de cartões e integridade de dados.

---

## Como Executar a Aplicação

### 1. Clonar o Repositório

Para obter o código-fonte em sua máquina local, utilize o comando:

```bash
git clone https://github.com/niltongms/desafio-urbana-fullstack.git
cd desafio-urbana-fullstack

```

### 2. Execução via Docker (Recomendado)

Este projeto possui suporte total a containers. O comando abaixo sobe o banco de dados PostgreSQL, o Backend (Spring) e o Frontend (Angular) de forma integrada:

```bash
docker-compose up --build

```

* **Interface Web (Frontend):** `http://localhost:4200`
* **Documentação API (Swagger):** `http://localhost:8080/swagger-ui.html`
* **API Base URL:** `http://localhost:8080`

### 3. Execução Local (Manual)

Caso prefira rodar fora do Docker, certifique-se de ter o Java 17, Maven e Node.js instalados:

* **Backend:**
```bash
cd urbana-backend
mvn spring-boot:run

```


* **Frontend:**
```bash
cd urbana-frontend
npm install
ng serve

```



---



**Desenvolvido por: Elenilton Gomes**
[LinkedIn](https://www.linkedin.com/in/eleniltonsgomes/) | [GitHub](https://github.com/niltongms/) | niltonsgms@gmail.com