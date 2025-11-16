package model;

import java.time.LocalDateTime;

public class Reserva {
    private LocalDateTime dataReserva;
    private LocalDateTime dataDevolucao;

    public Reserva(LocalDateTime dataReserva, LocalDateTime dataDevolucao) {
        this.dataReserva = dataReserva;
        this.dataDevolucao = dataDevolucao;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "dataReserva=" + dataReserva +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }
}