//package model;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class Reserva {
//    private LocalDate dataReserva;
//    private LocalDate dataDevolucao;
//    private float valor;
//    private Cliente c1;
//    private Veiculo v1;
//
//    public Reserva(LocalDate dataReserva, LocalDate dataDevolucao, float valor) {
//        super();
//        this.dataReserva = dataReserva;
//        this.dataDevolucao = dataDevolucao;
//        this.valor = valor;
//    }
//
//    public LocalDate getDataReserva() {
//        return dataReserva;
//    }
//
//    public void setDataReserva(LocalDate dataReserva) {
//        this.dataReserva = dataReserva;
//    }
//
//    public LocalDate getDataDevolucao() { return dataDevolucao; }
//
//    public void setDataDevolucao(LocalDate dataDevolucao) {
//        this.dataDevolucao = dataDevolucao;
//    }
//
//    public float getValor() { return valor; }
//
//    public void setValor(float valor) { this.valor = valor; }
//
//    public Cliente getCliente() { return c1; }
//
//    public void setCliente(Cliente c1) { this.c1 = c1; }
//
//    public Veiculo getVeiculo() { return v1; }
//
//    public void setVeiculo(Veiculo v1) { this.v1 = v1; }
//
//    @Override
//    public String toString() {
//        return "Reserva{" +
//                "dataReserva=" + dataReserva +
//                ", dataDevolucao=" + dataDevolucao +
//                '}';
//    }
//}