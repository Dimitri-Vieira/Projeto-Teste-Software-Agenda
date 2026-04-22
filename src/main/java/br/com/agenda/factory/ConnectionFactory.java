package br.com.agenda.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    //Nome do usuário do MySql
    private static final String USERNAME = "root";

    //Senha do banco
    private static final String PASSWORD = "";

    //Caminho do banco de dados, porta, nome do banco de dados
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/agenda_java";

    /*
    conexão com o banco de dados
     */
    public static Connection createConnectionToMySQL() throws Exception {
        //Faz com que a classe seja carregada pela JVM
        driver:Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

        return connection;
    }

    public static void main(String[] args) throws Exception {
        //Recuperar conexão com banco de dados
        Connection con = createConnectionToMySQL();

        //Testar se a conexão pe nula
        if (con != null){
            System.out.println("Conexão obtida com sucesso.");
            con.close();
        }
    }
}
