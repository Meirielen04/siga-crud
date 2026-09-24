package siga;

import java.util.List;

/**
 * Ponto de entrada do SIGA (código INICIAL da atividade da Aula 8).
 *
 * Esta é a camada de APRESENTAÇÃO. Ela demonstra, em execução, os três
 * deslizes que você deverá corrigir — e contém, ela própria, um deles.
 *
 * DESLIZE 3 (aqui): a validação da média está repetida nesta camada, com um
 * limite DIFERENTE do usado no ServicoAluno (aqui aceita até 100). Regra de
 * domínio duplicada em dois lugares acaba divergindo, como se vê.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Atividade CRUD e Etapa 1 (código inicial) ===\n");

        AlunoDAO dao = new AlunoDAOMemoria();
        ServicoAluno servico = new ServicoAluno(dao);

        // --- CREATE ---
        cadastrar(servico, new Aluno("Maria Silva", "2026001", 8.5));
        cadastrar(servico, new Aluno("João Souza",  "2026002", 6.0));
        System.out.println();

        // --- READ ---
        System.out.println("Alunos cadastrados:");
        for (Aluno aluno : servico.listar()) {
            System.out.println("  " + aluno);
        }

        // DESLIZE 1 em ação: a lista devolvida é a coleção INTERNA do DAO.
        // A apresentação consegue alterar o armazém por fora, sem passar
        // por nenhuma regra do serviço.
        List<Aluno> lista = servico.listar();
        lista.add(new Aluno("Intruso Silva", "9999999", 10));
        System.out.println("\nApós a tela inserir um aluno DIRETAMENTE na lista:");
        for (Aluno aluno : servico.listar()) {
            System.out.println("  " + aluno);
        }
        System.out.println("  (o 'Intruso' entrou sem passar pelo serviço)");

        // DESLIZE 2 em ação: exclusão de matrícula inexistente informa sucesso.
        dao.remover("0000000");
        System.out.println("\nExclusão de matrícula inexistente: 'removido com sucesso'");
        System.out.println("  (mas nada foi removido — falha silenciosa)");

        // DESLIZE 3 em ação: esta camada valida com limite diferente do serviço.
        Aluno suspeito = new Aluno("Média Absurda", "2026003", 50);
        if (suspeito.getMedia() >= 0 && suspeito.getMedia() <= 100) {   // limite divergente!
            System.out.println("\nA tela aprovou média 50 (limite 0..100),");
            try {
                servico.cadastrar(suspeito);
            } catch (IllegalArgumentException e) {
                System.out.println("  mas o serviço recusou: " + e.getMessage());
                System.out.println("  (a mesma regra mora em dois lugares, com limites diferentes)");
            }
        }

        // --- UPDATE (ainda não implementado) ---
        System.out.println();
        try {
            dao.atualizar(new Aluno("Maria Silva", "2026001", 9.0));
        } catch (UnsupportedOperationException e) {
            System.out.println("Atualizar: " + e.getMessage());
        }

        System.out.println("\nSua tarefa: completar o CRUD, implementar a camada de serviço");
        System.out.println("e corrigir os três deslizes (coleção exposta, exclusão silenciosa");
        System.out.println("e validação duplicada). Depois, consolide a Etapa 1 no repositório.");
    }

    /** Apresentação: traduz as exceções do serviço em mensagens ao usuário. */
    private static void cadastrar(ServicoAluno servico, Aluno aluno) {
        try {
            servico.cadastrar(aluno);
            System.out.println("Cadastrado: " + aluno);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }
}
