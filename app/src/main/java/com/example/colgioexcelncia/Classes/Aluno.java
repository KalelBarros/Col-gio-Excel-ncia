package com.example.colgioexcelncia.Classes;

import java.util.Date;

public class Aluno
{
    private String cpf, nome, senha, data_nascimento, serie, nome_turma;

    public Aluno(String cpf, String nome, String senha, String data_nascimento, String serie, String nome_turma) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.serie = serie;
        this.nome_turma = nome_turma;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getSenha() { return senha; }
    public String getDataNascimento() {return data_nascimento;}
    public String getSerie(){return serie;}
    public String getNome_turma() {return nome_turma;}

    public String getIniciais() {
        String[] partes = nome.split(" ");
        String iniciais = "";

        if (partes.length > 0) {
            iniciais += partes[0].substring(0, 1).toUpperCase();
            if (partes.length > 1) {
                iniciais += partes[partes.length - 1].substring(0, 1).toUpperCase();
            }
        }

        return iniciais;
    }

    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}

