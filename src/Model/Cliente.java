package Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int CPF;
    private String nome;
    private ArrayList formaPagamento;
    private ArrayList<Aluga> historicoLocacoes;
    private ArrayList telefone;

    public Cliente(int CPF, String nome, ArrayList formaPagamento, ArrayList<Aluga> historicoLocacoes, ArrayList telefone) {
        this.CPF = CPF;
        this.nome = nome;
        this.formaPagamento = formaPagamento;
        this.historicoLocacoes = historicoLocacoes;
        this.telefone = telefone;
    }

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

    public ArrayList getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(ArrayList formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public ArrayList<Aluga> getHistoricoLocacoes() {
        return historicoLocacoes;
    }

    public void setHistoricoLocacoes(ArrayList<Aluga> historicoLocacoes) {
        this.historicoLocacoes = historicoLocacoes;
    }

    public ArrayList getTelefone() {
        return telefone;
    }

    public void setTelefone(ArrayList telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "CPF=" + CPF +
                ", nome='" + nome + '\'' +
                ", formaPagamento=" + formaPagamento +
                ", historicoLocacoes=" + historicoLocacoes +
                ", telefone=" + telefone +
                '}';
    }
}