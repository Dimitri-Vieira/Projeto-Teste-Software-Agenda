package br.com.agenda.dao;

import br.com.agenda.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioDAOTest {

    @Test
    public void deveFalharAoPassarSenhaIncorreta() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        // Tenta logar com uma conta que não existe ou senha errada
        Usuario usuario = usuarioDAO.autenticar("usuario_fantasma", "senha123");

        // O teste passa se o retorno for NULO (ou seja, barrou o acesso)
        Assertions.assertNull(usuario, "O sistema não deve autenticar usuários com credenciais inválidas.");
    }

    @Test
    public void deveAutenticarUsuarioValido() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // PREPARAÇÃO: Vamos criar um usuário só para esse teste
        Usuario novoUser = new Usuario();
        novoUser.setLogin("testador");
        novoUser.setSenha("1234");
        usuarioDAO.save(novoUser); // Salva no banco

        // AÇÃO: Tenta logar com ele
        Usuario usuarioLogado = usuarioDAO.autenticar("testador", "1234");

        // VERIFICAÇÃO: Garante que ele logou e trouxe os dados certos
        Assertions.assertNotNull(usuarioLogado, "O usuário deve ser autenticado com sucesso.");
        Assertions.assertEquals("testador", usuarioLogado.getLogin());
    }
}