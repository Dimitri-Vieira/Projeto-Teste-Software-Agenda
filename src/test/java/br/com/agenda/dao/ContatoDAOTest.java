package br.com.agenda.dao;

import br.com.agenda.model.Contato;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class ContatoDAOTest {

    // IMPORTANTE: Como temos uma chave estrangeira, esses testes assumem que
    // já existe um usuário cadastrado no seu banco de dados com o ID = 1.
    private final int ID_USUARIO_TESTE = 1;

    @Test
    public void deveCriarNovoContato() { // CREATE
        ContatoDAO contatoDAO = new ContatoDAO();

        // 1. Preparação (Arrange)
        Contato novoContato = new Contato();
        novoContato.setNome("João do Create");
        novoContato.setTelefone("(11) 1111-1111");
        novoContato.setDataCadastro(new Date());
        novoContato.setIdUsuario(ID_USUARIO_TESTE);

        // 2. Ação (Act)
        contatoDAO.save(novoContato);

        // 3. Verificação (Assert)
        List<Contato> contatos = contatoDAO.getContatos(ID_USUARIO_TESTE);
        boolean encontrou = false;
        for (Contato c : contatos) {
            if (c.getNome().equals("João do Create")) {
                encontrou = true;
                break;
            }
        }
        Assertions.assertTrue(encontrou, "O contato salvo deveria aparecer na lista do usuário.");
    }

    @Test
    public void deveListarContatosDoUsuario() { // READ
        ContatoDAO contatoDAO = new ContatoDAO();

        // 1. Ação (Act)
        List<Contato> contatos = contatoDAO.getContatos(ID_USUARIO_TESTE);

        // 2. Verificação (Assert)
        // O teste passa se a lista retornar (mesmo que vazia, ela não pode ser nula)
        Assertions.assertNotNull(contatos, "A lista de contatos não deve ser nula.");
    }

    @Test
    public void deveAtualizarContatoExistente() { // UPDATE
        ContatoDAO contatoDAO = new ContatoDAO();

        // 1. Preparação (Arrange) - Pega o primeiro contato da lista para editar
        List<Contato> contatos = contatoDAO.getContatos(ID_USUARIO_TESTE);

        // Se não houver contatos, o teste falha preventivamente (precisa ter algo para atualizar)
        Assertions.assertFalse(contatos.isEmpty(), "Precisa existir pelo menos um contato para testar o Update.");

        Contato contatoParaEditar = contatos.get(0);
        String nomeAntigo = contatoParaEditar.getNome();
        String novoNome = "Nome Atualizado " + System.currentTimeMillis(); // Gera um nome único

        contatoParaEditar.setNome(novoNome);

        // 2. Ação (Act)
        contatoDAO.update(contatoParaEditar);

        // 3. Verificação (Assert)
        List<Contato> contatosAposUpdate = contatoDAO.getContatos(ID_USUARIO_TESTE);
        Contato contatoVerificacao = null;

        for (Contato c : contatosAposUpdate) {
            if (c.getId() == contatoParaEditar.getId()) {
                contatoVerificacao = c;
                break;
            }
        }

        Assertions.assertNotNull(contatoVerificacao);
        Assertions.assertEquals(novoNome, contatoVerificacao.getNome(), "O nome do contato deveria ter sido atualizado no banco de dados.");
        Assertions.assertNotEquals(nomeAntigo, contatoVerificacao.getNome(), "O nome novo não pode ser igual ao antigo.");
    }

    @Test
    public void deveDeletarContato() { // DELETE
        ContatoDAO contatoDAO = new ContatoDAO();

        // 1. Preparação (Arrange)
        // Vamos criar um contato EXCLUSIVO para ser deletado, para não apagar dados importantes
        Contato contatoDescartavel = new Contato();
        contatoDescartavel.setNome("Contato Para Deletar");
        contatoDescartavel.setTelefone("0000-0000");
        contatoDescartavel.setDataCadastro(new Date());
        contatoDescartavel.setIdUsuario(ID_USUARIO_TESTE);
        contatoDAO.save(contatoDescartavel);

        // Buscamos a lista para descobrir qual ID o MySQL gerou para esse contato descartável
        List<Contato> contatos = contatoDAO.getContatos(ID_USUARIO_TESTE);
        int idParaDeletar = contatos.get(contatos.size() - 1).getId(); // Pega o último

        // 2. Ação (Act)
        contatoDAO.delete(idParaDeletar, ID_USUARIO_TESTE);

        // 3. Verificação (Assert)
        List<Contato> contatosAposDelete = contatoDAO.getContatos(ID_USUARIO_TESTE);
        boolean aindaExiste = false;

        for (Contato c : contatosAposDelete) {
            if (c.getId() == idParaDeletar) {
                aindaExiste = true;
                break;
            }
        }

        Assertions.assertFalse(aindaExiste, "O contato deveria ter sido removido do banco de dados.");
    }
}