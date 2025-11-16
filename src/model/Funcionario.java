package model;

public class Funcionario {
    private String CPF;
    private String nome;
    private String senha;

    public Funcionario(String CPF, String nome, String senha) {
        this.CPF = CPF;
        this.nome = nome;
        this.senha = senha;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
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

    @Override
    public String toString() {
        return "Funcionario{" +
                "CPF='" + CPF + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}