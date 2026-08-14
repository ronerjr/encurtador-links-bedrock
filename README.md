# 🔗 Encurtador de Links (com Bedrock Framework)

Um projeto de exemplo elegante, rápido e com **Zero Dependências**, construído do zero para demonstrar o poder e a simplicidade do **Bedrock Framework**.

## 🚀 O Projeto

Este é um encurtador de links completo que mantém todos os dados em memória através da utilização de um `ConcurrentHashMap`. Ele foi projetado para exemplificar a arquitetura baseada em componentes, injeção de dependência e roteamento limpo que o Bedrock Framework proporciona.

### ✨ Principais Funcionalidades

- **Encurtamento de URL:** Transforma URLs longas em hashes curtos de 6 caracteres.
- **Redirecionamento Rápido:** Resolve o hash e faz um redirect (HTTP 302) para a URL original instantaneamente.
- **Métricas de Acesso:** Contabiliza automaticamente quantos cliques um link encurtado recebeu.
- **Segurança (Middleware):** As rotas de criação e consulta de métricas da API são protegidas via Middleware utilizando uma chave (`X-API-KEY`), enquanto as rotas de redirecionamento ficam abertas para o público.
- **Testes Unitários:** Cobertura de testes demonstrando como mockar e testar os Controllers, Services, Repositories e Middlewares do Bedrock.

## 🏗️ Arquitetura

O projeto foi organizado para validar o funcionamento das três camadas principais suportadas pelo Bedrock:

1. **Camada de Dados (`@BedrockComponent`):** Um `LinkRepository` *in-memory* que dispensa o uso de banco de dados externo.
2. **Camada de Serviço (`@BedrockComponent`):** Um `LinkService` limpo contendo as regras de negócio de métricas e encurtamento, recebendo dependências puramente via injeção no construtor.
3. **Camada Web (`@BedrockController`):** Dividido inteligentemente entre `LinkController` (para a API administrativa) e `RedirectController` (para o acesso final do usuário).

## 🛠️ Como rodar (Exemplo)

```bash
# Clone este repositório
git clone https://github.com/seu-user/encurtador-links.git

# Acesse a pasta do projeto
cd encurtador-links

# Compile e inicie o projeto utilizando sua build tool
# O servidor iniciará na porta 8080 (http://localhost:8080)
```

## 🔐 Exemplos de Uso

**1. Encurtar uma URL (Protegido)**
```bash
curl -X POST http://localhost:8080/api/links \
  -H "X-API-KEY: chave-secreta-bedrock" \
  -d "https://github.com/seu-user/bedrock-framework"
```

**2. Acessar o Link Encurtado (Público)**
Basta acessar o link gerado pelo navegador:
`http://localhost:8080/go/{hash}`

**3. Ver Métricas de Acesso (Protegido)**
```bash
curl -X GET http://localhost:8080/api/links/{hash}/metrics \
  -H "X-API-KEY: chave-secreta-bedrock"
```

---
Feito com ☕ e focado na simplicidade. Ideal para testar e validar o **Bedrock Framework**.
