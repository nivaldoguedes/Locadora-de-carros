package model;
import java.util.ArrayList;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private float quilometragem;
    private String categoria;
    private boolean disponibilidade;
    private ArrayList<Manutencao> manutencao;
    private ArrayList<Devolucao> devolucao;
    private ArrayList<Aluga> aluga;
    private ArrayList<Reserva> reserva;

    public Veiculo(String placa, String marca, String modelo, int ano, float quilometragem,
                   String categoria, boolean disponibilidade, ArrayList<Manutencao> manutencao,
                   ArrayList<Devolucao> devolucao, ArrayList<Aluga> aluga, ArrayList<Reserva> reserva) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.categoria = categoria;
        this.disponibilidade = disponibilidade;
        this.manutencao = manutencao;
        this.devolucao = devolucao;
        this.aluga = aluga;
        this.reserva = reserva;
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

    public ArrayList<Manutencao> getManutencao() {
        return manutencao;
    }

    public void setManutencao(ArrayList<Manutencao> manutencao) {
        this.manutencao = manutencao;
    }

    public ArrayList<Devolucao> getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(ArrayList<Devolucao> devolucao) {
        this.devolucao = devolucao;
    }

    public ArrayList<Aluga> getAluga() {
        return aluga;
    }

    public void setAluga(ArrayList<Aluga> aluga) {
        this.aluga = aluga;
    }

    public ArrayList<Reserva> getReserva() {
        return reserva;
    }

    public void setReserva(ArrayList<Reserva> reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", quilometragem=" + quilometragem +
                ", categoria='" + categoria + '\'' +
                ", disponibilidade=" + disponibilidade +
                ", manutencao=" + manutencao +
                ", devolucao=" + devolucao +
                ", aluga=" + aluga +
                ", reserva=" + reserva +
                '}';
    }
}