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
        System.out.println("=== SIGA - Atividade CRUD ===\n");

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
        // System.out.println("  (o 'Intruso' entrou sem passar pelo serviço)");
        System.out.println("  (o 'Intruso' não entrou, a lista é uma cópia)");

        // DESLIZE 2 em ação: exclusão de matrícula inexistente informa sucesso.
        // dao.remover("0000000");
        excluir(servico, "0000000");
        // System.out.println("\nExclusão de matrícula inexistente agora informa erro);
        // System.out.println("  (mas nada foi removido — falha silenciosa)");

        // DESLIZE 3 em ação: esta camada valida com limite diferente do serviço.
        Aluno suspeito = new Aluno("Média Absurda", "2026003", 50);
        cadastrar(servico, suspeito);
        // if (suspeito.getMedia() >= 0 && suspeito.getMedia() <= 100) {   // limite divergente!
        //     System.out.println("\nA tela aprovou média 50 (limite 0..100),");
            // try {
            //     servico.cadastrar(suspeito);
            // } catch (IllegalArgumentException e) {
            //     System.out.println("  mas o serviço recusou: " + e.getMessage());
            //     System.out.println("  (a mesma regra mora em dois lugares, com limites diferentes)");
            // }
        // }

        // --- UPDATE, DELETE E READ ---
        System.out.println();
        alterar(servico, new Aluno ("Maria Silva", "2026001" , 9.0));
        alterar(servico, new Aluno ("Maria Silva", "2126001" , 9.0));
        excluir(servico, "2026001");
        excluir(servico, "0000000");
        consultar(servico, "2326001");
        consultar(servico, "2026002");
        // try {
        //     dao.atualizar(new Aluno("Maria Silva", "2026001", 9.0));
        // } catch (UnsupportedOperationException e) {
        //     System.out.println("Atualizar: " + e.getMessage());
        // }

        // System.out.println("\nSua tarefa: completar o CRUD, implementar a camada de serviço");
        // System.out.println("e corrigir os três deslizes (coleção exposta, exclusão silenciosa");
        // System.out.println("e validação duplicada). Depois, consolide a Etapa 1 no repositório.");
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
    private static void excluir(ServicoAluno servico, String matricula) {
        try {
            servico.excluir(matricula);
            System.out.println("Removido: " + matricula);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível excluir: " + e.getMessage());
        }
    }
    private static void alterar(ServicoAluno servico, Aluno aluno) {
        try {
            servico.alterar(aluno);
            System.out.println("Alterado: " + aluno);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível alterar: " + e.getMessage());
        }
    }
    private static void consultar(ServicoAluno servico, String matricula) {
        try {
            Aluno a = servico.consultar(matricula);
            System.out.println("Encontrado: " + a);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível consultar: " + e.getMessage());
        }
    }
}
