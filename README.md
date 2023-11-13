# Sofia API

<p align="center">
   <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=RED&style=for-the-badge" #vitrinedev/>
</p>

## Descrição
A Sofia API é uma API RESTful desenvolvida com Spring Boot e Java no VS Code. Ela foi projetada para ser utilizada pelo Sofia Mobile para realização do CRUD com MySQL.

## Pré-Requisitos
* JDK (Java Development Kit)
* MySQL
* Git
* VS Code

## Configuração do Projeto

1. Clone o repositório para sua máquina local usando o seguinte comando
```
git clone https://github.com/aasjunior/com.sofia.restapi.git
```

2. Abra o projeto no VS Code e adicione as seguintes extensões:
     * `Extension Pack for Java`
     * `Spring Boot Extension Pack`
     * `Thunder Client` ou outro cliente REST API para testar as requisições HTTP.
       

3. Certifique-se de que o JDK está instalado e configurado corretamente.
   

4. Certifique-se de que o MySQL está instalado e funcionando corretamente.
   

5. Inicie a aplicação. A API estará rodando em `http://localhost:8080`

## Endpoints

A API possui os seguintes endpoints:

* `GET /pacientes`: Retorna uma lista de todos os pacientes.
* `POST /pacientes`: Insere um novo paciente.
* `GET /pacientes/{id}`: Retorna um paciente específico pelo ID.
* `PUT /pacientes`: Atualiza um paciente.
* `DELETE /pacientes/{id}`: Remove um paciente pelo ID.

