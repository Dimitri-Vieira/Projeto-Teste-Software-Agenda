package br.com.agenda.dao;

import br.com.agenda.factory.ConnectionFactory;
import br.com.agenda.model.Contato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    public void save(Contato contato) {
        String sql = "INSERT INTO contatos(nome, telefone, datacadastro, id_usuario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, contato.getNome());
            pstm.setString(2, contato.getTelefone()); // Alterado para String
            pstm.setDate(3, new Date(contato.getDataCadastro().getTime()));
            pstm.setInt(4, contato.getIdUsuario());
            pstm.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(Contato contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, dataCadastro = ? WHERE id = ? AND id_usuario = ?";
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, contato.getNome());
            pstm.setString(2, contato.getTelefone()); // Alterado para String
            pstm.setDate(3, new Date(contato.getDataCadastro().getTime()));
            pstm.setInt(4, contato.getId());
            pstm.setInt(5, contato.getIdUsuario());
            pstm.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Contato> getContatos(int idUsuario) {
        String sql = "SELECT * FROM contatos WHERE id_usuario = ?";
        List<Contato> contatos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, idUsuario);
            try (ResultSet rset = pstm.executeQuery()) {
                while (rset.next()) {
                    Contato c = new Contato();
                    c.setId(rset.getInt("id"));
                    c.setNome(rset.getString("nome"));
                    c.setTelefone(rset.getString("telefone")); // Recuperando o telefone
                    c.setDataCadastro(rset.getDate("datacadastro"));
                    c.setIdUsuario(rset.getInt("id_usuario"));
                    contatos.add(c);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return contatos;
    }

    public void delete(int id, int idUsuario) {
        String sql = "DELETE FROM contatos WHERE id = ? AND id_usuario = ?";
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.setInt(2, idUsuario);
            pstm.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }
}