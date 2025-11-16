package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Aluga {
    private LocalDate dataAluguel;
    private LocalDate dataDevolucao;
    private float valor;
    private String formaPagamento;
    private float quilometragemFinal;
    private String danos;
    private Cliente c1;
    private Veiculo v1;

    public Aluga(LocalDate dataAluguel, LocalDate dataDevolucao, float valor, String formaPagamento) {
        this.dataAluguel = dataAluguel;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDate dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public float getQuilometragemFinal() {
        return quilometragemFinal;
    }

    public void setQuilometragemFinal(float quilometragemFinal) {
        this.quilometragemFinal = quilometragemFinal;
    }

    public String getDanos() { return danos; }

    public void setDanos(String danos) { this.danos = danos; }

    public Cliente getCliente() { return c1; }

    public void setCliente(Cliente c1) { this.c1 = c1; }

    public Veiculo getVeiculo() { return v1; }

    public void setVeiculo(Veiculo v1) { this.v1 = v1; }

    @Override
    public String toString() {
        return "Aluga{" +
                "dataAluguel=" + dataAluguel +
                ", dataDevolucao=" + dataDevolucao +
                ", valor=" + valor +
                ", formaPagamento='" + formaPagamento + '\'' +
                '}';
    }
}