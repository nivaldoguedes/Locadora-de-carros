package Model;

import java.util.List;

public class Cliente {
    int CPF;
    String nome;
    List formaPagamento;
    List historicoLocacoes;
    List<Telefone> telefone;

    public int getCPF() {
        return CPF;
    }

    public void setCPF(int CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(List formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List getHistoricoLocacoes() {
        return historicoLocacoes;
    }

    public void setHistoricoLocacoes(List historicoLocacoes) {
        this.historicoLocacoes = historicoLocacoes;
    }

    public List<Telefone> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }
}