package br.com.agenda.aplicacao;

import br.com.agenda.dao.ContatoDAO;
import br.com.agenda.dao.UsuarioDAO;
import br.com.agenda.model.Contato;
import br.com.agenda.model.Usuario;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsuarioDAO uDAO = new UsuarioDAO();
        int opt = -1;

        while (opt != 0) {
            System.out.println("\n--- AGENDA CENTRAL ---");
            System.out.println("1. Login | 2. Cadastro | 0. Sair");
            System.out.print("Escolha: ");
            opt = sc.nextInt(); sc.nextLine();

            if (opt == 1) {
                System.out.print("Login: "); String l = sc.nextLine();
                System.out.print("Senha: "); String s = sc.nextLine();
                Usuario logado = uDAO.autenticar(l, s);
                if (logado != null) {
                    menuAgenda(sc, logado);
                } else {
                    System.out.println("Erro: Credenciais inválidas.");
                }
            } else if (opt == 2) {
                System.out.print("Novo Login: "); String nl = sc.nextLine();
                System.out.print("Nova Senha: "); String ns = sc.nextLine();
                Usuario u = new Usuario(); u.setLogin(nl); u.setSenha(ns);
                uDAO.save(u);
                System.out.println("Usuário criado!");
            }
        }
    }

    private static void menuAgenda(Scanner sc, Usuario user) {
        ContatoDAO cDAO = new ContatoDAO();
        int opt = -1;
        while (opt != 0) {
            System.out.println("\nAgenda de: " + user.getLogin());
            System.out.println("1. Novo Contato | 2. Listar | 3. Editar | 4. Excluir | 0. Logout");
            System.out.print("Opção: ");
            opt = sc.nextInt(); sc.nextLine();
            switch (opt) {
                case 1 -> {
                    System.out.print("Nome: "); String n = sc.nextLine();
                    System.out.print("Telefone: "); String tel = sc.nextLine(); // Entrada como String
                    Contato c = new Contato(); c.setNome(n); c.setTelefone(tel);
                    c.setDataCadastro(new Date()); c.setIdUsuario(user.getId());
                    cDAO.save(c);
                    System.out.println("Contato salvo!");
                }
                case 2 -> {
                    System.out.println("\n--- Seus Contatos ---");
                    cDAO.getContatos(user.getId()).forEach(c ->
                            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Tel: " + c.getTelefone()));
                }
                case 3 -> {
                    System.out.print("ID do contato para editar: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo Nome: "); String nn = sc.nextLine();
                    System.out.print("Novo Telefone: "); String nt = sc.nextLine();
                    Contato c = new Contato(); c.setId(id); c.setNome(nn);
                    c.setTelefone(nt); c.setDataCadastro(new Date());
                    c.setIdUsuario(user.getId());
                    cDAO.update(c);
                    System.out.println("Atualização concluída.");
                }
                case 4 -> {
                    System.out.print("ID para excluir: ");
                    int idExc = sc.nextInt();
                    cDAO.delete(idExc, user.getId());
                    System.out.println("Contato removido.");
                }
            }
        }
    }
}