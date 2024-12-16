# LiterAlura 

## **Visão Geral**

O *LiterAlura* é uma aplicação console desenvolvida em **Java** utilizando o framework **Spring Boot**. Ela integra a API do **Gutendex.com**, que fornece uma vasta coleção de informações sobre livros. O objetivo principal do projeto é permitir que os usuários pesquisem livros na API, salvem-nos em um banco de dados e consultem informações posteriormente com base em critérios como título, idioma e autores.

Além disso, o projeto oferece funcionalidades para listar autores que estavam vivos após uma data específica, fornecida pelo usuário.

Agradecimentos especiais ao **Gutendex.com** por disponibilizar dados valiosos sobre livros e à comunidade **Spring Boot** pelo framework robusto.

---

## **Recursos**

+ **Integração com API**: Conecta-se à API do Gutendex para buscar informações sobre livros.
+ **Interação com Banco de Dados**: Permite salvar e recuperar informações de livros armazenadas em um banco de dados relacional.
+ **Funcionalidades de Pesquisa**: 
  - Buscar livros salvos por título, idioma ou autores.
+ **Filtragem de Autores**:
  - Exibe autores que estavam vivos após uma data informada pelo usuário.

---

## **Tecnologias Utilizadas**

+ **Java**: Linguagem principal utilizada no desenvolvimento do projeto.
+ **Spring Boot 3.2**: Framework utilizado para criação do backend.
+ **Gutendex API**: Fonte de dados de livros.
+ **PostgreSQL**: Banco de dados para armazenamento persistente.

---

## **Como Começar**

Para executar o projeto *LiterAlura* localmente, siga os passos abaixo:

1. Clone este repositório no seu computador:
   ```bash
   git clone https://github.com/seu-usuario/LiterAlura.git
## Uso
Após iniciar a aplicação, o menu principal será exibido no console com as seguintes opções:

+ Buscar livros na API por título e salvá-los no banco de dados.
+ Listar os livros registrados no banco de dados.
+ Listar autores registrados.
+ Filtrar autores vivos após uma determinada data.
+ Listar livros registrados por idioma.
