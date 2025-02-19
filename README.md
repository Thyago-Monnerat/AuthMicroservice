# Microsserviço de Login/Registro

#### O Microsserviço lida com criação de conta e gera um token como autenticação após o login bem-sucedido.
#### Utilizei JWT para gerar o token de autenticação e validar o token nas requisições. No application-properties, coloquei uma chave mockada para assinar o token, apenas por praticidade.
#### Utilizei BCrypt para encriptar as senhas ao salvá-las no banco de dados, que nesse caso, utilizei o h2.

#### Fiz tratamento de exceções utilizando um handler com a anotação @ControllerAdvice, e criei algumas exceções para facilitar o controle.

#### Utilizei um Mapper para converter DTO para Modelo, livrando a classe da responsabilidade de lidar com essa conversão. Não utilizei o MapStruct por precisar apenas de uma única conversão.

#### Separei a responsabilidade de encode e verificar as senhas em uma classe utilitária, melhorando o desacoplamento e seguindo o clean code.

#### Utilizei o Spring Security com filtro para lidar com requisições, embora seja completamente inútil nesse caso, pois todas as requisições, login e registro, não necessitam de autenticação. Mas configurei um para uma possível escalabilidade, podendo haver a necessidade de ter um no futuro.

![Static Badge](https://img.shields.io/badge/Login-Post-yellow?style=for-the-badge)
```
host:port/auth/login

@RequestBody
{
    "username": "abc",
    "password": "123123",
}

```
* pode lançar em caso de usuário não registrado:
  UserNotRegisteredException("Invalid credentials");

![Static Badge](https://img.shields.io/badge/Registro-Post-yellow?style=for-the-badge)

```
host:port/auth/register

@RequestBody
{
    "username": "abc",
    "email": "asdasd",
    "password": "123123",
    "confirmPassword": "123123"
}
```
* pode lançar em caso de username já registrado:
  UserAlreadyRegisteredException("Username already registered");

* pode lançar em caso de email já registrado:
  UserAlreadyRegisteredException("Email already registered");

* pode lançar em caso de senhas não coincidem:
  PasswordsUnmatchedException("Passwords unmatched");
---
#### Essa lógica de senhas não coincidem poderia ficar a cargo do frontend, mas acredito que um double check seja interessante para evitar quaisquer tipo de erro, ainda mais sendo um microsserviço desacoplado.

No mais, é isso ❤️

---

Dependências maven:
* java 17
* spring security
* spring security crypto
* spring JPA
* JWT
* Lombok
* spring web
* h2

