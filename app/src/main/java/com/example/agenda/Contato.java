package com.example.agenda;

public class Contato {
    private String nome;
    private String telefone;
    private String nascimento;
    private int contatoId;

    public Contato(String nome, String telefone, String nascimento, int contatoId) {
        this.nome = nome;
        this.telefone = telefone;
        this.nascimento = nascimento;
        this.contatoId = contatoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public int getContatoId() {
        return contatoId;
    }

    public void setContatoId(int contatoId) {
        this.contatoId = contatoId;
    }
}
