# SIGA — CRUD de Alunos (Etapa 1)

**Técnicas de Programação II (TP2) · Aula 8** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Autoria: Meirielen

Sistema de Gestão Acadêmica Simplificado (SIGA). Este projeto implementa o CRUD completo de alunos (cadastrar, consultar, alterar, excluir e listar), organizado em camadas, e compõe a entrega da **Etapa 1 do Projeto Integrador**.

## Estrutura do projeto

```
siga-crud/
└── src/
    └── siga/
        ├── Aluno.java             (entidade de domínio)
        ├── AlunoDAO.java          (interface de acesso a dados)
        ├── AlunoDAOMemoria.java   (DAO que guarda os alunos em memória)
        ├── ServicoAluno.java      (regras de negócio e validação)
        └── Main.java              (apresentação: demonstra o CRUD e trata as exceções)
```

| Arquivo | Responsabilidade |
|---|---|
| `Aluno` | Representa um aluno (nome, matrícula e média). É imutável. |
| `AlunoDAO` | Contrato das operações de persistência, sem detalhes de armazenamento. |
| `AlunoDAOMemoria` | Implementação do contrato usando uma lista em memória. |
| `ServicoAluno` | Valida os dados e garante as regras do domínio antes de chamar o DAO. |
| `Main` | Camada de apresentação: chama o serviço e traduz as exceções em mensagens. |

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

## O que foi implementado

- **DAO (`AlunoDAOMemoria`)**
  - `inserir` impede matrícula duplicada, lançando `IllegalStateException`.
  - `atualizar` localiza o aluno pela matrícula e o substitui; lança `IllegalStateException` se não existir.
- **Serviço (`ServicoAluno`)**
  - `validar(Aluno)`: método privado com as regras de nome e de média, reutilizado por `cadastrar` e `alterar`.
  - `consultar`, `alterar` e `excluir`, que verificam se o aluno existe antes de agir.
- **Apresentação (`Main`)**
  - Métodos `cadastrar`, `consultar`, `alterar` e `excluir` com `try/catch`, mensagens específicas e nenhum `catch` vazio.

## Deslizes corrigidos

| # | Deslize | Como foi corrigido |
|---|---|---|
| 1 | Coleção interna exposta em `listarTodos` | O método passou a devolver uma cópia da lista (cópia defensiva). Alterar a lista recebida não afeta o DAO. |
| 2 | Exclusão silenciosa em `remover` | O método verifica se o aluno existe e lança `IllegalStateException` quando a matrícula não é encontrada. |
| 3 | Validação da média duplicada no `Main` | A validação foi removida da apresentação. A regra existe apenas em `ServicoAluno.validar`. |

## Decisões de design

- **A regra da média fica só no serviço.** Quando a mesma regra mora em dois lugares, os limites divergem com o tempo. A apresentação apenas chama o serviço e mostra o resultado, sem conhecer o limite.
- **O DAO falha com exceção, e não em silêncio.** Remover ou atualizar um registro que não existe é um erro, e o usuário precisa ser avisado em vez de receber uma confirmação falsa.
- **`excluir` e `alterar` reutilizam `consultar`.** A verificação de existência fica em um único método do serviço.
- **O serviço recebe o DAO pelo construtor** (injeção de dependência), dependendo da interface `AlunoDAO` e não da implementação em memória. Assim o armazenamento pode ser trocado sem alterar o serviço.

## Critério de sucesso

(a) As quatro operações do CRUD funcionam; (b) a regra da média existe em um único lugar; (c) a tela não consegue alterar a coleção interna do DAO; e (d) excluir uma matrícula inexistente produz mensagem de erro, não de sucesso.

## Padrão de entrega

Identificadores em português, um arquivo `.java` por classe pública, código formatado, entrega no repositório Git com README e commits descritivos (um por etapa).

> **Etapa 1 do Projeto Integrador:** além desta atividade, a entrega inclui o modelo de domínio, o diagrama de classes, ao menos um padrão criacional justificado e a documentação das decisões de design. Consulte a Seção 10 da apostila da Aula 8.
