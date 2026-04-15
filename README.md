# Clima API Console ⛅

![Java](https://img.shields.io/badge/Language-Java_21-orange)
![API](https://img.shields.io/badge/API-WeatherAPI-blue)
![Interface](https://img.shields.io/badge/Interface-Console-lightgrey)
![Status](https://img.shields.io/badge/Status-Concluído-green)

Projeto de uma aplicação de console desenvolvida em Java para buscar e exibir dados meteorológicos em tempo real consumindo uma API externa. Criada para aplicar e consolidar conceitos de requisições HTTP, manipulação de JSON e proteção de credenciais.

---

## 📜 Sobre o Projeto
> **Nota de Contexto**
>
> Este projeto foi desenvolvido como um laboratório prático para dominar a comunicação do Java com serviços web. O foco principal foi criar um código limpo para realizar requisições HTTP (usando as bibliotecas nativas do Java), processar respostas em formato JSON e garantir a segurança de dados sensíveis (API Key).
-------------------------------------------------------------

A aplicação permite ao usuário digitar o nome de qualquer cidade no terminal e obter as condições climáticas atuais de forma rápida e formatada.

## ✨ Funcionalidades

* **Consulta em Tempo Real:** Busca dados de temperatura, sensação térmica, umidade, ventos e pressão atmosférica.
* **Consumo de API REST:** Integração direta com a [WeatherAPI](https://www.weatherapi.com/).
* **Tratamento de Erros:** Identifica e avisa amigavelmente caso a cidade digitada não seja encontrada.
* **Segurança de Credenciais:** A chave da API é lida de um arquivo externo (`api-key.txt`) ignorado pelo controle de versão, evitando vazamento de dados.

## 💡 Destaque da Implementação

O principal marco deste projeto é a integração nativa com a web e o processamento estruturado de dados de terceiros. 

A aplicação demonstra de forma clara:
- O uso moderno de `HttpClient`, `HttpRequest` e `HttpResponse`.
- A manipulação e extração de nós específicos utilizando a biblioteca `org.json.JSONObject`.
- A leitura de arquivos encapsulados na pasta *resources* através de fluxos de dados (`InputStream`).
- O uso de `URLEncoder` para tratar espaços e acentos em parâmetros de URL de forma segura.

## 🚀 Como Executar o Projeto

Certifique-se de ter o **Java JDK 21** e o **Maven** instalados. 

**Pré-requisito:** Crie uma conta gratuita na [WeatherAPI](https://www.weatherapi.com/) para obter a sua chave de acesso.

```bash
# 1. Clone o repositório
git clone https://github.com/michell-ferreira/clima-api-console

# 2. Acesse a pasta do projeto
cd clima-api-console

# 3. Configure a sua chave da API
# Crie um arquivo chamado "api-key.txt" no caminho: src/main/resources/
# Cole a sua chave da WeatherAPI dentro dele.

# 4. Compile e execute a aplicação via Maven
mvn clean compile exec:java -Dexec.mainClass="org.example.Main"

```

## ✒️ Autor

**Michell Ferreira**

* **GitHub:** [michell-ferreira](https://github.com/michell-ferreira)
* **LinkedIn:** [ferreira-michel](https://www.linkedin.com/in/ferreira-michel/)

---
