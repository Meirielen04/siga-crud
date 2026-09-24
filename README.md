# SIGA — Atividade de CRUD completo e Etapa 1 (código inicial)

**Técnicas de Programação II (TP2) · Aula 8** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este é o **código inicial** da atividade prática da Aula 8. O CRUD está **incompleto** e o código contém **três deslizes propositais**, que você deverá corrigir. Ao final, este projeto compõe a entrega da **Etapa 1 do Projeto Integrador**.

## Estrutura do projeto

```
siga-crud/
└── src/
    └── siga/
        ├── Aluno.java             (entidade de domínio; pronta)
        ├── AlunoDAO.java          (interface do DAO, da Aula 7; pronta)
        ├── AlunoDAOMemoria.java   (CRUD incompleto + deslizes 1 e 2)
        ├── ServicoAluno.java      (camada de serviço incompleta + deslize 3)
        └── Main.java              (apresentação; demonstra os deslizes)
```

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

Execute e observe a saída: ela **demonstra os três deslizes acontecendo**.

## O que está incompleto

| Local | Situação |
|---|---|
| `AlunoDAOMemoria.inserir` | Não impede matrícula duplicada |
| `AlunoDAOMemoria.atualizar` | Não implementado (lança `UnsupportedOperationException`) |
| `ServicoAluno` | Faltam `consultar`, `alterar` e `excluir` |

## Os três deslizes propositais

| # | Deslize | Onde | Por que é um problema |
|---|---|---|---|
| 1 | **Coleção interna exposta** | `AlunoDAOMemoria.listarTodos` | Devolve a própria lista interna; a tela consegue inserir um aluno sem passar pelo serviço, quebrando o encapsulamento. |
| 2 | **Exclusão silenciosa** | `AlunoDAOMemoria.remover` | Não verifica se o registro existia; o usuário recebe confirmação de uma operação que não ocorreu. |
| 3 | **Validação duplicada e divergente** | `ServicoAluno` e `Main` | A regra da média está nos dois lugares, com limites diferentes (0..10 e 0..100). Regra duplicada diverge com o tempo. |

## Sua tarefa

Siga as etapas da ficha de atividade prática:

1. **Completar o CRUD** no `AlunoDAOMemoria`: impedir matrícula duplicada em `inserir` e implementar `atualizar`, verificando a existência do registro.
2. **Implementar a camada de serviço**: `consultar`, `alterar` e `excluir` no `ServicoAluno`, extraindo a validação para um método privado `validar(Aluno)` reutilizado pelas operações.
3. **Corrigir os três deslizes**: cópia defensiva em `listarTodos`, verificação de existência em `remover` e eliminação da validação duplicada na apresentação (a regra do domínio fica **apenas** no serviço).
4. **Tratar as exceções** na camada de apresentação, com mensagens claras e específicas — e sem blocos `catch` vazios.
5. **Consolidar a Etapa 1** no repositório, com README e commits descritivos.

## Critério de sucesso

Ao final: (a) as **quatro operações** do CRUD funcionam; (b) a regra da média existe em **um único lugar**; (c) a tela **não consegue** alterar a coleção interna do DAO; e (d) excluir uma matrícula inexistente produz **mensagem de erro**, não de sucesso.

## Padrão de entrega

Conforme a ficha de atividade prática: identificadores em português, um arquivo `.java` por classe pública, código formatado, entrega no repositório Git com README e commits descritivos. O uso de IA para gerar o código é proibido nesta atividade (ver seção 5.3 da ficha).

> **Etapa 1 do Projeto Integrador:** além desta atividade, a entrega inclui o modelo de domínio, o diagrama de classes, ao menos um padrão criacional justificado e a documentação das decisões de design. Consulte a Seção 10 da apostila da Aula 8.
