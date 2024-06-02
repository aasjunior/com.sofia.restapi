# Sofia Mobile 💜

<p align="justify"> Sofia é um Software Orientado por Inteligência Artificial para Auxílio ao Pré-diagnóstico de Crianças de 0 a 4 Anos com Manifestações Comportamentais do Transtorno do Espectro Autista (TEA). O aplicativo mobile CAD (computer aided design) é destinado ao auxílio de profissionais da saúde na triagem e identificação de sinais do TEA. <strong> 💜 Nosso Objetivo 💜 </strong> é promover a acessibilidade ao diagnóstico precoce de TEA! 💜 </p>

<p align="center">
  <img src="https://github.com/aasjunior/com.sofia.mobile/assets/85968113/ce5ba98e-c63a-4fb7-a311-ced454084bc7" width="700" alt="ilustracao">
</p>

### Descrição do projeto 💜

<p align="justify">
A Sofia API é uma API RESTful desenvolvida com Spring Boot e Java. Ela foi projetada para ser utilizada pelo Sofia Mobile para realização do CRUD com MongoDB.
</p>

Ficou interessado? Veja mais no nosso pitch de apresentação da SOFIA, [assista aqui](https://youtu.be/ArjSy3HSWuY) 💜

## Pré-Requisitos 💜

* JDK (Java Development Kit)
* MongoDB
* Git
* Alguma IDE de sua preferência

## Configuração do Projeto 💜

1. Clone o repositório para sua máquina local usando o seguinte comando
```
git clone https://github.com/aasjunior/com.sofia.restapi.git
```

2. Abra o projeto na sua IDE preferida (como IntelliJ, Eclipse, NetBeans, etc.) e instale os plugins ou extensões necessários para o desenvolvimento em Java e Spring Boot. No caso do IntelliJ, você pode querer instalar o plugin Spring Assistant.


3. Se a sua IDE tiver um cliente REST API integrado (como o HTTP Client no IntelliJ), você pode usá-lo para testar as requisições HTTP. Caso contrário, você pode usar um cliente REST API externo, como Postman ou Insomnia.


4. Certifique-se de que o JDK está instalado e configurado corretamente na sua IDE. No IntelliJ, você pode verificar isso em File > Project Structure > Project Settings > Project > Project SDK.

5. Certifique-se de que o MongoDB está instalado e funcionando corretamente. Você pode usar o MongoDB Compass para visualizar e interagir com seus dados no MongoDB.

6. Inicie a aplicação. A API estará rodando em `http://localhost:8081`

## Endpoints 💜

A API possui os seguintes endpoints:

| Type          | Path            | Obs.                                      |
| ------------- |-----------------|:-----------------------------------------:|
| GET           | /patients       | Retorna uma lista de todos os pacientes.  |
| POST          | /patients       | Insere um novo paciente.                  |
| GET           | /patients/{id}  | Retorna um paciente específico pelo ID.   |
| DELETE        | /patients/{id}  | Remove um paciente pelo ID.               |

**Login**
- **URI**: `/auth/login`
- **Método**: `POST`
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
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTcxNzI5MzMzNH0.64SmeaXnCzHoZ2og58U9-L5KWWaTM1kH6WhB1YPDNLA",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTcxNzg5MDkzNH0.HhmkCijF3tORNG2Ir-J9f4v-5-nsA0eJ7HpDpcPPGCg",
  "userId": "664be20a8be5ae0784bb9c70"
}
```

## Tecnologias 💜
<p align="center">
   <img src="https://github.com/aasjunior/com.sofia.mobile/assets/85968113/adc364c7-8401-4326-ad56-3807673b85f2" width="600px" alt="Android"/>
   <div align="center">
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring_Boot-2B9348?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot"/>
    <img src="https://img.shields.io/badge/MongoDB-green?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB"/>
  </div>
</p>

## Nosso Time AJA 💜
You can see more about us in our profile:
* [Amanda](https://github.com/mandis-ncs)
* [Junior](https://github.com/aasjunior)
* [Aline](https://github.com/AlineLauriano)

###### Aviso
Esta é uma iniciativa acadêmica, sendo assim, não possui todas as funcionalidades e características de uma aplicação real.