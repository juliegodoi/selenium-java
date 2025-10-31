## Testes Automatizados de Cadastro - Serverest

### 💻 Sobre o Projeto

Este projeto contém testes automatizados (Web UI) para o fluxo de cadastro de usuários no site **Serverest**.

* **URL Testada:** [https://front.serverest.dev/login](https://front.serverest.dev/login)
* **Tecnologias:** Java, Selenium WebDriver, JUnit 5 e Maven.

Os testes validam:
1.  O cadastro de um novo usuário com sucesso.
2.  A tentativa de cadastro com um email que já existe (duplicado).

### 🚀 Como Executar

É necessário ter o **Java** e o **Maven** instalados.

1.  Abra o terminal na pasta raiz do projeto.
2.  Execute o comando para rodar os testes:

```bash
mvn clean test
```

### 📄 Como Gerar o Relatório
Para gerar o relatório de testes (arquivo HTML), execute:

```bash
mvn site
```
O relatório estará disponível em: target/site/surefire-report.html
