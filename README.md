# Olá, bem-vindo(a) ao Fiap Bank!

## Introdução

#### Essa API foi desenvolvida usando Spring e Java 17.
#### Este readme contém informações sobre como rodar a aplicação e testar os endpoints da API.

## Acesso ao Swagger

O Swagger está configurado no projeto. Ao inicializar a aplicação, você pode acessar a documentação na seguinte URL:

[Swagger UI](http://localhost:8080/swagger-ui.html)

## Testando os Endpoints

### 1. Endpoint Principal

- **GET /**
  - Retorna o nome do projeto e o nome do autor.

### 2. Criar uma Nova Conta

- **POST /account**
  - Utilizado para criar uma nova conta.
  - O corpo da requisição deve seguir o seguinte formato:

```json
{
  "accountId": 1073741824,
  "accountNumber": "string",
  "agency": "string",
  "holderName": "string",
  "holderCpf": "string",
  "balance": 0.1,
  "openingDate": "2025-03-11", (Tenta a sorte, ela sempre pegará o dia e horário atual)
  "accountType": "CORRENTE", (CORRENTE, POUPANCA ou SALARIO, não tente nada diferente para não ser explodido por um 400!)
  "active": true
}
```

### 3. Buscar Contas

- **GET /account**
  - Utilizado para buscar todas as contas.
  - O corpo da requisição deve seguir o seguinte formato:
 
### 4. Buscar Conta por ID

- **GET /account/id/{accountId}**
  - Utilizado para buscar uma conta por id.
  - O id deve ser passado por path.

 ### 5. Buscar Conta por CPF

- **GET /account/cpf/{cpf}**
  - Utilizado para buscar uma conta por CPF.
  - O CPF deve ser passado por path.

### 6. Fazer um depósito

- **PUT /account/deposit**
  - Utilizado para depositar saldo em uma conta.
  - O corpo da requisição deve seguir o seguinte formato:

```json
{
  "accountId": 1073741824,
  "amount": 0.1
}
```

### 7. Fazer um saque

- **PUT /account/withdraw**
  - Utilizado para sacar saldo em uma conta.
  - O corpo da requisição deve seguir o seguinte formato:

```json
{
  "accountId": 1073741824,
  "amount": 0.1 (Não tente roubar nosso banco e retirar mais do que possui)
}
```

### 8. Realizar um PIX

- **PUT /account/pix**
  - Utilizado para realizar uma transfeência via PIX.
  - O corpo da requisição deve seguir o seguinte formato:

```json
{
  "accountId": 1073741824,
  "pixAccountId": 1073741824,
  "amount": 0.1
}
```

### 9. Encerrar uma Conta

- **PUT /account/inactivate/{accountId}**
  - Utilizado para encerrar uma conta.
  - Para realizar o Soft Delete (apenas inativar no banco), foi utilizado o método HTTP PUT no endpoint.
  - O id deve ser passado por path.


---
Desenvolvido por **Leandro Correia**  
[![GitHub](https://img.shields.io/badge/GitHub-000?logo=github&logoColor=white&style=for-the-badge)](https://github.com/correialeo)

