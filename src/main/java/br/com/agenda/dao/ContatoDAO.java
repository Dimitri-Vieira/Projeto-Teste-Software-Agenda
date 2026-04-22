package br.com.agenda.dao;

import br.com.agenda.factory.ConnectionFactory;
import br.com.agenda.model.Contato;
import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    //CRUD
    public void save(Contato contato){
        String sql = "INSERT INTO contatos(nome, idade, datacadastro) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            //Criar uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            //Criamos uma PreparedStatement para executar uma query
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //Adicionar os valores que são esperados pela query
            pstm.setString(1, contato.getNome());
            pstm.setInt(2, contato.getIdade());
            pstm.setDate(3, new Date(contato.getDataCadastro().getTime()));

            //Executar a querey
            pstm.execute();
            System.out.println("Contato salvo com sucesso!");

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            //Fechar as conexões
            try{
                if(pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void update (Contato contato){
        String sql = "UPDATE contatos SET nome = ?, idade = ?, dataCadastro = ? " + "WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            //criar conexão com o bolo
            conn = ConnectionFactory.createConnectionToMySQL();

            //criar a classe para executar a query
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //adicionar os valores para atualizar
            pstm.setString(1, contato.getNome());
            pstm.setInt(2, contato.getId());
            pstm.setDate(3, new Date(contato.getDataCadastro().getTime()));

            //qual o id do registro que deseja atualizar?
            pstm.setInt(4, contato.getId());

            //executar a query
            pstm.execute();

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try{
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public List<Contato> getContatos (){
        String sql = "SELECT * FROM contatos";

        List<Contato> contatos = new ArrayList<Contato>();

        Connection conn = null;
        PreparedStatement pstm = null;
        //classe que vai recuperar os dados do banco *****SELECT*****
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();

            pstm = (PreparedStatement) conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {

                Contato contato = new Contato();

                //recuperar o id
                contato.setId(rset.getInt("id"));
                //recuperar nome
                contato.setNome(rset.getString("nome"));
                //recuperar a idade
                contato.setIdade(rset.getInt("idade"));
                //recuperar data de cadastro
                contato.setDataCadastro(rset.getDate("datacadastro"));

                contatos.add(contato);
            }
        }catch(Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (rset != null) {
                        rset.close();
                    }

                    if (pstm != null) {
                        pstm.close();
                    }

                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return contatos;
    }

    public void delete (int id){
        String sql = "DELETE FROM contatos WHERE id = ?";

        Connection conn = null;

        PreparedStatement pstm = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();

            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.execute();

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
