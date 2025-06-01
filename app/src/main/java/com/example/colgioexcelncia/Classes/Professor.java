package com.example.colgioexcelncia.Classes;

public class Professor
{
    private String cpf, nome, senha, id_disciplina;

    public Professor(String cpf, String nome, String senha, String id_disciplina ) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.id_disciplina = id_disciplina;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(String id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

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

    public String getDisciplina()
    {
        switch (Integer.parseInt(id_disciplina))
        {
            case 1:
                return "Matemática";
            case 2:
                return "História";
            case 3:
                return "Linguagens";
            case 4:
                return "Ed.Física";
        }
        return null;
    }
}
