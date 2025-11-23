package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private float quilometragem;
    private String categoria;
    private boolean disponibilidade;
    private Aluga a1;
    private Manutencao m1;

    public Veiculo(String placa, String marca, String modelo, int ano, float quilometragem,
                   String categoria, boolean disponibilidade) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.categoria = categoria;
        this.disponibilidade = disponibilidade;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public float getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(float quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Aluga getAluga() { return a1; }

    public void setAluga(Aluga a1) { this.a1 = a1; }

    public Manutencao getManutencao(){ return m1; }

    public void setManutencao(Manutencao m1) { this.m1 = m1; }

    @Override
    public String toString() {
        return marca + " " + modelo + " (" + placa + ")";
    }
}