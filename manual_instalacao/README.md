# 📧 Manual de Instalação — ACDNB-EMAIL-SERVICE

## 🧩 Visão Geral

O **ACDNB-EMAIL-SERVICE** é um microserviço responsável por **ler e processar automaticamente e-mails de pagamento** enviados pelos alunos do clube de tênis de mesa.  
Ele atua como intermediário entre a **caixa de entrada de e-mails**, a **API Gemini** e o **sistema principal (ACDNB-BACKEND)**.

Seu principal objetivo é **automatizar o controle de mensalidades**, reduzindo erros manuais e economizando tempo para o gestor **Walter**, que passa a visualizar os pagamentos diretamente no painel administrativo.

---

## 🧠 Fluxo de Funcionamento

1. **Leitura de e-mails:**  
   O sistema acessa a caixa de entrada configurada (via **Jakarta Mail**) e identifica mensagens que contenham **comprovantes de pagamento**.

2. **Extração de dados:**  
   Utilizando a **API Gemini**, o serviço analisa o conteúdo dos anexos (PDFs ou imagens) para extrair informações como:
    - Nome do aluno
    - Valor pago
    - Data do pagamento


3. **Validação e envio:**  
   As informações extraídas são validadas e publicadas em uma **fila RabbitMQ**, de onde o **ACDNB-BACKEND** consome os dados.

4. **Exibição no sistema principal:**  
   O backend armazena e exibe os dados no painel de controle, permitindo que o beneficiário acompanhe os pagamentos e estados das mensalidades dos alunos.

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia | Função |
|-------------|--------|
| **Jakarta Mail** | Leitura e autenticação da caixa de entrada |
| **Gemini API** | Interpretação e extração de dados dos comprovantes |
| **RabbitMQ** | Comunicação assíncrona entre o serviço de e-mail e o backend |
| **Spring Boot** | Framework principal do microserviço |
| **MySQL** | Armazenamento auxiliar e logs de processamento |

---

## ⚙️ Requisitos de Instalação

| Requisito |
|-----------|
| **Java JDK** |
| **Maven** |
| **Spring Boot** |
| **RabbitMQ** |
| **Docker** |
| **MySQL** |
| **Git**  |

---

## 🧩 Configurações Essenciais (`application.properties`)

```properties
spring.application.name=ACDNB-EMAIL-SERVICE
server.port=8081

# Email (Jakarta Mail)
mail.username=${MAIL_USERNAME}
mail.password=${MAIL_PASSWORD}

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
app.rabbitmq.queue=aluno.email

# MySQL (para logs)
spring.datasource.url=jdbc:mysql://localhost:3307/acdnb_email?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=user
spring.datasource.password=user123
spring.jpa.hibernate.ddl-auto=update
