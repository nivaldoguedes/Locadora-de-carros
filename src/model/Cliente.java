package model;

public class Cliente {
    private String CPF;
    private String nome;
    private String formaPagamento;
    private String telefone;

    public Cliente(String CPF, String nome, String formaPagamento, String telefone) {
        this.CPF = CPF;
        this.nome = nome;
        this.formaPagamento = formaPagamento;
        this.telefone = telefone;
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

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "CPF=" + CPF +
                ", nome='" + nome + '\'' +
                ", formaPagamento=" + formaPagamento +
                ", telefone=" + telefone +
                '}';
    }
}