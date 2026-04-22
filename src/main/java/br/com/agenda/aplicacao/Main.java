package br.com.agenda.aplicacao;

import br.com.agenda.dao.ContatoDAO;
import br.com.agenda.model.Contato;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ContatoDAO contatoDAO = new ContatoDAO();
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== AGENDA DE CONTATOS =====");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Listar Todos");
            System.out.println("3. Atualizar Contato");
            System.out.println("4. Deletar Contato");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Idade: ");
                    int idade = scanner.nextInt();

                    Contato novoContato = new Contato();
                    novoContato.setNome(nome);
                    novoContato.setIdade(idade);
                    novoContato.setDataCadastro(new Date());

                    contatoDAO.save(novoContato);
                    System.out.println("--- Contato salvo com sucesso! ---");
                    break;

                case 2:
                    System.out.println("\n--- Lista de Contatos ---");
                    for (Contato c : contatoDAO.getContatos()) {
                        System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Idade: " + c.getIdade());
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID do contato que deseja atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpa buffer

                    System.out.print("Novo Nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Nova Idade: ");
                    int novaIdade = scanner.nextInt();

                    Contato cAtualizado = new Contato();
                    cAtualizado.setId(idAtualizar);
                    cAtualizado.setNome(novoNome);
                    cAtualizado.setIdade(novaIdade);
                    cAtualizado.setDataCadastro(new Date());

                    contatoDAO.update(cAtualizado);
                    System.out.println("--- Contato atualizado! ---");
                    break;

                case 4:
                    System.out.print("Digite o ID do contato que deseja excluir: ");
                    int idExcluir = scanner.nextInt();

                    contatoDAO.delete(idExcluir);
                    System.out.println("--- Contato removido! ---");
                    break;

                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
        scanner.close();
    }
}