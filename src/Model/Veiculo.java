package Model;
import java.util.List;

public class Veiculo {
    String placa;
    String marca;
    String modelo;
    int ano;
    float quilometragem;
    String categoria;
    boolean disponibilidade;
    List<Manutencao> manutencao;
    List<Devolucao> devolucao;
    List<Aluga> aluga;
    List<Reserva> reserva;

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

    public List<Manutencao> getManutencao() {
        return manutencao;
    }

    public void setManutencao(List<Manutencao> manutencao) {
        this.manutencao = manutencao;
    }

    public List<Devolucao> getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(List<Devolucao> devolucao) {
        this.devolucao = devolucao;
    }

    public List<Aluga> getAluga() {
        return aluga;
    }

    public void setAluga(List<Aluga> aluga) {
        this.aluga = aluga;
    }

    public List<Reserva> getReserva() {
        return reserva;
    }

    public void setReserva(List<Reserva> reserva) {
        this.reserva = reserva;
    }
}
