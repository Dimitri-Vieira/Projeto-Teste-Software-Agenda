package br.com.agenda.dao;

import br.com.agenda.factory.ConnectionFactory;
import br.com.agenda.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    public void save(Usuario usuario) {
        String sql = "INSERT INTO usuarios(login, senha) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, usuario.getLogin());
            pstm.setString(2, usuario.getSenha());
            pstm.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, login);
            pstm.setString(2, senha);
            try (ResultSet rset = pstm.executeQuery()) {
                if (rset.next()) {
                    Usuario u = new Usuario();
                    u.setId(rset.getInt("id"));
                    u.setLogin(rset.getString("login"));
                    return u;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}