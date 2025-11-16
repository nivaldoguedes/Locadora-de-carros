package model;
import java.time.LocalDate;

public class Manutencao {
    private LocalDate data;
    private float custo;
    private String detalhesManutencao;

    public Manutencao(LocalDate data, float custo, String detalhesManutencao) {
        this.data = data;
        this.custo = custo;
        this.detalhesManutencao = detalhesManutencao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public String getDetalhesManutencao() {
        return detalhesManutencao;
    }

    public void setDetalhesManutencao(String detalhesManutencao) {
        this.detalhesManutencao = detalhesManutencao;
    }

    @Override
    public String toString() {
        return "Manutencao{" +
                "data=" + data +
                ", custo=" + custo +
                ", detalhesManutencao='" + detalhesManutencao + '\'' +
                '}';
    }
}