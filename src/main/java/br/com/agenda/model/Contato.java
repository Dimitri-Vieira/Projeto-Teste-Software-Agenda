package br.com.agenda.model;

import java.util.Date;

public class Contato {
    private int id;
    private String nome;
    private String telefone; // Alterado de int para String
    private Date dataCadastro;
    private int idUsuario;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; } // Getter atualizado
    public void setTelefone(String telefone) { this.telefone = telefone; } // Setter atualizado
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}