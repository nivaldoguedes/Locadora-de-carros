package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reserva {
    private LocalDate dataReserva;
    private LocalDate dataInicioAluguel;
    private float valor;
    private Cliente c1;
    private Veiculo v1;

    public Reserva(LocalDate dataReserva, LocalDate dataInicioAluguel, float valor) {
        this.dataReserva = dataReserva;
        this.dataInicioAluguel = dataInicioAluguel;
        this.valor = valor;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDate getDataInicioAluguel() { return dataInicioAluguel; }

    public void setDataInicioAluguel(LocalDate dataInicioAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
    }

    public Cliente getCliente() { return c1; }

    public void setCliente(Cliente c1) { this.c1 = c1; }

    public Veiculo getVeiculo() { return v1; }

    public void setVeiculo(Veiculo v1) { this.v1 = v1; }

    @Override
    public String toString() {
        return "Reserva{" +
                "dataReserva=" + dataReserva +
                ", dataInicioAluguel=" + dataInicioAluguel +
                '}';
    }
}