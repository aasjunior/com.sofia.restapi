# Sofia Mobile 💜

Sofia é um Software Orientado por Inteligência Artificial para Auxílio ao Pré-diagnóstico de Crianças de 0 a 2 Anos com Manifestações Comportamentais do Transtorno do Espectro Autista (TEA). O aplicativo mobile CAD _(Computer-aided diagnosis)_ é destinado ao auxílio de profissionais da saúde na triagem e identificação de sinais do TEA. <strong> 💜 Nosso Objetivo 💜 </strong> é promover a acessibilidade ao diagnóstico precoce de TEA! 💜

<div align="center">
  <img src="https://github.com/aasjunior/com.sofia.mobile/assets/85968113/ce5ba98e-c63a-4fb7-a311-ced454084bc7" width="700" alt="ilustracao">
</div>

### Descrição do projeto 💜

<p align="justify">
A Sofia API é uma API RESTful desenvolvida com Spring Boot e Java. Ela foi projetada para ser utilizada pelo <a href="https://github.com/aasjunior/com.sofia.mobile.git">Sofia Mobile</a> para realização do CRUD com MongoDB.
</p>

Ficou interessado? Veja mais no nosso [pitch](https://www.youtube.com/watch?v=wSeBx_eXvcY) de apresentação da SOFIA, ou acesse o nosso [site](https://sofia-aja.vercel.app/). 💜

## Pré-Requisitos 💜

* JDK (Java Development Kit)
* MongoDB
* Git
* Alguma IDE de sua preferência

## Dependências 💜

Este projeto depende da seguinte API:

* [Sofia api-flask (Neural Network)](https://github.com/mandis-ncs/api-flask.git)
  <br>

## Configuração do Projeto 💜

1. Clone o repositório para sua máquina local usando o seguinte comando
```
git clone https://github.com/aasjunior/com.sofia.restapi.git
```

2. Importe o projeto na sua IDE preferida. Por exemplo:

  - No **IntelliJ**: Vá para `File > Open`, selecione o diretório do projeto e clique em `OK`. Certifique-se de que o `Maven` está configurado corretamente.
  
  - No **Eclipse**: Clique em `File > Import > Maven > Existing Maven Projects`, selecione o diretório do projeto e clique em `Finish`.

  - No **VSCode**: Instale as extensões `Spring Boot Extension Pack` e `Extension Pack for Java`. Abra o projeto [sofia-api](./sofia-api/).

3. Se a sua IDE tiver um cliente REST API integrado (como o HTTP Client no IntelliJ), você pode usá-lo para testar as requisições HTTP. Caso contrário, você pode usar um cliente REST API externo, como Postman ou Insomnia.

4. Certifique-se de que o **JDK** está instalado e configurado corretamente na sua IDE. No **IntelliJ**, você pode verificar isso em `File > Project Structure > Project Settings > Project > Project SDK`.

5. Certifique-se de que o **MongoDB** está instalado e funcionando corretamente. Você pode usar o **MongoDB** Compass para visualizar e interagir com seus dados no **MongoDB**.

6. Inicie a aplicação. A API estará rodando em `http://localhost:8080`

7. Acesse a documentação da API no **Swagger UI** em: `http://localhost:8080/swagger-ui.html`


### Instalação via Docker 💜

O projeto pode ser criado pelo arquivo via **Docker Compose**. Existem dois níveis de configuração disponíveis:

  - **Docker Compose Local**: Para rodar apenas a API **Spring Boot** localmente.
  - **Docker Compose Completo**: Para provisionar toda a infraestrutura do **SOFIA** (API + FastAPI + NGINX).

#### Docker Compose Local (API Spring Boot)

Para iniciar apenas o container da API Spring Boot:

1. Acesse o diretório `/sofia-api`:

```bash
cd sofia-api
```

2. Execute o comando para iniciar o container:

```bash
docker-compose up
```

#### Docker Compose Completo (Infraestrutura SOFIA)

Para provisionar o back-end completo do **SOFIA**:

1. Navegue até o diretório `/infra`:

```bash
cd infra
```

2. Execute o comando para subir todos os serviços (API, FastAPI e NGINX):

```bash
docker-compose up
```

## Sofia-Server 💜

Para provisionar um servidor **Ubuntu** e configurar toda a infraestrutura do SOFIA, consulte a [documentação do Sofia-Server](./infra/README.md).

Esta documentação inclui:

- Passos para criar um servidor Ubuntu na AWS ou localmente.
- Instalação do **Docker** e **Docker Compose**.
- Execução do ambiente completo de back-end utilizando o arquivo `docker-compose.yml`, disponívem em [infra/docker-compose.yml](./infra/docker-compose.yml).

⚠️ Importante: Certifique-se de configurar as variáveis de ambiente corretamente para conectar ao MongoDB Atlas.

## Endpoints 💜

A API possui os seguintes endpoints:

| Type   | Path                       | Authorization         |                     Obs.                     |
|--------|----------------------------|-----------------------|:--------------------------------------------:|
| POST   | /auth/login                | null                  |       Realiza autenticação do usúario.       |
| POST   | /auth/register             | null                  |          Registra um novo usuário.           |
| POST   | /auth/refresh              | Bearear token         | Atueliza o token de autenticação do usuário. |
| GET    | /patients                  | Bearear token         |   Retorna uma lista de todos os pacientes.   |
| POST   | /patients-with-guardian    | Bearear token         |    Insere um novo paciente e responsável.    |
| GET    | /patients/{id}             | Bearer token          |   Retorna um paciente específico pelo ID.    |
| DELETE | /patients/{id}             | Bearer token / Admin. |         Remove um paciente pelo ID.          |
| POST   | /checklist/qchat/submit    | Bearer token          | Submete o questionário Q-Chat 10 preenchido. |
| GET    | /checklist/qchat/patientId | Bearer token          |  Retorna o resultado do teste do paciente.   |

### Login
- **URI**: `/auth/login`
- **Método**: `POST`
- **Request body**: `application/JSON`
- **Status**: `200 Ok`
- **Entrada**:
```json
{
  "email": "admin@email.com",
  "password": "admin"
}
```
- **Saída**:
```json
{
  "accessToken": "string <JWT>",
  "refreshToken": "string <JWT>",
  "userId": "string"
}
```

### Register
- **URI**: `/auth/register`
- **Método**: `POST`
- **Request body**: `application/JSON`
- **Status**: `200 Ok`
- **Entrada**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "username": "john.doe",
  "email": "john.doe@email.com",
  "password": "123",
  "role": "USER"
}
```

- **Saída**:
```json
{
  "id": "665bbbc64d3b496d45370802",
  "firstName": "John",
  "lastName": "John",
  "username": "john.doe",
  "email": "john.doe@email.com",
  "role": "USER",
  "registrationDate": "2024-06-01T21:24:38.7597449"
}
```

### Refresh Token
- **URI**: `/auth/refresh`
- **Método**: `POST`
- **Request body**: `application/JSON`
- **Status**: `200 Ok`
- **Entrada**:
```json
{
  "token": "string <JWT>"
}
```

- **Saída**:
```json
{
  "accessToken": "string <JWT>",
  "refreshToken": "string <JWT>",
  "userId": "string"
}
```

### Patient with Guardian
- **URI**: `/patients/patient-with-guardian`
- **Método**: `POST`
- **Request body**: `application/JSON`
- **Headers**: <code><b>Authorization:</b> Bearer token</code>
- **Status**: `201 Created`
- **Entrada**:
```json
{
  "patientRequest": {
    "firstName": "string",
    "lastName": "string",
    "birthDate": "string",
    "gender": "Male",
    "ethnicity": "White",
    "familyCases": true,
    "pregnancyComplication": true,
    "premature": true
  },
  "guardianRequest": {
    "firstName": "string",
    "lastName": "string",
    "phone": "string",
    "email": "string"
  },
  "kinship": "Mother"
}
```

- **Saída**:
```json
{
  "id": "string",
  "firstName": "string",
  "lastName": "string",
  "birthDate": "string",
  "gender": "Male",
  "ethnicity": "White",
  "familyCases": true,
  "pregnancyComplications": true,
  "premature": true,
  "guardians": {
    "guardian1": {
      "id": "string",
      "firstName": "string",
      "lastName": "string",
      "phone": "string",
      "email": "string",
      "patients": {
        "relationship1": {
          "patientId": "string",
          "guardianId": "string",
          "kinship": "Mother"
        },
        "relationship2": {
          "patientId": "string",
          "guardianId": "string",
          "kinship": "Mother"
        }
      }
    },
    "guardian2": {
      "id": "string",
      "firstName": "string",
      "lastName": "string",
      "phone": "string",
      "email": "string",
      "patients": {
        "relationship1": {
          "patientId": "string",
          "guardianId": "string",
          "kinship": "Mother"
        }
      }
    }
  },
  "registerDate": "2024-06-02T00:34:27.997Z",
  "ageMonths": 0
}
```


### Q-Chat 10
- **URI**: `/checklist/qchat/submit`
- **Método**: `POST`
- **Request body**: `application/JSON`
- **Headers**: <code><b>Authorization:</b> Bearer token</code>
- **Status**: `200 Ok`
- **Entrada**:
```json
{
  "patientId": "string",
  "questions": {
    "A1": true,
    "A2": true,
    "A3": true,
    "A4": true,
    "A5": true,
    "A6": true,
    "A7": true,
    "A8": true,
    "A9": true,
    "A10": true,
  }
}
```

- **Saída**:
```json
{}
```

- **URI**: `/checklist/qchat/{patientId}`
- **Método**: `GET`
- **Headers**: <code><b>Authorization:</b> Bearer token</code>
- **Status**: `200 Ok`
- **Entrada**:
```
patientId: String
```

- **Saída**:
```json
{
  "testId": "string",
  "testName": "QChat",
  "testType": "SCREENING",
  "registerDateTime": "2024-06-02T00:48:57.304Z",
  "result": true
}
```

## Deploy 💜
Está aplicação possui uma versão disponível pelo endereço abaixo. Porém para utilização da [aplicação móvel](https://github.com/aasjunior/com.sofia.mobile.git) ainda se faz necessário a utilização localmente em conjunto com a [api-flask](https://github.com/mandis-ncs/api-flask.git) (Rede Neural).
```
https://sofia-api-hsrn.onrender.com/swagger-ui/index.html
```

## Tecnologias 💜
<p align="center">
   <img src="https://github.com/aasjunior/com.sofia.mobile/assets/61213599/db90a6e0-3c46-4891-ad39-8405d499bea9" width="800px" alt="Android"/>
   <div align="center">
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring_Boot-2B9348?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot"/>
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
    <img src="https://img.shields.io/badge/MongoDB-green?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB"/>
    <img src="https://img.shields.io/badge/Docker_Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Compose"/>
  </div>
</p>

## Nosso Time AJA 💜
You can see more about us in our profile:
* [Amanda](https://github.com/mandis-ncs)
* [Junior](https://github.com/aasjunior)
* [Aline](https://github.com/AlineLauriano)

###### Aviso
Esta é uma iniciativa acadêmica, sendo assim, não possui todas as funcionalidades e características de uma aplicação real.
