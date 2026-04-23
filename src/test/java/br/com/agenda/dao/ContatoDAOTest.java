package br.com.agenda.dao;

import br.com.agenda.model.Contato;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class ContatoDAOTest {

    @Test
    public void deveSalvarEListarContatoDoUsuarioCorreto() {
        ContatoDAO contatoDAO = new ContatoDAO();

        // Simula que o usuário de ID 1 (ex: admin) está logado
        int idUsuarioLogado = 1;

        // Cria o contato
        Contato c = new Contato();
        c.setNome("Contato de Teste");
        c.setTelefone("(11) 99999-9999");
        c.setDataCadastro(new Date());
        c.setIdUsuario(idUsuarioLogado);

        // Salva
        contatoDAO.save(c);

        // Busca os contatos SÓ desse usuário
        List<Contato> contatosDoUsuario = contatoDAO.getContatos(idUsuarioLogado);

        // Verifica se a lista não está vazia e se o contato está lá
        Assertions.assertFalse(contatosDoUsuario.isEmpty(), "A lista de contatos não deveria estar vazia.");

        // Pega o último contato inserido e verifica se pertence mesmo ao usuário 1
        Contato ultimoInserido = contatosDoUsuario.get(contatosDoUsuario.size() - 1);
        Assertions.assertEquals(idUsuarioLogado, ultimoInserido.getIdUsuario(), "O contato deve pertencer ao usuário logado.");
    }
}